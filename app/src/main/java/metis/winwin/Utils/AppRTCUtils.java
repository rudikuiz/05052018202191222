/*
 *  Copyright 2014 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package metis.winwin.Utils;

/**
 * AppRTCUtils provides helper functions for managing thread safety.
 */
public final class AppRTCUtils {

  private AppRTCUtils() {
  }

  /**
   * NonThreadSafe is a helper class used to help verify that methods of a
   * class are called from the same thread.
   */
  public static class NonThreadSafe {
    private final Long threadId;

    public NonThreadSafe() {
      // Store thread ID of the creating thread.
      threadId = Thread.currentThread().getId();
    }

   /** Checks if the method is called on the valid/creating thread. */
    public boolean calledOnValidThread() {
       return threadId.equals(Thread.currentThread().getId());
    }
  }

  /** Helper method which throws an exception  when an assertion has failed. */
  public static void assertIsTrue(boolean condition) {
    if (!condition) {
      throw new AssertionError("Expected condition to be true");
    }
  }

  /** Helper method for building a string of thread information.*/
  public static String getThreadInfo() {
    return "@[name=" + Thread.currentThread().getName()
        + ", id=" + Thread.currentThread().getId() + "]";
  }

  /** Information about the current build, taken from system properties. */
  public static void logDeviceInfo(String tag) {
    /*AndLog.ShowLog(tag, "Android SDK: " + Build.VERSION.SDK_INT + ", "
        + "Release: " + Build.VERSION.RELEASE + ", "
        + "Brand: " + Build.BRAND + ", "
        + "Device: " + Build.DEVICE + ", "
        + "Id: " + Build.ID + ", "
        + "Hardware: " + Build.HARDWARE + ", "
        + "Manufacturer: " + Build.MANUFACTURER + ", "
        + "Model: " + Build.MODEL + ", "
        + "Product: " + Build.PRODUCT);*/
  }
}
