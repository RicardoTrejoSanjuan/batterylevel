package uk.co.senab.photoview.gestures;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class CupcakeGestureDetector implements GestureDetector {
  private static final String LOG_TAG = "CupcakeGestureDetector";
  
  private boolean mIsDragging;
  
  float mLastTouchX;
  
  float mLastTouchY;
  
  protected OnGestureListener mListener;
  
  final float mMinimumVelocity;
  
  final float mTouchSlop;
  
  private VelocityTracker mVelocityTracker;
  
  public CupcakeGestureDetector(Context paramContext) {
    ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
    this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
  }
  
  float getActiveX(MotionEvent paramMotionEvent) {
    return paramMotionEvent.getX();
  }
  
  float getActiveY(MotionEvent paramMotionEvent) {
    return paramMotionEvent.getY();
  }
  
  public boolean isScaling() {
    return false;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    float f1;
    float f2;
    float f3;
    float f4;
    boolean bool = false;
    switch (paramMotionEvent.getAction()) {
      default:
        return true;
      case 0:
        this.mVelocityTracker = VelocityTracker.obtain();
        if (this.mVelocityTracker != null) {
          this.mVelocityTracker.addMovement(paramMotionEvent);
          this.mLastTouchX = getActiveX(paramMotionEvent);
          this.mLastTouchY = getActiveY(paramMotionEvent);
          this.mIsDragging = false;
          return true;
        } 
        Log.i("CupcakeGestureDetector", "Velocity tracker is null");
        this.mLastTouchX = getActiveX(paramMotionEvent);
        this.mLastTouchY = getActiveY(paramMotionEvent);
        this.mIsDragging = false;
        return true;
      case 2:
        f1 = getActiveX(paramMotionEvent);
        f2 = getActiveY(paramMotionEvent);
        f3 = f1 - this.mLastTouchX;
        f4 = f2 - this.mLastTouchY;
        if (!this.mIsDragging) {
          if ((float)Math.sqrt((f3 * f3 + f4 * f4)) >= this.mTouchSlop)
            bool = true; 
          this.mIsDragging = bool;
        } 
        if (this.mIsDragging) {
          this.mListener.onDrag(f3, f4);
          this.mLastTouchX = f1;
          this.mLastTouchY = f2;
          if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(paramMotionEvent);
            return true;
          } 
        } 
      case 3:
        if (this.mVelocityTracker != null) {
          this.mVelocityTracker.recycle();
          this.mVelocityTracker = null;
          return true;
        } 
      case 1:
        break;
    } 
    if (this.mIsDragging && this.mVelocityTracker != null) {
      this.mLastTouchX = getActiveX(paramMotionEvent);
      this.mLastTouchY = getActiveY(paramMotionEvent);
      this.mVelocityTracker.addMovement(paramMotionEvent);
      this.mVelocityTracker.computeCurrentVelocity(1000);
      f1 = this.mVelocityTracker.getXVelocity();
      f2 = this.mVelocityTracker.getYVelocity();
      if (Math.max(Math.abs(f1), Math.abs(f2)) >= this.mMinimumVelocity)
        this.mListener.onFling(this.mLastTouchX, this.mLastTouchY, -f1, -f2); 
    } 
    if (this.mVelocityTracker != null) {
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
      return true;
    } 
  }
  
  public void setOnGestureListener(OnGestureListener paramOnGestureListener) {
    this.mListener = paramOnGestureListener;
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\gestures\CupcakeGestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */