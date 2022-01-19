package ch.heigvd.amt.resource.exception_mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@Provider
public class NoSuchKeyExceptionMapper implements ExceptionMapper<NoSuchKeyException> {

  @Override
  public Response toResponse(NoSuchKeyException e) {
    return Response.status(Status.NOT_FOUND).entity("The image was not found").build();
  }
}
