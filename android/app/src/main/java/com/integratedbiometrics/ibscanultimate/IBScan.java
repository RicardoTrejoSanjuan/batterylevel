package com.integratedbiometrics.ibscanultimate;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import java.util.Iterator;
import org.libusb.LibUsbManager;

public class IBScan {
  private static final String ACTION_USB_PERMISSION = "ibscan.USB_PERMISSION";
  
  private static int METHOD_STACK_INDEX = 0;
  
  private static final int PID_COLUMBO = 4352;
  
  private static final int PID_COLUMBO_REV1 = 4353;
  
  private static final int PID_CURVE = 4100;
  
  private static final int PID_FIVE0 = 5376;
  
  private static final int PID_FIVE0_DERMALOG = 52;
  
  private static final int PID_FIVE0_REV1 = 5377;
  
  private static final int PID_HOLMES = 4608;
  
  private static final int PID_KOJAK = 4864;
  
  private static final int PID_KOJAK_REV1 = 4865;
  
  private static final int PID_SHERLOCK = 4112;
  
  private static final int PID_SHERLOCK_REV1 = 4113;
  
  private static final int PID_WATSON = 4101;
  
  private static final int PID_WATSON_MINI = 4128;
  
  private static final int PID_WATSON_MINI_REV1 = 4129;
  
  private static final int PID_WATSON_REV1 = 4102;
  
  private static final int VID_DERMALOG = 8122;
  
  private static final int VID_IB = 4415;
  
  private static IBScan m_instance = null;
  
  private Context m_context = null;
  
  private IBScanListener m_listener = null;
  
  private final BroadcastReceiver m_usbReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        UsbDevice usbDevice;
        String str = param1Intent.getAction();
        if (str.equals("android.hardware.usb.action.USB_DEVICE_ATTACHED")) {
          usbDevice = (UsbDevice)param1Intent.getParcelableExtra("device");
          if (usbDevice != null && IBScan.isScanDevice(usbDevice))
            IBScan.callbackScanDeviceAttached(usbDevice.getDeviceId()); 
          return;
        } 
        if (usbDevice.equals("android.hardware.usb.action.USB_DEVICE_DETACHED")) {
          usbDevice = (UsbDevice)param1Intent.getParcelableExtra("device");
          if (usbDevice != null && IBScan.isScanDevice(usbDevice)) {
            IBScan.callbackScanDeviceDetached(usbDevice.getDeviceId());
            return;
          } 
          return;
        } 
        if (usbDevice.equals("ibscan.USB_PERMISSION") && param1Intent.hasExtra("permission")) {
          boolean bool = param1Intent.getBooleanExtra("permission", false);
          usbDevice = (UsbDevice)param1Intent.getParcelableExtra("device");
          if (usbDevice != null && IBScan.isScanDevice(usbDevice)) {
            IBScan.callbackScanDevicePermissionGranted(usbDevice.getDeviceId(), bool);
            return;
          } 
        } 
      }
    };
  
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
        if (stackTraceElement.getClassName().equals(IBScan.class.getName())) {
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
  
  private IBScan() {
    initNative();
  }
  
  private static void callbackScanDeviceAttached(int paramInt) {
    IBScan iBScan = m_instance;
    if (iBScan != null && iBScan.m_listener != null)
      iBScan.m_listener.scanDeviceAttached(paramInt); 
  }
  
  private static void callbackScanDeviceCountChanged(int paramInt) {
    IBScan iBScan = m_instance;
    if (iBScan != null && iBScan.m_listener != null)
      iBScan.m_listener.scanDeviceCountChanged(paramInt); 
  }
  
  private static void callbackScanDeviceDetached(int paramInt) {
    IBScan iBScan = m_instance;
    if (iBScan != null && iBScan.m_listener != null)
      iBScan.m_listener.scanDeviceDetached(paramInt); 
  }
  
  private static void callbackScanDeviceInitProgress(int paramInt1, int paramInt2) {
    IBScan iBScan = m_instance;
    if (iBScan != null && iBScan.m_listener != null)
      iBScan.m_listener.scanDeviceInitProgress(paramInt1, paramInt2); 
  }
  
  private static void callbackScanDeviceOpenComplete(int paramInt1, IBScanDevice paramIBScanDevice, int paramInt2) {
    IBScan iBScan = m_instance;
    if (iBScan != null && iBScan.m_listener != null) {
      IBScanException.Type type = IBScanException.Type.fromCode(paramInt2);
      iBScan.m_listener.scanDeviceOpenComplete(paramInt1, paramIBScanDevice, new IBScanException(type));
    } 
  }
  
  private static void callbackScanDevicePermissionGranted(int paramInt, boolean paramBoolean) {
    IBScan iBScan = m_instance;
    if (iBScan != null && iBScan.m_listener != null)
      iBScan.m_listener.scanDevicePermissionGranted(paramInt, paramBoolean); 
  }
  
  private native void enableTraceLogNative(boolean paramBoolean, NativeError paramNativeError);
  
  private UsbDevice findDevice(int paramInt) {
    UsbDevice usbDevice2 = null;
    UsbDevice usbDevice1 = usbDevice2;
    if (this.m_context != null) {
      Iterator<UsbDevice> iterator = ((UsbManager)this.m_context.getSystemService("usb")).getDeviceList().values().iterator();
      while (true) {
        usbDevice1 = usbDevice2;
        if (iterator.hasNext()) {
          usbDevice1 = iterator.next();
          if (usbDevice1.getDeviceId() == paramInt)
            break; 
          continue;
        } 
        break;
      } 
    } 
    return usbDevice1;
  }
  
  private native int getDeviceCountNative(NativeError paramNativeError);
  
  private native DeviceDesc getDeviceDescNative(int paramInt, NativeError paramNativeError);
  
  private native int getInitProgressNative(int paramInt, NativeError paramNativeError);
  
  public static IBScan getInstance(Context paramContext) {
    if (m_instance == null)
      m_instance = new IBScan(); 
    m_instance.setContext(paramContext);
    return m_instance;
  }
  
  private static String getMethodName() {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    return (arrayOfStackTraceElement.length > METHOD_STACK_INDEX) ? arrayOfStackTraceElement[METHOD_STACK_INDEX].getMethodName() : "?";
  }
  
  private native String getRequiredSDKVersionNative(int paramInt, NativeError paramNativeError);
  
  private native SdkVersion getSdkVersionNative(NativeError paramNativeError);
  
  private static void handleError(NativeError paramNativeError) throws IBScanException {
    if (paramNativeError.code != 0) {
      IBScanException.Type type2 = IBScanException.Type.fromCode(paramNativeError.code);
      IBScanException.Type type1 = type2;
      if (type2 == null) {
        logPrintError(getMethodName() + ": unrecognized error code(" + paramNativeError.code + ") returned from native code");
        type1 = IBScanException.Type.COMMAND_FAILED;
      } 
      throw new IBScanException(type1);
    } 
  }
  
  private native void initNative();
  
  public static boolean isScanDevice(UsbDevice paramUsbDevice) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #5
    //   3: aload_0
    //   4: invokevirtual getVendorId : ()I
    //   7: istore_1
    //   8: iload_1
    //   9: sipush #4415
    //   12: if_icmpeq -> 26
    //   15: iload #5
    //   17: istore #4
    //   19: iload_1
    //   20: sipush #8122
    //   23: if_icmpne -> 164
    //   26: bipush #15
    //   28: newarray int
    //   30: astore #6
    //   32: aload #6
    //   34: dup
    //   35: iconst_0
    //   36: sipush #4100
    //   39: iastore
    //   40: dup
    //   41: iconst_1
    //   42: sipush #4101
    //   45: iastore
    //   46: dup
    //   47: iconst_2
    //   48: sipush #4102
    //   51: iastore
    //   52: dup
    //   53: iconst_3
    //   54: sipush #4112
    //   57: iastore
    //   58: dup
    //   59: iconst_4
    //   60: sipush #4113
    //   63: iastore
    //   64: dup
    //   65: iconst_5
    //   66: sipush #4128
    //   69: iastore
    //   70: dup
    //   71: bipush #6
    //   73: sipush #4129
    //   76: iastore
    //   77: dup
    //   78: bipush #7
    //   80: sipush #4352
    //   83: iastore
    //   84: dup
    //   85: bipush #8
    //   87: sipush #4353
    //   90: iastore
    //   91: dup
    //   92: bipush #9
    //   94: sipush #4864
    //   97: iastore
    //   98: dup
    //   99: bipush #10
    //   101: sipush #4865
    //   104: iastore
    //   105: dup
    //   106: bipush #11
    //   108: sipush #4608
    //   111: iastore
    //   112: dup
    //   113: bipush #12
    //   115: sipush #5376
    //   118: iastore
    //   119: dup
    //   120: bipush #13
    //   122: sipush #5377
    //   125: iastore
    //   126: dup
    //   127: bipush #14
    //   129: bipush #52
    //   131: iastore
    //   132: pop
    //   133: aload_0
    //   134: invokevirtual getProductId : ()I
    //   137: istore_2
    //   138: aload #6
    //   140: arraylength
    //   141: istore_3
    //   142: iconst_0
    //   143: istore_1
    //   144: iload #5
    //   146: istore #4
    //   148: iload_1
    //   149: iload_3
    //   150: if_icmpge -> 164
    //   153: aload #6
    //   155: iload_1
    //   156: iaload
    //   157: iload_2
    //   158: if_icmpne -> 167
    //   161: iconst_1
    //   162: istore #4
    //   164: iload #4
    //   166: ireturn
    //   167: iload_1
    //   168: iconst_1
    //   169: iadd
    //   170: istore_1
    //   171: goto -> 144
  }
  
  private static void logPrintError(String paramString) {
    Log.e("IBScan", paramString);
  }
  
  private static void logPrintWarning(String paramString) {
    Log.w("IBScan", paramString);
  }
  
  private native void openDeviceAsyncExNative(int paramInt, String paramString, NativeError paramNativeError);
  
  private native void openDeviceAsyncNative(int paramInt, NativeError paramNativeError);
  
  private native IBScanDevice openDeviceExNative(int paramInt, String paramString, NativeError paramNativeError);
  
  private native IBScanDevice openDeviceNative(int paramInt, NativeError paramNativeError);
  
  private native int unloadLibraryNative(NativeError paramNativeError);
  
  private native void updateUsbPermissionNative();
  
  public void enableTraceLog(boolean paramBoolean) throws IBScanException {
    NativeError nativeError = new NativeError();
    enableTraceLogNative(paramBoolean, nativeError);
    handleError(nativeError);
  }
  
  public int getDeviceCount() throws IBScanException {
    NativeError nativeError = new NativeError();
    int i = getDeviceCountNative(nativeError);
    handleError(nativeError);
    return i;
  }
  
  public DeviceDesc getDeviceDescription(int paramInt) throws IBScanException {
    NativeError nativeError = new NativeError();
    DeviceDesc deviceDesc = getDeviceDescNative(paramInt, nativeError);
    handleError(nativeError);
    return deviceDesc;
  }
  
  public int getInitProgress(int paramInt) throws IBScanException {
    NativeError nativeError = new NativeError();
    paramInt = getInitProgressNative(paramInt, nativeError);
    handleError(nativeError);
    return paramInt;
  }
  
  public String getRequiredSDKVersion(int paramInt) throws IBScanException {
    NativeError nativeError = new NativeError();
    String str = getRequiredSDKVersionNative(paramInt, nativeError);
    handleError(nativeError);
    return str;
  }
  
  public SdkVersion getSdkVersion() throws IBScanException {
    NativeError nativeError = new NativeError();
    SdkVersion sdkVersion = getSdkVersionNative(nativeError);
    handleError(nativeError);
    return sdkVersion;
  }
  
  public boolean hasPermission(int paramInt) {
    boolean bool = false;
    UsbDevice usbDevice = findDevice(paramInt);
    if (usbDevice != null)
      bool = ((UsbManager)this.m_context.getSystemService("usb")).hasPermission(usbDevice); 
    return bool;
  }
  
  public IBScanDevice openDevice(int paramInt) throws IBScanException {
    NativeError nativeError = new NativeError();
    IBScanDevice iBScanDevice = openDeviceNative(paramInt, nativeError);
    handleError(nativeError);
    return iBScanDevice;
  }
  
  public IBScanDevice openDevice(int paramInt, String paramString) throws IBScanException {
    if (paramString == null) {
      logPrintWarning(getMethodName() + ": receive null uniformityMaskPath");
      throw new IllegalArgumentException();
    } 
    NativeError nativeError = new NativeError();
    IBScanDevice iBScanDevice = openDeviceExNative(paramInt, paramString, nativeError);
    handleError(nativeError);
    return iBScanDevice;
  }
  
  public void openDeviceAsync(int paramInt) throws IBScanException {
    NativeError nativeError = new NativeError();
    openDeviceAsyncNative(paramInt, nativeError);
    handleError(nativeError);
  }
  
  public void openDeviceAsync(int paramInt, String paramString) throws IBScanException {
    if (paramString == null) {
      logPrintWarning(getMethodName() + ": receive null uniformityMaskPath");
      throw new IllegalArgumentException();
    } 
    NativeError nativeError = new NativeError();
    openDeviceAsyncExNative(paramInt, paramString, nativeError);
    handleError(nativeError);
  }
  
  public void requestPermission(int paramInt) {
    UsbDevice usbDevice = findDevice(paramInt);
    if (usbDevice != null) {
      UsbManager usbManager = (UsbManager)this.m_context.getSystemService("usb");
      Intent intent = new Intent("ibscan.USB_PERMISSION");
      usbManager.requestPermission(usbDevice, PendingIntent.getBroadcast(this.m_context, 0, intent, 0));
    } 
  }
  
  public void setContext(Context paramContext) {
    if (this.m_context != null) {
      this.m_context.unregisterReceiver(this.m_usbReceiver);
      this.m_context = null;
      LibUsbManager.setContext(null);
    } 
    if (paramContext != null) {
      this.m_context = paramContext;
      LibUsbManager.setContext(this.m_context);
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
      intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
      intentFilter.addAction("ibscan.USB_PERMISSION");
      this.m_context.registerReceiver(this.m_usbReceiver, intentFilter);
    } 
  }
  
  public void setScanListener(IBScanListener paramIBScanListener) {
    this.m_listener = paramIBScanListener;
  }
  
  public void unloadLibrary() throws IBScanException {
    NativeError nativeError = new NativeError();
    unloadLibraryNative(nativeError);
    handleError(nativeError);
  }
  
  public void updateUsbPermission() {
    updateUsbPermissionNative();
  }
  
  public static final class DeviceDesc {
    public final String devRevision;
    
    public final int deviceId;
    
    public final String fwVersion;
    
    public final String interfaceType;
    
    public final boolean isOpened;
    
    public final String productName;
    
    public final String serialNumber;
    
    protected DeviceDesc(String param1String1, String param1String2, String param1String3, String param1String4, String param1String5, boolean param1Boolean, int param1Int) {
      this.serialNumber = param1String1;
      this.productName = param1String2;
      this.interfaceType = param1String3;
      this.fwVersion = param1String4;
      this.devRevision = param1String5;
      this.isOpened = param1Boolean;
      this.deviceId = param1Int;
    }
    
    public String toString() {
      return "Serial Number: " + this.serialNumber + "\nProduct Name: " + this.productName + "\nInterface Type: " + this.interfaceType + "\nFirmware Version: " + this.fwVersion + "\nDevice Revision: " + this.devRevision + "\nDevice Opened: " + this.isOpened + "\nDevice ID: " + this.deviceId + "\n";
    }
  }
  
  protected static final class NativeError {
    public int code = 0;
  }
  
  public static final class SdkVersion {
    public final String file;
    
    public final String product;
    
    protected SdkVersion(String param1String1, String param1String2) {
      this.product = param1String1;
      this.file = param1String2;
    }
    
    public String toString() {
      return "Product: " + this.product + "\nFile: " + this.file + "\n";
    }
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\integratedbiometrics\ibscanultimate\IBScan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */