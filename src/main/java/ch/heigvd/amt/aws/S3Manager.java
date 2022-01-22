package ch.heigvd.amt.aws;

import java.io.InputStream;
import java.nio.file.Path;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/** Class that handles all the actions with the S3 database */
public class S3Manager {

  // Contains the bucket name
  private final String bucketName;
  // Contains the connection to the S3 service
  private final S3Client s3Client;

  /**
   * Constructor of the S3Manager.
   *
   * @param s3client the client that is used to connect to the S3 instance
   * @param bucketName the name of the bucket where the files are stored or downloaded
   */
  public S3Manager(S3Client s3client, String bucketName) {
    this.s3Client = s3client;
    this.bucketName = bucketName;
  }

  /**
   * Uploads an inputStream in the S3.
   *
   * @param key the id that must be set to the file in the S3
   * @param inputStream the inputStream to upload
   * @param length the length of the InputStream
   */
  public void upload(String key, InputStream inputStream, long length) {
    // Prepare the request
    PutObjectRequest objectRequest =
        PutObjectRequest.builder().bucket(this.bucketName).key(key).build();
    // Upload the file to the s3 bucket
    this.s3Client.putObject(objectRequest, RequestBody.fromInputStream(inputStream, length));
  }

  /**
   * Uploads a file to the S3.
   *
   * @param key the id that must be set to the file in the S3
   * @param path the file to upload
   */
  public void upload(String key, Path path) {
    // Prepare the request
    PutObjectRequest objectRequest =
        PutObjectRequest.builder().bucket(this.bucketName).key(key).build();
    // Upload the file to the s3 bucket
    this.s3Client.putObject(objectRequest, RequestBody.fromFile(path));
  }

  /**
   * Gets an object in a S3 server and saves it in the file passed in parameter.
   *
   * @param key the id of the file to download
   * @param path the path of the file where the downloaded content is stored
   */
  public void download(String key, Path path) {
    // Prepare the request
    GetObjectRequest objectRequest =
        GetObjectRequest.builder().key(key).bucket(this.bucketName).build();
    // Send the request to s3 and get the file
    this.s3Client.getObject(objectRequest, path);
  }

  /**
   * Gets an object in a S3 server and return the result as an InputStream.
   *
   * @param key the id of the file that we want
   * @return an InputStream that is the content of the file
   */
  public InputStream download(String key) {
    // Prepare the request
    GetObjectRequest objectRequest =
        GetObjectRequest.builder().key(key).bucket(this.bucketName).build();
    // Send the request to s3 and get the file
    return this.s3Client.getObject(objectRequest);
  }
}
