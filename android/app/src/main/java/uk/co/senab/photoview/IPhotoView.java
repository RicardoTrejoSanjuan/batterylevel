package uk.co.senab.photoview;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.View;
import android.widget.ImageView;

public interface IPhotoView {
  public static final float DEFAULT_MAX_SCALE = 3.0F;
  
  public static final float DEFAULT_MID_SCALE = 1.75F;
  
  public static final float DEFAULT_MIN_SCALE = 1.0F;
  
  public static final int DEFAULT_ZOOM_DURATION = 200;
  
  boolean canZoom();
  
  Matrix getDisplayMatrix();
  
  RectF getDisplayRect();
  
  IPhotoView getIPhotoViewImplementation();
  
  @Deprecated
  float getMaxScale();
  
  float getMaximumScale();
  
  float getMediumScale();
  
  @Deprecated
  float getMidScale();
  
  @Deprecated
  float getMinScale();
  
  float getMinimumScale();
  
  PhotoViewAttacher.OnPhotoTapListener getOnPhotoTapListener();
  
  PhotoViewAttacher.OnViewTapListener getOnViewTapListener();
  
  float getScale();
  
  ImageView.ScaleType getScaleType();
  
  Bitmap getVisibleRectangleBitmap();
  
  void setAllowParentInterceptOnEdge(boolean paramBoolean);
  
  boolean setDisplayMatrix(Matrix paramMatrix);
  
  @Deprecated
  void setMaxScale(float paramFloat);
  
  void setMaximumScale(float paramFloat);
  
  void setMediumScale(float paramFloat);
  
  @Deprecated
  void setMidScale(float paramFloat);
  
  @Deprecated
  void setMinScale(float paramFloat);
  
  void setMinimumScale(float paramFloat);
  
  void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener);
  
  void setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener);
  
  void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener paramOnMatrixChangedListener);
  
  void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener paramOnPhotoTapListener);
  
  void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener paramOnViewTapListener);
  
  void setPhotoViewRotation(float paramFloat);
  
  void setRotationBy(float paramFloat);
  
  void setRotationTo(float paramFloat);
  
  void setScale(float paramFloat);
  
  void setScale(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean);
  
  void setScale(float paramFloat, boolean paramBoolean);
  
  void setScaleType(ImageView.ScaleType paramScaleType);
  
  void setZoomTransitionDuration(int paramInt);
  
  void setZoomable(boolean paramBoolean);
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\IPhotoView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */