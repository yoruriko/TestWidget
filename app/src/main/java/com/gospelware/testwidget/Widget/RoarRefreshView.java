package com.gospelware.testwidget.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.gospelware.testwidget.R;


/**
 * Created by ricogao on 18/04/2016.
 */
public class RoarRefreshView extends BaseRefreshView implements Animatable {

    private static final float SCALE_START_PERCENT = 0.5f;
    private static final int ANIMATION_DURATION = 1000;

    private static final float ROAR_FINAL_SCALE = 0.75f;
    private static final float ROAR_INITIAL_ROTATE_GROWTH = 1.2f;
    private static final float ROAR_FINAL_ROTATE_GROWTH = 1.5f;

    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();

    private PullToRefreshView mParent;
    private Matrix mMatrix;
    private Animation mAnimation;

    private int mTop;
    private int mScreenWidth;

    private int mRoarSize = 160;
    private int mFoursquareHeight = 50;
    private float mRoarLeftOffset;
    private float mRoarTopOffset;

//    private int mRoarFaceSize = 100;
//    private float mRoarFaceLeftOffset;

    private float mPercent = 0.0f;
    private float mRotate = 0.0f;

    private Bitmap mRoar;
    private Bitmap mRoarFace;
    private Bitmap mFoursquare;

    private boolean isRefreshing = false;

    public RoarRefreshView(Context context, final PullToRefreshView parent) {
        super(context, parent);
        mParent = parent;
        mMatrix = new Matrix();

        setupAnimations();
        parent.post(new Runnable() {
            @Override
            public void run() {
                initiateDimens(parent.getWidth());
            }
        });
    }

    public void initiateDimens(int viewWidth) {
        if (viewWidth <= 0 || viewWidth == mScreenWidth) return;

        mScreenWidth = viewWidth;

        mRoarLeftOffset = (0.5f * (float) mScreenWidth)-(mRoarSize/2);
        mRoarTopOffset = (mParent.getTotalDragDistance() * 0.1f);

//        mRoarFaceLeftOffset = mRoarLeftOffset +(mRoarFaceSize/2);

        mTop = -mParent.getTotalDragDistance();

        createBitmaps();
    }

    private void createBitmaps() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        mRoar = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.mane, options);
        mRoar = Bitmap.createScaledBitmap(mRoar, mRoarSize, mRoarSize, true);

        mRoarFace = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.lion,options);
//        mRoarFace = Bitmap.createScaledBitmap(mRoarFace,mRoarFaceSize,mRoarFaceSize,true);
        mRoarFace = Bitmap.createScaledBitmap(mRoarFace,mRoarSize,mRoarSize,true);

        mFoursquare = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.foursquare,options);
        float scale = (float)mFoursquareHeight/mFoursquare.getHeight();
        int width = Math.round(scale*mFoursquare.getWidth());
        mFoursquare = Bitmap.createScaledBitmap(mFoursquare,width,mFoursquareHeight,true);
    }

    @Override
    public void setPercent(float percent, boolean invalidate) {
        setPercent(percent);
        if (invalidate) setRotate(percent);
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mTop += offset;
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mScreenWidth <= 0) return;

        final int saveCount = canvas.save();

        canvas.translate(0, mTop);
        canvas.clipRect(0, -mTop, mScreenWidth, mParent.getTotalDragDistance());

        drawFoursquare(canvas);
        drawRoar(canvas);
//        drawRoarFace(canvas);
        canvas.restoreToCount(saveCount);
    }


    private void drawRoar(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();

        float dragPercent = mPercent;
        if (dragPercent > 1.0f) { // Slow down if pulling over set height
            dragPercent = (dragPercent + 9.0f) / 10;
        }

        float roarRadius = (float) mRoarSize / 2.0f;
        float roarRotateGrowth = ROAR_INITIAL_ROTATE_GROWTH;

        float offsetX = mRoarLeftOffset;
        float offsetY = mRoarTopOffset
                + (mParent.getTotalDragDistance() / 2) * (1.0f - dragPercent)
                - mTop; // Depending on Canvas position

        float scalePercentDelta = dragPercent - SCALE_START_PERCENT;
        if (scalePercentDelta > 0) {
            float scalePercent = scalePercentDelta / (1.0f - SCALE_START_PERCENT);
            float roarScale = 1.0f - (1.0f - ROAR_FINAL_SCALE) * scalePercent;
            roarRotateGrowth += (ROAR_FINAL_ROTATE_GROWTH - ROAR_INITIAL_ROTATE_GROWTH) * scalePercent;

            matrix.preTranslate(offsetX + (roarRadius - roarRadius * roarScale), offsetY * (2.0f - roarScale));
            matrix.preScale(roarScale, roarScale);

            offsetX += roarRadius;
            offsetY = offsetY * (2.0f - roarScale) + roarRadius * roarScale;
        } else {
            matrix.postTranslate(offsetX, offsetY);

            offsetX += roarRadius;
            offsetY += roarRadius;
        }
        Matrix rotateMatrix = new Matrix(matrix);
        rotateMatrix.postRotate(
                (isRefreshing ? -360 : 360) * mRotate * (isRefreshing ? 1 : roarRotateGrowth),
                offsetX,
                offsetY);

        canvas.drawBitmap(mRoar,rotateMatrix, null);
        canvas.drawBitmap(mRoarFace,matrix,null);
    }

    private void drawFoursquare(Canvas canvas){
        Matrix matrix = mMatrix;
        matrix.reset();
        float dragPercent = mPercent;
        float scalePercentDelta = dragPercent - SCALE_START_PERCENT;
        if(scalePercentDelta>0){
            float offsetX = (mScreenWidth-mFoursquare.getWidth())/2;
            float offsetY = mParent.getTotalDragDistance() - mFoursquareHeight -10;
            matrix.postTranslate(offsetX,offsetY);
            canvas.drawBitmap(mFoursquare,matrix,null);
        }
    }

//    private void drawRoarFace(Canvas canvas){
//        Matrix matrix = mMatrix;
//        matrix.reset();
//
//        float dragPercent = mPercent;
//        if (dragPercent > 1.0f) { // Slow down if pulling over set height
//            dragPercent = (dragPercent + 9.0f) / 10;
//        }
//
//        float roarRadius = (float) mRoarFaceSize / 2.0f;
//
//        float offsetX = mRoarFaceLeftOffset;
//        float offsetY = mRoarTopOffset
//                + (mParent.getTotalDragDistance() / 2) * (1.0f - dragPercent)
//                - mTop; // Depending on Canvas position
//
//        float scalePercentDelta = dragPercent - SCALE_START_PERCENT;
//        if (scalePercentDelta > 0) {
//            float scalePercent = scalePercentDelta / (1.0f - SCALE_START_PERCENT);
//            float roarScale = 1.0f - (1.0f - ROAR_FINAL_SCALE) * scalePercent;
//            float transY = roarScale*(mRoarFaceSize/2);
//
//            matrix.preTranslate(offsetX + (roarRadius - roarRadius * roarScale), offsetY * (2.0f - roarScale)+transY);
//            matrix.preScale(roarScale, roarScale);
//        } else {
//            matrix.postTranslate(offsetX, offsetY+(mRoarFaceSize/2));
//        }
//        canvas.drawBitmap(mRoarFace,matrix, null);
//    }



    public void setPercent(float percent) {
        mPercent = percent;
    }

    public void setRotate(float rotate) {
        mRotate = rotate;
        invalidateSelf();
    }

    public void resetOriginals() {
        setPercent(0);
        setRotate(0);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right,top);
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void start() {
        mAnimation.reset();
        isRefreshing = true;
        mParent.startAnimation(mAnimation);
    }

    @Override
    public void stop() {
        mParent.clearAnimation();
        isRefreshing = false;
        resetOriginals();
    }

    private void setupAnimations() {
        mAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setRotate(interpolatedTime);
            }
        };
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(LINEAR_INTERPOLATOR);
        mAnimation.setDuration(ANIMATION_DURATION);
    }

}
