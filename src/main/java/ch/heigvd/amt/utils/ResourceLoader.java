package ch.heigvd.amt.utils;

import com.google.common.io.Resources;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/** Resource loader. used for loading data from resources file */
public class ResourceLoader {

  /**
   * Load a resource from the resource folder
   *
   * @param resource The path to the resource (Root is the resource folder)
   * @return the content of the file
   */
  public static String loadResource(String resource) {
    try {
      return Resources.toString(Resources.getResource(resource), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private ResourceLoader() {}
}
