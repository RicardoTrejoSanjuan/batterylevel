package uk.co.senab.photoview.gestures;

import android.view.MotionEvent;

public interface GestureDetector {
  boolean isScaling();
  
  boolean onTouchEvent(MotionEvent paramMotionEvent);
  
  void setOnGestureListener(OnGestureListener paramOnGestureListener);
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\gestures\GestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */