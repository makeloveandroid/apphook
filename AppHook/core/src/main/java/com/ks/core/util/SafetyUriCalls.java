package com.ks.core.util;

import android.net.Uri;

import java.io.File;
import java.util.List;
import java.util.Set;

public class SafetyUriCalls {
  private SafetyUriCalls() {}

  /**
   * Returns a URI for a given file using {@link Uri#fromFile(File)}.
   *
   * @param file a file with a valid path
   * @return the URI
   */
  public static Uri getUriFromFile(File file) {
    try {
      return Uri.fromFile(file);
    } catch (NullPointerException e) {
      handleException(e);
      return null;
    }
  }

  /**
   * Returns a URI for a given file using {@link Uri#parse(String)}.
   *
   * @param string
   * @return the URI
   */
  public static Uri parseUriFromString(String string) {
    try {
      return Uri.parse(string);
    } catch (NullPointerException e) {
      handleException(e);
      return null;
    }
  }

  /**
   * Returns a URI for a given file using {@link Uri#fromParts(String, String, String)}.
   *
   * @return the URI
   */
  public static Uri getUriFromParts(String scheme, String ssp, String fragment) {
    try {
      return Uri.fromParts(scheme, ssp, fragment);
    } catch (NullPointerException e) {
      handleException(e);
      return null;
    }
  }

  /**
   * Returns a URI for a given file using {@link Uri#getQueryParameterNames()}.
   *
   * @return the URI
   */
  public static Set<String> getQueryParameterNamesFromUri(Uri uri) {
    try {
      return uri.getQueryParameterNames();
    } catch (UnsupportedOperationException e) {
      handleException(e);
      return null;
    }
  }

  /**
   * call {@link Uri#getQueryParameters(String)} inner
   *
   * @return
   */
  public static List<String> getQueryParametersFromUri(Uri uri, String key) {
    try {
      return uri.getQueryParameters(key);
    } catch (NullPointerException e) {
      handleException(e);
      return null;
    } catch (UnsupportedOperationException e) {
      handleException(e);
      return null;
    }
  }

  /**
   * call {@link Uri#getQueryParameter(String)} inner
   *
   * @return
   */
  public static String getQueryParameterFromUri(Uri uri, String key) {
    try {
      return uri.getQueryParameter(key);
    } catch (NullPointerException e) {
      handleException(e);
      return null;
    } catch (UnsupportedOperationException e) {
      handleException(e);
      return null;
    }
  }

  private static void handleException(RuntimeException e) {
    throw e;
  }
}
