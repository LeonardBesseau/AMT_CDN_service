package ch.heigvd.amt.resource;

import ch.heigvd.amt.service.ImageService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@ApplicationScoped
@Path("/image")
public class ImageResource {

  private final ImageService imageService;

  @Inject
  public ImageResource(ImageService imageService) {
    this.imageService = imageService;
  }

  @GET
  @Path("/{id}")
  @PermitAll
  @Produces("image/png")
  public InputStream get(@PathParam("id") UUID id) {
    return imageService.getImage(id);
  }

  @GET
  @Path("/default")
  @PermitAll
  @Produces("image/png")
  public InputStream getDefault() {
    return imageService.getDefaultImage();
  }

  @POST
  @RolesAllowed("ADMIN")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.TEXT_HTML)
  public Response add(
      @MultipartForm MultipartFormDataInput input, @QueryParam("default") boolean def)
      throws IOException {
    Map<String, List<InputPart>> uploadForm = input.getFormDataMap();

    List<InputPart> inputParts = uploadForm.get("image");
    if (inputParts.isEmpty()) {
      return Response.status(Status.BAD_REQUEST).entity("No file uploaded").build();
    }

    InputPart inputPart = inputParts.get(0);

    byte[] bytes = extractImageData(inputPart);
    if (bytes.length == 0) {
      return Response.status(Status.BAD_REQUEST).entity("The image cannot be empty").build();
    }

    UUID id = def ? imageService.setDefaultImage(bytes) : imageService.addImage(bytes);

    return Response.status(Status.CREATED).entity(id.toString()).build();
  }

  public static byte[] extractImageData(InputPart inputPart) throws IOException {
    InputStream inputStream = inputPart.getBody(InputStream.class, null);
    return IOUtils.toByteArray(inputStream);
  }
}
