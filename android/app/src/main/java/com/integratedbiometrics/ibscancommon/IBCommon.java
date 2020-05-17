package com.integratedbiometrics.ibscancommon;

public class IBCommon {
  public enum CaptureDeviceTechId {
    ELECTRO_LUMINESCENT,
    GLASS_FIBER,
    MECHANICAL,
    MONOCHROMATIC_IR_OPTICAL_DIRECT_VIEW_ON_PLATEN,
    MONOCHROMATIC_IR_OPTICAL_TIR,
    MONOCHROMATIC_IR_OPTICAL_TOUCHLESS,
    MONOCHROMATIC_VISIBLE_OPTICAL_DIRECT_VIEW_ON_PLATEN,
    MONOCHROMATIC_VISIBLE_OPTICAL_TIR,
    MONOCHROMATIC_VISIBLE_OPTICAL_TOUCHLESS,
    MULTISPECTRAL_OPTICAL_DIRECT_VIEW_ON_PLATEN,
    MULTISPECTRAL_OPTICAL_TIR,
    MULTISPECTRAL_OPTICAL_TOUCHLESS,
    PRESSURE_SENSITIVE,
    SEMICONDUCTOR_CAPACITIVE,
    SEMICONDUCTOR_RF,
    SEMICONDUCTOR_THEMAL,
    ULTRASOUND,
    UNKNOWN_OR_UNSPECIFIED(0),
    WHITE_LIGHT_OPTICAL_DIRECT_VIEW_ON_PLATEN(0),
    WHITE_LIGHT_OPTICAL_TIR(1),
    WHITE_LIGHT_OPTICAL_TOUCHLESS(1);
    
    private final int code;
    
    static {
      MONOCHROMATIC_VISIBLE_OPTICAL_TIR = new CaptureDeviceTechId("MONOCHROMATIC_VISIBLE_OPTICAL_TIR", 4, 4);
      MONOCHROMATIC_VISIBLE_OPTICAL_DIRECT_VIEW_ON_PLATEN = new CaptureDeviceTechId("MONOCHROMATIC_VISIBLE_OPTICAL_DIRECT_VIEW_ON_PLATEN", 5, 5);
      MONOCHROMATIC_VISIBLE_OPTICAL_TOUCHLESS = new CaptureDeviceTechId("MONOCHROMATIC_VISIBLE_OPTICAL_TOUCHLESS", 6, 6);
      MONOCHROMATIC_IR_OPTICAL_TIR = new CaptureDeviceTechId("MONOCHROMATIC_IR_OPTICAL_TIR", 7, 7);
      MONOCHROMATIC_IR_OPTICAL_DIRECT_VIEW_ON_PLATEN = new CaptureDeviceTechId("MONOCHROMATIC_IR_OPTICAL_DIRECT_VIEW_ON_PLATEN", 8, 8);
      MONOCHROMATIC_IR_OPTICAL_TOUCHLESS = new CaptureDeviceTechId("MONOCHROMATIC_IR_OPTICAL_TOUCHLESS", 9, 9);
      MULTISPECTRAL_OPTICAL_TIR = new CaptureDeviceTechId("MULTISPECTRAL_OPTICAL_TIR", 10, 10);
      MULTISPECTRAL_OPTICAL_DIRECT_VIEW_ON_PLATEN = new CaptureDeviceTechId("MULTISPECTRAL_OPTICAL_DIRECT_VIEW_ON_PLATEN", 11, 11);
      MULTISPECTRAL_OPTICAL_TOUCHLESS = new CaptureDeviceTechId("MULTISPECTRAL_OPTICAL_TOUCHLESS", 12, 12);
      ELECTRO_LUMINESCENT = new CaptureDeviceTechId("ELECTRO_LUMINESCENT", 13, 13);
      SEMICONDUCTOR_CAPACITIVE = new CaptureDeviceTechId("SEMICONDUCTOR_CAPACITIVE", 14, 14);
      SEMICONDUCTOR_RF = new CaptureDeviceTechId("SEMICONDUCTOR_RF", 15, 15);
      SEMICONDUCTOR_THEMAL = new CaptureDeviceTechId("SEMICONDUCTOR_THEMAL", 16, 16);
      PRESSURE_SENSITIVE = new CaptureDeviceTechId("PRESSURE_SENSITIVE", 17, 17);
      ULTRASOUND = new CaptureDeviceTechId("ULTRASOUND", 18, 18);
      MECHANICAL = new CaptureDeviceTechId("MECHANICAL", 19, 19);
      GLASS_FIBER = new CaptureDeviceTechId("GLASS_FIBER", 20, 20);
      $VALUES = new CaptureDeviceTechId[] { 
          UNKNOWN_OR_UNSPECIFIED, WHITE_LIGHT_OPTICAL_TIR, WHITE_LIGHT_OPTICAL_DIRECT_VIEW_ON_PLATEN, WHITE_LIGHT_OPTICAL_TOUCHLESS, MONOCHROMATIC_VISIBLE_OPTICAL_TIR, MONOCHROMATIC_VISIBLE_OPTICAL_DIRECT_VIEW_ON_PLATEN, MONOCHROMATIC_VISIBLE_OPTICAL_TOUCHLESS, MONOCHROMATIC_IR_OPTICAL_TIR, MONOCHROMATIC_IR_OPTICAL_DIRECT_VIEW_ON_PLATEN, MONOCHROMATIC_IR_OPTICAL_TOUCHLESS, 
          MULTISPECTRAL_OPTICAL_TIR, MULTISPECTRAL_OPTICAL_DIRECT_VIEW_ON_PLATEN, MULTISPECTRAL_OPTICAL_TOUCHLESS, ELECTRO_LUMINESCENT, SEMICONDUCTOR_CAPACITIVE, SEMICONDUCTOR_RF, SEMICONDUCTOR_THEMAL, PRESSURE_SENSITIVE, ULTRASOUND, MECHANICAL, 
          GLASS_FIBER };
    }
    
    CaptureDeviceTechId(int param1Int1) {
      this.code = param1Int1;
    }
    
    public static CaptureDeviceTechId fromCode(int param1Int) {
      for (CaptureDeviceTechId captureDeviceTechId : values()) {
        if (captureDeviceTechId.code == param1Int)
          return captureDeviceTechId; 
      } 
      return null;
    }
    
    public int toCode() {
      return this.code;
    }
  }
  
  public enum FingerPosition {
    UNKNOWN(0),
    UNKNOWN_PALM(0),
    LEFT_FULL_PALM(1),
    LEFT_HYPOTHENAR(1),
    LEFT_INDEX_AND_MIDDLE(1),
    LEFT_INDEX_AND_MIDDLE_AND_RING(1),
    LEFT_INDEX_FINGER(1),
    LEFT_INTERDIGITAL(1),
    LEFT_LITTLE_FINGER(1),
    LEFT_LOWER_PALM(1),
    LEFT_MIDDLE_AND_RING(1),
    LEFT_MIDDLE_AND_RING_AND_LITTLE(1),
    LEFT_MIDDLE_FINGER(1),
    LEFT_OTHER(1),
    LEFT_RING_AND_LITTLE(1),
    LEFT_RING_FINGER(1),
    LEFT_THENAR(1),
    LEFT_THUMB(1),
    LEFT_UPPER_PALM(1),
    LEFT_WRITERS_PALM(1),
    PLAIN_LEFT_FOUR_FINGERS(1),
    PLAIN_RIGHT_FOUR_FINGERS(1),
    PLAIN_THUMBS(1),
    RIGHT_FULL_PALM(1),
    RIGHT_HYPOTHENAR(1),
    RIGHT_INDEX_AND_LEFT_INDEX(1),
    RIGHT_INDEX_AND_MIDDLE(1),
    RIGHT_INDEX_AND_MIDDLE_AND_RING(1),
    RIGHT_INDEX_FINGER(1),
    RIGHT_INTERDIGITAL(1),
    RIGHT_LITTLE_FINGER(1),
    RIGHT_LOWER_PALM(1),
    RIGHT_MIDDLE_AND_RING(1),
    RIGHT_MIDDLE_AND_RING_AND_LITTLE(1),
    RIGHT_MIDDLE_FINGER(1),
    RIGHT_OTHER(1),
    RIGHT_RING_AND_LITTLE(1),
    RIGHT_RING_FINGER(1),
    RIGHT_THENAR(1),
    RIGHT_THUMB(1),
    RIGHT_UPPER_PALM(1),
    RIGHT_WRITERS_PALM(1);
    
    private final int code;
    
    static {
      RIGHT_INDEX_FINGER = new FingerPosition("RIGHT_INDEX_FINGER", 2, 2);
      RIGHT_MIDDLE_FINGER = new FingerPosition("RIGHT_MIDDLE_FINGER", 3, 3);
      RIGHT_RING_FINGER = new FingerPosition("RIGHT_RING_FINGER", 4, 4);
      RIGHT_LITTLE_FINGER = new FingerPosition("RIGHT_LITTLE_FINGER", 5, 5);
      LEFT_THUMB = new FingerPosition("LEFT_THUMB", 6, 6);
      LEFT_INDEX_FINGER = new FingerPosition("LEFT_INDEX_FINGER", 7, 7);
      LEFT_MIDDLE_FINGER = new FingerPosition("LEFT_MIDDLE_FINGER", 8, 8);
      LEFT_RING_FINGER = new FingerPosition("LEFT_RING_FINGER", 9, 9);
      LEFT_LITTLE_FINGER = new FingerPosition("LEFT_LITTLE_FINGER", 10, 10);
      PLAIN_RIGHT_FOUR_FINGERS = new FingerPosition("PLAIN_RIGHT_FOUR_FINGERS", 11, 13);
      PLAIN_LEFT_FOUR_FINGERS = new FingerPosition("PLAIN_LEFT_FOUR_FINGERS", 12, 14);
      PLAIN_THUMBS = new FingerPosition("PLAIN_THUMBS", 13, 15);
      UNKNOWN_PALM = new FingerPosition("UNKNOWN_PALM", 14, 20);
      RIGHT_FULL_PALM = new FingerPosition("RIGHT_FULL_PALM", 15, 21);
      RIGHT_WRITERS_PALM = new FingerPosition("RIGHT_WRITERS_PALM", 16, 22);
      LEFT_FULL_PALM = new FingerPosition("LEFT_FULL_PALM", 17, 23);
      LEFT_WRITERS_PALM = new FingerPosition("LEFT_WRITERS_PALM", 18, 24);
      RIGHT_LOWER_PALM = new FingerPosition("RIGHT_LOWER_PALM", 19, 25);
      RIGHT_UPPER_PALM = new FingerPosition("RIGHT_UPPER_PALM", 20, 26);
      LEFT_LOWER_PALM = new FingerPosition("LEFT_LOWER_PALM", 21, 27);
      LEFT_UPPER_PALM = new FingerPosition("LEFT_UPPER_PALM", 22, 28);
      RIGHT_OTHER = new FingerPosition("RIGHT_OTHER", 23, 29);
      LEFT_OTHER = new FingerPosition("LEFT_OTHER", 24, 30);
      RIGHT_INTERDIGITAL = new FingerPosition("RIGHT_INTERDIGITAL", 25, 31);
      RIGHT_THENAR = new FingerPosition("RIGHT_THENAR", 26, 32);
      RIGHT_HYPOTHENAR = new FingerPosition("RIGHT_HYPOTHENAR", 27, 33);
      LEFT_INTERDIGITAL = new FingerPosition("LEFT_INTERDIGITAL", 28, 34);
      LEFT_THENAR = new FingerPosition("LEFT_THENAR", 29, 35);
      LEFT_HYPOTHENAR = new FingerPosition("LEFT_HYPOTHENAR", 30, 36);
      RIGHT_INDEX_AND_MIDDLE = new FingerPosition("RIGHT_INDEX_AND_MIDDLE", 31, 40);
      RIGHT_MIDDLE_AND_RING = new FingerPosition("RIGHT_MIDDLE_AND_RING", 32, 41);
      RIGHT_RING_AND_LITTLE = new FingerPosition("RIGHT_RING_AND_LITTLE", 33, 42);
      LEFT_INDEX_AND_MIDDLE = new FingerPosition("LEFT_INDEX_AND_MIDDLE", 34, 43);
      LEFT_MIDDLE_AND_RING = new FingerPosition("LEFT_MIDDLE_AND_RING", 35, 44);
      LEFT_RING_AND_LITTLE = new FingerPosition("LEFT_RING_AND_LITTLE", 36, 45);
      RIGHT_INDEX_AND_LEFT_INDEX = new FingerPosition("RIGHT_INDEX_AND_LEFT_INDEX", 37, 46);
      RIGHT_INDEX_AND_MIDDLE_AND_RING = new FingerPosition("RIGHT_INDEX_AND_MIDDLE_AND_RING", 38, 47);
      RIGHT_MIDDLE_AND_RING_AND_LITTLE = new FingerPosition("RIGHT_MIDDLE_AND_RING_AND_LITTLE", 39, 48);
      LEFT_INDEX_AND_MIDDLE_AND_RING = new FingerPosition("LEFT_INDEX_AND_MIDDLE_AND_RING", 40, 49);
      LEFT_MIDDLE_AND_RING_AND_LITTLE = new FingerPosition("LEFT_MIDDLE_AND_RING_AND_LITTLE", 41, 50);
      $VALUES = new FingerPosition[] { 
          UNKNOWN, RIGHT_THUMB, RIGHT_INDEX_FINGER, RIGHT_MIDDLE_FINGER, RIGHT_RING_FINGER, RIGHT_LITTLE_FINGER, LEFT_THUMB, LEFT_INDEX_FINGER, LEFT_MIDDLE_FINGER, LEFT_RING_FINGER, 
          LEFT_LITTLE_FINGER, PLAIN_RIGHT_FOUR_FINGERS, PLAIN_LEFT_FOUR_FINGERS, PLAIN_THUMBS, UNKNOWN_PALM, RIGHT_FULL_PALM, RIGHT_WRITERS_PALM, LEFT_FULL_PALM, LEFT_WRITERS_PALM, RIGHT_LOWER_PALM, 
          RIGHT_UPPER_PALM, LEFT_LOWER_PALM, LEFT_UPPER_PALM, RIGHT_OTHER, LEFT_OTHER, RIGHT_INTERDIGITAL, RIGHT_THENAR, RIGHT_HYPOTHENAR, LEFT_INTERDIGITAL, LEFT_THENAR, 
          LEFT_HYPOTHENAR, RIGHT_INDEX_AND_MIDDLE, RIGHT_MIDDLE_AND_RING, RIGHT_RING_AND_LITTLE, LEFT_INDEX_AND_MIDDLE, LEFT_MIDDLE_AND_RING, LEFT_RING_AND_LITTLE, RIGHT_INDEX_AND_LEFT_INDEX, RIGHT_INDEX_AND_MIDDLE_AND_RING, RIGHT_MIDDLE_AND_RING_AND_LITTLE, 
          LEFT_INDEX_AND_MIDDLE_AND_RING, LEFT_MIDDLE_AND_RING_AND_LITTLE };
    }
    
    FingerPosition(int param1Int1) {
      this.code = param1Int1;
    }
    
    public static FingerPosition fromCode(int param1Int) {
      for (FingerPosition fingerPosition : values()) {
        if (fingerPosition.code == param1Int)
          return fingerPosition; 
      } 
      return null;
    }
    
    public int toCode() {
      return this.code;
    }
  }
  
  public static class ImageDataExt {
    public final byte bitDepth;
    
    public final IBCommon.CaptureDeviceTechId captureDeviceTechId;
    
    public final short captureDeviceTypeId;
    
    public final short captureDeviceVendorId;
    
    public final IBCommon.FingerPosition fingerPosition;
    
    public final byte[] imageData;
    
    public final IBCommon.ImageFormat imageFormat;
    
    public final short imageSamplingX;
    
    public final short imageSamplingY;
    
    public final short imageSizeX;
    
    public final short imageSizeY;
    
    public final IBCommon.ImpressionType impressionType;
    
    public final byte scaleUnit;
    
    public final short scanSamplingX;
    
    public final short scanSamplingY;
    
    protected ImageDataExt(int param1Int1, int param1Int2, int param1Int3, int param1Int4, short param1Short1, short param1Short2, short param1Short3, short param1Short4, short param1Short5, short param1Short6, short param1Short7, short param1Short8, byte param1Byte1, byte param1Byte2, byte[] param1ArrayOfbyte) {
      this.imageFormat = IBCommon.ImageFormat.fromCode(param1Int1);
      this.impressionType = IBCommon.ImpressionType.fromCode(param1Int2);
      this.fingerPosition = IBCommon.FingerPosition.fromCode(param1Int3);
      this.captureDeviceTechId = IBCommon.CaptureDeviceTechId.fromCode(param1Int4);
      this.captureDeviceVendorId = param1Short1;
      this.captureDeviceTypeId = param1Short2;
      this.scanSamplingX = param1Short3;
      this.scanSamplingY = param1Short4;
      this.imageSamplingX = param1Short5;
      this.imageSamplingY = param1Short6;
      this.imageSizeX = param1Short7;
      this.imageSizeY = param1Short8;
      this.scaleUnit = param1Byte1;
      this.bitDepth = param1Byte2;
      this.imageData = param1ArrayOfbyte;
    }
    
    public String toString() {
      return "Image format = " + this.imageFormat.toString() + "\nImpression type = " + this.impressionType.toString() + "\nFinger position = " + this.fingerPosition.toString() + "\nCapture device tech ID = " + this.captureDeviceTechId.toString() + "\nCapture device vendor ID = " + this.captureDeviceVendorId + "\nCapture device type ID = " + this.captureDeviceTypeId + "\nScan sampling = " + this.scanSamplingX + " x " + this.scanSamplingY + "\nImage sampling = " + this.imageSamplingX + " x " + this.imageSamplingY + "\nImage size = " + this.imageSizeX + " x " + this.imageSizeY + "\nScale unit = " + this.scaleUnit + "\n";
    }
  }
  
  public enum ImageFormat {
    BIT_PACKED(0),
    JPEG2000_LOSSLESS(0),
    JPEG2000_LOSSY(0),
    JPEG_LOSSY(0),
    NO_BIT_PACKING(0),
    PNG(0),
    WSQ(0);
    
    private final int code;
    
    static {
      JPEG_LOSSY = new ImageFormat("JPEG_LOSSY", 3, 3);
      JPEG2000_LOSSY = new ImageFormat("JPEG2000_LOSSY", 4, 4);
      JPEG2000_LOSSLESS = new ImageFormat("JPEG2000_LOSSLESS", 5, 5);
      PNG = new ImageFormat("PNG", 6, 6);
      $VALUES = new ImageFormat[] { NO_BIT_PACKING, BIT_PACKED, WSQ, JPEG_LOSSY, JPEG2000_LOSSY, JPEG2000_LOSSLESS, PNG };
    }
    
    ImageFormat(int param1Int1) {
      this.code = param1Int1;
    }
    
    public static ImageFormat fromCode(int param1Int) {
      for (ImageFormat imageFormat : values()) {
        if (imageFormat.code == param1Int)
          return imageFormat; 
      } 
      return null;
    }
    
    public int toCode() {
      return this.code;
    }
  }
  
  public enum ImpressionType {
    LATENT_IMPRESSION(0),
    LATENT_LIFT(0),
    LATENT_PALM_IMPRESSION(0),
    LATENT_PALM_LIFT(0),
    LATENT_PALM_PHOTO(0),
    LATENT_PALM_TRACING(0),
    LATENT_PHOTO(0),
    LATENT_TRACING(0),
    LIVE_SCAN_OPTICAL_CONTRCTLESS_PLAIN(0),
    LIVE_SCAN_PALM(0),
    LIVE_SCAN_PLAIN(0),
    LIVE_SCAN_ROLLED(1),
    LIVE_SCAN_SWIPE(1),
    LIVE_SCAN_VERTICAL_ROLL(1),
    NONLIVE_SCAN_PALM(1),
    NONLIVE_SCAN_PLAIN(2),
    NONLIVE_SCAN_ROLLED(3),
    OTHER(3),
    UNKNOWN(3);
    
    private final int code;
    
    static {
      LATENT_PHOTO = new ImpressionType("LATENT_PHOTO", 6, 6);
      LATENT_LIFT = new ImpressionType("LATENT_LIFT", 7, 7);
      LIVE_SCAN_SWIPE = new ImpressionType("LIVE_SCAN_SWIPE", 8, 8);
      LIVE_SCAN_VERTICAL_ROLL = new ImpressionType("LIVE_SCAN_VERTICAL_ROLL", 9, 9);
      LIVE_SCAN_PALM = new ImpressionType("LIVE_SCAN_PALM", 10, 10);
      NONLIVE_SCAN_PALM = new ImpressionType("NONLIVE_SCAN_PALM", 11, 11);
      LATENT_PALM_IMPRESSION = new ImpressionType("LATENT_PALM_IMPRESSION", 12, 12);
      LATENT_PALM_TRACING = new ImpressionType("LATENT_PALM_TRACING", 13, 13);
      LATENT_PALM_PHOTO = new ImpressionType("LATENT_PALM_PHOTO", 14, 14);
      LATENT_PALM_LIFT = new ImpressionType("LATENT_PALM_LIFT", 15, 15);
      LIVE_SCAN_OPTICAL_CONTRCTLESS_PLAIN = new ImpressionType("LIVE_SCAN_OPTICAL_CONTRCTLESS_PLAIN", 16, 24);
      OTHER = new ImpressionType("OTHER", 17, 28);
      UNKNOWN = new ImpressionType("UNKNOWN", 18, 29);
      $VALUES = new ImpressionType[] { 
          LIVE_SCAN_PLAIN, LIVE_SCAN_ROLLED, NONLIVE_SCAN_PLAIN, NONLIVE_SCAN_ROLLED, LATENT_IMPRESSION, LATENT_TRACING, LATENT_PHOTO, LATENT_LIFT, LIVE_SCAN_SWIPE, LIVE_SCAN_VERTICAL_ROLL, 
          LIVE_SCAN_PALM, NONLIVE_SCAN_PALM, LATENT_PALM_IMPRESSION, LATENT_PALM_TRACING, LATENT_PALM_PHOTO, LATENT_PALM_LIFT, LIVE_SCAN_OPTICAL_CONTRCTLESS_PLAIN, OTHER, UNKNOWN };
    }
    
    ImpressionType(int param1Int1) {
      this.code = param1Int1;
    }
    
    public static ImpressionType fromCode(int param1Int) {
      for (ImpressionType impressionType : values()) {
        if (impressionType.code == param1Int)
          return impressionType; 
      } 
      return null;
    }
    
    public int toCode() {
      return this.code;
    }
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\integratedbiometrics\ibscancommon\IBCommon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */