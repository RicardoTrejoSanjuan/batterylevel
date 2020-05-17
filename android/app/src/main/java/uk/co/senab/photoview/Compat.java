package uk.co.senab.photoview;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

public class Compat {
  private static final int SIXTY_FPS_INTERVAL = 16;
  
  public static int getPointerIndex(int paramInt) {
    return (Build.VERSION.SDK_INT >= 11) ? getPointerIndexHoneyComb(paramInt) : getPointerIndexEclair(paramInt);
  }
  
  @TargetApi(5)
  private static int getPointerIndexEclair(int paramInt) {
    return (0xFF00 & paramInt) >> 8;
  }
  
  @TargetApi(11)
  private static int getPointerIndexHoneyComb(int paramInt) {
    return (0xFF00 & paramInt) >> 8;
  }
  
  public static void postOnAnimation(View paramView, Runnable paramRunnable) {}
  
  @TargetApi(16)
  private static void postOnAnimationJellyBean(View paramView, Runnable paramRunnable) {}
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\Compat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */