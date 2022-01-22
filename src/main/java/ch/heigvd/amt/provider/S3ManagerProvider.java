package ch.heigvd.amt.provider;

import ch.heigvd.amt.aws.S3Manager;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.S3Client;

@Singleton
public class S3ManagerProvider {

  @Inject S3Client client;

  @ConfigProperty(name = "aws.bucketName")
  String bucketName;

  @Produces
  public S3Manager getS3Manager() {
    return new S3Manager(client, bucketName);
  }
}
