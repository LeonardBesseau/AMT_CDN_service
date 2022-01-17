package ch.heigvd.amt.resource;

import ch.heigvd.amt.service.ImageService;
import java.io.InputStream;
import java.util.UUID;
import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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
}
