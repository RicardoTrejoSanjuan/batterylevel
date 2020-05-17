package com.integratedbiometrics.ibscanultimate;

public interface IBScanListener {
  void scanDeviceAttached(int paramInt);
  
  void scanDeviceCountChanged(int paramInt);
  
  void scanDeviceDetached(int paramInt);
  
  void scanDeviceInitProgress(int paramInt1, int paramInt2);
  
  void scanDeviceOpenComplete(int paramInt, IBScanDevice paramIBScanDevice, IBScanException paramIBScanException);
  
  void scanDevicePermissionGranted(int paramInt, boolean paramBoolean);
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\integratedbiometrics\ibscanultimate\IBScanListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */