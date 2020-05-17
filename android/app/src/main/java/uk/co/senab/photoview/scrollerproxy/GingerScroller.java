package uk.co.senab.photoview.scrollerproxy;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.OverScroller;

@TargetApi(9)
public class GingerScroller extends ScrollerProxy {
  private boolean mFirstScroll = false;
  
  protected final OverScroller mScroller;
  
  public GingerScroller(Context paramContext) {
    this.mScroller = new OverScroller(paramContext);
  }
  
  public boolean computeScrollOffset() {
    if (this.mFirstScroll) {
      this.mScroller.computeScrollOffset();
      this.mFirstScroll = false;
    } 
    return this.mScroller.computeScrollOffset();
  }
  
  public void fling(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) {
    this.mScroller.fling(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);
  }
  
  public void forceFinished(boolean paramBoolean) {
    this.mScroller.forceFinished(paramBoolean);
  }
  
  public int getCurrX() {
    return this.mScroller.getCurrX();
  }
  
  public int getCurrY() {
    return this.mScroller.getCurrY();
  }
  
  public boolean isFinished() {
    return this.mScroller.isFinished();
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\scrollerproxy\GingerScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */