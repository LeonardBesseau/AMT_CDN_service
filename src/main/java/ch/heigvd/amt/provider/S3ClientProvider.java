package ch.heigvd.amt.provider;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Singleton
public class S3ClientProvider {

  @Inject
  @ConfigProperty(name = "aws.region")
  String region;

  @Produces
  public S3Client getS3Client() {
    return S3Client.builder().region(Region.of(region)).build();
  }
}
