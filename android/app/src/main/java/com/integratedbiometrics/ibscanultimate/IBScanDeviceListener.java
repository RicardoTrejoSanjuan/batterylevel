package com.integratedbiometrics.ibscanultimate;

public interface IBScanDeviceListener {
  void deviceAcquisitionBegun(IBScanDevice paramIBScanDevice, IBScanDevice.ImageType paramImageType);
  
  void deviceAcquisitionCompleted(IBScanDevice paramIBScanDevice, IBScanDevice.ImageType paramImageType);
  
  void deviceCommunicationBroken(IBScanDevice paramIBScanDevice);
  
  void deviceFingerCountChanged(IBScanDevice paramIBScanDevice, IBScanDevice.FingerCountState paramFingerCountState);
  
  void deviceFingerQualityChanged(IBScanDevice paramIBScanDevice, IBScanDevice.FingerQualityState[] paramArrayOfFingerQualityState);
  
  void deviceImagePreviewAvailable(IBScanDevice paramIBScanDevice, IBScanDevice.ImageData paramImageData);
  
  void deviceImageResultAvailable(IBScanDevice paramIBScanDevice, IBScanDevice.ImageData paramImageData, IBScanDevice.ImageType paramImageType, IBScanDevice.ImageData[] paramArrayOfImageData);
  
  void deviceImageResultExtendedAvailable(IBScanDevice paramIBScanDevice, IBScanException paramIBScanException, IBScanDevice.ImageData paramImageData, IBScanDevice.ImageType paramImageType, int paramInt, IBScanDevice.ImageData[] paramArrayOfImageData, IBScanDevice.SegmentPosition[] paramArrayOfSegmentPosition);
  
  void devicePlatenStateChanged(IBScanDevice paramIBScanDevice, IBScanDevice.PlatenState paramPlatenState);
  
  void devicePressedKeyButtons(IBScanDevice paramIBScanDevice, int paramInt);
  
  void deviceWarningReceived(IBScanDevice paramIBScanDevice, IBScanException paramIBScanException);
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\integratedbiometrics\ibscanultimate\IBScanDeviceListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */