package uk.co.senab.photoview.scrollerproxy;

import android.annotation.TargetApi;
import android.content.Context;

@TargetApi(14)
public class IcsScroller extends GingerScroller {
  public IcsScroller(Context paramContext) {
    super(paramContext);
  }
  
  public boolean computeScrollOffset() {
    return this.mScroller.computeScrollOffset();
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\scrollerproxy\IcsScroller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */