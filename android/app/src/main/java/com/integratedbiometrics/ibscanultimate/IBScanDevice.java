package com.integratedbiometrics.ibscanultimate;

import android.graphics.Bitmap;
import android.util.Log;
import com.integratedbiometrics.ibscancommon.IBCommon;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class IBScanDevice {
  public static final long IBSU_FINGER_ALL = 1023L;
  
  public static final long IBSU_FINGER_BOTH_THUMBS = 48L;
  
  public static final long IBSU_FINGER_LEFT_HAND = 15L;
  
  public static final long IBSU_FINGER_LEFT_INDEX = 8L;
  
  public static final long IBSU_FINGER_LEFT_LITTLE = 1L;
  
  public static final long IBSU_FINGER_LEFT_LITTLE_RING = 3L;
  
  public static final long IBSU_FINGER_LEFT_MIDDLE = 4L;
  
  public static final long IBSU_FINGER_LEFT_MIDDLE_INDEX = 12L;
  
  public static final long IBSU_FINGER_LEFT_RING = 2L;
  
  public static final long IBSU_FINGER_LEFT_THUMB = 16L;
  
  public static final long IBSU_FINGER_NONE = 0L;
  
  public static final long IBSU_FINGER_RIGHT_HAND = 960L;
  
  public static final long IBSU_FINGER_RIGHT_INDEX = 64L;
  
  public static final long IBSU_FINGER_RIGHT_INDEX_MIDDLE = 192L;
  
  public static final long IBSU_FINGER_RIGHT_LITTLE = 512L;
  
  public static final long IBSU_FINGER_RIGHT_MIDDLE = 128L;
  
  public static final long IBSU_FINGER_RIGHT_RING = 256L;
  
  public static final long IBSU_FINGER_RIGHT_RING_LITTLE = 768L;
  
  public static final long IBSU_FINGER_RIGHT_THUMB = 32L;
  
  public static final long IBSU_LED_F_BLINK_GREEN = 268435456L;
  
  public static final long IBSU_LED_F_BLINK_RED = 536870912L;
  
  public static final long IBSU_LED_F_LEFT_INDEX_GREEN = 4194304L;
  
  public static final long IBSU_LED_F_LEFT_INDEX_RED = 8388608L;
  
  public static final long IBSU_LED_F_LEFT_LITTLE_GREEN = 16777216L;
  
  public static final long IBSU_LED_F_LEFT_LITTLE_RED = 33554432L;
  
  public static final long IBSU_LED_F_LEFT_MIDDLE_GREEN = 1048576L;
  
  public static final long IBSU_LED_F_LEFT_MIDDLE_RED = 2097152L;
  
  public static final long IBSU_LED_F_LEFT_RING_GREEN = 67108864L;
  
  public static final long IBSU_LED_F_LEFT_RING_RED = 134217728L;
  
  public static final long IBSU_LED_F_LEFT_THUMB_GREEN = 65536L;
  
  public static final long IBSU_LED_F_LEFT_THUMB_RED = 131072L;
  
  public static final long IBSU_LED_F_PROGRESS_LEFT_HAND = 32L;
  
  public static final long IBSU_LED_F_PROGRESS_RIGHT_HAND = 128L;
  
  public static final long IBSU_LED_F_PROGRESS_ROLL = 16L;
  
  public static final long IBSU_LED_F_PROGRESS_TWO_THUMB = 64L;
  
  public static final long IBSU_LED_F_RIGHT_INDEX_GREEN = 4096L;
  
  public static final long IBSU_LED_F_RIGHT_INDEX_RED = 8192L;
  
  public static final long IBSU_LED_F_RIGHT_LITTLE_GREEN = 1024L;
  
  public static final long IBSU_LED_F_RIGHT_LITTLE_RED = 2048L;
  
  public static final long IBSU_LED_F_RIGHT_MIDDLE_GREEN = 16384L;
  
  public static final long IBSU_LED_F_RIGHT_MIDDLE_RED = 1073741824L;
  
  public static final long IBSU_LED_F_RIGHT_RING_GREEN = 256L;
  
  public static final long IBSU_LED_F_RIGHT_RING_RED = 512L;
  
  public static final long IBSU_LED_F_RIGHT_THUMB_GREEN = 262144L;
  
  public static final long IBSU_LED_F_RIGHT_THUMB_RED = 524288L;
  
  public static final int LED_INIT_BLUE = 1;
  
  public static final int LED_NONE = 0;
  
  public static final int LED_SCAN_CURVE_BLUE = 64;
  
  public static final int LED_SCAN_CURVE_GREEN = 32;
  
  public static final int LED_SCAN_CURVE_RED = 16;
  
  public static final int LED_SCAN_GREEN = 2;
  
  public static final int MAX_CONTRAST_VALUE = 34;
  
  private static int METHOD_STACK_INDEX = 0;
  
  public static final int MIN_CONTRAST_VALUE = 0;
  
  public static final int OPTION_AUTO_CAPTURE = 2;
  
  public static final int OPTION_AUTO_CONTRAST = 1;
  
  public static final int OPTION_IGNORE_FINGER_COUNT = 4;
  
  private final long m_handleNative;
  
  private boolean m_isOpened;
  
  private IBScanDeviceListener m_listener = null;
  
  static {
    int i = 0;
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    int k = arrayOfStackTraceElement.length;
    int j = 0;
    while (true) {
      int m = i;
      if (j < k) {
        StackTraceElement stackTraceElement = arrayOfStackTraceElement[j];
        i++;
        if (stackTraceElement.getClassName().equals(IBScanDevice.class.getName())) {
          m = i;
        } else {
          j++;
          continue;
        } 
      } 
      METHOD_STACK_INDEX = m;
      System.loadLibrary("usb");
      System.loadLibrary("ibscanultimate");
      System.loadLibrary("ibscanultimatejni");
      return;
    } 
  }
  
  protected IBScanDevice(long paramLong) {
    this.m_handleNative = paramLong;
    this.m_isOpened = true;
  }
  
  private native int SaveBitmapImageNative(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, NativeError paramNativeError);
  
  private native int SaveJP2ImageNative(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, int paramInt4, NativeError paramNativeError);
  
  private native int SavePngImageNative(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, NativeError paramNativeError);
  
  private native int SetEncryptionKeyNative(byte[] paramArrayOfbyte, int paramInt, NativeError paramNativeError);
  
  private native int addFingerImageNative(ImageData paramImageData, long paramLong, int paramInt, boolean paramBoolean, NativeError paramNativeError);
  
  private native void beginCaptureImageNative(int paramInt1, int paramInt2, int paramInt3, NativeError paramNativeError);
  
  private native int calculateNfiqScoreNative(ImageData paramImageData, NativeError paramNativeError);
  
  private void callbackDeviceAcquisitionBegun(int paramInt) {
    ImageType imageType;
    if (this.m_listener != null) {
      imageType = ImageType.fromCode(paramInt);
      if (imageType == null) {
        logPrintError(getMethodName() + ": unrecognized imageType code (" + imageType + ") returned from native code");
        return;
      } 
    } else {
      return;
    } 
    this.m_listener.deviceAcquisitionBegun(this, imageType);
  }
  
  private void callbackDeviceAcquisitionCompleted(int paramInt) {
    ImageType imageType;
    if (this.m_listener != null) {
      imageType = ImageType.fromCode(paramInt);
      if (imageType == null) {
        logPrintError(getMethodName() + ": unrecognized imageType code (" + imageType + ") returned from native code");
        return;
      } 
    } else {
      return;
    } 
    this.m_listener.deviceAcquisitionCompleted(this, imageType);
  }
  
  private void callbackDeviceCommunicationBroken() {
    if (this.m_listener != null)
      this.m_listener.deviceCommunicationBroken(this); 
  }
  
  private void callbackDeviceFingerCountChanged(int paramInt) {
    FingerCountState fingerCountState;
    if (this.m_listener != null) {
      fingerCountState = FingerCountState.fromCode(paramInt);
      if (fingerCountState == null) {
        logPrintError(getMethodName() + ": unrecognized fingerState code (" + paramInt + ") returned from native code");
        return;
      } 
    } else {
      return;
    } 
    this.m_listener.deviceFingerCountChanged(this, fingerCountState);
  }
  
  private void callbackDeviceFingerQualityChanged(int[] paramArrayOfint) {
    if (this.m_listener != null && paramArrayOfint != null) {
      boolean bool = true;
      FingerQualityState[] arrayOfFingerQualityState = new FingerQualityState[paramArrayOfint.length];
      int i = 0;
      while (true) {
        boolean bool1 = bool;
        if (i < paramArrayOfint.length) {
          arrayOfFingerQualityState[i] = FingerQualityState.fromCode(paramArrayOfint[i]);
          if (arrayOfFingerQualityState[i] == null) {
            logPrintError(getMethodName() + ": unrecognized fingerQuality code (" + paramArrayOfint[i] + ") returned from native code");
            bool1 = false;
          } else {
            i++;
            continue;
          } 
        } 
        if (bool1)
          this.m_listener.deviceFingerQualityChanged(this, arrayOfFingerQualityState); 
        return;
      } 
    } 
  }
  
  private void callbackDeviceImagePreviewAvailable(ImageData paramImageData) {
    if (this.m_listener != null)
      this.m_listener.deviceImagePreviewAvailable(this, paramImageData); 
  }
  
  private void callbackDeviceImageResultAvailable(ImageData paramImageData, int paramInt, ImageData[] paramArrayOfImageData) {
    ImageType imageType;
    if (this.m_listener != null) {
      imageType = ImageType.fromCode(paramInt);
      if (imageType == null) {
        logPrintError(getMethodName() + ": unrecognized imageType code (" + imageType + ") returned from native code");
        return;
      } 
    } else {
      return;
    } 
    this.m_listener.deviceImageResultAvailable(this, paramImageData, imageType, paramArrayOfImageData);
  }
  
  private void callbackDeviceImageResultExtendedAvailable(int paramInt1, ImageData paramImageData, int paramInt2, int paramInt3, ImageData[] paramArrayOfImageData, SegmentPosition[] paramArrayOfSegmentPosition) {
    IBScanException.Type type1;
    IBScanException iBScanException;
    ImageType imageType;
    IBScanException.Type type2 = null;
    if (this.m_listener != null) {
      imageType = ImageType.fromCode(paramInt2);
      if (imageType == null) {
        logPrintError(getMethodName() + ": unrecognized imageType code (" + imageType + ") returned from native code");
        return;
      } 
    } else {
      return;
    } 
    if (paramInt1 == 0) {
      type1 = null;
    } else {
      type1 = IBScanException.Type.fromCode(paramInt1);
    } 
    if (paramInt1 != 0 && type1 == null) {
      logPrintError(getMethodName() + ": unrecognized imageStatus code (" + paramInt1 + ") returned from native code");
      return;
    } 
    if (paramInt1 == 0) {
      type1 = type2;
    } else {
      iBScanException = new IBScanException(type1);
    } 
    this.m_listener.deviceImageResultExtendedAvailable(this, iBScanException, paramImageData, imageType, paramInt3, paramArrayOfImageData, paramArrayOfSegmentPosition);
  }
  
  private void callbackDevicePlatenStateChanged(int paramInt) {
    PlatenState platenState;
    if (this.m_listener != null) {
      platenState = PlatenState.fromCode(paramInt);
      if (platenState == null) {
        logPrintError(getMethodName() + ": unrecognized platenState code (" + paramInt + ") returned from native code");
        return;
      } 
    } else {
      return;
    } 
    this.m_listener.devicePlatenStateChanged(this, platenState);
  }
  
  private void callbackDevicePressedKeyButtons(int paramInt) {
    if (this.m_listener != null)
      this.m_listener.devicePressedKeyButtons(this, paramInt); 
  }
  
  private void callbackDeviceWarningReceived(int paramInt) {
    IBScanException.Type type;
    if (this.m_listener != null) {
      type = IBScanException.Type.fromCode(paramInt);
      if (type == null) {
        logPrintError(getMethodName() + ": unrecognized warning code (" + paramInt + ") returned from native code");
        return;
      } 
    } else {
      return;
    } 
    this.m_listener.deviceWarningReceived(this, new IBScanException(type));
  }
  
  private native void cancelCaptureImageNative(NativeError paramNativeError);
  
  private native Object[] captureImageExtendedNative(NativeError paramNativeError);
  
  private native void captureImageManuallyNative(NativeError paramNativeError);
  
  private native Object[] captureImageNative(NativeError paramNativeError);
  
  private native void closeNative(NativeError paramNativeError);
  
  private native void createBmpExNative(byte[] paramArrayOfbyte, Bitmap paramBitmap, NativeError paramNativeError);
  
  private native void enableEventNative(int paramInt, boolean paramBoolean, NativeError paramNativeError);
  
  private native int generateDisplayImageNative(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, byte paramByte, int paramInt5, int paramInt6, boolean paramBoolean, NativeError paramNativeError);
  
  private native int generateZoomOutImageExNative(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, byte paramByte, NativeError paramNativeError);
  
  private native Object[] getCombineImageExNative(ImageData paramImageData1, ImageData paramImageData2, int paramInt, NativeError paramNativeError);
  
  private native Object getCombineImageNative(ImageData paramImageData1, ImageData paramImageData2, int paramInt, NativeError paramNativeError);
  
  private native int getContrastNative(NativeError paramNativeError);
  
  private native Object[] getEnhancedImageReservedNative(String paramString, ImageData paramImageData, NativeError paramNativeError);
  
  private native long getLEDsNative(NativeError paramNativeError);
  
  private native int getLEOperationModeNative(NativeError paramNativeError);
  
  private static String getMethodName() {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    return (arrayOfStackTraceElement.length > METHOD_STACK_INDEX) ? arrayOfStackTraceElement[METHOD_STACK_INDEX].getMethodName() : "?";
  }
  
  private native int getOperableBeeperNative(NativeError paramNativeError);
  
  private native LedState getOperableLEDsNative(NativeError paramNativeError);
  
  private native int getPlatenStateAtCaptureNative(NativeError paramNativeError);
  
  private native String getPropertyNative(int paramInt, NativeError paramNativeError);
  
  private native Object[] getResultImageExtNative(int paramInt, NativeError paramNativeError);
  
  private native RollingData getRollingInfoNative(NativeError paramNativeError);
  
  private static void handleError(NativeError paramNativeError) throws IBScanException {
    if (paramNativeError.code != 0) {
      IBScanException.Type type2 = IBScanException.Type.fromCode(paramNativeError.code);
      IBScanException.Type type1 = type2;
      if (type2 == null) {
        logPrintError(getMethodName() + ": unrecognized error code (" + paramNativeError.code + ") returned from native code");
        type1 = IBScanException.Type.COMMAND_FAILED;
      } 
      throw new IBScanException(type1);
    } 
  }
  
  private native boolean isCaptureActiveNative(NativeError paramNativeError);
  
  private native boolean isCaptureAvailableNative(int paramInt1, int paramInt2, NativeError paramNativeError);
  
  private native long isFingerDuplicatedNative(ImageData paramImageData, long paramLong, int paramInt1, int paramInt2, NativeError paramNativeError);
  
  private native boolean isFingerTouchingNative(NativeError paramNativeError);
  
  private native boolean isValidFingerGeometryNative(ImageData paramImageData, long paramLong, int paramInt, NativeError paramNativeError);
  
  private static void logPrintError(String paramString) {
    Log.e("IBScanDevice", paramString);
  }
  
  private static void logPrintWarning(String paramString) {
    Log.w("IBScanDevice", paramString);
  }
  
  private native int removeFingerImageNative(long paramLong, NativeError paramNativeError);
  
  private native int setBeeperNative(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, NativeError paramNativeError);
  
  private native void setContrastNative(int paramInt, NativeError paramNativeError);
  
  private native void setLEDsNative(long paramLong, NativeError paramNativeError);
  
  private native void setLEOperationModeNative(int paramInt, NativeError paramNativeError);
  
  private native void setPropertyNative(int paramInt, String paramString, NativeError paramNativeError);
  
  private native void setPropertyReservedNative(String paramString1, int paramInt, String paramString2, NativeError paramNativeError);
  
  private native int wsqEncodeToFileNative(String paramString1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble, String paramString2, NativeError paramNativeError);
  
  public int SaveBitmapImage(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2) throws IBScanException {
    NativeError nativeError = new NativeError();
    paramInt1 = SaveBitmapImageNative(paramString, paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramDouble1, paramDouble2, nativeError);
    handleError(nativeError);
    return paramInt1;
  }
  
  public int SaveJP2Image(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, int paramInt4) throws IBScanException {
    NativeError nativeError = new NativeError();
    paramInt1 = SaveJP2ImageNative(paramString, paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramDouble1, paramDouble2, paramInt4, nativeError);
    handleError(nativeError);
    return paramInt1;
  }
  
  public int SavePngImage(String paramString, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2) throws IBScanException {
    NativeError nativeError = new NativeError();
    paramInt1 = SavePngImageNative(paramString, paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramDouble1, paramDouble2, nativeError);
    handleError(nativeError);
    return paramInt1;
  }
  
  public int SetEncryptionKey(byte[] paramArrayOfbyte, EncyptionMode paramEncyptionMode) throws IBScanException {
    NativeError nativeError = new NativeError();
    int i = SetEncryptionKeyNative(paramArrayOfbyte, paramEncyptionMode.toCode(), nativeError);
    handleError(nativeError);
    return i;
  }
  
  public int addFingerImage(ImageData paramImageData, long paramLong, ImageType paramImageType, boolean paramBoolean) throws IBScanException {
    NativeError nativeError = new NativeError();
    int i = addFingerImageNative(paramImageData, paramLong, paramImageType.toCode(), paramBoolean, nativeError);
    handleError(nativeError);
    return i;
  }
  
  public void beginCaptureImage(ImageType paramImageType, ImageResolution paramImageResolution, int paramInt) throws IBScanException {
    if (paramImageType == null) {
      logPrintWarning(getMethodName() + ": received null imageType");
      throw new IllegalArgumentException("Received null imageType");
    } 
    if (paramImageResolution == null) {
      logPrintWarning(getMethodName() + ": received null imageResolution");
      throw new IllegalArgumentException("Received null imageResolution");
    } 
    NativeError nativeError = new NativeError();
    beginCaptureImageNative(paramImageType.toCode(), paramImageResolution.toCode(), paramInt, nativeError);
    handleError(nativeError);
  }
  
  public int calculateNfiqScore(ImageData paramImageData) throws IBScanException {
    if (paramImageData == null) {
      logPrintWarning(getMethodName() + ": received null image");
      throw new IllegalArgumentException();
    } 
    NativeError nativeError = new NativeError();
    int i = calculateNfiqScoreNative(paramImageData, nativeError);
    handleError(nativeError);
    return i;
  }
  
  public void cancelCaptureImage() throws IBScanException {
    NativeError nativeError = new NativeError();
    cancelCaptureImageNative(nativeError);
    handleError(nativeError);
  }
  
  public Object[] captureImage() throws IBScanException {
    NativeError nativeError = new NativeError();
    Object[] arrayOfObject1 = captureImageNative(nativeError);
    handleError(nativeError);
    if (arrayOfObject1 == null || arrayOfObject1.length != 5 || arrayOfObject1[0] == null || arrayOfObject1[1] == null || arrayOfObject1[2] == null || arrayOfObject1[3] == null || arrayOfObject1[4] == null) {
      logPrintError(getMethodName() + ": null or invalid image information returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    Object[] arrayOfObject2 = new Object[5];
    int[] arrayOfInt = (int[])arrayOfObject1[4];
    ImageType imageType = ImageType.fromCode(((Integer)arrayOfObject1[1]).intValue());
    FingerCountState fingerCountState = FingerCountState.fromCode(((Integer)arrayOfObject1[3]).intValue());
    boolean bool = true;
    arrayOfObject2[0] = arrayOfObject1[0];
    arrayOfObject2[1] = imageType;
    arrayOfObject2[2] = arrayOfObject1[2];
    arrayOfObject2[3] = fingerCountState;
    FingerQualityState[] arrayOfFingerQualityState = new FingerQualityState[arrayOfInt.length];
    int i = 0;
    while (true) {
      boolean bool1 = bool;
      if (i < arrayOfInt.length) {
        arrayOfFingerQualityState[i] = FingerQualityState.fromCode(arrayOfInt[i]);
        if (arrayOfFingerQualityState[i] == null) {
          logPrintError(getMethodName() + ": unrecognized fingerQuality code (" + arrayOfInt[i] + ") returned from native code");
          bool1 = false;
        } else {
          i++;
          continue;
        } 
      } 
      arrayOfObject2[4] = arrayOfFingerQualityState;
      if (fingerCountState == null) {
        logPrintError(getMethodName() + ": unrecognized fingerCountState code (" + ((Integer)arrayOfObject1[3]).intValue() + ") returned from native code");
        nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
        handleError(nativeError);
        return arrayOfObject2;
      } 
      if (imageType == null) {
        logPrintError(getMethodName() + ": unrecognized imageType code (" + ((Integer)arrayOfObject1[1]).intValue() + ") returned from native code");
        nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
        handleError(nativeError);
        return arrayOfObject2;
      } 
      if (!bool1) {
        nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
        handleError(nativeError);
        return arrayOfObject2;
      } 
      return arrayOfObject2;
    } 
  }
  
  public Object[] captureImageExtended() throws IBScanException {
    IBScanException iBScanException;
    NativeError nativeError = new NativeError();
    Object[] arrayOfObject1 = captureImageExtendedNative(nativeError);
    handleError(nativeError);
    if (arrayOfObject1 == null || arrayOfObject1.length != 8 || arrayOfObject1[0] == null || arrayOfObject1[1] == null || arrayOfObject1[2] == null || arrayOfObject1[3] == null || arrayOfObject1[4] == null || arrayOfObject1[5] == null || arrayOfObject1[6] == null || arrayOfObject1[7] == null) {
      logPrintError(getMethodName() + ": null or invalid image information returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    Object[] arrayOfObject2 = new Object[8];
    int j = ((Integer)arrayOfObject1[0]).intValue();
    IBScanException.Type type = IBScanException.Type.fromCode(((Integer)arrayOfObject1[0]).intValue());
    if (type == null) {
      type = null;
    } else {
      iBScanException = new IBScanException(type);
    } 
    int[] arrayOfInt = (int[])arrayOfObject1[7];
    ImageType imageType = ImageType.fromCode(((Integer)arrayOfObject1[2]).intValue());
    FingerCountState fingerCountState = FingerCountState.fromCode(((Integer)arrayOfObject1[6]).intValue());
    boolean bool = true;
    arrayOfObject2[0] = iBScanException;
    arrayOfObject2[1] = arrayOfObject1[1];
    arrayOfObject2[2] = imageType;
    arrayOfObject2[3] = arrayOfObject1[3];
    arrayOfObject2[4] = arrayOfObject1[4];
    arrayOfObject2[5] = arrayOfObject1[5];
    arrayOfObject2[6] = fingerCountState;
    FingerQualityState[] arrayOfFingerQualityState = new FingerQualityState[arrayOfInt.length];
    int i = 0;
    while (true) {
      boolean bool1 = bool;
      if (i < arrayOfInt.length) {
        arrayOfFingerQualityState[i] = FingerQualityState.fromCode(arrayOfInt[i]);
        if (arrayOfFingerQualityState[i] == null) {
          logPrintError(getMethodName() + ": unrecognized fingerQuality code (" + arrayOfInt[i] + ") returned from native code");
          bool1 = false;
        } else {
          i++;
          continue;
        } 
      } 
      arrayOfObject2[7] = arrayOfFingerQualityState;
      if (fingerCountState == null) {
        logPrintError(getMethodName() + ": unrecognized fingerCountState code (" + ((Integer)arrayOfObject1[6]).intValue() + ") returned from native code");
        nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
        handleError(nativeError);
        return arrayOfObject2;
      } 
      if (imageType == null) {
        logPrintError(getMethodName() + ": unrecognized imageType code (" + ((Integer)arrayOfObject1[2]).intValue() + ") returned from native code");
        nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
        handleError(nativeError);
        return arrayOfObject2;
      } 
      if (iBScanException == null && j != 0) {
        logPrintError(getMethodName() + ": unrecognized imageStatus code (" + ((Integer)arrayOfObject1[0]).intValue() + ") returned from native code");
        nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
        handleError(nativeError);
        return arrayOfObject2;
      } 
      if (!bool1) {
        nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
        handleError(nativeError);
        return arrayOfObject2;
      } 
      return arrayOfObject2;
    } 
  }
  
  public void captureImageManually() throws IBScanException {
    NativeError nativeError = new NativeError();
    captureImageManuallyNative(nativeError);
    handleError(nativeError);
  }
  
  public void close() throws IBScanException {
    NativeError nativeError = new NativeError();
    closeNative(nativeError);
    handleError(nativeError);
    this.m_isOpened = false;
  }
  
  public void createBmpEx(byte[] paramArrayOfbyte, Bitmap paramBitmap) throws IBScanException {
    NativeError nativeError = new NativeError();
    createBmpExNative(paramArrayOfbyte, paramBitmap, nativeError);
    handleError(nativeError);
  }
  
  public void enableEvent(EventType paramEventType, boolean paramBoolean) throws IBScanException {
    if (paramEventType == null) {
      logPrintWarning(getMethodName() + ": received null event");
      throw new IllegalArgumentException("Received null event");
    } 
    NativeError nativeError = new NativeError();
    enableEventNative(paramEventType.toCode(), paramBoolean, nativeError);
    handleError(nativeError);
  }
  
  public int generateDisplayImage(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, byte paramByte, int paramInt5, int paramInt6, boolean paramBoolean) throws IBScanException {
    NativeError nativeError = new NativeError();
    paramInt1 = generateDisplayImageNative(paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4, paramByte, paramInt5, paramInt6, paramBoolean, nativeError);
    handleError(nativeError);
    return paramInt1;
  }
  
  public int generateZoomOutImageEx(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, byte paramByte) throws IBScanException {
    NativeError nativeError = new NativeError();
    paramInt1 = generateZoomOutImageExNative(paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4, paramByte, nativeError);
    handleError(nativeError);
    return paramInt1;
  }
  
  public Object getCombineImage(ImageData paramImageData1, ImageData paramImageData2, CombineImageWhichHand paramCombineImageWhichHand) throws IBScanException {
    if (paramImageData1 == null) {
      logPrintWarning(getMethodName() + ": received null ImageData1");
      throw new IllegalArgumentException("Received null ImageData1");
    } 
    if (paramImageData2 == null) {
      logPrintWarning(getMethodName() + ": received null ImageData2");
      throw new IllegalArgumentException("Received null ImageData2");
    } 
    NativeError nativeError = new NativeError();
    Object object = getCombineImageNative(paramImageData1, paramImageData2, paramCombineImageWhichHand.toCode(), nativeError);
    handleError(nativeError);
    return object;
  }
  
  public Object[] getCombineImageEx(ImageData paramImageData1, ImageData paramImageData2, CombineImageWhichHand paramCombineImageWhichHand) throws IBScanException {
    if (paramImageData1 == null) {
      logPrintWarning(getMethodName() + ": received null ImageData1");
      throw new IllegalArgumentException("Received null ImageData1");
    } 
    if (paramImageData2 == null) {
      logPrintWarning(getMethodName() + ": received null ImageData2");
      throw new IllegalArgumentException("Received null ImageData2");
    } 
    NativeError nativeError = new NativeError();
    Object[] arrayOfObject = getCombineImageExNative(paramImageData1, paramImageData2, paramCombineImageWhichHand.toCode(), nativeError);
    handleError(nativeError);
    if (arrayOfObject == null || arrayOfObject.length != 4 || arrayOfObject[0] == null || arrayOfObject[1] == null || arrayOfObject[2] == null || arrayOfObject[3] == null) {
      logPrintError(getMethodName() + ": null or invalid image information returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    return new Object[] { arrayOfObject[0], arrayOfObject[1], arrayOfObject[2], arrayOfObject[3] };
  }
  
  public int getContrast() throws IBScanException {
    NativeError nativeError = new NativeError();
    int i = getContrastNative(nativeError);
    handleError(nativeError);
    return i;
  }
  
  public Object[] getEnhancedImageReserved(String paramString, ImageData paramImageData) throws IBScanException {
    if (paramString == null) {
      logPrintWarning(getMethodName() + ": received null reservedKey");
      throw new IllegalArgumentException("Received null reservedKey");
    } 
    if (paramImageData == null) {
      logPrintWarning(getMethodName() + ": received null image");
      throw new IllegalArgumentException();
    } 
    NativeError nativeError = new NativeError();
    Object[] arrayOfObject = getEnhancedImageReservedNative(paramString, paramImageData, nativeError);
    handleError(nativeError);
    if (arrayOfObject == null || arrayOfObject.length != 4 || arrayOfObject[0] == null || arrayOfObject[1] == null || arrayOfObject[2] == null || arrayOfObject[3] == null) {
      logPrintError(getMethodName() + ": null or invalid image information returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    return new Object[] { arrayOfObject[0], arrayOfObject[1], arrayOfObject[2], arrayOfObject[3] };
  }
  
  public long getLEDs() throws IBScanException {
    NativeError nativeError = new NativeError();
    long l = getLEDsNative(nativeError);
    handleError(nativeError);
    return l;
  }
  
  public LEOperationMode getLEOperationMode() throws IBScanException {
    NativeError nativeError = new NativeError();
    int i = getLEOperationModeNative(nativeError);
    handleError(nativeError);
    LEOperationMode lEOperationMode = LEOperationMode.fromCode(i);
    if (lEOperationMode == null) {
      logPrintError(getMethodName() + ": unrecognized leOperationMode code (" + i + ") returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    return lEOperationMode;
  }
  
  public BeeperType getOperableBeeper() throws IBScanException {
    NativeError nativeError = new NativeError();
    int i = getOperableBeeperNative(nativeError);
    handleError(nativeError);
    BeeperType beeperType = BeeperType.fromCode(i);
    if (beeperType == null) {
      logPrintError(getMethodName() + ": unrecognized beeperType code (" + i + ") returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    return beeperType;
  }
  
  public LedState getOperableLEDs() throws IBScanException {
    NativeError nativeError = new NativeError();
    LedState ledState = getOperableLEDsNative(nativeError);
    handleError(nativeError);
    if (ledState == null || ledState.ledType == null) {
      logPrintError(getMethodName() + ": null or invalid ledState returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    return ledState;
  }
  
  public PlatenState getPlatenStateAtCapture() throws IBScanException {
    NativeError nativeError = new NativeError();
    int i = getPlatenStateAtCaptureNative(nativeError);
    handleError(nativeError);
    PlatenState platenState = PlatenState.fromCode(i);
    if (platenState == null) {
      logPrintError(getMethodName() + ": unrecognized platenState code (" + i + ") returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    return platenState;
  }
  
  public String getProperty(PropertyId paramPropertyId) throws IBScanException {
    if (paramPropertyId == null) {
      logPrintWarning(getMethodName() + ": received null propertyId");
      throw new IllegalArgumentException("Received null propertyId");
    } 
    NativeError nativeError = new NativeError();
    String str = getPropertyNative(paramPropertyId.toCode(), nativeError);
    handleError(nativeError);
    return str;
  }
  
  public Object[] getResultImageExt(IBCommon.FingerPosition paramFingerPosition) throws IBScanException {
    NativeError nativeError = new NativeError();
    Object[] arrayOfObject = getResultImageExtNative(paramFingerPosition.toCode(), nativeError);
    handleError(nativeError);
    return arrayOfObject;
  }
  
  public RollingData getRollingInfo() throws IBScanException {
    NativeError nativeError = new NativeError();
    RollingData rollingData = getRollingInfoNative(nativeError);
    handleError(nativeError);
    if (rollingData == null || rollingData.rollingState == null) {
      logPrintError(getMethodName() + ": null or invalid rollingData returned from native code");
      nativeError.code = IBScanException.Type.COMMAND_FAILED.toCode();
      handleError(nativeError);
    } 
    return rollingData;
  }
  
  public boolean isCaptureActive() throws IBScanException {
    NativeError nativeError = new NativeError();
    boolean bool = isCaptureActiveNative(nativeError);
    handleError(nativeError);
    return bool;
  }
  
  public boolean isCaptureAvailable(ImageType paramImageType, ImageResolution paramImageResolution) throws IBScanException {
    if (paramImageType == null) {
      logPrintWarning(getMethodName() + ": received null imageType");
      throw new IllegalArgumentException("Received null imageType");
    } 
    if (paramImageResolution == null) {
      logPrintWarning(getMethodName() + ": received null imageResolution");
      throw new IllegalArgumentException("Received null imageResolution");
    } 
    NativeError nativeError = new NativeError();
    boolean bool = isCaptureAvailableNative(paramImageType.toCode(), paramImageResolution.toCode(), nativeError);
    handleError(nativeError);
    return bool;
  }
  
  public long isFingerDuplicated(ImageData paramImageData, long paramLong, ImageType paramImageType, int paramInt) throws IBScanException {
    NativeError nativeError = new NativeError();
    paramLong = isFingerDuplicatedNative(paramImageData, paramLong, paramImageType.toCode(), paramInt, nativeError);
    handleError(nativeError);
    return paramLong;
  }
  
  public boolean isFingerTouching() throws IBScanException {
    NativeError nativeError = new NativeError();
    boolean bool = isFingerTouchingNative(nativeError);
    handleError(nativeError);
    return bool;
  }
  
  public boolean isOpened() {
    return this.m_isOpened;
  }
  
  public boolean isValidFingerGeometry(ImageData paramImageData, long paramLong, ImageType paramImageType) throws IBScanException {
    NativeError nativeError = new NativeError();
    boolean bool = isValidFingerGeometryNative(paramImageData, paramLong, paramImageType.toCode(), nativeError);
    handleError(nativeError);
    return bool;
  }
  
  public int removeFingerImage(long paramLong) throws IBScanException {
    NativeError nativeError = new NativeError();
    int i = removeFingerImageNative(paramLong, nativeError);
    handleError(nativeError);
    return i;
  }
  
  public void setBeeper(BeepPattern paramBeepPattern, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IBScanException {
    NativeError nativeError = new NativeError();
    setBeeperNative(paramBeepPattern.toCode(), paramInt1, paramInt2, paramInt3, paramInt4, nativeError);
    handleError(nativeError);
  }
  
  public void setContrast(int paramInt) throws IBScanException {
    NativeError nativeError = new NativeError();
    setContrastNative(paramInt, nativeError);
    handleError(nativeError);
  }
  
  public void setLEDs(long paramLong) throws IBScanException {
    NativeError nativeError = new NativeError();
    setLEDsNative(paramLong, nativeError);
    handleError(nativeError);
  }
  
  public void setLEOperationMode(LEOperationMode paramLEOperationMode) throws IBScanException {
    if (paramLEOperationMode == null) {
      logPrintWarning(getMethodName() + ": received null leOperationMode");
      throw new IllegalArgumentException("Received null leOperationMode");
    } 
    NativeError nativeError = new NativeError();
    setLEOperationModeNative(paramLEOperationMode.toCode(), nativeError);
    handleError(nativeError);
  }
  
  public void setProperty(PropertyId paramPropertyId, String paramString) throws IBScanException {
    if (paramPropertyId == null) {
      logPrintWarning(getMethodName() + ": received null propertyId");
      throw new IllegalArgumentException("Received null propertyId");
    } 
    if (paramString == null) {
      logPrintWarning(getMethodName() + ": received null propertyValue");
      throw new IllegalArgumentException("Received null propertyValue");
    } 
    NativeError nativeError = new NativeError();
    setPropertyNative(paramPropertyId.toCode(), paramString, nativeError);
    handleError(nativeError);
  }
  
  public void setPropertyReserved(String paramString1, PropertyId paramPropertyId, String paramString2) throws IBScanException {
    if (paramString1 == null) {
      logPrintWarning(getMethodName() + ": received null reservedKey");
      throw new IllegalArgumentException("Received null reservedKey");
    } 
    if (paramPropertyId == null) {
      logPrintWarning(getMethodName() + ": received null propertyId");
      throw new IllegalArgumentException("Received null propertyId");
    } 
    if (paramString2 == null) {
      logPrintWarning(getMethodName() + ": received null propertyValue");
      throw new IllegalArgumentException("Received null propertyValue");
    } 
    NativeError nativeError = new NativeError();
    setPropertyReservedNative(paramString1, paramPropertyId.toCode(), paramString2, nativeError);
    handleError(nativeError);
  }
  
  public void setScanDeviceListener(IBScanDeviceListener paramIBScanDeviceListener) {
    this.m_listener = paramIBScanDeviceListener;
  }
  
  public int wsqEncodeToFile(String paramString1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble, String paramString2) throws IBScanException {
    NativeError nativeError = new NativeError();
    paramInt1 = wsqEncodeToFileNative(paramString1, paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramDouble, paramString2, nativeError);
    handleError(nativeError);
    return paramInt1;
  }
  
  public enum BeepPattern {
    BEEP_PATTERN_GENERIC(0),
    BEEP_PATTERN_REPEAT(1);
    
    private final int code;
    
    static {
    
    }
    
    BeepPattern(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static BeepPattern fromCode(int param1Int) {
      for (BeepPattern beepPattern : values()) {
        if (beepPattern.code == param1Int)
          return beepPattern; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public enum BeeperType {
    BEEPER_TYPE_NONE(0),
    BEEPER_TYPE_MONOTONE(1);
    
    private final int code;
    
    static {
      $VALUES = new BeeperType[] { BEEPER_TYPE_NONE, BEEPER_TYPE_MONOTONE };
    }
    
    BeeperType(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static BeeperType fromCode(int param1Int) {
      for (BeeperType beeperType : values()) {
        if (beeperType.code == param1Int)
          return beeperType; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public enum CombineImageWhichHand {
    COMBINE_IMAGE_LEFT_HAND(0),
    COMBINE_IMAGE_RIGHT_HAND(1);
    
    private final int code;
    
    static {
    
    }
    
    CombineImageWhichHand(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public enum EncyptionMode {
    ENCRYPTION_KEY_RANDOM(0),
    ENCRYPTION_KEY_CUSTOM(1);
    
    private final int code;
    
    static {
      $VALUES = new EncyptionMode[] { ENCRYPTION_KEY_RANDOM, ENCRYPTION_KEY_CUSTOM };
    }
    
    EncyptionMode(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static EncyptionMode fromCode(int param1Int) {
      for (EncyptionMode encyptionMode : values()) {
        if (encyptionMode.code == param1Int)
          return encyptionMode; 
      } 
      return null;
    }
    
    public int toCode() {
      return this.code;
    }
  }
  
  public enum EventType {
    ACQUISITION_BEGUN(0),
    ACQUISITION_COMPLETED(0),
    COMMUNICATION_BROKEN(1),
    FINGER_COUNT_CHANGED(1),
    FINGER_QUALITY_CHANGED(1),
    KEYBUTTON(1),
    PLATEN_STATE_CHANGED(1),
    PREVIEW_IMAGE_AVAILABLE(2),
    RESULT_IMAGE_AVAILABLE(2),
    RESULT_IMAGE_EXTENDED_AVAILABLE(2),
    WARNING_RECEIVED(2);
    
    private final int code;
    
    static {
      FINGER_QUALITY_CHANGED = new EventType("FINGER_QUALITY_CHANGED", 5, 6);
      FINGER_COUNT_CHANGED = new EventType("FINGER_COUNT_CHANGED", 6, 7);
      PLATEN_STATE_CHANGED = new EventType("PLATEN_STATE_CHANGED", 7, 9);
      WARNING_RECEIVED = new EventType("WARNING_RECEIVED", 8, 11);
      RESULT_IMAGE_EXTENDED_AVAILABLE = new EventType("RESULT_IMAGE_EXTENDED_AVAILABLE", 9, 12);
      KEYBUTTON = new EventType("KEYBUTTON", 10, 13);
      $VALUES = new EventType[] { 
          COMMUNICATION_BROKEN, PREVIEW_IMAGE_AVAILABLE, ACQUISITION_BEGUN, ACQUISITION_COMPLETED, RESULT_IMAGE_AVAILABLE, FINGER_QUALITY_CHANGED, FINGER_COUNT_CHANGED, PLATEN_STATE_CHANGED, WARNING_RECEIVED, RESULT_IMAGE_EXTENDED_AVAILABLE, 
          KEYBUTTON };
    }
    
    EventType(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public enum FingerCountState {
    FINGER_COUNT_OK(0),
    NON_FINGER(0),
    TOO_FEW_FINGERS(0),
    TOO_MANY_FINGERS(1);
    
    private final int code;
    
    static {
      NON_FINGER = new FingerCountState("NON_FINGER", 3, 3);
      $VALUES = new FingerCountState[] { FINGER_COUNT_OK, TOO_MANY_FINGERS, TOO_FEW_FINGERS, NON_FINGER };
    }
    
    FingerCountState(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static FingerCountState fromCode(int param1Int) {
      for (FingerCountState fingerCountState : values()) {
        if (fingerCountState.code == param1Int)
          return fingerCountState; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public enum FingerQualityState {
    FINGER_NOT_PRESENT(0),
    FAIR(1),
    GOOD(1),
    INVALID_AREA_BOTTOM(1),
    INVALID_AREA_LEFT(1),
    INVALID_AREA_RIGHT(1),
    INVALID_AREA_TOP(1),
    POOR(1);
    
    private final int code;
    
    static {
      INVALID_AREA_TOP = new FingerQualityState("INVALID_AREA_TOP", 4, 4);
      INVALID_AREA_LEFT = new FingerQualityState("INVALID_AREA_LEFT", 5, 5);
      INVALID_AREA_RIGHT = new FingerQualityState("INVALID_AREA_RIGHT", 6, 6);
      INVALID_AREA_BOTTOM = new FingerQualityState("INVALID_AREA_BOTTOM", 7, 7);
      $VALUES = new FingerQualityState[] { FINGER_NOT_PRESENT, GOOD, FAIR, POOR, INVALID_AREA_TOP, INVALID_AREA_LEFT, INVALID_AREA_RIGHT, INVALID_AREA_BOTTOM };
    }
    
    FingerQualityState(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static FingerQualityState fromCode(int param1Int) {
      for (FingerQualityState fingerQualityState : values()) {
        if (fingerQualityState.code == param1Int)
          return fingerQualityState; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public static class ImageData {
    private static final int COMPLETE_ACQUISITION_COLOR = -16711936;
    
    private static final int QUALITY_ARROW_COLOR = -16776961;
    
    private static final int QUALITY_ARROW_HEIGHT = 10;
    
    private static final int QUALITY_ARROW_LENGTH = 40;
    
    private static final int TAKE_ACQUISITION_COLOR = -65536;
    
    private static final int TARGET_LINE_WIDTH = 2;
    
    public final short bitsPerPixel;
    
    public final byte[] buffer;
    
    public final IBScanDevice.ImageFormat format;
    
    public final double frameTime;
    
    public final int height;
    
    public final boolean isFinal;
    
    public final int pitch;
    
    public final int processThres;
    
    public final double resolutionX;
    
    public final double resolutionY;
    
    public final int width;
    
    protected ImageData(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2, double param1Double1, double param1Double2, double param1Double3, int param1Int3, short param1Short, int param1Int4, boolean param1Boolean, int param1Int5) {
      this.buffer = param1ArrayOfbyte;
      this.width = param1Int1;
      this.height = param1Int2;
      this.resolutionX = param1Double1;
      this.resolutionY = param1Double2;
      this.frameTime = param1Double3;
      this.pitch = param1Int3;
      this.bitsPerPixel = param1Short;
      this.format = IBScanDevice.ImageFormat.fromCode(param1Int4);
      this.isFinal = param1Boolean;
      this.processThres = param1Int5;
      if (this.format == null)
        IBScanDevice.logPrintError(IBScanDevice.getMethodName() + ": unrecognized format code(" + param1Int4 + ") received from native code"); 
    }
    
    private void drawRollingLine(Bitmap param1Bitmap, int param1Int1, int param1Int2, IBScanDevice.RollingState param1RollingState, int param1Int3, int param1Int4) {
      if ((param1RollingState.equals(IBScanDevice.RollingState.TAKE_ACQUISITION) || param1RollingState.equals(IBScanDevice.RollingState.COMPLETE_ACQUISITION)) && param1Int3 >= 0) {
        int j = param1Int3 * param1Int1 / this.width;
        if (param1RollingState.equals(IBScanDevice.RollingState.TAKE_ACQUISITION)) {
          param1Int3 = -65536;
        } else {
          param1Int3 = -16711936;
        } 
        int i;
        for (i = 0; i < param1Int2; i++) {
          int k;
          for (k = j - param1Int4 / 2; k < j - param1Int4 / 2 + param1Int4; k++) {
            if (k >= 0 && k < param1Int1)
              param1Bitmap.setPixel(k, i, param1Int3); 
          } 
        } 
      } 
    }
    
    public void drawQualityArrow(Bitmap param1Bitmap, int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
      if (param1Boolean2) {
        int i;
        for (i = 0; i < 40; i++) {
          int j;
          for (j = param1Int2 / 2 - 5; j < param1Int2 / 2 + 5; j++) {
            if (j > 0 && j < param1Int2)
              param1Bitmap.setPixel(i, j, -16776961); 
          } 
        } 
      } 
      if (param1Boolean3) {
        int i;
        for (i = param1Int1 - 40 - 1; i < param1Int1; i++) {
          int j;
          for (j = param1Int2 / 2 - 5; j < param1Int2 / 2 + 5; j++) {
            if (j > 0 && j < param1Int2)
              param1Bitmap.setPixel(i, j, -16776961); 
          } 
        } 
      } 
      if (param1Boolean1)
        for (param1Int2 = 0; param1Int2 < 40; param1Int2++) {
          int i;
          for (i = param1Int1 / 2 - 5; i < param1Int1 / 2 + 5; i++) {
            if (i > 0 && i < param1Int1)
              param1Bitmap.setPixel(i, param1Int2, -16776961); 
          } 
        }  
    }
    
    public boolean saveToFile(File param1File, String param1String) throws IOException {
      if (param1File == null || param1String == null)
        throw new IllegalArgumentException(); 
      boolean bool2 = false;
      Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.valueOf(param1String.toUpperCase());
      boolean bool1 = bool2;
      if (compressFormat != null) {
        Bitmap bitmap = toBitmap();
        bool1 = bool2;
        if (bitmap != null)
          bool1 = bitmap.compress(compressFormat, 100, new FileOutputStream(param1File)); 
      } 
      return bool1;
    }
    
    public Bitmap toBitmap() {
      Bitmap bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
      if (bitmap != null) {
        byte[] arrayOfByte = new byte[this.width * this.height * 4];
        for (int i = 0; i < this.height; i++) {
          for (int j = 0; j < this.width; j++) {
            int k = this.width;
            int m = this.width;
            int n = this.width;
            byte b = this.buffer[(this.height - i - 1) * this.width + j];
            arrayOfByte[(n * i + j) * 4 + 2] = b;
            arrayOfByte[(m * i + j) * 4 + 1] = b;
            arrayOfByte[(k * i + j) * 4] = b;
            arrayOfByte[(this.width * i + j) * 4 + 3] = -1;
          } 
        } 
        bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(arrayOfByte));
      } 
      return bitmap;
    }
    
    public Bitmap toBitmapScaled(int param1Int1, int param1Int2) {
      if (param1Int1 <= 0 || param1Int2 <= 0)
        throw new IllegalArgumentException(); 
      Bitmap bitmap1 = null;
      Bitmap bitmap2 = toBitmap();
      if (bitmap2 != null)
        bitmap1 = Bitmap.createScaledBitmap(bitmap2, param1Int1, param1Int2, false); 
      return bitmap1;
    }
    
    public Bitmap toBitmapScaled(int param1Int1, int param1Int2, IBScanDevice.RollingState param1RollingState, int param1Int3) {
      if (param1Int1 <= 0 || param1Int2 <= 0)
        throw new IllegalArgumentException(); 
      if (param1RollingState == null)
        throw new IllegalArgumentException(); 
      Bitmap bitmap = toBitmapScaled(param1Int1, param1Int2);
      if (bitmap != null)
        drawRollingLine(bitmap, param1Int1, param1Int2, param1RollingState, param1Int3, 2); 
      return bitmap;
    }
    
    public Bitmap toBitmapScaled(int param1Int1, int param1Int2, IBScanDevice.RollingState param1RollingState, int param1Int3, int param1Int4, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
      if (param1Int1 <= 0 || param1Int2 <= 0)
        throw new IllegalArgumentException(); 
      if (param1RollingState == null)
        throw new IllegalArgumentException(); 
      if (param1Int4 < 1 || param1Int4 > 6)
        throw new IllegalArgumentException(); 
      Bitmap bitmap = toBitmapScaled(param1Int1, param1Int2);
      if (bitmap != null) {
        drawRollingLine(bitmap, param1Int1, param1Int2, param1RollingState, param1Int3, param1Int4);
        drawQualityArrow(bitmap, param1Int1, param1Int2, param1Boolean1, param1Boolean2, param1Boolean3);
      } 
      return bitmap;
    }
    
    public Bitmap toBitmapScaled(int param1Int1, int param1Int2, IBScanDevice.RollingState param1RollingState, int param1Int3, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
      if (param1Int1 <= 0 || param1Int2 <= 0)
        throw new IllegalArgumentException(); 
      if (param1RollingState == null)
        throw new IllegalArgumentException(); 
      Bitmap bitmap = toBitmapScaled(param1Int1, param1Int2);
      if (bitmap != null) {
        drawRollingLine(bitmap, param1Int1, param1Int2, param1RollingState, param1Int3, 2);
        drawQualityArrow(bitmap, param1Int1, param1Int2, param1Boolean1, param1Boolean2, param1Boolean3);
      } 
      return bitmap;
    }
    
    public Bitmap toBitmapScaled(int param1Int1, int param1Int2, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {
      if (param1Int1 <= 0 || param1Int2 <= 0)
        throw new IllegalArgumentException(); 
      Bitmap bitmap = toBitmapScaled(param1Int1, param1Int2);
      if (bitmap != null)
        drawQualityArrow(bitmap, param1Int1, param1Int2, param1Boolean1, param1Boolean2, param1Boolean3); 
      return bitmap;
    }
  }
  
  public enum ImageFormat {
    GRAY(0),
    RGB24(1),
    RGB32(2),
    UNKNOWN(3);
    
    private final int code;
    
    static {
    
    }
    
    ImageFormat(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static ImageFormat fromCode(int param1Int) {
      for (ImageFormat imageFormat : values()) {
        if (imageFormat.code == param1Int)
          return imageFormat; 
      } 
      return UNKNOWN;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public enum ImageResolution {
    RESOLUTION_500(500),
    RESOLUTION_1000(3);
    
    private final int code;
    
    static {
      $VALUES = new ImageResolution[] { RESOLUTION_500, RESOLUTION_1000 };
    }
    
    ImageResolution(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static ImageResolution fromCode(int param1Int) {
      for (ImageResolution imageResolution : values()) {
        if (imageResolution.code == param1Int)
          return imageResolution; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public enum ImageType {
    FLAT_FOUR_FINGERS(500),
    FLAT_SINGLE_FINGER(500),
    FLAT_THREE_FINGERS(500),
    FLAT_TWO_FINGERS(500),
    ROLL_SINGLE_FINGER(500),
    TYPE_NONE(0, "Unsupported image type");
    
    private final int code;
    
    private final String description;
    
    static {
      FLAT_SINGLE_FINGER = new ImageType("FLAT_SINGLE_FINGER", 2, 2, "One-finger flat fingerprint");
      FLAT_TWO_FINGERS = new ImageType("FLAT_TWO_FINGERS", 3, 3, "Two-finger flat fingerprint");
      FLAT_FOUR_FINGERS = new ImageType("FLAT_FOUR_FINGERS", 4, 4, "Four-finger flat fingerprint");
      FLAT_THREE_FINGERS = new ImageType("FLAT_THREE_FINGERS", 5, 5, "Three-finger flat fingerprint");
      $VALUES = new ImageType[] { TYPE_NONE, ROLL_SINGLE_FINGER, FLAT_SINGLE_FINGER, FLAT_TWO_FINGERS, FLAT_FOUR_FINGERS, FLAT_THREE_FINGERS };
    }
    
    ImageType(int param1Int1, String param1String1) {
      this.code = param1Int1;
      this.description = param1String1;
    }
    
    protected static ImageType fromCode(int param1Int) {
      for (ImageType imageType : values()) {
        if (imageType.code == param1Int)
          return imageType; 
      } 
      return TYPE_NONE;
    }
    
    protected int toCode() {
      return this.code;
    }
    
    public String toDescription() {
      return this.description;
    }
  }
  
  public enum LEOperationMode {
    AUTO(0),
    OFF(0),
    ON(1);
    
    private final int code;
    
    static {
      $VALUES = new LEOperationMode[] { AUTO, ON, OFF };
    }
    
    LEOperationMode(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static LEOperationMode fromCode(int param1Int) {
      for (LEOperationMode lEOperationMode : values()) {
        if (lEOperationMode.code == param1Int)
          return lEOperationMode; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public static class LedState {
    public final int ledCount;
    
    public final IBScanDevice.LedType ledType;
    
    public final long operableLEDs;
    
    protected LedState(int param1Int1, int param1Int2, long param1Long) {
      this.ledType = IBScanDevice.LedType.fromCode(param1Int1);
      this.ledCount = param1Int2;
      this.operableLEDs = param1Long;
      if (this.ledType == null)
        IBScanDevice.logPrintError(IBScanDevice.getMethodName() + ": unrecognized ledType code(" + param1Int1 + ") received from native code"); 
    }
    
    public String toString() {
      return "LED type = " + this.ledType + "\nLED count = " + this.ledCount + "\nOperable LEDs = " + String.format("%1$08X", new Object[] { Long.valueOf(this.operableLEDs) }) + "\n";
    }
  }
  
  public enum LedType {
    NONE(0),
    FSCAN(1),
    TSCAN(1);
    
    private final int code;
    
    static {
      $VALUES = new LedType[] { NONE, TSCAN, FSCAN };
    }
    
    LedType(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static LedType fromCode(int param1Int) {
      for (LedType ledType : values()) {
        if (ledType.code == param1Int)
          return ledType; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  protected static final class NativeError {
    public int code = 0;
  }
  
  public enum PlatenState {
    CLEARD(0),
    HAS_FINGERS(1);
    
    private final int code;
    
    static {
    
    }
    
    PlatenState(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static PlatenState fromCode(int param1Int) {
      for (PlatenState platenState : values()) {
        if (platenState.code == param1Int)
          return platenState; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public enum PropertyId {
    PRODUCT_ID(0),
    RECOMMENDED_LEVEL(0),
    RESERVED_1(0),
    RESERVED_100(0),
    RESERVED_2(0),
    RESERVED_CAPTURE_BRIGHTNESS_THRESHOLD_FOR_FLAT(0),
    RESERVED_CAPTURE_BRIGHTNESS_THRESHOLD_FOR_ROLL(0),
    RESERVED_ENABLE_TOF_FOR_ROLL(0),
    RESERVED_ENHANCED_RESULT_IMAGE(0),
    RESERVED_IMAGE_PROCESS_THRESHOLD(0),
    RETRY_WRONG_COMMUNICATION(0),
    REVISION(0),
    ROLLED_IMAGE_HEIGHT(0),
    ROLLED_IMAGE_WIDTH(0),
    ROLL_IMAGE_OVERRIDE(0),
    ROLL_LEVEL(0),
    ROLL_MIN_WIDTH(0),
    ROLL_MODE(0),
    CAPTURE_AREA_THRESHOLD(1),
    CAPTURE_TIMEOUT(1),
    DEVICE_ID(1),
    DEVICE_INDEX(1),
    ENABLE_CAPTURE_ON_RELEASE(1),
    ENABLE_DECIMATION(1),
    ENABLE_ENCRYPTION(1),
    ENABLE_POWER_SAVE_MODE(1),
    ENABLE_TOF(1),
    ENABLE_WET_FINGER_DETECT(1),
    FIRMWARE(1),
    IBIA_DEVICE_ID(1),
    IBIA_VENDOR_ID(1),
    IBIA_VERSION(1),
    IGNORE_FINGER_TIME(1),
    IMAGE_HEIGHT(1),
    IMAGE_WIDTH(1),
    MIN_CAPTURE_TIME_IN_SUPER_DRY_MODE(1),
    NO_PREVIEW_IMAGE(1),
    POLLINGTIME_TO_BGETIMAGE(1),
    PRODUCTION_DATE(1),
    SERIAL_NUMBER(1),
    SERVICE_DATE(1),
    START_POSITION_OF_ROLLING_AREA(1),
    START_ROLL_WITHOUT_LOCK(1),
    SUPER_DRY_MODE(1),
    VENDOR_ID(2),
    WARNING_MESSAGE_INVALID_AREA(2),
    WET_FINGER_DETECT_LEVEL(2),
    WET_FINGER_DETECT_LEVEL_THRESHOLD(2);
    
    private final int code;
    
    static {
      IBIA_DEVICE_ID = new PropertyId("IBIA_DEVICE_ID", 5, 5);
      FIRMWARE = new PropertyId("FIRMWARE", 6, 6);
      REVISION = new PropertyId("REVISION", 7, 7);
      PRODUCTION_DATE = new PropertyId("PRODUCTION_DATE", 8, 8);
      SERVICE_DATE = new PropertyId("SERVICE_DATE", 9, 9);
      IMAGE_WIDTH = new PropertyId("IMAGE_WIDTH", 10, 10);
      IMAGE_HEIGHT = new PropertyId("IMAGE_HEIGHT", 11, 11);
      IGNORE_FINGER_TIME = new PropertyId("IGNORE_FINGER_TIME", 12, 12);
      RECOMMENDED_LEVEL = new PropertyId("RECOMMENDED_LEVEL", 13, 13);
      POLLINGTIME_TO_BGETIMAGE = new PropertyId("POLLINGTIME_TO_BGETIMAGE", 14, 14);
      ENABLE_POWER_SAVE_MODE = new PropertyId("ENABLE_POWER_SAVE_MODE", 15, 15);
      RETRY_WRONG_COMMUNICATION = new PropertyId("RETRY_WRONG_COMMUNICATION", 16, 16);
      CAPTURE_TIMEOUT = new PropertyId("CAPTURE_TIMEOUT", 17, 17);
      ROLL_MIN_WIDTH = new PropertyId("ROLL_MIN_WIDTH", 18, 18);
      ROLL_MODE = new PropertyId("ROLL_MODE", 19, 19);
      ROLL_LEVEL = new PropertyId("ROLL_LEVEL", 20, 20);
      CAPTURE_AREA_THRESHOLD = new PropertyId("CAPTURE_AREA_THRESHOLD", 21, 21);
      ENABLE_DECIMATION = new PropertyId("ENABLE_DECIMATION", 22, 22);
      ENABLE_CAPTURE_ON_RELEASE = new PropertyId("ENABLE_CAPTURE_ON_RELEASE", 23, 23);
      DEVICE_INDEX = new PropertyId("DEVICE_INDEX", 24, 24);
      DEVICE_ID = new PropertyId("DEVICE_ID", 25, 25);
      SUPER_DRY_MODE = new PropertyId("SUPER_DRY_MODE", 26, 26);
      MIN_CAPTURE_TIME_IN_SUPER_DRY_MODE = new PropertyId("MIN_CAPTURE_TIME_IN_SUPER_DRY_MODE", 27, 27);
      ROLLED_IMAGE_WIDTH = new PropertyId("ROLLED_IMAGE_WIDTH", 28, 28);
      ROLLED_IMAGE_HEIGHT = new PropertyId("ROLLED_IMAGE_HEIGHT", 29, 29);
      NO_PREVIEW_IMAGE = new PropertyId("NO_PREVIEW_IMAGE", 30, 30);
      ROLL_IMAGE_OVERRIDE = new PropertyId("ROLL_IMAGE_OVERRIDE", 31, 31);
      WARNING_MESSAGE_INVALID_AREA = new PropertyId("WARNING_MESSAGE_INVALID_AREA", 32, 32);
      ENABLE_WET_FINGER_DETECT = new PropertyId("ENABLE_WET_FINGER_DETECT", 33, 33);
      WET_FINGER_DETECT_LEVEL = new PropertyId("WET_FINGER_DETECT_LEVEL", 34, 34);
      WET_FINGER_DETECT_LEVEL_THRESHOLD = new PropertyId("WET_FINGER_DETECT_LEVEL_THRESHOLD", 35, 35);
      START_POSITION_OF_ROLLING_AREA = new PropertyId("START_POSITION_OF_ROLLING_AREA", 36, 36);
      START_ROLL_WITHOUT_LOCK = new PropertyId("START_ROLL_WITHOUT_LOCK", 37, 37);
      ENABLE_TOF = new PropertyId("ENABLE_TOF", 38, 38);
      ENABLE_ENCRYPTION = new PropertyId("ENABLE_ENCRYPTION", 39, 39);
      RESERVED_1 = new PropertyId("RESERVED_1", 40, 200);
      RESERVED_2 = new PropertyId("RESERVED_2", 41, 201);
      RESERVED_100 = new PropertyId("RESERVED_100", 42, 202);
      RESERVED_IMAGE_PROCESS_THRESHOLD = new PropertyId("RESERVED_IMAGE_PROCESS_THRESHOLD", 43, 400);
      RESERVED_ENABLE_TOF_FOR_ROLL = new PropertyId("RESERVED_ENABLE_TOF_FOR_ROLL", 44, 401);
      RESERVED_CAPTURE_BRIGHTNESS_THRESHOLD_FOR_FLAT = new PropertyId("RESERVED_CAPTURE_BRIGHTNESS_THRESHOLD_FOR_FLAT", 45, 402);
      RESERVED_CAPTURE_BRIGHTNESS_THRESHOLD_FOR_ROLL = new PropertyId("RESERVED_CAPTURE_BRIGHTNESS_THRESHOLD_FOR_ROLL", 46, 403);
      RESERVED_ENHANCED_RESULT_IMAGE = new PropertyId("RESERVED_ENHANCED_RESULT_IMAGE", 47, 404);
      $VALUES = new PropertyId[] { 
          PRODUCT_ID, SERIAL_NUMBER, VENDOR_ID, IBIA_VENDOR_ID, IBIA_VERSION, IBIA_DEVICE_ID, FIRMWARE, REVISION, PRODUCTION_DATE, SERVICE_DATE, 
          IMAGE_WIDTH, IMAGE_HEIGHT, IGNORE_FINGER_TIME, RECOMMENDED_LEVEL, POLLINGTIME_TO_BGETIMAGE, ENABLE_POWER_SAVE_MODE, RETRY_WRONG_COMMUNICATION, CAPTURE_TIMEOUT, ROLL_MIN_WIDTH, ROLL_MODE, 
          ROLL_LEVEL, CAPTURE_AREA_THRESHOLD, ENABLE_DECIMATION, ENABLE_CAPTURE_ON_RELEASE, DEVICE_INDEX, DEVICE_ID, SUPER_DRY_MODE, MIN_CAPTURE_TIME_IN_SUPER_DRY_MODE, ROLLED_IMAGE_WIDTH, ROLLED_IMAGE_HEIGHT, 
          NO_PREVIEW_IMAGE, ROLL_IMAGE_OVERRIDE, WARNING_MESSAGE_INVALID_AREA, ENABLE_WET_FINGER_DETECT, WET_FINGER_DETECT_LEVEL, WET_FINGER_DETECT_LEVEL_THRESHOLD, START_POSITION_OF_ROLLING_AREA, START_ROLL_WITHOUT_LOCK, ENABLE_TOF, ENABLE_ENCRYPTION, 
          RESERVED_1, RESERVED_2, RESERVED_100, RESERVED_IMAGE_PROCESS_THRESHOLD, RESERVED_ENABLE_TOF_FOR_ROLL, RESERVED_CAPTURE_BRIGHTNESS_THRESHOLD_FOR_FLAT, RESERVED_CAPTURE_BRIGHTNESS_THRESHOLD_FOR_ROLL, RESERVED_ENHANCED_RESULT_IMAGE };
    }
    
    PropertyId(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static PropertyId fromCode(int param1Int) {
      for (PropertyId propertyId : values()) {
        if (propertyId.code == param1Int)
          return propertyId; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public static class RollingData {
    public final int rollingLineX;
    
    public final IBScanDevice.RollingState rollingState;
    
    protected RollingData(int param1Int1, int param1Int2) {
      this.rollingState = IBScanDevice.RollingState.fromCode(param1Int1);
      this.rollingLineX = param1Int2;
      if (this.rollingState == null)
        IBScanDevice.logPrintError(IBScanDevice.getMethodName() + ": unrecognized rollingState code(" + param1Int1 + ") received from native code"); 
    }
  }
  
  public enum RollingState {
    NOT_PRESENT(0),
    RESULT_IMAGE(0),
    TAKE_ACQUISITION(1),
    COMPLETE_ACQUISITION(2);
    
    private final int code;
    
    static {
      $VALUES = new RollingState[] { NOT_PRESENT, TAKE_ACQUISITION, COMPLETE_ACQUISITION, RESULT_IMAGE };
    }
    
    RollingState(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static RollingState fromCode(int param1Int) {
      for (RollingState rollingState : values()) {
        if (rollingState.code == param1Int)
          return rollingState; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
  
  public static class SegmentPosition {
    public final int x1;
    
    public final int x2;
    
    public final int x3;
    
    public final int x4;
    
    public final int y1;
    
    public final int y2;
    
    public final int y3;
    
    public final int y4;
    
    protected SegmentPosition(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8) {
      this.x1 = param1Int1;
      this.y1 = param1Int2;
      this.x2 = param1Int3;
      this.y2 = param1Int4;
      this.x3 = param1Int5;
      this.y3 = param1Int6;
      this.x4 = param1Int7;
      this.y4 = param1Int8;
    }
  }
  
  public static class WsqImage {
    public final byte[] buffer;
    
    public final int len;
    
    protected WsqImage(byte[] param1ArrayOfbyte, int param1Int) {
      this.buffer = param1ArrayOfbyte;
      this.len = param1Int;
    }
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\integratedbiometrics\ibscanultimate\IBScanDevice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */