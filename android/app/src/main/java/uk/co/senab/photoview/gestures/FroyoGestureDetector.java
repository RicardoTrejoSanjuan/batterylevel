package uk.co.senab.photoview.gestures;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

@TargetApi(8)
public class FroyoGestureDetector extends EclairGestureDetector {
  protected final ScaleGestureDetector mDetector;
  
  public FroyoGestureDetector(Context paramContext) {
    super(paramContext);
    this.mDetector = new ScaleGestureDetector(paramContext, new ScaleGestureDetector.OnScaleGestureListener() {
          public boolean onScale(ScaleGestureDetector param1ScaleGestureDetector) {
            float f = param1ScaleGestureDetector.getScaleFactor();
            if (Float.isNaN(f) || Float.isInfinite(f))
              return false; 
            FroyoGestureDetector.this.mListener.onScale(f, param1ScaleGestureDetector.getFocusX(), param1ScaleGestureDetector.getFocusY());
            return true;
          }
          
          public boolean onScaleBegin(ScaleGestureDetector param1ScaleGestureDetector) {
            return true;
          }
          
          public void onScaleEnd(ScaleGestureDetector param1ScaleGestureDetector) {}
        });
  }
  
  public boolean isScaling() {
    return this.mDetector.isInProgress();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    this.mDetector.onTouchEvent(paramMotionEvent);
    return super.onTouchEvent(paramMotionEvent);
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\gestures\FroyoGestureDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */