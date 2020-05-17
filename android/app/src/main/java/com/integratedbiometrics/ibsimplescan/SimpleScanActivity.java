package com.integratedbiometrics.ibsimplescan;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.integratedbiometrics.ibscanultimate.IBScan;
import com.integratedbiometrics.ibscanultimate.IBScanDevice;
import com.integratedbiometrics.ibscanultimate.IBScanDeviceListener;
import com.integratedbiometrics.ibscanultimate.IBScanException;
import com.integratedbiometrics.ibscanultimate.IBScanListener;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Vector;
import uk.co.senab.photoview.PhotoViewAttacher;

public class SimpleScanActivity extends Activity implements IBScanListener, IBScanDeviceListener {
  protected static final String FILE_NAME_DEFAULT = "output";
  
  protected static final int FINGER_QUALITIES_COUNT = 4;
  
  protected static final int FINGER_QUALITY_FAIR_COLOR = -256;
  
  protected static final int FINGER_QUALITY_GOOD_COLOR = -16711936;
  
  protected static final int FINGER_QUALITY_NOT_PRESENT_COLOR = -3355444;
  
  protected static final int FINGER_QUALITY_POOR_COLOR = -65536;
  
  protected static final int FINGER_SEGMENT_COUNT = 4;
  
  protected static final int PREVIEW_IMAGE_BACKGROUND = -3355444;
  
  private static final String TAG = "Simple Scan";
  
  protected static final int __INVALID_POS__ = -1;
  
  protected static final String __NA_DEFAULT__ = "n/a";
  
  protected static final String __NFIQ_DEFAULT__ = "0-0-0-0";
  
  protected final String CAPTURE_SEQ_10_FLAT_WITH_4_FINGER_SCANNER = "10 flat fingers with 4-finger scanner";
  
  protected final String CAPTURE_SEQ_10_SINGLE_FLAT_FINGERS = "10 single flat fingers";
  
  protected final String CAPTURE_SEQ_10_SINGLE_ROLLED_FINGERS = "10 single rolled fingers";
  
  protected final String CAPTURE_SEQ_2_FLAT_FINGERS = "2 flat fingers";
  
  protected final String CAPTURE_SEQ_4_FLAT_FINGERS = "4 flat fingers";
  
  protected final String CAPTURE_SEQ_FLAT_SINGLE_FINGER = "Single flat finger";
  
  protected final String CAPTURE_SEQ_ROLL_SINGLE_FINGER = "Single rolled finger";
  
  protected final int __BEEP_DEVICE_COMMUNICATION_BREAK__ = 3;
  
  protected final int __BEEP_FAIL__ = 0;
  
  protected final int __BEEP_OK__ = 2;
  
  protected final int __BEEP_SUCCESS__ = 1;
  
  protected final int __LED_COLOR_GREEN__ = 1;
  
  protected final int __LED_COLOR_NONE__ = 0;
  
  protected final int __LED_COLOR_RED__ = 2;
  
  protected final int __LED_COLOR_YELLOW__ = 3;
  
  protected final int __LEFT_KEY_BUTTON__ = 1;
  
  protected final int __RIGHT_KEY_BUTTON__ = 2;
  
  protected final int __TIMER_STATUS_DELAY__ = 500;
  
  private Bitmap m_BitmapImage;
  
  protected IBScanDevice.FingerQualityState[] m_FingerQuality = new IBScanDevice.FingerQualityState[] { IBScanDevice.FingerQualityState.FINGER_NOT_PRESENT, IBScanDevice.FingerQualityState.FINGER_NOT_PRESENT, IBScanDevice.FingerQualityState.FINGER_NOT_PRESENT, IBScanDevice.FingerQualityState.FINGER_NOT_PRESENT };
  
  protected IBScanDevice.ImageType m_ImageType;
  
  String m_ImgSaveFolder = "";
  
  protected String m_ImgSaveFolderName = "";
  
  String m_ImgSubFolder = "";
  
  protected IBScanDevice.LedState m_LedState;
  
  protected IBScanDevice.SegmentPosition[] m_SegmentPositionArray;
  
  protected ArrayList<String> m_arrCaptureSeq;
  
  protected ArrayList<String> m_arrUsbDevices;
  
  protected boolean m_bBlank = false;
  
  protected boolean m_bInitializing = false;
  
  protected boolean m_bNeedClearPlaten = false;
  
  protected boolean m_bSaveWarningOfClearPlaten;
  
  private PlaySound m_beeper = new PlaySound();
  
  private Button m_btnCaptureStart;
  
  private View.OnClickListener m_btnCaptureStartClickListener = new View.OnClickListener() {
      public void onClick(View param1View) {
        if (!SimpleScanActivity.this.m_bInitializing && SimpleScanActivity.this.m_cboUsbDevices.getSelectedItemPosition() - 1 >= 0) {
          if (SimpleScanActivity.this.m_nCurrentCaptureStep != -1)
            try {
              if (SimpleScanActivity.this.getIBScanDevice().isCaptureActive()) {
                SimpleScanActivity.this.getIBScanDevice().captureImageManually();
                return;
              } 
            } catch (IBScanException iBScanException) {
              SimpleScanActivity.this._SetStatusBarMessage("IBScanDevice.takeResultImageManually() returned exception " + iBScanException.getType().toString() + ".");
            }  
          if (SimpleScanActivity.this.getIBScanDevice() == null) {
            SimpleScanActivity.this.m_bInitializing = true;
            (new SimpleScanActivity._InitializeDeviceThreadCallback(SimpleScanActivity.this.m_nSelectedDevIndex - 1)).start();
          } else {
            SimpleScanActivity.this.OnMsg_CaptureSeqStart();
          } 
          SimpleScanActivity.this.OnMsg_UpdateDisplayResources();
          return;
        } 
      }
    };
  
  private Button m_btnCaptureStop;
  
  private View.OnClickListener m_btnCaptureStopClickListener = new View.OnClickListener() {
      public void onClick(View param1View) {
        if (SimpleScanActivity.this.getIBScanDevice() == null)
          return; 
        try {
          SimpleScanActivity.this.getIBScanDevice().cancelCaptureImage();
          SimpleScanActivity.CaptureInfo captureInfo = new SimpleScanActivity.CaptureInfo();
          SimpleScanActivity.this._SetLEDs(captureInfo, 0, false);
          SimpleScanActivity.this.m_nCurrentCaptureStep = -1;
          SimpleScanActivity.this.m_bNeedClearPlaten = false;
          SimpleScanActivity.this.m_bBlank = false;
          SimpleScanActivity.this._SetStatusBarMessage("Capture Sequence aborted");
          SimpleScanActivity.this.m_strImageMessage = "";
          SimpleScanActivity.this._SetImageMessage("");
          SimpleScanActivity.this.OnMsg_UpdateDisplayResources();
          return;
        } catch (IBScanException iBScanException) {
          SimpleScanActivity.this._SetStatusBarMessage("cancel returned exception " + iBScanException.getType().toString() + ".");
          return;
        } 
      }
    };
  
  private Button m_btnCloseEnlargedDialog;
  
  private View.OnClickListener m_btnCloseEnlargedDialogClickListener = new View.OnClickListener() {
      public void onClick(View param1View) {
        if (SimpleScanActivity.this.m_enlargedDialog != null) {
          SimpleScanActivity.this.m_enlargedDialog.cancel();
          SimpleScanActivity.access$2902(SimpleScanActivity.this, (Dialog)null);
        } 
      }
    };
  
  private AdapterView.OnItemSelectedListener m_captureTypeItemSelectedListener = new AdapterView.OnItemSelectedListener() {
      public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
        if (param1Int == 0) {
          SimpleScanActivity.this.m_btnCaptureStart.setEnabled(false);
        } else {
          SimpleScanActivity.this.m_btnCaptureStart.setEnabled(true);
        } 
        SimpleScanActivity.this.m_savedData.captureSeq = param1Int;
      }
      
      public void onNothingSelected(AdapterView<?> param1AdapterView) {
        SimpleScanActivity.this.m_savedData.captureSeq = -1;
      }
    };
  
  private Spinner m_cboCaptureSeq;
  
  private Spinner m_cboUsbDevices;
  
  private AdapterView.OnItemSelectedListener m_cboUsbDevicesItemSelectedListener = new AdapterView.OnItemSelectedListener() {
      public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
        SimpleScanActivity.this.OnMsg_cboUsbDevice_Changed();
        SimpleScanActivity.this.m_savedData.usbDevices = param1Int;
      }
      
      public void onNothingSelected(AdapterView<?> param1AdapterView) {
        SimpleScanActivity.this.m_savedData.usbDevices = -1;
      }
    };
  
  protected byte[] m_drawBuffer;
  
  private Dialog m_enlargedDialog;
  
  private IBScan m_ibScan;
  
  private IBScanDevice m_ibScanDevice;
  
  private ImageView m_imgEnlargedView;
  
  private ImageView m_imgPreview;
  
  private View.OnLongClickListener m_imgPreviewLongClickListener = new View.OnLongClickListener() {
      public boolean onLongClick(View param1View) {
        PopupMenu popupMenu = new PopupMenu((Context)SimpleScanActivity.this, (View)SimpleScanActivity.this.m_txtNFIQ);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
              public boolean onMenuItemClick(MenuItem param2MenuItem) {
                switch (param2MenuItem.getItemId()) {
                  default:
                    return false;
                  case 2131230749:
                    SimpleScanActivity.this.promptForEmail(SimpleScanActivity.this.m_lastResultImage);
                    return true;
                  case 2131230750:
                    break;
                } 
                SimpleScanActivity.this.showEnlargedImage();
                return true;
              }
            });
        popupMenu.getMenuInflater().inflate(2131165184, popupMenu.getMenu());
        popupMenu.show();
        return true;
      }
    };
  
  private IBScanDevice.ImageData m_lastResultImage;
  
  private IBScanDevice.ImageData[] m_lastSegmentImages = new IBScanDevice.ImageData[4];
  
  protected int m_leftMargin;
  
  protected String m_minSDKVersion = "";
  
  protected int m_nCurrentCaptureStep = -1;
  
  protected int m_nSegmentImageArrayCount = 0;
  
  protected int m_nSelectedDevIndex = -1;
  
  private AppData m_savedData = new AppData();
  
  protected double m_scaleFactor;
  
  protected String m_strImageMessage = "";
  
  protected int m_topMargin;
  
  private TextView m_txtDeviceCount;
  
  private TextView m_txtEnlargedScale;
  
  private TextView[] m_txtFingerQuality = new TextView[4];
  
  private TextView m_txtFrameTime;
  
  private TextView m_txtNFIQ;
  
  private TextView m_txtOverlayText;
  
  private TextView m_txtSDKVersion;
  
  private TextView m_txtStatusMessage;
  
  protected Vector<CaptureInfo> m_vecCaptureSeq = new Vector<CaptureInfo>();
  
  private void OnMsg_AskRecapture(final IBScanException imageStatus) {
    runOnUiThread(new Runnable() {
          public void run() {
            String str = "[Warning = " + imageStatus.getType().toString() + "] Do you want a recapture?";
            AlertDialog.Builder builder = new AlertDialog.Builder((Context)SimpleScanActivity.this);
            builder.setMessage(str);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                    SimpleScanActivity simpleScanActivity = SimpleScanActivity.this;
                    simpleScanActivity.m_nCurrentCaptureStep--;
                    SimpleScanActivity.this.OnMsg_CaptureSeqNext();
                  }
                });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                    SimpleScanActivity.this.OnMsg_CaptureSeqNext();
                  }
                });
            builder.show();
          }
        });
  }
  
  private void OnMsg_Beep(final int beepType) {
    runOnUiThread(new Runnable() {
          public void run() {
            if (beepType == 0) {
              SimpleScanActivity.this._BeepFail();
              return;
            } 
            if (beepType == 1) {
              SimpleScanActivity.this._BeepSuccess();
              return;
            } 
            if (beepType == 2) {
              SimpleScanActivity.this._BeepOk();
              return;
            } 
            if (beepType == 3) {
              SimpleScanActivity.this._BeepDeviceCommunicationBreak();
              return;
            } 
          }
        });
  }
  
  private void OnMsg_CaptureSeqNext() {
    runOnUiThread(new Runnable() {
          public void run() {
            IBScanDevice.ImageResolution imageResolution;
            if (SimpleScanActivity.this.getIBScanDevice() == null)
              return; 
            SimpleScanActivity.this.m_bBlank = false;
            for (int i = 0; i < 4; i++)
              SimpleScanActivity.this.m_FingerQuality[i] = IBScanDevice.FingerQualityState.FINGER_NOT_PRESENT; 
            SimpleScanActivity simpleScanActivity = SimpleScanActivity.this;
            simpleScanActivity.m_nCurrentCaptureStep++;
            if (SimpleScanActivity.this.m_nCurrentCaptureStep >= SimpleScanActivity.this.m_vecCaptureSeq.size()) {
              SimpleScanActivity.CaptureInfo captureInfo = new SimpleScanActivity.CaptureInfo();
              SimpleScanActivity.this._SetLEDs(captureInfo, 0, false);
              SimpleScanActivity.this.m_nCurrentCaptureStep = -1;
              SimpleScanActivity.this.OnMsg_UpdateDisplayResources();
              return;
            } 
            try {
              if (SimpleScanActivity.this.m_nCurrentCaptureStep > 0) {
                SimpleScanActivity.this._Sleep(500);
                SimpleScanActivity.this.m_strImageMessage = "";
              } 
              SimpleScanActivity.CaptureInfo captureInfo = SimpleScanActivity.this.m_vecCaptureSeq.elementAt(SimpleScanActivity.this.m_nCurrentCaptureStep);
              imageResolution = IBScanDevice.ImageResolution.RESOLUTION_500;
              if (!SimpleScanActivity.this.getIBScanDevice().isCaptureAvailable(captureInfo.ImageType, imageResolution)) {
                SimpleScanActivity.this._SetStatusBarMessage("The capture mode (" + captureInfo.ImageType + ") is not available");
                SimpleScanActivity.this.m_nCurrentCaptureStep = -1;
                SimpleScanActivity.this.OnMsg_UpdateDisplayResources();
                return;
              } 
            } catch (IBScanException iBScanException) {
              iBScanException.printStackTrace();
              SimpleScanActivity.this._SetStatusBarMessage("Failed to execute beginCaptureImage()");
              SimpleScanActivity.this.m_nCurrentCaptureStep = -1;
              return;
            } 
            SimpleScanActivity.this.getIBScanDevice().beginCaptureImage(((SimpleScanActivity.CaptureInfo)iBScanException).ImageType, imageResolution, false | true | 0x2 | 0x4);
            String str = ((SimpleScanActivity.CaptureInfo)iBScanException).PreCaptureMessage;
            SimpleScanActivity.this._SetStatusBarMessage(str);
            SimpleScanActivity.this._SetImageMessage(str);
            SimpleScanActivity.this.m_strImageMessage = str;
            SimpleScanActivity.this.m_ImageType = ((SimpleScanActivity.CaptureInfo)iBScanException).ImageType;
            SimpleScanActivity.this._SetLEDs((SimpleScanActivity.CaptureInfo)iBScanException, 2, true);
            SimpleScanActivity.this.OnMsg_UpdateDisplayResources();
          }
        });
  }
  
  private void OnMsg_CaptureSeqStart() {
    runOnUiThread(new Runnable() {
          public void run() {
            if (SimpleScanActivity.this.getIBScanDevice() == null) {
              SimpleScanActivity.this.OnMsg_UpdateDisplayResources();
              return;
            } 
            String str = "";
            if (SimpleScanActivity.this.m_cboCaptureSeq.getSelectedItemPosition() > -1)
              str = SimpleScanActivity.this.m_cboCaptureSeq.getSelectedItem().toString(); 
            SimpleScanActivity.this.m_vecCaptureSeq.clear();
            if (str.equals("Single flat finger"))
              SimpleScanActivity.this._AddCaptureSeqVector("Please put a single finger on the sensor!", "Keep finger on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Unknown"); 
            if (str.equals("Single rolled finger"))
              SimpleScanActivity.this._AddCaptureSeqVector("Please put a single finger on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SRF_Unknown"); 
            if (str == "2 flat fingers")
              SimpleScanActivity.this._AddCaptureSeqVector("Please put two fingers on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_TWO_FINGERS, 2, "TFF_Unknown"); 
            if (str == "10 single flat fingers") {
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right thumb on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Right_Thumb");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right index on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Right_Index");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right middle on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Right_Middle");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right ring on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Right_Ring");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right little on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Right_Little");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left thumb on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Left_Thumb");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left index on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Left_Index");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left middle on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Left_Middle");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left ring on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Left_Ring");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left little on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_SINGLE_FINGER, 1, "SFF_Left_Little");
            } 
            if (str == "10 single rolled fingers") {
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right thumb on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Right_Thumb");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right index on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Right_Index");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right middle on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Right_Middle");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right ring on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Right_Ring");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right little on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Right_Little");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left thumb on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Left_Thumb");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left index on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Left_Index");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left middle on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Left_Middle");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left ring on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Left_Ring");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left little on the sensor!", "Roll finger!", IBScanDevice.ImageType.ROLL_SINGLE_FINGER, 1, "SFF_Left_Little");
            } 
            if (str == "4 flat fingers")
              SimpleScanActivity.this._AddCaptureSeqVector("Please put 4 fingers on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_FOUR_FINGERS, 4, "4FF_Unknown"); 
            if (str == "10 flat fingers with 4-finger scanner") {
              SimpleScanActivity.this._AddCaptureSeqVector("Please put right 4-fingers on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_FOUR_FINGERS, 4, "4FF_Right_4_Fingers");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put left 4-fingers on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_FOUR_FINGERS, 4, "4FF_Left_4_Fingers");
              SimpleScanActivity.this._AddCaptureSeqVector("Please put 2-thumbs on the sensor!", "Keep fingers on the sensor!", IBScanDevice.ImageType.FLAT_TWO_FINGERS, 2, "TFF_2_Thumbs");
            } 
            SimpleScanActivity.this.OnMsg_CaptureSeqNext();
          }
        });
  }
  
  private void OnMsg_DeviceCommunicationBreak() {
    runOnUiThread(new Runnable() {
          public void run() {
            if (SimpleScanActivity.this.getIBScanDevice() != null) {
              SimpleScanActivity.this._SetStatusBarMessage("Device communication was broken");
              try {
                SimpleScanActivity.this._ReleaseDevice();
                SimpleScanActivity.this.OnMsg_Beep(3);
                SimpleScanActivity.this.OnMsg_UpdateDeviceList(false);
                return;
              } catch (IBScanException iBScanException) {}
            } 
          }
        });
  }
  
  private void OnMsg_DrawFingerQuality() {
    runOnUiThread(new Runnable() {
          public void run() {
            for (int i = 0; i < 4; i++) {
              int j;
              if (SimpleScanActivity.this.m_FingerQuality[i] == IBScanDevice.FingerQualityState.GOOD) {
                j = Color.rgb(0, 128, 0);
              } else if (SimpleScanActivity.this.m_FingerQuality[i] == IBScanDevice.FingerQualityState.FAIR) {
                j = Color.rgb(255, 128, 0);
              } else if (SimpleScanActivity.this.m_FingerQuality[i] == IBScanDevice.FingerQualityState.POOR || SimpleScanActivity.this.m_FingerQuality[i] == IBScanDevice.FingerQualityState.INVALID_AREA_TOP || SimpleScanActivity.this.m_FingerQuality[i] == IBScanDevice.FingerQualityState.INVALID_AREA_BOTTOM || SimpleScanActivity.this.m_FingerQuality[i] == IBScanDevice.FingerQualityState.INVALID_AREA_LEFT || SimpleScanActivity.this.m_FingerQuality[i] == IBScanDevice.FingerQualityState.INVALID_AREA_RIGHT) {
                j = Color.rgb(255, 0, 0);
              } else {
                j = -3355444;
              } 
              SimpleScanActivity.this.m_savedData.fingerQualityColors[i] = j;
              SimpleScanActivity.this.m_txtFingerQuality[i].setBackgroundColor(j);
            } 
          }
        });
  }
  
  private void OnMsg_DrawImage(IBScanDevice paramIBScanDevice, final IBScanDevice.ImageData image) {
    runOnUiThread(new Runnable() {
          public void run() {
            int i = SimpleScanActivity.this.m_imgPreview.getWidth() - 20;
            int j = SimpleScanActivity.this.m_imgPreview.getHeight() - 20;
            if (j <= 0 || i <= 0)
              return; 
            try {
              if (i != SimpleScanActivity.this.m_BitmapImage.getWidth() || j != SimpleScanActivity.this.m_BitmapImage.getHeight()) {
                SimpleScanActivity.access$402(SimpleScanActivity.this, Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888));
                SimpleScanActivity.this.m_drawBuffer = new byte[i * j * 4];
              } 
              if (image.isFinal) {
                SimpleScanActivity.this.getIBScanDevice().generateDisplayImage(image.buffer, image.width, image.height, SimpleScanActivity.this.m_drawBuffer, i, j, (byte)-1, 2, 2, true);
              } else {
                SimpleScanActivity.this.getIBScanDevice().generateDisplayImage(image.buffer, image.width, image.height, SimpleScanActivity.this.m_drawBuffer, i, j, (byte)-1, 2, 0, true);
              } 
            } catch (IBScanException iBScanException) {
              iBScanException.printStackTrace();
            } 
            SimpleScanActivity.this.m_BitmapImage.copyPixelsFromBuffer(ByteBuffer.wrap(SimpleScanActivity.this.m_drawBuffer));
            Canvas canvas = new Canvas(SimpleScanActivity.this.m_BitmapImage);
            SimpleScanActivity.this._DrawOverlay_ImageText(canvas);
            SimpleScanActivity.this._DrawOverlay_WarningOfClearPlaten(canvas, 0, 0, i, j);
            SimpleScanActivity.this._DrawOverlay_ResultSegmentImage(canvas, image, i, j);
            SimpleScanActivity.this._DrawOverlay_RollGuideLine(canvas, image, i, j);
            SimpleScanActivity.this.m_savedData.imageBitmap = SimpleScanActivity.this.m_BitmapImage;
            SimpleScanActivity.this.m_imgPreview.setImageBitmap(SimpleScanActivity.this.m_BitmapImage);
          }
        });
  }
  
  private void OnMsg_SetStatusBarMessage(final String s) {
    runOnUiThread(new Runnable() {
          public void run() {
            SimpleScanActivity.this._SetStatusBarMessage(s);
          }
        });
  }
  
  private void OnMsg_SetTxtNFIQScore(final String s) {
    runOnUiThread(new Runnable() {
          public void run() {
            SimpleScanActivity.this._SetTxtNFIQScore(s);
          }
        });
  }
  
  private void OnMsg_UpdateDeviceList(final boolean bConfigurationChanged) {
    runOnUiThread(new Runnable() {
          public void run() {
            boolean bool = false;
            try {
              if ((!SimpleScanActivity.this.m_bInitializing && SimpleScanActivity.this.m_nCurrentCaptureStep == -1) || bConfigurationChanged)
                bool = true; 
              if (bool) {
                SimpleScanActivity.this.m_btnCaptureStop.setEnabled(false);
                SimpleScanActivity.this.m_btnCaptureStart.setEnabled(false);
              } 
              String str = "";
              if (SimpleScanActivity.this.m_cboUsbDevices.getSelectedItemPosition() > -1)
                str = SimpleScanActivity.this.m_cboUsbDevices.getSelectedItem().toString(); 
              SimpleScanActivity.this.m_arrUsbDevices = new ArrayList<String>();
              SimpleScanActivity.this.m_arrUsbDevices.add("- Please select -");
              int k = SimpleScanActivity.this.getIBScan().getDeviceCount();
              SimpleScanActivity.this.setDeviceCount(k);
              int i = 0;
              for (int j = 0;; j++) {
                if (j < k) {
                  IBScan.DeviceDesc deviceDesc = SimpleScanActivity.this.getIBScan().getDeviceDescription(j);
                  String str1 = deviceDesc.productName + "_v" + deviceDesc.fwVersion + "(" + deviceDesc.serialNumber + ")";
                  SimpleScanActivity.this.m_arrUsbDevices.add(str1);
                  if (str1 == str)
                    i = j + 1; 
                } else {
                  ArrayAdapter arrayAdapter = new ArrayAdapter((Context)SimpleScanActivity.this, 2130903044, SimpleScanActivity.this.m_arrUsbDevices);
                  arrayAdapter.setDropDownViewResource(17367049);
                  SimpleScanActivity.this.m_cboUsbDevices.setAdapter((SpinnerAdapter)arrayAdapter);
                  SimpleScanActivity.this.m_cboUsbDevices.setOnItemSelectedListener(SimpleScanActivity.this.m_cboUsbDevicesItemSelectedListener);
                  j = i;
                  if (i == 0) {
                    j = i;
                    if (SimpleScanActivity.this.m_cboUsbDevices.getCount() == 2)
                      j = 1; 
                  } 
                  SimpleScanActivity.this.m_cboUsbDevices.setSelection(j);
                  if (bool) {
                    SimpleScanActivity.this.OnMsg_cboUsbDevice_Changed();
                    SimpleScanActivity.this._UpdateCaptureSequences();
                  } 
                  return;
                } 
              } 
            } catch (IBScanException iBScanException) {
              iBScanException.printStackTrace();
              return;
            } 
          }
        });
  }
  
  private void OnMsg_UpdateDisplayResources() {
    runOnUiThread(new Runnable() {
          public void run() {
            boolean bool1;
            boolean bool2;
            boolean bool3;
            boolean bool4;
            boolean bool5 = true;
            if (SimpleScanActivity.this.m_cboUsbDevices.getSelectedItemPosition() > 0) {
              bool1 = true;
            } else {
              bool1 = false;
            } 
            if (SimpleScanActivity.this.m_cboCaptureSeq.getSelectedItemPosition() > 0) {
              bool2 = true;
            } else {
              bool2 = false;
            } 
            if (!SimpleScanActivity.this.m_bInitializing && SimpleScanActivity.this.m_nCurrentCaptureStep == -1) {
              bool3 = true;
            } else {
              bool3 = false;
            } 
            if (!SimpleScanActivity.this.m_bInitializing && SimpleScanActivity.this.m_nCurrentCaptureStep != -1) {
              bool4 = true;
            } else {
              bool4 = false;
            } 
            if (!bool1 || SimpleScanActivity.this.getIBScanDevice() == null);
            SimpleScanActivity.this.m_cboUsbDevices.setEnabled(bool3);
            Spinner spinner = SimpleScanActivity.this.m_cboCaptureSeq;
            if (bool1 && bool3) {
              bool3 = bool5;
            } else {
              bool3 = false;
            } 
            spinner.setEnabled(bool3);
            SimpleScanActivity.this.m_btnCaptureStart.setEnabled(bool2);
            SimpleScanActivity.this.m_btnCaptureStop.setEnabled(bool4);
            if (bool4) {
              SimpleScanActivity.this.m_btnCaptureStart.setText("Take Result Image");
              return;
            } 
            SimpleScanActivity.this.m_btnCaptureStart.setText("Start");
          }
        });
  }
  
  private void OnMsg_cboUsbDevice_Changed() {
    runOnUiThread(new Runnable() {
          public void run() {
            if (SimpleScanActivity.this.m_nSelectedDevIndex == SimpleScanActivity.this.m_cboUsbDevices.getSelectedItemPosition())
              return; 
            SimpleScanActivity.this.m_nSelectedDevIndex = SimpleScanActivity.this.m_cboUsbDevices.getSelectedItemPosition();
            if (SimpleScanActivity.this.getIBScanDevice() != null)
              try {
                SimpleScanActivity.this._ReleaseDevice();
              } catch (IBScanException iBScanException) {
                iBScanException.printStackTrace();
              }  
            SimpleScanActivity.this._UpdateCaptureSequences();
          }
        });
  }
  
  private void _InitUIFields() {
    this.m_txtDeviceCount = (TextView)findViewById(2131230736);
    this.m_txtNFIQ = (TextView)findViewById(2131230738);
    this.m_txtStatusMessage = (TextView)findViewById(2131230747);
    this.m_txtOverlayText = (TextView)findViewById(2131230746);
    this.m_txtFingerQuality[0] = (TextView)findViewById(2131230741);
    this.m_txtFingerQuality[1] = (TextView)findViewById(2131230742);
    this.m_txtFingerQuality[2] = (TextView)findViewById(2131230743);
    this.m_txtFingerQuality[3] = (TextView)findViewById(2131230744);
    this.m_txtFrameTime = (TextView)findViewById(2131230740);
    this.m_txtSDKVersion = (TextView)findViewById(2131230728);
    this.m_imgPreview = (ImageView)findViewById(2131230745);
    this.m_imgPreview.setOnLongClickListener(this.m_imgPreviewLongClickListener);
    this.m_imgPreview.setBackgroundColor(-3355444);
    this.m_btnCaptureStop = (Button)findViewById(2131230733);
    this.m_btnCaptureStop.setOnClickListener(this.m_btnCaptureStopClickListener);
    this.m_btnCaptureStart = (Button)findViewById(2131230734);
    this.m_btnCaptureStart.setOnClickListener(this.m_btnCaptureStartClickListener);
    this.m_cboUsbDevices = (Spinner)findViewById(2131230731);
    this.m_cboCaptureSeq = (Spinner)findViewById(2131230732);
  }
  
  private void _PopulateUI() {
    setSDKVersionInfo();
    if (this.m_savedData.usbDevices != -1)
      this.m_cboUsbDevices.setSelection(this.m_savedData.usbDevices); 
    if (this.m_savedData.captureSeq != -1)
      this.m_cboCaptureSeq.setSelection(this.m_savedData.captureSeq); 
    if (this.m_savedData.nfiq != null)
      this.m_txtNFIQ.setText(this.m_savedData.nfiq); 
    if (this.m_savedData.frameTime != null)
      this.m_txtFrameTime.setText(this.m_savedData.frameTime); 
    if (this.m_savedData.overlayText != null) {
      this.m_txtOverlayText.setTextColor(this.m_savedData.overlayColor);
      this.m_txtOverlayText.setText(this.m_savedData.overlayText);
    } 
    if (this.m_savedData.imageBitmap != null)
      this.m_imgPreview.setImageBitmap(this.m_savedData.imageBitmap); 
    for (int i = 0; i < 4; i++)
      this.m_txtFingerQuality[i].setBackgroundColor(this.m_savedData.fingerQualityColors[i]); 
    if (this.m_BitmapImage != null)
      this.m_BitmapImage.isRecycled(); 
    this.m_imgPreview.setLongClickable(this.m_savedData.imagePreviewImageClickable);
  }
  
  private void attachAndSendEmail(ArrayList paramArrayList, String paramString1, String paramString2) {
    Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
    intent.setType("message/rfc822");
    intent.putExtra("android.intent.extra.EMAIL", new String[] { "" });
    intent.putExtra("android.intent.extra.SUBJECT", paramString1);
    intent.putParcelableArrayListExtra("android.intent.extra.STREAM", paramArrayList);
    intent.putExtra("android.intent.extra.TEXT", paramString2);
    try {
      startActivity(Intent.createChooser(intent, "Send mail..."));
      return;
    } catch (ActivityNotFoundException activityNotFoundException) {
      showToastOnUiThread("There are no e-mail clients installed", 1);
      return;
    } 
  }
  
  private static void exitApp(Activity paramActivity) {
    paramActivity.moveTaskToBack(true);
    paramActivity.finish();
    Process.killProcess(Process.myPid());
  }
  
  private void promptForEmail(final IBScanDevice.ImageData imageData) {
    runOnUiThread(new Runnable() {
          public void run() {
            final View fileNameView = SimpleScanActivity.this.getLayoutInflater().inflate(2130903041, null);
            AlertDialog.Builder builder = (new AlertDialog.Builder((Context)SimpleScanActivity.this)).setView(view).setTitle("Enter file name").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                    (new Thread() {
                        public void run() {
                          SimpleScanActivity.this.sendImageInEmail(imageData, fileName);
                        }
                      }).start();
                  }
                }).setNegativeButton("Cancel", null);
            ((EditText)view.findViewById(2131230724)).setText("output");
            builder.create().show();
          }
        });
  }
  
  private void sendImageInEmail(IBScanDevice.ImageData paramImageData, String paramString) {
    boolean bool2 = false;
    ArrayList<Uri> arrayList = new ArrayList();
    try {
      String str1 = Environment.getExternalStorageDirectory().getPath() + "/" + paramString + ".png";
      String str2 = Environment.getExternalStorageDirectory().getPath() + "/" + paramString + ".bmp";
      String str3 = Environment.getExternalStorageDirectory().getPath() + "/" + paramString + ".jp2";
      String str4 = Environment.getExternalStorageDirectory().getPath() + "/" + paramString + ".wsq";
      getIBScanDevice().SavePngImage(str1, paramImageData.buffer, paramImageData.width, paramImageData.height, paramImageData.pitch, paramImageData.resolutionX, paramImageData.resolutionY);
      getIBScanDevice().SaveBitmapImage(str2, paramImageData.buffer, paramImageData.width, paramImageData.height, paramImageData.pitch, paramImageData.resolutionX, paramImageData.resolutionY);
      getIBScanDevice().SaveJP2Image(str3, paramImageData.buffer, paramImageData.width, paramImageData.height, paramImageData.pitch, paramImageData.resolutionX, paramImageData.resolutionY, 80);
      getIBScanDevice().wsqEncodeToFile(str4, paramImageData.buffer, paramImageData.width, paramImageData.height, paramImageData.pitch, paramImageData.bitsPerPixel, 500, 0.75D, "");
      arrayList.add(Uri.fromFile(new File(str1)));
      arrayList.add(Uri.fromFile(new File(str2)));
      arrayList.add(Uri.fromFile(new File(str3)));
      arrayList.add(Uri.fromFile(new File(str4)));
      for (int i = 0;; i++) {
        if (i < this.m_nSegmentImageArrayCount) {
          String str = Environment.getExternalStorageDirectory().getPath() + "/segment_" + i + "_" + paramString + ".png";
          str1 = Environment.getExternalStorageDirectory().getPath() + "/segment_" + i + "_" + paramString + ".bmp";
          str2 = Environment.getExternalStorageDirectory().getPath() + "/segment_" + i + "_" + paramString + ".jp2";
          str3 = Environment.getExternalStorageDirectory().getPath() + "/segment_" + i + "_" + paramString + ".wsq";
          try {
            getIBScanDevice().SavePngImage(str, (this.m_lastSegmentImages[i]).buffer, (this.m_lastSegmentImages[i]).width, (this.m_lastSegmentImages[i]).height, (this.m_lastSegmentImages[i]).pitch, (this.m_lastSegmentImages[i]).resolutionX, (this.m_lastSegmentImages[i]).resolutionY);
            getIBScanDevice().SaveBitmapImage(str1, (this.m_lastSegmentImages[i]).buffer, (this.m_lastSegmentImages[i]).width, (this.m_lastSegmentImages[i]).height, (this.m_lastSegmentImages[i]).pitch, (this.m_lastSegmentImages[i]).resolutionX, (this.m_lastSegmentImages[i]).resolutionY);
            getIBScanDevice().SaveJP2Image(str2, (this.m_lastSegmentImages[i]).buffer, (this.m_lastSegmentImages[i]).width, (this.m_lastSegmentImages[i]).height, (this.m_lastSegmentImages[i]).pitch, (this.m_lastSegmentImages[i]).resolutionX, (this.m_lastSegmentImages[i]).resolutionY, 80);
            getIBScanDevice().wsqEncodeToFile(str3, (this.m_lastSegmentImages[i]).buffer, (this.m_lastSegmentImages[i]).width, (this.m_lastSegmentImages[i]).height, (this.m_lastSegmentImages[i]).pitch, (this.m_lastSegmentImages[i]).bitsPerPixel, 500, 0.75D, "");
            arrayList.add(Uri.fromFile(new File(str)));
            arrayList.add(Uri.fromFile(new File(str1)));
            arrayList.add(Uri.fromFile(new File(str2)));
            arrayList.add(Uri.fromFile(new File(str3)));
          } catch (IBScanException iBScanException) {}
        } else {
          break;
        } 
      } 
    } catch (IBScanException iBScanException) {
      Toast.makeText(getApplicationContext(), "Could not create image for e-mail", 1).show();
      boolean bool = bool2;
      if (bool)
        attachAndSendEmail(arrayList, "Fingerprint Image", paramString); 
      return;
    } 
    boolean bool1 = true;
    if (bool1)
      attachAndSendEmail(arrayList, "Fingerprint Image", paramString); 
  }
  
  private void setDeviceCount(final int deviceCount) {
    runOnUiThread(new Runnable() {
          public void run() {
            SimpleScanActivity.this.m_txtDeviceCount.setText("" + deviceCount);
          }
        });
  }
  
  private void setFrameTime(final String s) {
    this.m_savedData.frameTime = s;
    runOnUiThread(new Runnable() {
          public void run() {
            SimpleScanActivity.this.m_txtFrameTime.setText(s);
          }
        });
  }
  
  private void setSDKVersionInfo() {
    final String txtValueTemp;
    try {
      IBScan.SdkVersion sdkVersion = this.m_ibScan.getSdkVersion();
      str = "SDK version: " + sdkVersion.file;
    } catch (IBScanException iBScanException) {
      str = "(failure)";
    } 
    runOnUiThread(new Runnable() {
          public void run() {
            SimpleScanActivity.this.m_txtSDKVersion.setText(txtValueTemp);
          }
        });
  }
  
  private void showEnlargedImage() {
    final float zoom_1x;
    if (this.m_lastResultImage == null) {
      showToastOnUiThread("No last image information", 0);
      return;
    } 
    this.m_enlargedDialog = new Dialog((Context)this, 2131099648);
    this.m_enlargedDialog.setContentView(2130903040);
    this.m_enlargedDialog.setCancelable(false);
    Bitmap bitmap = this.m_lastResultImage.toBitmap();
    this.m_imgEnlargedView = (ImageView)this.m_enlargedDialog.findViewById(2131230723);
    this.m_btnCloseEnlargedDialog = (Button)this.m_enlargedDialog.findViewById(2131230722);
    this.m_txtEnlargedScale = (TextView)this.m_enlargedDialog.findViewById(2131230721);
    this.m_imgEnlargedView.setScaleType(ImageView.ScaleType.CENTER);
    this.m_imgEnlargedView.setImageBitmap(bitmap);
    this.m_btnCloseEnlargedDialog.setOnClickListener(this.m_btnCloseEnlargedDialogClickListener);
    final PhotoViewAttacher mAttacher = new PhotoViewAttacher(this.m_imgEnlargedView);
    Display display = getWindowManager().getDefaultDisplay();
    Point point = new Point();
    display.getSize(point);
    int i = point.x - 20;
    int j = point.y - 50;
    if (i / this.m_lastResultImage.width > j / this.m_lastResultImage.height) {
      f = this.m_lastResultImage.height / j;
    } else {
      f = this.m_lastResultImage.width / i;
    } 
    photoViewAttacher.setMaximumScale(8.0F * f);
    photoViewAttacher.setMediumScale(4.0F * f);
    photoViewAttacher.setMinimumScale(f);
    photoViewAttacher.setOnMatrixChangeListener(new PhotoViewAttacher.OnMatrixChangedListener() {
          public void onMatrixChanged(RectF param1RectF) {
            SimpleScanActivity.this.m_txtEnlargedScale.setText(String.format("Scale : %1$.1f x", new Object[] { Float.valueOf(this.val$mAttacher.getScale() / this.val$zoom_1x) }));
          }
        });
    this.m_imgEnlargedView.post(new Runnable() {
          public void run() {
            mAttacher.setScale(zoom_1x, false);
          }
        });
    this.m_enlargedDialog.show();
  }
  
  private void showToastOnUiThread(final String message, final int duration) {
    runOnUiThread(new Runnable() {
          public void run() {
            Toast.makeText(SimpleScanActivity.this.getApplicationContext(), message, duration).show();
          }
        });
  }
  
  protected void _AddCaptureSeqVector(String paramString1, String paramString2, IBScanDevice.ImageType paramImageType, int paramInt, String paramString3) {
    CaptureInfo captureInfo = new CaptureInfo();
    captureInfo.PreCaptureMessage = paramString1;
    captureInfo.PostCaptuerMessage = paramString2;
    captureInfo.ImageType = paramImageType;
    captureInfo.NumberOfFinger = paramInt;
    captureInfo.fingerName = paramString3;
    this.m_vecCaptureSeq.addElement(captureInfo);
  }
  
  protected void _BeepDeviceCommunicationBreak() {
    for (int i = 0; i < 8; i++) {
      (new ToneGenerator(4, 30)).startTone(93, 100);
      _Sleep(200);
    } 
  }
  
  protected void _BeepFail() {
    try {
      if (getIBScanDevice().getOperableBeeper() != IBScanDevice.BeeperType.BEEPER_TYPE_NONE) {
        getIBScanDevice().setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 12, 0, 0);
        _Sleep(150);
        getIBScanDevice().setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 6, 0, 0);
        _Sleep(150);
        getIBScanDevice().setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 6, 0, 0);
        _Sleep(150);
        getIBScanDevice().setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 6, 0, 0);
      } 
      return;
    } catch (IBScanException iBScanException) {
      ToneGenerator toneGenerator = new ToneGenerator(4, 30);
      toneGenerator.startTone(93, 300);
      _Sleep(450);
      toneGenerator.startTone(93, 150);
      _Sleep(300);
      toneGenerator.startTone(93, 150);
      _Sleep(300);
      toneGenerator.startTone(93, 150);
      return;
    } 
  }
  
  protected void _BeepOk() {
    try {
      if (getIBScanDevice().getOperableBeeper() != IBScanDevice.BeeperType.BEEPER_TYPE_NONE)
        getIBScanDevice().setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 4, 0, 0); 
      return;
    } catch (IBScanException iBScanException) {
      (new ToneGenerator(4, 30)).startTone(93, 100);
      return;
    } 
  }
  
  protected void _BeepSuccess() {
    try {
      if (getIBScanDevice().getOperableBeeper() != IBScanDevice.BeeperType.BEEPER_TYPE_NONE) {
        getIBScanDevice().setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 4, 0, 0);
        _Sleep(50);
        getIBScanDevice().setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 4, 0, 0);
      } 
      return;
    } catch (IBScanException iBScanException) {
      ToneGenerator toneGenerator = new ToneGenerator(4, 30);
      toneGenerator.startTone(93, 100);
      _Sleep(150);
      toneGenerator.startTone(93, 100);
      return;
    } 
  }
  
  protected void _CalculateScaleFactors(IBScanDevice.ImageData paramImageData, int paramInt1, int paramInt2) {
    int i = 0;
    int i1 = 0;
    int j = paramInt1;
    int k = paramInt2;
    int m = paramImageData.width;
    int n = paramImageData.height;
    if (paramInt1 > m) {
      j = m;
      i = (paramInt1 - m) / 2;
    } 
    paramInt1 = i1;
    if (paramInt2 > n) {
      k = n;
      paramInt1 = (paramInt2 - n) / 2;
    } 
    if (j / m >= k / n) {
      paramInt2 = k * m / n;
      paramInt2 -= paramInt2 % 4;
      j = (j - paramInt2) / 2 + i;
      i = paramInt2;
      paramInt2 = j;
    } else {
      i1 = j - j % 4;
      j = j * n / m;
      paramInt2 = i;
      paramInt1 = (k - j) / 2 + paramInt1;
      i = i1;
    } 
    j = paramInt2;
    if (paramInt2 < 0)
      j = 0; 
    paramInt2 = paramInt1;
    if (paramInt1 < 0)
      paramInt2 = 0; 
    this.m_scaleFactor = i / paramImageData.width;
    this.m_leftMargin = j;
    this.m_topMargin = paramInt2;
  }
  
  protected Bitmap _CreateBitmap(int paramInt1, int paramInt2) {
    Bitmap bitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
    if (bitmap != null) {
      byte[] arrayOfByte = new byte[paramInt1 * paramInt2 * 4];
      for (int i = 0; i < paramInt2; i++) {
        int j;
        for (j = 0; j < paramInt1; j++) {
          arrayOfByte[(i * paramInt1 + j) * 4 + 2] = Byte.MIN_VALUE;
          arrayOfByte[(i * paramInt1 + j) * 4 + 1] = Byte.MIN_VALUE;
          arrayOfByte[(i * paramInt1 + j) * 4] = Byte.MIN_VALUE;
          arrayOfByte[(i * paramInt1 + j) * 4 + 3] = -1;
        } 
      } 
      bitmap.copyPixelsFromBuffer(ByteBuffer.wrap(arrayOfByte));
    } 
    return bitmap;
  }
  
  protected void _DrawOverlay_ImageText(Canvas paramCanvas) {
    if (this.m_bNeedClearPlaten) {
      _SetOverlayText(this.m_strImageMessage, -65536);
      return;
    } 
    _SetOverlayText(this.m_strImageMessage, -16776961);
  }
  
  protected void _DrawOverlay_ResultSegmentImage(Canvas paramCanvas, IBScanDevice.ImageData paramImageData, int paramInt1, int paramInt2) {
    if (paramImageData.isFinal) {
      _CalculateScaleFactors(paramImageData, paramInt1, paramInt2);
      Paint paint = new Paint();
      paint.setColor(Color.rgb(0, 128, 0));
      paint.setStrokeWidth(4.0F);
      paint.setAntiAlias(true);
      for (paramInt1 = 0; paramInt1 < this.m_nSegmentImageArrayCount; paramInt1++) {
        paramInt2 = this.m_leftMargin + (int)((this.m_SegmentPositionArray[paramInt1]).x1 * this.m_scaleFactor);
        int i = this.m_leftMargin + (int)((this.m_SegmentPositionArray[paramInt1]).x2 * this.m_scaleFactor);
        int j = this.m_leftMargin + (int)((this.m_SegmentPositionArray[paramInt1]).x3 * this.m_scaleFactor);
        int k = this.m_leftMargin + (int)((this.m_SegmentPositionArray[paramInt1]).x4 * this.m_scaleFactor);
        int m = this.m_topMargin + (int)((this.m_SegmentPositionArray[paramInt1]).y1 * this.m_scaleFactor);
        int n = this.m_topMargin + (int)((this.m_SegmentPositionArray[paramInt1]).y2 * this.m_scaleFactor);
        int i1 = this.m_topMargin + (int)((this.m_SegmentPositionArray[paramInt1]).y3 * this.m_scaleFactor);
        int i2 = this.m_topMargin + (int)((this.m_SegmentPositionArray[paramInt1]).y4 * this.m_scaleFactor);
        paramCanvas.drawLine(paramInt2, m, i, n, paint);
        paramCanvas.drawLine(i, n, j, i1, paint);
        paramCanvas.drawLine(j, i1, k, i2, paint);
        paramCanvas.drawLine(k, i2, paramInt2, m, paint);
      } 
    } 
  }
  
  protected void _DrawOverlay_RollGuideLine(Canvas paramCanvas, IBScanDevice.ImageData paramImageData, int paramInt1, int paramInt2) {
    if (getIBScanDevice() != null && this.m_nCurrentCaptureStep != -1 && this.m_ImageType == IBScanDevice.ImageType.ROLL_SINGLE_FINGER) {
      Paint paint = new Paint();
      paint.setAntiAlias(true);
      try {
        IBScanDevice.RollingData rollingData = getIBScanDevice().getRollingInfo();
      } catch (IBScanException iBScanException) {
        iBScanException = null;
      } 
      if (iBScanException != null && ((IBScanDevice.RollingData)iBScanException).rollingLineX > 0 && (((IBScanDevice.RollingData)iBScanException).rollingState.equals(IBScanDevice.RollingState.TAKE_ACQUISITION) || ((IBScanDevice.RollingData)iBScanException).rollingState.equals(IBScanDevice.RollingState.COMPLETE_ACQUISITION))) {
        _CalculateScaleFactors(paramImageData, paramInt1, paramInt2);
        paramInt1 = this.m_leftMargin + (int)(((IBScanDevice.RollingData)iBScanException).rollingLineX * this.m_scaleFactor);
        if (((IBScanDevice.RollingData)iBScanException).rollingState.equals(IBScanDevice.RollingState.TAKE_ACQUISITION)) {
          paint.setColor(-65536);
        } else if (((IBScanDevice.RollingData)iBScanException).rollingState.equals(IBScanDevice.RollingState.COMPLETE_ACQUISITION)) {
          paint.setColor(-16711936);
        } 
        if (((IBScanDevice.RollingData)iBScanException).rollingLineX > -1) {
          paint.setStrokeWidth(4.0F);
          paramCanvas.drawLine(paramInt1, 0.0F, paramInt1, paramInt2, paint);
          return;
        } 
      } 
    } 
  }
  
  protected void _DrawOverlay_WarningOfClearPlaten(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (getIBScanDevice() != null) {
      boolean bool;
      if (!this.m_bInitializing && this.m_nCurrentCaptureStep == -1) {
        bool = true;
      } else {
        bool = false;
      } 
      if (!bool && this.m_bNeedClearPlaten && this.m_bBlank) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(-65536);
        paint.setStrokeWidth(20.0F);
        paint.setAntiAlias(true);
        paramCanvas.drawRect(paramInt1, paramInt2, (paramInt3 - 1), (paramInt4 - 1), paint);
        return;
      } 
    } 
  }
  
  protected void _ReleaseDevice() throws IBScanException {
    if (getIBScanDevice() != null && getIBScanDevice().isOpened() == true) {
      getIBScanDevice().close();
      setIBScanDevice((IBScanDevice)null);
    } 
    this.m_nCurrentCaptureStep = -1;
    this.m_bInitializing = false;
  }
  
  protected void _SaveBitmapImage(IBScanDevice.ImageData paramImageData, String paramString) {}
  
  protected void _SaveJP2Image(IBScanDevice.ImageData paramImageData, String paramString) {}
  
  protected void _SavePngImage(IBScanDevice.ImageData paramImageData, String paramString) {
    // Byte code:
    //   0: new java/io/File
    //   3: dup
    //   4: new java/lang/StringBuilder
    //   7: dup
    //   8: invokespecial <init> : ()V
    //   11: aload_0
    //   12: getfield m_ImgSaveFolderName : Ljava/lang/String;
    //   15: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   18: ldc_w '.png'
    //   21: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: invokevirtual toString : ()Ljava/lang/String;
    //   27: invokespecial <init> : (Ljava/lang/String;)V
    //   30: astore_3
    //   31: aconst_null
    //   32: astore_2
    //   33: aconst_null
    //   34: astore #4
    //   36: new java/io/FileOutputStream
    //   39: dup
    //   40: aload_3
    //   41: invokespecial <init> : (Ljava/io/File;)V
    //   44: astore_3
    //   45: aload_1
    //   46: invokevirtual toBitmap : ()Landroid/graphics/Bitmap;
    //   49: getstatic android/graphics/Bitmap$CompressFormat.PNG : Landroid/graphics/Bitmap$CompressFormat;
    //   52: bipush #100
    //   54: aload_3
    //   55: invokevirtual compress : (Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   58: pop
    //   59: aload_3
    //   60: invokevirtual close : ()V
    //   63: return
    //   64: astore_1
    //   65: aload_1
    //   66: invokevirtual printStackTrace : ()V
    //   69: return
    //   70: astore_3
    //   71: aload #4
    //   73: astore_1
    //   74: aload_1
    //   75: astore_2
    //   76: aload_3
    //   77: invokevirtual printStackTrace : ()V
    //   80: aload_1
    //   81: invokevirtual close : ()V
    //   84: return
    //   85: astore_1
    //   86: aload_1
    //   87: invokevirtual printStackTrace : ()V
    //   90: return
    //   91: astore_1
    //   92: aload_2
    //   93: invokevirtual close : ()V
    //   96: aload_1
    //   97: athrow
    //   98: astore_2
    //   99: aload_2
    //   100: invokevirtual printStackTrace : ()V
    //   103: goto -> 96
    //   106: astore_1
    //   107: aload_3
    //   108: astore_2
    //   109: goto -> 92
    //   112: astore_2
    //   113: aload_3
    //   114: astore_1
    //   115: aload_2
    //   116: astore_3
    //   117: goto -> 74
    // Exception table:
    //   from	to	target	type
    //   36	45	70	java/lang/Exception
    //   36	45	91	finally
    //   45	59	112	java/lang/Exception
    //   45	59	106	finally
    //   59	63	64	java/io/IOException
    //   76	80	91	finally
    //   80	84	85	java/io/IOException
    //   92	96	98	java/io/IOException
  }
  
  protected void _SaveWsqImage(IBScanDevice.ImageData paramImageData, String paramString) {
    paramString = this.m_ImgSaveFolderName + ".wsq";
    try {
      getIBScanDevice().wsqEncodeToFile(paramString, paramImageData.buffer, paramImageData.width, paramImageData.height, paramImageData.pitch, paramImageData.bitsPerPixel, 500, 0.75D, "");
      return;
    } catch (IBScanException iBScanException) {
      iBScanException.printStackTrace();
      return;
    } 
  }
  
  protected void _SetImageMessage(String paramString) {
    this.m_strImageMessage = paramString;
  }
  
  public void _SetLEDs(CaptureInfo paramCaptureInfo, int paramInt, boolean paramBoolean) {
    try {
      int j = (getIBScanDevice().getOperableLEDs()).ledCount;
      if (j == 0)
        return; 
    } catch (IBScanException iBScanException1) {
      iBScanException1.printStackTrace();
    } 
    int i = 0;
    if (paramInt == 0)
      try {
        getIBScanDevice().setLEDs(0L);
        return;
      } catch (IBScanException iBScanException) {
        iBScanException.printStackTrace();
        return;
      }  
    if (this.m_LedState.ledType == IBScanDevice.LedType.FSCAN) {
      int j = i;
      if (paramBoolean)
        if (paramInt == 1) {
          j = (int)(0L | 0x10000000L);
        } else if (paramInt == 2) {
          j = (int)(0L | 0x20000000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(0L | 0x10000000L) | 0x20000000L); 
        }  
      i = j;
      if (((CaptureInfo)iBScanException).ImageType == IBScanDevice.ImageType.ROLL_SINGLE_FINGER)
        i = (int)(j | 0x10L); 
      if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Right_Thumb") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Right_Thumb")) {
        i = (int)(i | 0x40L);
        if (paramInt == 1) {
          j = (int)(i | 0x40000L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x80000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x40000L) | 0x80000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Left_Thumb") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Left_Thumb")) {
        i = (int)(i | 0x40L);
        if (paramInt == 1) {
          j = (int)(i | 0x10000L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x20000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x10000L) | 0x20000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("TFF_2_Thumbs")) {
        i = (int)(i | 0x40L);
        if (paramInt == 1) {
          j = (int)((int)(i | 0x10000L) | 0x40000L);
        } else if (paramInt == 2) {
          j = (int)((int)(i | 0x20000L) | 0x80000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)((int)((int)(i | 0x10000L) | 0x20000L) | 0x40000L) | 0x80000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Left_Index") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Left_Index")) {
        i = (int)(i | 0x20L);
        if (paramInt == 1) {
          j = (int)(i | 0x400000L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x800000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x400000L) | 0x800000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Left_Middle") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Left_Middle")) {
        i = (int)(i | 0x20L);
        if (paramInt == 1) {
          j = (int)(i | 0x100000L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x200000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x100000L) | 0x200000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Left_Ring") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Left_Ring")) {
        i = (int)(i | 0x20L);
        if (paramInt == 1) {
          j = (int)(i | 0x4000000L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x8000000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x4000000L) | 0x8000000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Left_Little") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Left_Little")) {
        i = (int)(i | 0x20L);
        if (paramInt == 1) {
          j = (int)(i | 0x1000000L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x2000000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x1000000L) | 0x2000000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("4FF_Left_4_Fingers")) {
        i = (int)(i | 0x20L);
        if (paramInt == 1) {
          j = (int)((int)((int)((int)(i | 0x400000L) | 0x100000L) | 0x4000000L) | 0x1000000L);
        } else if (paramInt == 2) {
          j = (int)((int)((int)((int)(i | 0x800000L) | 0x200000L) | 0x8000000L) | 0x2000000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)((int)((int)((int)((int)((int)((int)(i | 0x400000L) | 0x100000L) | 0x4000000L) | 0x1000000L) | 0x800000L) | 0x200000L) | 0x8000000L) | 0x2000000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Right_Index") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Right_Index")) {
        i = (int)(i | 0x80L);
        if (paramInt == 1) {
          j = (int)(i | 0x1000L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x2000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x1000L) | 0x2000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Right_Middle") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Right_Middle")) {
        i = (int)(i | 0x80L);
        if (paramInt == 1) {
          j = (int)(i | 0x4000L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x40000000L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x4000L) | 0x40000000L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Right_Ring") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Right_Ring")) {
        i = (int)(i | 0x80L);
        if (paramInt == 1) {
          j = (int)(i | 0x100L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x200L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x100L) | 0x200L); 
        } 
      } else if (((CaptureInfo)iBScanException).fingerName.equals("SFF_Right_Little") || ((CaptureInfo)iBScanException).fingerName.equals("SRF_Right_Little")) {
        i = (int)(i | 0x80L);
        if (paramInt == 1) {
          j = (int)(i | 0x400L);
        } else if (paramInt == 2) {
          j = (int)(i | 0x800L);
        } else {
          j = i;
          if (paramInt == 3)
            j = (int)((int)(i | 0x400L) | 0x800L); 
        } 
      } else {
        j = i;
        if (((CaptureInfo)iBScanException).fingerName.equals("4FF_Right_4_Fingers")) {
          i = (int)(i | 0x80L);
          if (paramInt == 1) {
            j = (int)((int)((int)((int)(i | 0x1000L) | 0x4000L) | 0x100L) | 0x400L);
          } else if (paramInt == 2) {
            j = (int)((int)((int)((int)(i | 0x2000L) | 0x40000000L) | 0x200L) | 0x800L);
          } else {
            j = i;
            if (paramInt == 3)
              j = (int)((int)((int)((int)((int)((int)((int)((int)(i | 0x1000L) | 0x4000L) | 0x100L) | 0x400L) | 0x2000L) | 0x40000000L) | 0x200L) | 0x800L); 
          } 
        } 
      } 
      if (paramInt == 0)
        j = 0; 
      try {
        getIBScanDevice().setLEDs(j);
        return;
      } catch (IBScanException iBScanException1) {
        iBScanException1.printStackTrace();
        return;
      } 
    } 
  }
  
  protected void _SetOverlayText(final String s, final int txtColor) {
    this.m_savedData.overlayText = s;
    this.m_savedData.overlayColor = txtColor;
    runOnUiThread(new Runnable() {
          public void run() {
            SimpleScanActivity.this.m_txtOverlayText.setTextColor(txtColor);
            SimpleScanActivity.this.m_txtOverlayText.setText(s);
          }
        });
  }
  
  protected void _SetStatusBarMessage(final String s) {
    runOnUiThread(new Runnable() {
          public void run() {
            SimpleScanActivity.this.m_txtStatusMessage.setText(s);
          }
        });
  }
  
  protected void _SetTxtNFIQScore(final String s) {
    this.m_savedData.nfiq = s;
    runOnUiThread(new Runnable() {
          public void run() {
            SimpleScanActivity.this.m_txtNFIQ.setText(s);
          }
        });
  }
  
  protected void _Sleep(int paramInt) {
    long l = paramInt;
    try {
      Thread.sleep(l);
      return;
    } catch (InterruptedException interruptedException) {
      return;
    } 
  }
  
  protected void _UpdateCaptureSequences() {
    try {
      if (this.m_cboCaptureSeq.getSelectedItemPosition() > -1)
        this.m_cboCaptureSeq.getSelectedItem().toString(); 
      this.m_arrCaptureSeq = new ArrayList<String>();
      this.m_arrCaptureSeq.add("- Please select -");
      int i = this.m_cboUsbDevices.getSelectedItemPosition() - 1;
      if (i > -1) {
        IBScan.DeviceDesc deviceDesc = getIBScan().getDeviceDescription(i);
        if (deviceDesc.productName.equals("WATSON") || deviceDesc.productName.equals("WATSON MINI") || deviceDesc.productName.equals("SHERLOCK_ROIC") || deviceDesc.productName.equals("SHERLOCK")) {
          this.m_arrCaptureSeq.add("Single flat finger");
          this.m_arrCaptureSeq.add("Single rolled finger");
          this.m_arrCaptureSeq.add("2 flat fingers");
          this.m_arrCaptureSeq.add("10 single flat fingers");
          this.m_arrCaptureSeq.add("10 single rolled fingers");
        } else if (deviceDesc.productName.equals("COLUMBO") || deviceDesc.productName.equals("CURVE")) {
          this.m_arrCaptureSeq.add("Single flat finger");
          this.m_arrCaptureSeq.add("10 single flat fingers");
        } else if (deviceDesc.productName.equals("HOLMES") || deviceDesc.productName.equals("KOJAK") || deviceDesc.productName.equals("FIVE-0") || deviceDesc.productName.equals("TF10")) {
          this.m_arrCaptureSeq.add("Single flat finger");
          this.m_arrCaptureSeq.add("Single rolled finger");
          this.m_arrCaptureSeq.add("2 flat fingers");
          this.m_arrCaptureSeq.add("4 flat fingers");
          this.m_arrCaptureSeq.add("10 single flat fingers");
          this.m_arrCaptureSeq.add("10 single rolled fingers");
          this.m_arrCaptureSeq.add("10 flat fingers with 4-finger scanner");
        } 
      } 
      ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 2130903044, this.m_arrCaptureSeq);
      arrayAdapter.setDropDownViewResource(17367049);
      this.m_cboCaptureSeq.setAdapter((SpinnerAdapter)arrayAdapter);
      this.m_cboCaptureSeq.setOnItemSelectedListener(this.m_captureTypeItemSelectedListener);
      OnMsg_UpdateDisplayResources();
      return;
    } catch (IBScanException iBScanException) {
      iBScanException.printStackTrace();
      return;
    } 
  }
  
  public void deviceAcquisitionBegun(IBScanDevice paramIBScanDevice, IBScanDevice.ImageType paramImageType) {
    if (paramImageType.equals(IBScanDevice.ImageType.ROLL_SINGLE_FINGER)) {
      OnMsg_Beep(2);
      this.m_strImageMessage = "When done remove finger from sensor";
      _SetImageMessage(this.m_strImageMessage);
      _SetStatusBarMessage(this.m_strImageMessage);
    } 
  }
  
  public void deviceAcquisitionCompleted(IBScanDevice paramIBScanDevice, IBScanDevice.ImageType paramImageType) {
    if (paramImageType.equals(IBScanDevice.ImageType.ROLL_SINGLE_FINGER)) {
      OnMsg_Beep(2);
      return;
    } 
    OnMsg_Beep(1);
    _SetImageMessage("Remove fingers from sensor");
    _SetStatusBarMessage("Acquisition completed, postprocessing..");
  }
  
  public void deviceCommunicationBroken(IBScanDevice paramIBScanDevice) {
    OnMsg_DeviceCommunicationBreak();
  }
  
  public void deviceFingerCountChanged(IBScanDevice paramIBScanDevice, IBScanDevice.FingerCountState paramFingerCountState) {
    CaptureInfo captureInfo;
    if (this.m_nCurrentCaptureStep >= 0) {
      captureInfo = this.m_vecCaptureSeq.elementAt(this.m_nCurrentCaptureStep);
      if (paramFingerCountState == IBScanDevice.FingerCountState.NON_FINGER) {
        _SetLEDs(captureInfo, 2, true);
        return;
      } 
    } else {
      return;
    } 
    _SetLEDs(captureInfo, 3, true);
  }
  
  public void deviceFingerQualityChanged(IBScanDevice paramIBScanDevice, IBScanDevice.FingerQualityState[] paramArrayOfFingerQualityState) {
    for (int i = 0; i < paramArrayOfFingerQualityState.length; i++)
      this.m_FingerQuality[i] = paramArrayOfFingerQualityState[i]; 
    OnMsg_DrawFingerQuality();
  }
  
  public void deviceImagePreviewAvailable(IBScanDevice paramIBScanDevice, IBScanDevice.ImageData paramImageData) {
    setFrameTime(String.format("%1$.3f ms", new Object[] { Double.valueOf(paramImageData.frameTime * 1000.0D) }));
    OnMsg_DrawImage(paramIBScanDevice, paramImageData);
  }
  
  public void deviceImageResultAvailable(IBScanDevice paramIBScanDevice, IBScanDevice.ImageData paramImageData, IBScanDevice.ImageType paramImageType, IBScanDevice.ImageData[] paramArrayOfImageData) {}
  
  public void deviceImageResultExtendedAvailable(IBScanDevice paramIBScanDevice, IBScanException paramIBScanException, IBScanDevice.ImageData paramImageData, IBScanDevice.ImageType paramImageType, int paramInt, IBScanDevice.ImageData[] paramArrayOfImageData, IBScanDevice.SegmentPosition[] paramArrayOfSegmentPosition) {
    // Byte code:
    //   0: aload_0
    //   1: ldc_w '%1$.3f ms'
    //   4: iconst_1
    //   5: anewarray java/lang/Object
    //   8: dup
    //   9: iconst_0
    //   10: aload_3
    //   11: getfield frameTime : D
    //   14: ldc2_w 1000.0
    //   17: dmul
    //   18: invokestatic valueOf : (D)Ljava/lang/Double;
    //   21: aastore
    //   22: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   25: invokespecial setFrameTime : (Ljava/lang/String;)V
    //   28: aload_0
    //   29: getfield m_savedData : Lcom/integratedbiometrics/ibsimplescan/SimpleScanActivity$AppData;
    //   32: iconst_1
    //   33: putfield imagePreviewImageClickable : Z
    //   36: aload_0
    //   37: getfield m_imgPreview : Landroid/widget/ImageView;
    //   40: iconst_1
    //   41: invokevirtual setLongClickable : (Z)V
    //   44: aload_0
    //   45: aload_3
    //   46: putfield m_lastResultImage : Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$ImageData;
    //   49: aload_0
    //   50: aload #6
    //   52: putfield m_lastSegmentImages : [Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$ImageData;
    //   55: aload_2
    //   56: ifnull -> 72
    //   59: aload_2
    //   60: invokevirtual getType : ()Lcom/integratedbiometrics/ibscanultimate/IBScanException$Type;
    //   63: getstatic com/integratedbiometrics/ibscanultimate/IBScanException$Type.INVALID_PARAM_VALUE : Lcom/integratedbiometrics/ibscanultimate/IBScanException$Type;
    //   66: invokevirtual compareTo : (Ljava/lang/Enum;)I
    //   69: ifle -> 88
    //   72: aload #4
    //   74: getstatic com/integratedbiometrics/ibscanultimate/IBScanDevice$ImageType.ROLL_SINGLE_FINGER : Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$ImageType;
    //   77: invokevirtual equals : (Ljava/lang/Object;)Z
    //   80: ifeq -> 88
    //   83: aload_0
    //   84: iconst_1
    //   85: invokespecial OnMsg_Beep : (I)V
    //   88: aload_0
    //   89: getfield m_bNeedClearPlaten : Z
    //   92: ifeq -> 104
    //   95: aload_0
    //   96: iconst_0
    //   97: putfield m_bNeedClearPlaten : Z
    //   100: aload_0
    //   101: invokespecial OnMsg_DrawFingerQuality : ()V
    //   104: aload_2
    //   105: ifnull -> 121
    //   108: aload_2
    //   109: invokevirtual getType : ()Lcom/integratedbiometrics/ibscanultimate/IBScanException$Type;
    //   112: getstatic com/integratedbiometrics/ibscanultimate/IBScanException$Type.INVALID_PARAM_VALUE : Lcom/integratedbiometrics/ibscanultimate/IBScanException$Type;
    //   115: invokevirtual compareTo : (Ljava/lang/Enum;)I
    //   118: ifle -> 435
    //   121: aload_0
    //   122: aload_0
    //   123: getfield m_vecCaptureSeq : Ljava/util/Vector;
    //   126: aload_0
    //   127: getfield m_nCurrentCaptureStep : I
    //   130: invokevirtual elementAt : (I)Ljava/lang/Object;
    //   133: checkcast com/integratedbiometrics/ibsimplescan/SimpleScanActivity$CaptureInfo
    //   136: iconst_1
    //   137: iconst_0
    //   138: invokevirtual _SetLEDs : (Lcom/integratedbiometrics/ibsimplescan/SimpleScanActivity$CaptureInfo;IZ)V
    //   141: aload_0
    //   142: iload #5
    //   144: putfield m_nSegmentImageArrayCount : I
    //   147: aload_0
    //   148: aload #7
    //   150: putfield m_SegmentPositionArray : [Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$SegmentPosition;
    //   153: iconst_4
    //   154: newarray byte
    //   156: astore #7
    //   158: aload #7
    //   160: dup
    //   161: iconst_0
    //   162: ldc_w 0
    //   165: bastore
    //   166: dup
    //   167: iconst_1
    //   168: ldc_w 0
    //   171: bastore
    //   172: dup
    //   173: iconst_2
    //   174: ldc_w 0
    //   177: bastore
    //   178: dup
    //   179: iconst_3
    //   180: ldc_w 0
    //   183: bastore
    //   184: pop
    //   185: iconst_0
    //   186: istore #8
    //   188: iconst_0
    //   189: istore #5
    //   191: iload #8
    //   193: iconst_4
    //   194: if_icmpge -> 257
    //   197: aload_0
    //   198: getfield m_FingerQuality : [Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$FingerQualityState;
    //   201: iload #8
    //   203: aaload
    //   204: invokevirtual ordinal : ()I
    //   207: getstatic com/integratedbiometrics/ibscanultimate/IBScanDevice$FingerQualityState.FINGER_NOT_PRESENT : Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$FingerQualityState;
    //   210: invokevirtual ordinal : ()I
    //   213: if_icmpeq -> 506
    //   216: aload_0
    //   217: invokevirtual getIBScanDevice : ()Lcom/integratedbiometrics/ibscanultimate/IBScanDevice;
    //   220: astore #4
    //   222: iload #5
    //   224: iconst_1
    //   225: iadd
    //   226: istore #9
    //   228: aload #7
    //   230: iload #8
    //   232: aload #4
    //   234: aload #6
    //   236: iload #5
    //   238: aaload
    //   239: invokevirtual calculateNfiqScore : (Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$ImageData;)I
    //   242: i2b
    //   243: bastore
    //   244: iload #9
    //   246: istore #5
    //   248: iload #8
    //   250: iconst_1
    //   251: iadd
    //   252: istore #8
    //   254: goto -> 191
    //   257: aload_0
    //   258: new java/lang/StringBuilder
    //   261: dup
    //   262: invokespecial <init> : ()V
    //   265: ldc_w ''
    //   268: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   271: aload #7
    //   273: iconst_0
    //   274: baload
    //   275: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   278: ldc_w '-'
    //   281: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   284: aload #7
    //   286: iconst_1
    //   287: baload
    //   288: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   291: ldc_w '-'
    //   294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: aload #7
    //   299: iconst_2
    //   300: baload
    //   301: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   304: ldc_w '-'
    //   307: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   310: aload #7
    //   312: iconst_3
    //   313: baload
    //   314: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   317: invokevirtual toString : ()Ljava/lang/String;
    //   320: invokespecial OnMsg_SetTxtNFIQScore : (Ljava/lang/String;)V
    //   323: aload_2
    //   324: ifnonnull -> 371
    //   327: aload_0
    //   328: ldc_w 'Acquisition completed successfully'
    //   331: putfield m_strImageMessage : Ljava/lang/String;
    //   334: aload_0
    //   335: aload_0
    //   336: getfield m_strImageMessage : Ljava/lang/String;
    //   339: invokevirtual _SetImageMessage : (Ljava/lang/String;)V
    //   342: aload_0
    //   343: aload_0
    //   344: getfield m_strImageMessage : Ljava/lang/String;
    //   347: invokevirtual _SetStatusBarMessage : (Ljava/lang/String;)V
    //   350: aload_0
    //   351: aload_1
    //   352: aload_3
    //   353: invokespecial OnMsg_DrawImage : (Lcom/integratedbiometrics/ibscanultimate/IBScanDevice;Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$ImageData;)V
    //   356: aload_0
    //   357: invokespecial OnMsg_CaptureSeqNext : ()V
    //   360: return
    //   361: astore #4
    //   363: aload #4
    //   365: invokevirtual printStackTrace : ()V
    //   368: goto -> 257
    //   371: aload_0
    //   372: new java/lang/StringBuilder
    //   375: dup
    //   376: invokespecial <init> : ()V
    //   379: ldc_w 'Acquisition Warning (Warning code = '
    //   382: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   385: aload_2
    //   386: invokevirtual getType : ()Lcom/integratedbiometrics/ibscanultimate/IBScanException$Type;
    //   389: invokevirtual toString : ()Ljava/lang/String;
    //   392: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   395: ldc_w ')'
    //   398: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   401: invokevirtual toString : ()Ljava/lang/String;
    //   404: putfield m_strImageMessage : Ljava/lang/String;
    //   407: aload_0
    //   408: aload_0
    //   409: getfield m_strImageMessage : Ljava/lang/String;
    //   412: invokevirtual _SetImageMessage : (Ljava/lang/String;)V
    //   415: aload_0
    //   416: aload_0
    //   417: getfield m_strImageMessage : Ljava/lang/String;
    //   420: invokevirtual _SetStatusBarMessage : (Ljava/lang/String;)V
    //   423: aload_0
    //   424: aload_1
    //   425: aload_3
    //   426: invokespecial OnMsg_DrawImage : (Lcom/integratedbiometrics/ibscanultimate/IBScanDevice;Lcom/integratedbiometrics/ibscanultimate/IBScanDevice$ImageData;)V
    //   429: aload_0
    //   430: aload_2
    //   431: invokespecial OnMsg_AskRecapture : (Lcom/integratedbiometrics/ibscanultimate/IBScanException;)V
    //   434: return
    //   435: aload_0
    //   436: new java/lang/StringBuilder
    //   439: dup
    //   440: invokespecial <init> : ()V
    //   443: ldc_w 'Acquisition failed (Error code = '
    //   446: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   449: aload_2
    //   450: invokevirtual getType : ()Lcom/integratedbiometrics/ibscanultimate/IBScanException$Type;
    //   453: invokevirtual toString : ()Ljava/lang/String;
    //   456: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   459: ldc_w ')'
    //   462: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   465: invokevirtual toString : ()Ljava/lang/String;
    //   468: putfield m_strImageMessage : Ljava/lang/String;
    //   471: aload_0
    //   472: aload_0
    //   473: getfield m_strImageMessage : Ljava/lang/String;
    //   476: invokevirtual _SetImageMessage : (Ljava/lang/String;)V
    //   479: aload_0
    //   480: aload_0
    //   481: getfield m_strImageMessage : Ljava/lang/String;
    //   484: invokevirtual _SetStatusBarMessage : (Ljava/lang/String;)V
    //   487: aload_0
    //   488: aload_0
    //   489: getfield m_vecCaptureSeq : Ljava/util/Vector;
    //   492: invokevirtual size : ()I
    //   495: putfield m_nCurrentCaptureStep : I
    //   498: goto -> 350
    //   501: astore #4
    //   503: goto -> 363
    //   506: goto -> 248
    // Exception table:
    //   from	to	target	type
    //   197	222	361	com/integratedbiometrics/ibscanultimate/IBScanException
    //   228	244	501	com/integratedbiometrics/ibscanultimate/IBScanException
  }
  
  public void devicePlatenStateChanged(IBScanDevice paramIBScanDevice, IBScanDevice.PlatenState paramPlatenState) {
    if (paramPlatenState.equals(IBScanDevice.PlatenState.HAS_FINGERS)) {
      this.m_bNeedClearPlaten = true;
    } else {
      this.m_bNeedClearPlaten = false;
    } 
    if (paramPlatenState.equals(IBScanDevice.PlatenState.HAS_FINGERS)) {
      this.m_strImageMessage = "Please remove your fingers on the platen first!";
      _SetImageMessage(this.m_strImageMessage);
      _SetStatusBarMessage(this.m_strImageMessage);
    } else if (this.m_nCurrentCaptureStep >= 0) {
      String str = ((CaptureInfo)this.m_vecCaptureSeq.elementAt(this.m_nCurrentCaptureStep)).PreCaptureMessage;
      _SetStatusBarMessage(str);
      _SetImageMessage(str);
      this.m_strImageMessage = str;
    } 
    OnMsg_DrawFingerQuality();
  }
  
  public void devicePressedKeyButtons(IBScanDevice paramIBScanDevice, int paramInt) {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    _SetStatusBarMessage("PressedKeyButtons " + paramInt);
    if (this.m_cboUsbDevices.getSelectedItemPosition() > 0) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (this.m_bInitializing && this.m_nCurrentCaptureStep == -1) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (this.m_bInitializing && this.m_nCurrentCaptureStep != -1) {
      bool3 = true;
    } else {
      bool3 = false;
    } 
    if (paramInt == 1) {
      if (bool1 && bool2)
        try {
          System.out.println("Capture Start");
          paramIBScanDevice.setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 4, 0, 0);
          this.m_btnCaptureStart.performClick();
          return;
        } catch (IBScanException iBScanException) {
          iBScanException.printStackTrace();
        }  
    } else if (paramInt == 2 && bool3) {
      System.out.println("Capture Stop");
      iBScanException.setBeeper(IBScanDevice.BeepPattern.BEEP_PATTERN_GENERIC, 2, 4, 0, 0);
      this.m_btnCaptureStop.performClick();
      return;
    } 
  }
  
  public void deviceWarningReceived(IBScanDevice paramIBScanDevice, IBScanException paramIBScanException) {
    _SetStatusBarMessage("Warning received " + paramIBScanException.getType().toString());
  }
  
  protected IBScan getIBScan() {
    return this.m_ibScan;
  }
  
  protected IBScanDevice getIBScanDevice() {
    return this.m_ibScanDevice;
  }
  
  public void onBackPressed() {
    exitApp(this);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    if (paramConfiguration.orientation == 1) {
      setContentView(2130903043);
    } else {
      setContentView(2130903042);
    } 
    _InitUIFields();
    OnMsg_UpdateDeviceList(true);
    _PopulateUI();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    this.m_ibScan = IBScan.getInstance(getApplicationContext());
    this.m_ibScan.setScanListener(this);
    if ((Resources.getSystem().getConfiguration()).orientation == 1) {
      setContentView(2130903043);
    } else {
      setContentView(2130903042);
    } 
    _InitUIFields();
    UsbManager usbManager = (UsbManager)getApplicationContext().getSystemService("usb");
    for (UsbDevice usbDevice : usbManager.getDeviceList().values()) {
      if (IBScan.isScanDevice(usbDevice) && !usbManager.hasPermission(usbDevice))
        this.m_ibScan.requestPermission(usbDevice.getDeviceId()); 
    } 
    OnMsg_UpdateDeviceList(false);
    _PopulateUI();
    (new _TimerTaskThreadCallback(500)).start();
  }
  
  protected void onDestroy() {
    super.onDestroy();
    byte b = 0;
    while (true) {
      if (b < 10)
        try {
          _ReleaseDevice();
        } catch (IBScanException iBScanException) {} 
      return;
    } 
  }
  
  public Object onRetainNonConfigurationInstance() {
    return null;
  }
  
  public void scanDeviceAttached(int paramInt) {
    showToastOnUiThread("Device " + paramInt + " attached", 0);
    if (!this.m_ibScan.hasPermission(paramInt))
      this.m_ibScan.requestPermission(paramInt); 
  }
  
  public void scanDeviceCountChanged(int paramInt) {
    OnMsg_UpdateDeviceList(false);
  }
  
  public void scanDeviceDetached(int paramInt) {
    showToastOnUiThread("Device " + paramInt + " detached", 0);
  }
  
  public void scanDeviceInitProgress(int paramInt1, int paramInt2) {
    OnMsg_SetStatusBarMessage("Initializing device..." + paramInt2 + "%");
  }
  
  public void scanDeviceOpenComplete(int paramInt, IBScanDevice paramIBScanDevice, IBScanException paramIBScanException) {}
  
  public void scanDevicePermissionGranted(int paramInt, boolean paramBoolean) {
    if (paramBoolean) {
      showToastOnUiThread("Permission granted to device " + paramInt, 0);
      return;
    } 
    showToastOnUiThread("Permission denied to device " + paramInt, 0);
  }
  
  protected void setIBScanDevice(IBScanDevice paramIBScanDevice) {
    this.m_ibScanDevice = paramIBScanDevice;
    if (paramIBScanDevice != null)
      paramIBScanDevice.setScanDeviceListener(this); 
  }
  
  protected class AppData {
    public int captureSeq = -1;
    
    public int[] fingerQualityColors = new int[] { -3355444, -3355444, -3355444, -3355444 };
    
    public String frameTime = "n/a";
    
    public Bitmap imageBitmap = null;
    
    public boolean imagePreviewImageClickable = false;
    
    public String nfiq = "0-0-0-0";
    
    public int overlayColor = -3355444;
    
    public String overlayText = "";
    
    public String statusMessage = "n/a";
    
    public int usbDevices = -1;
  }
  
  protected class CaptureInfo {
    IBScanDevice.ImageType ImageType;
    
    int NumberOfFinger;
    
    String PostCaptuerMessage;
    
    String PreCaptureMessage;
    
    String fingerName;
  }
  
  class _InitializeDeviceThreadCallback extends Thread {
    private int devIndex;
    
    _InitializeDeviceThreadCallback(int param1Int) {
      this.devIndex = param1Int;
    }
    
    public void run() {
      try {
        SimpleScanActivity.this.m_bInitializing = true;
        IBScanDevice iBScanDevice = SimpleScanActivity.this.getIBScan().openDevice(this.devIndex);
        SimpleScanActivity.this.setIBScanDevice(iBScanDevice);
        SimpleScanActivity.this.m_bInitializing = false;
        if (iBScanDevice != null) {
          int i = SimpleScanActivity.this.m_imgPreview.getWidth() - 20;
          int j = SimpleScanActivity.this.m_imgPreview.getHeight() - 20;
          SimpleScanActivity.access$402(SimpleScanActivity.this, Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888));
          SimpleScanActivity.this.m_drawBuffer = new byte[i * j * 4];
          SimpleScanActivity.this.m_LedState = SimpleScanActivity.this.getIBScanDevice().getOperableLEDs();
          SimpleScanActivity.this.OnMsg_CaptureSeqStart();
        } 
        return;
      } catch (IBScanException iBScanException) {
        SimpleScanActivity.this.m_bInitializing = false;
        if (iBScanException.getType().equals(IBScanException.Type.DEVICE_ACTIVE)) {
          SimpleScanActivity.this._SetStatusBarMessage("[Error Code =-203] Device initialization failed because in use by another thread/process.");
        } else if (iBScanException.getType().equals(IBScanException.Type.USB20_REQUIRED)) {
          SimpleScanActivity.this._SetStatusBarMessage("[Error Code =-209] Device initialization failed because SDK only works with USB 2.0.");
        } else if (iBScanException.getType().equals(IBScanException.Type.DEVICE_HIGHER_SDK_REQUIRED)) {
          try {
            SimpleScanActivity.this.m_minSDKVersion = SimpleScanActivity.this.getIBScan().getRequiredSDKVersion(this.devIndex);
            SimpleScanActivity.this._SetStatusBarMessage("[Error Code =-214] Devcie initialization failed because SDK Version " + SimpleScanActivity.this.m_minSDKVersion + " is required at least.");
          } catch (IBScanException iBScanException1) {}
        } else {
          SimpleScanActivity.this._SetStatusBarMessage("Device initialization failed.");
        } 
        SimpleScanActivity.this.OnMsg_UpdateDisplayResources();
        return;
      } 
    }
  }
  
  class _TimerTaskThreadCallback extends Thread {
    private int timeInterval;
    
    _TimerTaskThreadCallback(int param1Int) {
      this.timeInterval = param1Int;
    }
    
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
        if (SimpleScanActivity.this.getIBScanDevice() != null) {
          SimpleScanActivity.this.OnMsg_DrawFingerQuality();
          if (SimpleScanActivity.this.m_bNeedClearPlaten) {
            boolean bool;
            SimpleScanActivity simpleScanActivity = SimpleScanActivity.this;
            if (!SimpleScanActivity.this.m_bBlank) {
              bool = true;
            } else {
              bool = false;
            } 
            simpleScanActivity.m_bBlank = bool;
          } 
        } 
        SimpleScanActivity.this._Sleep(this.timeInterval);
        try {
          Thread.sleep(this.timeInterval);
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        } 
      } 
    }
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\integratedbiometrics\ibsimplescan\SimpleScanActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */