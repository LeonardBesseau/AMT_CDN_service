/*
 * Copyright (C) 2021 Heig-vd
 *
 * Licensed under the “Commons Clause” License Condition v1.0. You may obtain a copy of the License at
 *
 * https://github.com/heigvd-software-engineering/netscan/blob/main/LICENSE
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package ch.heivd.amt.aws;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.heigvd.amt.aws.S3Manager;
import ch.heivd.amt.database.PostgisResource;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@QuarkusTest
@QuarkusTestResource(PostgisResource.class)
class S3ManagerTest {

  // The key name of the files created by the tests
  private static final String TEST_KEY = "testFile";

  @Inject S3Manager s3Manager;

  @Inject S3Client s3Client;

  @ConfigProperty(name = "aws.bucketName")
  String bucketName;

  @Test
  @Tag("testAWS")
  void uploadWithFiles() throws IOException {
    // File to upload
    Path path = Paths.get("src/test/resources/S3FileTest.txt");
    // File containing the result of a download
    Path resultPath = Paths.get("src/test/resources/result.txt");
    // Upload the file in the s3 bucket
    s3Manager.upload(TEST_KEY, path);
    // Download the file and verify that the content of both files is equal
    s3Manager.download(TEST_KEY, resultPath);
    assertEquals(Files.readString(path), Files.readString(resultPath));
    assertTrue(Files.exists(resultPath));
    Files.delete(resultPath);
    // Assert that the deletion went well.
    assertFalse(Files.exists(resultPath));
  }

  @Test
  @Tag("testAWS")
  void uploadWithInputStream() throws IOException {
    String str = "Hi i'm testing inputStream";
    // Transform the string into an InputStream
    ByteArrayInputStream bufferedInputStream =
        new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    s3Manager.upload(TEST_KEY, bufferedInputStream, str.getBytes(StandardCharsets.UTF_8).length);
    InputStream inputStream = s3Manager.download(TEST_KEY);
    // Verify that the value read by the download is equal
    assertEquals(str, CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8)));
  }

  @AfterEach
  void deleteTestFile() {
    // Delete the file created by the test
    s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(TEST_KEY).build());
  }
}
