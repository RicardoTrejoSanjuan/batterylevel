package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import uk.co.senab.photoview.Compat;

@TargetApi(5)
public class EclairGestureDetector extends CupcakeGestureDetector {
  private static final int INVALID_POINTER_ID = -1;
  
  private int mActivePointerId = -1;
  
  private int mActivePointerIndex = 0;
  
  public EclairGestureDetector(Context paramContext) {
    super(paramContext);
  }
  
  float getActiveX(MotionEvent paramMotionEvent) {
    try {
      return paramMotionEvent.getX(this.mActivePointerIndex);
    } catch (Exception exception) {
      return paramMotionEvent.getX();
    } 
  }
  
  float getActiveY(MotionEvent paramMotionEvent) {
    try {
      return paramMotionEvent.getY(this.mActivePointerIndex);
    } catch (Exception exception) {
      return paramMotionEvent.getY();
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    byte b = 0;
    switch (paramMotionEvent.getAction() & 0xFF) {
      default:
        i = b;
        if (this.mActivePointerId != -1)
          i = this.mActivePointerId; 
        this.mActivePointerIndex = paramMotionEvent.findPointerIndex(i);
        return super.onTouchEvent(paramMotionEvent);
      case 0:
        this.mActivePointerId = paramMotionEvent.getPointerId(0);
      case 1:
      case 3:
        this.mActivePointerId = -1;
      case 6:
        break;
    } 
    int i = Compat.getPointerIndex(paramMotionEvent.getAction());
    if (paramMotionEvent.getPointerId(i) == this.mActivePointerId) {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      this.mActivePointerId = paramMotionEvent.getPointerId(i);
      this.mLastTouchX = paramMotionEvent.getX(i);
      this.mLastTouchY = paramMotionEvent.getY(i);
    } 
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\gestures\EclairGestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */