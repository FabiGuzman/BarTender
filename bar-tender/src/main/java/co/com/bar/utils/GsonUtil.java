package co.com.bar.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

  private GsonUtil() {
  }

  public static <T> String toString(final T object) {
    final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(object);

  }

}
