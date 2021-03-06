package com.integratedbiometrics.ibscanultimate;

public class IBScanException extends Exception {
  private final Type type;
  
  IBScanException(Type paramType) {
    this.type = paramType;
  }
  
  public Type getType() {
    return this.type;
  }
  
  public enum Type {
    ALREADY_ENHANCED_IMAGE,
    ALREADY_INITIALIZED,
    API_DEPRECATED,
    BGET_IMAGE,
    CAPTURE_ALGORITHM,
    CAPTURE_COMMAND_FAILED,
    CAPTURE_INVALID_MODE,
    CAPTURE_NOT_RUNNING,
    CAPTURE_ROLLING,
    CAPTURE_ROLLING_TIMEOUT,
    CAPTURE_STILL_RUNNING,
    CAPTURE_STOP,
    CAPTURE_TIMEOUT,
    CHANNEL_IO_CAMERA_WRONG,
    CHANNEL_IO_COMMAND_FAILED,
    CHANNEL_IO_FRAME_MISSING,
    CHANNEL_IO_INVALID_HANDLE,
    CHANNEL_IO_READ_FAILED,
    CHANNEL_IO_READ_TIMEOUT,
    CHANNEL_IO_SLEEP_STATUS,
    CHANNEL_IO_UNEXPECTED_FAILED,
    CHANNEL_IO_WRITE_FAILED,
    CHANNEL_IO_WRITE_TIMEOUT,
    CHANNEL_IO_WRONG_PIPE_INDEX,
    COMMAND_FAILED,
    DEVICE_ACTIVE,
    DEVICE_BUSY,
    DEVICE_ENABLED_POWER_SAVE_MODE,
    DEVICE_HIGHER_SDK_REQUIRED,
    DEVICE_INVALID_CALIBRATION_DATA,
    DEVICE_INVALID_STATE,
    DEVICE_IO,
    DEVICE_NEED_CALIBRATE_TOF,
    DEVICE_NEED_UPDATE_FIRMWARE,
    DEVICE_NOT_ACCESSIBLE,
    DEVICE_NOT_FOUND,
    DEVICE_NOT_INITIALIZED,
    DEVICE_NOT_MATCHED,
    DEVICE_NOT_SUPPORTED_FEATURE,
    DUPLICATE_ALREADY_USED,
    DUPLICATE_EXTRACTION_FAILED,
    DUPLICATE_MATCHING_FAILED,
    DUPLICATE_SEGMENTATION_FAILED,
    EMPTY_IBSM_RESULT_IMAGE,
    FILE_OPEN,
    FILE_READ,
    INCORRECT_FINGERS,
    INVALID_ACCESS_POINTER,
    INVALID_BRIGHTNESS_FINGERS,
    INVALID_LICENSE,
    INVALID_PARAM_VALUE(-1),
    LIBRARY_UNLOAD_FAILED(-1),
    MEM_ALLOC(-2),
    MISSING_RESOURCE(-2),
    MULTIPLE_FINGERS_DURING_ROLL(-2),
    NBIS_NFIQ_FAILED(-2),
    NBIS_WSQ_DECODE_FAILED(-2),
    NBIS_WSQ_ENCODE_FAILED(-2),
    NOT_SUPPORTED(-3),
    NO_FINGER(-3),
    OUTDATED_FIRMWARE(-3),
    QUALITY_INVALID_AREA(-3),
    QUALITY_INVALID_AREA_HORIZONTALLY(-3),
    QUALITY_INVALID_AREA_HORIZONTALLY_VERTICALLY(-3),
    QUALITY_INVALID_AREA_VERTICALLY(-3),
    RESOURCE_LOCKED(-3),
    ROLLING_NOT_RUNNING(-3),
    ROLLING_SHIFTED_HORIZONTALLY(-3),
    ROLLING_SHIFTED_HORIZONTALLY_VERTICALLY(-3),
    ROLLING_SHIFTED_VERTICALLY(-3),
    ROLLING_SMEAR(-3),
    SPOOF_DETECTED(-3),
    THREAD_CREATE(-3),
    USB20_REQUIRED(-3),
    WET_FINGERS(-3);
    
    private final int code;
    
    static {
      MISSING_RESOURCE = new Type("MISSING_RESOURCE", 6, -7);
      INVALID_ACCESS_POINTER = new Type("INVALID_ACCESS_POINTER", 7, -8);
      THREAD_CREATE = new Type("THREAD_CREATE", 8, -9);
      COMMAND_FAILED = new Type("COMMAND_FAILED", 9, -10);
      LIBRARY_UNLOAD_FAILED = new Type("LIBRARY_UNLOAD_FAILED", 10, -11);
      CHANNEL_IO_COMMAND_FAILED = new Type("CHANNEL_IO_COMMAND_FAILED", 11, -100);
      CHANNEL_IO_READ_FAILED = new Type("CHANNEL_IO_READ_FAILED", 12, -101);
      CHANNEL_IO_WRITE_FAILED = new Type("CHANNEL_IO_WRITE_FAILED", 13, -102);
      CHANNEL_IO_READ_TIMEOUT = new Type("CHANNEL_IO_READ_TIMEOUT", 14, -103);
      CHANNEL_IO_WRITE_TIMEOUT = new Type("CHANNEL_IO_WRITE_TIMEOUT", 15, -104);
      CHANNEL_IO_UNEXPECTED_FAILED = new Type("CHANNEL_IO_UNEXPECTED_FAILED", 16, -105);
      CHANNEL_IO_INVALID_HANDLE = new Type("CHANNEL_IO_INVALID_HANDLE", 17, -106);
      CHANNEL_IO_WRONG_PIPE_INDEX = new Type("CHANNEL_IO_WRONG_PIPE_INDEX", 18, -107);
      DEVICE_IO = new Type("DEVICE_IO", 19, -200);
      DEVICE_NOT_FOUND = new Type("DEVICE_NOT_FOUND", 20, -201);
      DEVICE_NOT_MATCHED = new Type("DEVICE_NOT_MATCHED", 21, -202);
      DEVICE_ACTIVE = new Type("DEVICE_ACTIVE", 22, -203);
      DEVICE_NOT_INITIALIZED = new Type("DEVICE_NOT_INITIALIZED", 23, -204);
      DEVICE_INVALID_STATE = new Type("DEVICE_INVALID_STATE", 24, -205);
      DEVICE_BUSY = new Type("DEVICE_BUSY", 25, -206);
      DEVICE_NOT_SUPPORTED_FEATURE = new Type("DEVICE_NOT_SUPPORTED_FEATURE", 26, -207);
      INVALID_LICENSE = new Type("INVALID_LICENSE", 27, -208);
      USB20_REQUIRED = new Type("USB20_REQUIRED", 28, -209);
      DEVICE_ENABLED_POWER_SAVE_MODE = new Type("DEVICE_ENABLED_POWER_SAVE_MODE", 29, -210);
      DEVICE_NEED_UPDATE_FIRMWARE = new Type("DEVICE_NEED_UPDATE_FIRMWARE", 30, -211);
      DEVICE_NEED_CALIBRATE_TOF = new Type("DEVICE_NEED_CALIBRATE_TOF", 31, -212);
      DEVICE_INVALID_CALIBRATION_DATA = new Type("DEVICE_INVALID_CALIBRATION_DATA", 32, -213);
      DEVICE_HIGHER_SDK_REQUIRED = new Type("DEVICE_HIGHER_SDK_REQUIRED", 33, -214);
      DEVICE_NOT_ACCESSIBLE = new Type("DEVICE_NOT_ACCESSIBLE", 34, -299);
      CAPTURE_COMMAND_FAILED = new Type("CAPTURE_COMMAND_FAILED", 35, -300);
      CAPTURE_STOP = new Type("CAPTURE_STOP", 36, -301);
      CAPTURE_TIMEOUT = new Type("CAPTURE_TIMEOUT", 37, -302);
      CAPTURE_STILL_RUNNING = new Type("CAPTURE_STILL_RUNNING", 38, -303);
      CAPTURE_NOT_RUNNING = new Type("CAPTURE_NOT_RUNNING", 39, -304);
      CAPTURE_INVALID_MODE = new Type("CAPTURE_INVALID_MODE", 40, -305);
      CAPTURE_ALGORITHM = new Type("CAPTURE_ALGORITHM", 41, -306);
      CAPTURE_ROLLING = new Type("CAPTURE_ROLLING", 42, -307);
      CAPTURE_ROLLING_TIMEOUT = new Type("CAPTURE_ROLLING_TIMEOUT", 43, -308);
      NBIS_NFIQ_FAILED = new Type("NBIS_NFIQ_FAILED", 44, -500);
      NBIS_WSQ_ENCODE_FAILED = new Type("NBIS_WSQ_ENCODE_FAILED", 45, -501);
      NBIS_WSQ_DECODE_FAILED = new Type("NBIS_WSQ_DECODE_FAILED", 46, -502);
      DUPLICATE_EXTRACTION_FAILED = new Type("DUPLICATE_EXTRACTION_FAILED", 47, -600);
      DUPLICATE_ALREADY_USED = new Type("DUPLICATE_ALREADY_USED", 48, -601);
      DUPLICATE_SEGMENTATION_FAILED = new Type("DUPLICATE_SEGMENTATION_FAILED", 49, -602);
      DUPLICATE_MATCHING_FAILED = new Type("DUPLICATE_MATCHING_FAILED", 50, -603);
      CHANNEL_IO_FRAME_MISSING = new Type("CHANNEL_IO_FRAME_MISSING", 51, 100);
      CHANNEL_IO_CAMERA_WRONG = new Type("CHANNEL_IO_CAMERA_WRONG", 52, 101);
      CHANNEL_IO_SLEEP_STATUS = new Type("CHANNEL_IO_SLEEP_STATUS", 53, 102);
      OUTDATED_FIRMWARE = new Type("OUTDATED_FIRMWARE", 54, 200);
      ALREADY_INITIALIZED = new Type("ALREADY_INITIALIZED", 55, 201);
      API_DEPRECATED = new Type("API_DEPRECATED", 56, 202);
      ALREADY_ENHANCED_IMAGE = new Type("ALREADY_ENHANCED_IMAGE", 57, 203);
      BGET_IMAGE = new Type("BGET_IMAGE", 58, 300);
      ROLLING_NOT_RUNNING = new Type("ROLLING_NOT_RUNNING", 59, 301);
      NO_FINGER = new Type("NO_FINGER", 60, 302);
      INCORRECT_FINGERS = new Type("INCORRECT_FINGERS", 61, 303);
      ROLLING_SMEAR = new Type("ROLLING_SMEAR", 62, 304);
      ROLLING_SHIFTED_HORIZONTALLY = new Type("ROLLING_SHIFTED_HORIZONTALLY", 63, 305);
      ROLLING_SHIFTED_VERTICALLY = new Type("ROLLING_SHIFTED_VERTICALLY", 64, 306);
      ROLLING_SHIFTED_HORIZONTALLY_VERTICALLY = new Type("ROLLING_SHIFTED_HORIZONTALLY_VERTICALLY", 65, 307);
      EMPTY_IBSM_RESULT_IMAGE = new Type("EMPTY_IBSM_RESULT_IMAGE", 66, 400);
      QUALITY_INVALID_AREA = new Type("QUALITY_INVALID_AREA", 67, 512);
      QUALITY_INVALID_AREA_HORIZONTALLY = new Type("QUALITY_INVALID_AREA_HORIZONTALLY", 68, 513);
      QUALITY_INVALID_AREA_VERTICALLY = new Type("QUALITY_INVALID_AREA_VERTICALLY", 69, 514);
      QUALITY_INVALID_AREA_HORIZONTALLY_VERTICALLY = new Type("QUALITY_INVALID_AREA_HORIZONTALLY_VERTICALLY", 70, 515);
      INVALID_BRIGHTNESS_FINGERS = new Type("INVALID_BRIGHTNESS_FINGERS", 71, 600);
      WET_FINGERS = new Type("WET_FINGERS", 72, 601);
      MULTIPLE_FINGERS_DURING_ROLL = new Type("MULTIPLE_FINGERS_DURING_ROLL", 73, 602);
      SPOOF_DETECTED = new Type("SPOOF_DETECTED", 74, 603);
      $VALUES = new Type[] { 
          INVALID_PARAM_VALUE, MEM_ALLOC, NOT_SUPPORTED, FILE_OPEN, FILE_READ, RESOURCE_LOCKED, MISSING_RESOURCE, INVALID_ACCESS_POINTER, THREAD_CREATE, COMMAND_FAILED, 
          LIBRARY_UNLOAD_FAILED, CHANNEL_IO_COMMAND_FAILED, CHANNEL_IO_READ_FAILED, CHANNEL_IO_WRITE_FAILED, CHANNEL_IO_READ_TIMEOUT, CHANNEL_IO_WRITE_TIMEOUT, CHANNEL_IO_UNEXPECTED_FAILED, CHANNEL_IO_INVALID_HANDLE, CHANNEL_IO_WRONG_PIPE_INDEX, DEVICE_IO, 
          DEVICE_NOT_FOUND, DEVICE_NOT_MATCHED, DEVICE_ACTIVE, DEVICE_NOT_INITIALIZED, DEVICE_INVALID_STATE, DEVICE_BUSY, DEVICE_NOT_SUPPORTED_FEATURE, INVALID_LICENSE, USB20_REQUIRED, DEVICE_ENABLED_POWER_SAVE_MODE, 
          DEVICE_NEED_UPDATE_FIRMWARE, DEVICE_NEED_CALIBRATE_TOF, DEVICE_INVALID_CALIBRATION_DATA, DEVICE_HIGHER_SDK_REQUIRED, DEVICE_NOT_ACCESSIBLE, CAPTURE_COMMAND_FAILED, CAPTURE_STOP, CAPTURE_TIMEOUT, CAPTURE_STILL_RUNNING, CAPTURE_NOT_RUNNING, 
          CAPTURE_INVALID_MODE, CAPTURE_ALGORITHM, CAPTURE_ROLLING, CAPTURE_ROLLING_TIMEOUT, NBIS_NFIQ_FAILED, NBIS_WSQ_ENCODE_FAILED, NBIS_WSQ_DECODE_FAILED, DUPLICATE_EXTRACTION_FAILED, DUPLICATE_ALREADY_USED, DUPLICATE_SEGMENTATION_FAILED, 
          DUPLICATE_MATCHING_FAILED, CHANNEL_IO_FRAME_MISSING, CHANNEL_IO_CAMERA_WRONG, CHANNEL_IO_SLEEP_STATUS, OUTDATED_FIRMWARE, ALREADY_INITIALIZED, API_DEPRECATED, ALREADY_ENHANCED_IMAGE, BGET_IMAGE, ROLLING_NOT_RUNNING, 
          NO_FINGER, INCORRECT_FINGERS, ROLLING_SMEAR, ROLLING_SHIFTED_HORIZONTALLY, ROLLING_SHIFTED_VERTICALLY, ROLLING_SHIFTED_HORIZONTALLY_VERTICALLY, EMPTY_IBSM_RESULT_IMAGE, QUALITY_INVALID_AREA, QUALITY_INVALID_AREA_HORIZONTALLY, QUALITY_INVALID_AREA_VERTICALLY, 
          QUALITY_INVALID_AREA_HORIZONTALLY_VERTICALLY, INVALID_BRIGHTNESS_FINGERS, WET_FINGERS, MULTIPLE_FINGERS_DURING_ROLL, SPOOF_DETECTED };
    }
    
    Type(int param1Int1) {
      this.code = param1Int1;
    }
    
    protected static Type fromCode(int param1Int) {
      for (Type type : values()) {
        if (type.code == param1Int)
          return type; 
      } 
      return null;
    }
    
    protected int toCode() {
      return this.code;
    }
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\integratedbiometrics\ibscanultimate\IBScanException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */