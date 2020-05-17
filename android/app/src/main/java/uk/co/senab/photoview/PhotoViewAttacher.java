package uk.co.senab.photoview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

//import uk.co.senab.photoview.gestures.GestureDetector;
import uk.co.senab.photoview.gestures.OnGestureListener;
import uk.co.senab.photoview.gestures.VersionedGestureDetector;
import uk.co.senab.photoview.log.LogManager;
import uk.co.senab.photoview.scrollerproxy.ScrollerProxy;

public class PhotoViewAttacher implements IPhotoView, View.OnTouchListener, OnGestureListener, ViewTreeObserver.OnGlobalLayoutListener {
  private static final boolean DEBUG = Log.isLoggable("PhotoViewAttacher", 3);
  
  static final int EDGE_BOTH = 2;
  
  static final int EDGE_LEFT = 0;
  
  static final int EDGE_NONE = -1;
  
  static final int EDGE_RIGHT = 1;
  
  private static final String LOG_TAG = "PhotoViewAttacher";
  
  static final Interpolator sInterpolator = (Interpolator)new AccelerateDecelerateInterpolator();
  
  int ZOOM_DURATION = 200;
  
  private boolean mAllowParentInterceptOnEdge = true;
  
  private final Matrix mBaseMatrix = new Matrix();
  
  private FlingRunnable mCurrentFlingRunnable;
  
  private final RectF mDisplayRect = new RectF();
  
  private final Matrix mDrawMatrix = new Matrix();
  
  private GestureDetector mGestureDetector;
  
  private WeakReference<ImageView> mImageView;
  
  private int mIvBottom;
  
  private int mIvLeft;
  
  private int mIvRight;
  
  private int mIvTop;
  
  private View.OnLongClickListener mLongClickListener;
  
  private OnMatrixChangedListener mMatrixChangeListener;
  
  private final float[] mMatrixValues = new float[9];
  
  private float mMaxScale = 3.0F;
  
  private float mMidScale = 1.75F;
  
  private float mMinScale = 1.0F;
  
  private OnPhotoTapListener mPhotoTapListener;
  
  private GestureDetector mScaleDragDetector;
  
  private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;
  
  private int mScrollEdge = 2;
  
  private final Matrix mSuppMatrix = new Matrix();
  
  private OnViewTapListener mViewTapListener;
  
  private boolean mZoomEnabled;
  
  public PhotoViewAttacher(ImageView paramImageView) {
    this.mImageView = new WeakReference<ImageView>(paramImageView);
    paramImageView.setDrawingCacheEnabled(true);
    paramImageView.setOnTouchListener(this);
    ViewTreeObserver viewTreeObserver = paramImageView.getViewTreeObserver();
    if (viewTreeObserver != null)
      viewTreeObserver.addOnGlobalLayoutListener(this); 
    setImageViewScaleTypeMatrix(paramImageView);
    if (paramImageView.isInEditMode())
      return; 
    this.mScaleDragDetector = VersionedGestureDetector.newInstance(paramImageView.getContext(), this);
    this.mGestureDetector = new GestureDetector(paramImageView.getContext(), (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener() {
          public void onLongPress(MotionEvent param1MotionEvent) {
            if (PhotoViewAttacher.this.mLongClickListener != null)
              PhotoViewAttacher.this.mLongClickListener.onLongClick((View)PhotoViewAttacher.this.getImageView()); 
          }
        });
    this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
    setZoomable(true);
  }
  
  private void cancelFling() {
    if (this.mCurrentFlingRunnable != null) {
      this.mCurrentFlingRunnable.cancelFling();
      this.mCurrentFlingRunnable = null;
    } 
  }
  
  private void checkAndDisplayMatrix() {
    if (checkMatrixBounds())
      setImageViewMatrix(getDrawMatrix()); 
  }
  
  private void checkImageViewScaleType() {
    ImageView imageView = getImageView();
    if (imageView != null && !(imageView instanceof IPhotoView) && !ImageView.ScaleType.MATRIX.equals(imageView.getScaleType()))
      throw new IllegalStateException("The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher"); 
  }
  
  private boolean checkMatrixBounds() {
    ImageView imageView = getImageView();
    if (imageView != null) {
      RectF rectF = getDisplayRect(getDrawMatrix());
      if (rectF != null) {
        float f4 = rectF.height();
        float f3 = rectF.width();
        float f2 = 0.0F;
        float f1 = 0.0F;
        int i = getImageViewHeight(imageView);
        if (f4 <= i) {
          switch (this.mScaleType) {
            default:
              f1 = (i - f4) / 2.0F - rectF.top;
              i = getImageViewWidth(imageView);
            case FIT_START:
              f1 = -rectF.top;
              i = getImageViewWidth(imageView);
            case FIT_END:
              f1 = i - f4 - rectF.top;
              i = getImageViewWidth(imageView);
          } 
        } else {
          if (rectF.top > 0.0F) {
            f1 = -rectF.top;
          } else if (rectF.bottom < i) {
            f1 = i - rectF.bottom;
          } 
          i = getImageViewWidth(imageView);
        } 
        if (rectF.left > 0.0F) {
          this.mScrollEdge = 0;
          f2 = -rectF.left;
          this.mSuppMatrix.postTranslate(f2, f1);
          return true;
        } 
        if (rectF.right < i) {
          f2 = i - rectF.right;
          this.mScrollEdge = 1;
          this.mSuppMatrix.postTranslate(f2, f1);
          return true;
        } 
        this.mScrollEdge = -1;
        this.mSuppMatrix.postTranslate(f2, f1);
        return true;
      } 
    } 
    return false;
  }
  
  private static void checkZoomLevels(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (paramFloat1 >= paramFloat2)
      throw new IllegalArgumentException("MinZoom has to be less than MidZoom"); 
    if (paramFloat2 >= paramFloat3)
      throw new IllegalArgumentException("MidZoom has to be less than MaxZoom"); 
  }
  
  private RectF getDisplayRect(Matrix paramMatrix) {
    ImageView imageView = getImageView();
    if (imageView != null) {
      Drawable drawable = imageView.getDrawable();
      if (drawable != null) {
        this.mDisplayRect.set(0.0F, 0.0F, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        paramMatrix.mapRect(this.mDisplayRect);
        return this.mDisplayRect;
      } 
    } 
    return null;
  }
  
  private int getImageViewHeight(ImageView paramImageView) {
    return (paramImageView == null) ? 0 : (paramImageView.getHeight() - paramImageView.getPaddingTop() - paramImageView.getPaddingBottom());
  }
  
  private int getImageViewWidth(ImageView paramImageView) {
    return (paramImageView == null) ? 0 : (paramImageView.getWidth() - paramImageView.getPaddingLeft() - paramImageView.getPaddingRight());
  }
  
  private float getValue(Matrix paramMatrix, int paramInt) {
    paramMatrix.getValues(this.mMatrixValues);
    return this.mMatrixValues[paramInt];
  }
  
  private static boolean hasDrawable(ImageView paramImageView) {
    return (paramImageView != null && paramImageView.getDrawable() != null);
  }
  
  private static boolean isSupportedScaleType(ImageView.ScaleType paramScaleType) {
    if (paramScaleType == null)
      return false; 
    switch (paramScaleType) {
      default:
        return true;
      case MATRIX:
        break;
    } 
    throw new IllegalArgumentException(paramScaleType.name() + " is not supported in PhotoView");
  }
  
  private void resetMatrix() {
    this.mSuppMatrix.reset();
    setImageViewMatrix(getDrawMatrix());
    checkMatrixBounds();
  }
  
  private void setImageViewMatrix(Matrix paramMatrix) {
    ImageView imageView = getImageView();
    if (imageView != null) {
      checkImageViewScaleType();
      imageView.setImageMatrix(paramMatrix);
      if (this.mMatrixChangeListener != null) {
        RectF rectF = getDisplayRect(paramMatrix);
        if (rectF != null)
          this.mMatrixChangeListener.onMatrixChanged(rectF); 
      } 
    } 
  }
  
  private static void setImageViewScaleTypeMatrix(ImageView paramImageView) {
    if (paramImageView != null && !(paramImageView instanceof IPhotoView) && !ImageView.ScaleType.MATRIX.equals(paramImageView.getScaleType()))
      paramImageView.setScaleType(ImageView.ScaleType.MATRIX); 
  }
  
  private void updateBaseMatrix(Drawable paramDrawable) {
    ImageView imageView = getImageView();
    if (imageView == null || paramDrawable == null)
      return; 
    float f1 = getImageViewWidth(imageView);
    float f2 = getImageViewHeight(imageView);
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    this.mBaseMatrix.reset();
    float f3 = f1 / i;
    float f4 = f2 / j;
    if (this.mScaleType == ImageView.ScaleType.CENTER)
      this.mBaseMatrix.postTranslate((f1 - i) / 2.0F, (f2 - j) / 2.0F); 
    if (this.mScaleType == ImageView.ScaleType.CENTER_CROP) {
      f3 = Math.max(f3, f4);
      this.mBaseMatrix.postScale(f3, f3);
      this.mBaseMatrix.postTranslate((f1 - i * f3) / 2.0F, (f2 - j * f3) / 2.0F);
    } 
    if (this.mScaleType == ImageView.ScaleType.CENTER_INSIDE) {
      f3 = Math.min(1.0F, Math.min(f3, f4));
      this.mBaseMatrix.postScale(f3, f3);
      this.mBaseMatrix.postTranslate((f1 - i * f3) / 2.0F, (f2 - j * f3) / 2.0F);
    } 
    RectF rectF1 = new RectF(0.0F, 0.0F, i, j);
    RectF rectF2 = new RectF(0.0F, 0.0F, f1, f2);
    switch (this.mScaleType) {
      default:
        resetMatrix();
        return;
      case FIT_START:
        this.mBaseMatrix.setRectToRect(rectF1, rectF2, Matrix.ScaleToFit.START);
      case FIT_CENTER:
        this.mBaseMatrix.setRectToRect(rectF1, rectF2, Matrix.ScaleToFit.CENTER);
      case FIT_END:
        this.mBaseMatrix.setRectToRect(rectF1, rectF2, Matrix.ScaleToFit.END);
      case FIT_XY:
        break;
    } 
    this.mBaseMatrix.setRectToRect(rectF1, rectF2, Matrix.ScaleToFit.FILL);
  }
  
  public boolean canZoom() {
    return this.mZoomEnabled;
  }
  
  public void cleanup() {
    if (this.mImageView == null)
      return; 
    ImageView imageView = this.mImageView.get();
    if (imageView != null) {
      ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
      if (viewTreeObserver != null && viewTreeObserver.isAlive())
        viewTreeObserver.removeGlobalOnLayoutListener(this); 
      imageView.setOnTouchListener(null);
      cancelFling();
    } 
    if (this.mGestureDetector != null)
      this.mGestureDetector.setOnDoubleTapListener(null); 
    this.mMatrixChangeListener = null;
    this.mPhotoTapListener = null;
    this.mViewTapListener = null;
    this.mImageView = null;
  }
  
  public Matrix getDisplayMatrix() {
    return new Matrix(getDrawMatrix());
  }
  
  public RectF getDisplayRect() {
    checkMatrixBounds();
    return getDisplayRect(getDrawMatrix());
  }
  
  public Matrix getDrawMatrix() {
    this.mDrawMatrix.set(this.mBaseMatrix);
    this.mDrawMatrix.postConcat(this.mSuppMatrix);
    return this.mDrawMatrix;
  }
  
  public IPhotoView getIPhotoViewImplementation() {
    return this;
  }
  
  public ImageView getImageView() {
    ImageView imageView = null;
    if (this.mImageView != null)
      imageView = this.mImageView.get(); 
    if (imageView == null) {
      cleanup();
      Log.i("PhotoViewAttacher", "ImageView no longer exists. You should not use this PhotoViewAttacher any more.");
    } 
    return imageView;
  }
  
  @Deprecated
  public float getMaxScale() {
    return getMaximumScale();
  }
  
  public float getMaximumScale() {
    return this.mMaxScale;
  }
  
  public float getMediumScale() {
    return this.mMidScale;
  }
  
  @Deprecated
  public float getMidScale() {
    return getMediumScale();
  }
  
  @Deprecated
  public float getMinScale() {
    return getMinimumScale();
  }
  
  public float getMinimumScale() {
    return this.mMinScale;
  }
  
  public OnPhotoTapListener getOnPhotoTapListener() {
    return this.mPhotoTapListener;
  }
  
  public OnViewTapListener getOnViewTapListener() {
    return this.mViewTapListener;
  }
  
  public float getScale() {
    return (float)Math.sqrt(((float)Math.pow(getValue(this.mSuppMatrix, 0), 2.0D) + (float)Math.pow(getValue(this.mSuppMatrix, 3), 2.0D)));
  }
  
  public ImageView.ScaleType getScaleType() {
    return this.mScaleType;
  }
  
  public Bitmap getVisibleRectangleBitmap() {
    ImageView imageView = getImageView();
    return (imageView == null) ? null : imageView.getDrawingCache();
  }
  
  public void onDrag(float paramFloat1, float paramFloat2) {
    if (!this.mScaleDragDetector.isScaling()) {
      if (DEBUG)
        LogManager.getLogger().d("PhotoViewAttacher", String.format("onDrag: dx: %.2f. dy: %.2f", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2) })); 
      ImageView imageView = getImageView();
      this.mSuppMatrix.postTranslate(paramFloat1, paramFloat2);
      checkAndDisplayMatrix();
      ViewParent viewParent = imageView.getParent();
      if (this.mAllowParentInterceptOnEdge && !this.mScaleDragDetector.isScaling()) {
        if ((this.mScrollEdge == 2 || (this.mScrollEdge == 0 && paramFloat1 >= 1.0F) || (this.mScrollEdge == 1 && paramFloat1 <= -1.0F)) && viewParent != null) {
          viewParent.requestDisallowInterceptTouchEvent(false);
          return;
        } 
        return;
      } 
      if (viewParent != null) {
        viewParent.requestDisallowInterceptTouchEvent(true);
        return;
      } 
    } 
  }
  
  public void onFling(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    if (DEBUG)
      LogManager.getLogger().d("PhotoViewAttacher", "onFling. sX: " + paramFloat1 + " sY: " + paramFloat2 + " Vx: " + paramFloat3 + " Vy: " + paramFloat4); 
    ImageView imageView = getImageView();
    this.mCurrentFlingRunnable = new FlingRunnable(imageView.getContext());
    this.mCurrentFlingRunnable.fling(getImageViewWidth(imageView), getImageViewHeight(imageView), (int)paramFloat3, (int)paramFloat4);
    imageView.post(this.mCurrentFlingRunnable);
  }
  
  public void onGlobalLayout() {
    ImageView imageView = getImageView();
    if (imageView != null) {
      if (this.mZoomEnabled) {
        int i = imageView.getTop();
        int j = imageView.getRight();
        int k = imageView.getBottom();
        int m = imageView.getLeft();
        if (i != this.mIvTop || k != this.mIvBottom || m != this.mIvLeft || j != this.mIvRight) {
          updateBaseMatrix(imageView.getDrawable());
          this.mIvTop = i;
          this.mIvRight = j;
          this.mIvBottom = k;
          this.mIvLeft = m;
        } 
        return;
      } 
    } else {
      return;
    } 
    updateBaseMatrix(imageView.getDrawable());
  }
  
  public void onScale(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (DEBUG)
      LogManager.getLogger().d("PhotoViewAttacher", String.format("onScale: scale: %.2f. fX: %.2f. fY: %.2f", new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), Float.valueOf(paramFloat3) })); 
    if (getScale() < this.mMaxScale || paramFloat1 < 1.0F) {
      this.mSuppMatrix.postScale(paramFloat1, paramFloat1, paramFloat2, paramFloat3);
      checkAndDisplayMatrix();
    } 
  }
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool1 = bool3;
    if (this.mZoomEnabled) {
      bool1 = bool3;
      if (hasDrawable((ImageView)paramView)) {
        ViewParent viewParent = paramView.getParent();
        bool1 = bool2;
        switch (paramMotionEvent.getAction()) {
          default:
            bool1 = bool2;
          case 2:
            bool2 = bool1;
            if (this.mScaleDragDetector != null) {
              bool2 = bool1;
              if (this.mScaleDragDetector.onTouchEvent(paramMotionEvent))
                bool2 = true; 
            } 
            bool1 = bool2;
            if (this.mGestureDetector != null) {
              bool1 = bool2;
              if (this.mGestureDetector.onTouchEvent(paramMotionEvent))
                bool1 = true; 
            } 
            return bool1;
          case 0:
            if (viewParent != null) {
              viewParent.requestDisallowInterceptTouchEvent(true);
            } else {
              Log.i("PhotoViewAttacher", "onTouch getParent() returned null");
            } 
            cancelFling();
            bool1 = bool2;
          case 1:
          case 3:
            break;
        } 
      } else {
        return bool1;
      } 
    } else {
      return bool1;
    } 
    bool1 = bool2;
    if (getScale() < this.mMinScale) {
      RectF rectF = getDisplayRect();
      bool1 = bool2;
      if (rectF != null) {
        paramView.post(new AnimatedZoomRunnable(getScale(), this.mMinScale, rectF.centerX(), rectF.centerY()));
        bool1 = true;
      } 
    } 
  }
  
  public void setAllowParentInterceptOnEdge(boolean paramBoolean) {
    this.mAllowParentInterceptOnEdge = paramBoolean;
  }
  
  public boolean setDisplayMatrix(Matrix paramMatrix) {
    if (paramMatrix == null)
      throw new IllegalArgumentException("Matrix cannot be null"); 
    ImageView imageView = getImageView();
    if (imageView != null && imageView.getDrawable() != null) {
      this.mSuppMatrix.set(paramMatrix);
      setImageViewMatrix(getDrawMatrix());
      checkMatrixBounds();
      return true;
    } 
    return false;
  }
  
  @Deprecated
  public void setMaxScale(float paramFloat) {
    setMaximumScale(paramFloat);
  }
  
  public void setMaximumScale(float paramFloat) {
    checkZoomLevels(this.mMinScale, this.mMidScale, paramFloat);
    this.mMaxScale = paramFloat;
  }
  
  public void setMediumScale(float paramFloat) {
    checkZoomLevels(this.mMinScale, paramFloat, this.mMaxScale);
    this.mMidScale = paramFloat;
  }
  
  @Deprecated
  public void setMidScale(float paramFloat) {
    setMediumScale(paramFloat);
  }
  
  @Deprecated
  public void setMinScale(float paramFloat) {
    setMinimumScale(paramFloat);
  }
  
  public void setMinimumScale(float paramFloat) {
    checkZoomLevels(paramFloat, this.mMidScale, this.mMaxScale);
    this.mMinScale = paramFloat;
  }
  
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener) {
    if (paramOnDoubleTapListener != null) {
      this.mGestureDetector.setOnDoubleTapListener(paramOnDoubleTapListener);
      return;
    } 
    this.mGestureDetector.setOnDoubleTapListener(new DefaultOnDoubleTapListener(this));
  }
  
  public void setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener) {
    this.mLongClickListener = paramOnLongClickListener;
  }
  
  public void setOnMatrixChangeListener(OnMatrixChangedListener paramOnMatrixChangedListener) {
    this.mMatrixChangeListener = paramOnMatrixChangedListener;
  }
  
  public void setOnPhotoTapListener(OnPhotoTapListener paramOnPhotoTapListener) {
    this.mPhotoTapListener = paramOnPhotoTapListener;
  }
  
  public void setOnViewTapListener(OnViewTapListener paramOnViewTapListener) {
    this.mViewTapListener = paramOnViewTapListener;
  }
  
  public void setPhotoViewRotation(float paramFloat) {
    this.mSuppMatrix.setRotate(paramFloat % 360.0F);
    checkAndDisplayMatrix();
  }
  
  public void setRotationBy(float paramFloat) {
    this.mSuppMatrix.postRotate(paramFloat % 360.0F);
    checkAndDisplayMatrix();
  }
  
  public void setRotationTo(float paramFloat) {
    this.mSuppMatrix.setRotate(paramFloat % 360.0F);
    checkAndDisplayMatrix();
  }
  
  public void setScale(float paramFloat) {
    setScale(paramFloat, false);
  }
  
  public void setScale(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean) {
    ImageView imageView = getImageView();
    if (imageView != null) {
      if (paramFloat1 < this.mMinScale || paramFloat1 > this.mMaxScale) {
        LogManager.getLogger().i("PhotoViewAttacher", "Scale must be within the range of minScale and maxScale");
        return;
      } 
    } else {
      return;
    } 
    if (paramBoolean) {
      imageView.post(new AnimatedZoomRunnable(getScale(), paramFloat1, paramFloat2, paramFloat3));
      return;
    } 
    this.mSuppMatrix.setScale(paramFloat1, paramFloat1, paramFloat2, paramFloat3);
    checkAndDisplayMatrix();
  }
  
  public void setScale(float paramFloat, boolean paramBoolean) {
    ImageView imageView = getImageView();
    if (imageView != null)
      setScale(paramFloat, (imageView.getRight() / 2), (imageView.getBottom() / 2), paramBoolean); 
  }
  
  public void setScaleType(ImageView.ScaleType paramScaleType) {
    if (isSupportedScaleType(paramScaleType) && paramScaleType != this.mScaleType) {
      this.mScaleType = paramScaleType;
      update();
    } 
  }
  
  public void setZoomTransitionDuration(int paramInt) {
    int i = paramInt;
    if (paramInt < 0)
      i = 200; 
    this.ZOOM_DURATION = i;
  }
  
  public void setZoomable(boolean paramBoolean) {
    this.mZoomEnabled = paramBoolean;
    update();
  }
  
  public void update() {
    ImageView imageView = getImageView();
    if (imageView != null) {
      if (this.mZoomEnabled) {
        setImageViewScaleTypeMatrix(imageView);
        updateBaseMatrix(imageView.getDrawable());
        return;
      } 
    } else {
      return;
    } 
    resetMatrix();
  }
  
  private class AnimatedZoomRunnable implements Runnable {
    private final float mFocalX;
    
    private final float mFocalY;
    
    private final long mStartTime;
    
    private final float mZoomEnd;
    
    private final float mZoomStart;
    
    public AnimatedZoomRunnable(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
      this.mFocalX = param1Float3;
      this.mFocalY = param1Float4;
      this.mStartTime = System.currentTimeMillis();
      this.mZoomStart = param1Float1;
      this.mZoomEnd = param1Float2;
    }
    
    private float interpolate() {
      float f = Math.min(1.0F, (float)(System.currentTimeMillis() - this.mStartTime) * 1.0F / PhotoViewAttacher.this.ZOOM_DURATION);
      return PhotoViewAttacher.sInterpolator.getInterpolation(f);
    }
    
    public void run() {
      ImageView imageView = PhotoViewAttacher.this.getImageView();
      if (imageView != null) {
        float f1 = interpolate();
        float f2 = (this.mZoomStart + (this.mZoomEnd - this.mZoomStart) * f1) / PhotoViewAttacher.this.getScale();
        PhotoViewAttacher.this.mSuppMatrix.postScale(f2, f2, this.mFocalX, this.mFocalY);
        PhotoViewAttacher.this.checkAndDisplayMatrix();
        if (f1 < 1.0F) {
          Compat.postOnAnimation((View)imageView, this);
          return;
        } 
      } 
    }
  }
  
  private class FlingRunnable implements Runnable {
    private int mCurrentX;
    
    private int mCurrentY;
    
    private final ScrollerProxy mScroller;
    
    public FlingRunnable(Context param1Context) {
      this.mScroller = ScrollerProxy.getScroller(param1Context);
    }
    
    public void cancelFling() {
      if (PhotoViewAttacher.DEBUG)
        LogManager.getLogger().d("PhotoViewAttacher", "Cancel Fling"); 
      this.mScroller.forceFinished(true);
    }
    
    public void fling(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      RectF rectF = PhotoViewAttacher.this.getDisplayRect();
      if (rectF != null) {
        int j;
        int m;
        int i = Math.round(-rectF.left);
        if (param1Int1 < rectF.width()) {
          boolean bool = false;
          j = Math.round(rectF.width() - param1Int1);
          param1Int1 = bool;
        } else {
          j = i;
          param1Int1 = i;
        } 
        int k = Math.round(-rectF.top);
        if (param1Int2 < rectF.height()) {
          boolean bool = false;
          m = Math.round(rectF.height() - param1Int2);
          param1Int2 = bool;
        } else {
          m = k;
          param1Int2 = k;
        } 
        this.mCurrentX = i;
        this.mCurrentY = k;
        if (PhotoViewAttacher.DEBUG)
          LogManager.getLogger().d("PhotoViewAttacher", "fling. StartX:" + i + " StartY:" + k + " MaxX:" + j + " MaxY:" + m); 
        if (i != j || k != m) {
          this.mScroller.fling(i, k, param1Int3, param1Int4, param1Int1, j, param1Int2, m, 0, 0);
          return;
        } 
      } 
    }
    
    public void run() {
      if (!this.mScroller.isFinished()) {
        ImageView imageView = PhotoViewAttacher.this.getImageView();
        if (imageView != null && this.mScroller.computeScrollOffset()) {
          int i = this.mScroller.getCurrX();
          int j = this.mScroller.getCurrY();
          if (PhotoViewAttacher.DEBUG)
            LogManager.getLogger().d("PhotoViewAttacher", "fling run(). CurrentX:" + this.mCurrentX + " CurrentY:" + this.mCurrentY + " NewX:" + i + " NewY:" + j); 
          PhotoViewAttacher.this.mSuppMatrix.postTranslate((this.mCurrentX - i), (this.mCurrentY - j));
          PhotoViewAttacher.this.setImageViewMatrix(PhotoViewAttacher.this.getDrawMatrix());
          this.mCurrentX = i;
          this.mCurrentY = j;
          Compat.postOnAnimation((View)imageView, this);
          return;
        } 
      } 
    }
  }
  
  public static interface OnMatrixChangedListener {
    void onMatrixChanged(RectF param1RectF);
  }
  
  public static interface OnPhotoTapListener {
    void onPhotoTap(View param1View, float param1Float1, float param1Float2);
  }
  
  public static interface OnViewTapListener {
    void onViewTap(View param1View, float param1Float1, float param1Float2);
  }
}


/* Location:              C:\Users\dell\Desktop\Ricardo\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar\\uk\co\senab\photoview\PhotoViewAttacher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */