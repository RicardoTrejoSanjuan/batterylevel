package org.libusb;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class LibUsbManager {
  private static final String TAG = "LibUsb";
  
  private static SparseArray<UsbDeviceConnection> m_connectionMap = new SparseArray();
  
  private static Context m_context = null;
  
  private static Vector<DeviceDesc> m_descVector = new Vector<DeviceDesc>();
  
  private static int m_hasPermitUsbDevice = 0;
  
  static {
    System.loadLibrary("usb");
  }
  
  protected static void closeDevice(int paramInt) {
    if (m_context != null) {
      UsbDeviceConnection usbDeviceConnection = (UsbDeviceConnection)m_connectionMap.get(paramInt);
      if (usbDeviceConnection != null) {
        usbDeviceConnection.close();
        m_connectionMap.remove(paramInt);
      } 
    } 
  }
  
  private static UsbDevice findDevice(int paramInt) {
    UsbDevice usbDevice1;
    UsbDevice usbDevice2 = null;
    Iterator<UsbDevice> iterator = ((UsbManager)m_context.getSystemService("usb")).getDeviceList().values().iterator();
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
    return usbDevice1;
  }
  
  protected static DeviceDesc[] getDeviceArray() {
    if (m_context != null) {
      UsbManager usbManager = (UsbManager)m_context.getSystemService("usb");
      HashMap hashMap = usbManager.getDeviceList();
      new Vector();
      int i = 0;
      Iterator<UsbDevice> iterator = hashMap.values().iterator();
      while (iterator.hasNext()) {
        if (usbManager.hasPermission(iterator.next()))
          i++; 
      } 
      if (i != m_hasPermitUsbDevice) {
        m_hasPermitUsbDevice = i;
        m_descVector.clear();
        for (UsbDevice usbDevice : hashMap.values()) {
          if (usbManager.hasPermission(usbDevice) && usbDevice.getVendorId() == 4415) {
            UsbDeviceConnection usbDeviceConnection = usbManager.openDevice(usbDevice);
            if (usbDeviceConnection != null) {
              byte[] arrayOfByte = usbDeviceConnection.getRawDescriptors();
              usbDeviceConnection.close();
              m_descVector.add(new DeviceDesc(usbDevice.getDeviceId(), arrayOfByte));
            } 
          } 
        } 
      } 
    } 
    return m_descVector.<DeviceDesc>toArray(new DeviceDesc[m_descVector.size()]);
  }
  
  private static void logPrintError(String paramString) {
    Log.e("IBScanDevice", paramString);
  }
  
  private static void logPrintWarning(String paramString) {
    Log.w("IBScanDevice", paramString);
  }
  
  protected static int openDevice(int paramInt) {
    byte b = -1;
    int i = b;
    if (m_context != null) {
      UsbDevice usbDevice = findDevice(paramInt);
      i = b;
      if (usbDevice != null) {
        UsbManager usbManager = (UsbManager)m_context.getSystemService("usb");
        i = b;
        if (usbManager.hasPermission(usbDevice)) {
          UsbDeviceConnection usbDeviceConnection = usbManager.openDevice(usbDevice);
          i = b;
          if (usbDeviceConnection != null) {
            i = usbDeviceConnection.getFileDescriptor();
            m_connectionMap.put(usbDevice.getDeviceId(), usbDeviceConnection);
          } 
        } 
      } 
    } 
    return i;
  }
  
  public static void setContext(Context paramContext) {
    m_context = paramContext;
  }
  
  protected static class DeviceDesc {
    public final byte[] descriptors;
    
    public final int deviceId;
    
    protected DeviceDesc(int param1Int, byte[] param1ArrayOfbyte) {
      this.deviceId = param1Int;
      this.descriptors = param1ArrayOfbyte;
    }
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\libusb\LibUsbManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */