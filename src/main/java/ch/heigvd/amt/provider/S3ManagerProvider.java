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
