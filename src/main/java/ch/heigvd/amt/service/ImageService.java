package ch.heigvd.amt.service;

import ch.heigvd.amt.aws.S3Manager;
import ch.heigvd.amt.database.UpdateHandler;
import ch.heigvd.amt.utils.ResourceLoader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import org.jdbi.v3.core.Jdbi;

@ApplicationScoped
public class ImageService {

  private final UpdateHandler updateHandler;
  private final S3Manager s3Manager;
  private final Jdbi jdbi;

  @Inject
  public ImageService(UpdateHandler updateHandler, S3Manager s3Manager, Jdbi jdbi) {
    this.updateHandler = updateHandler;
    this.s3Manager = s3Manager;
    this.jdbi = jdbi;
  }

  /**
   * Get an image with the given id
   *
   * @param id the id of the image
   * @return a stream of the image
   */
  public InputStream getImage(UUID id) {
    return s3Manager.download(String.valueOf(id));
  }

  /**
   * Add an image to S3
   *
   * @param data the data of the image
   * @return the id associated with the image
   */
  public UUID addImage(byte[] data) {
    UUID uuid = UUID.randomUUID();
    s3Manager.upload(String.valueOf(uuid), new ByteArrayInputStream(data), data.length);
    return uuid;
  }

  /**
   * Set the data for the default image
   *
   * @param data the data of the image
   * @return the id associated with the image
   */
  public UUID setDefaultImage(byte[] data) {
    UUID id = addImage(data);
    jdbi.useHandle(
        handle ->
            handle
                .createUpdate(ResourceLoader.loadResource("sql/setDefaultImage.sql"))
                .bind("id", id)
                .execute());
    return id;
  }

  /**
   * Get the default image
   *
   * @return a stream of the image
   */
  public InputStream getDefaultImage() {
    Optional<UUID> image =
        jdbi.withHandle(
            handle ->
                handle
                    .createQuery(ResourceLoader.loadResource("sql/getDefaultImage.sql"))
                    .mapTo(UUID.class)
                    .findOne());
    if (image.isEmpty()) {
      throw new NotFoundException();
    }
    return s3Manager.download(String.valueOf(image.get()));
  }
}
