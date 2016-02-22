package com.gospelware.testwidget.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.gospelware.testwidget.R;

/**
 * Created by ricogao on 22/02/2016.
 */
public class ShareLocationClock extends View {

    private int width, height;

    private Paint bitmapPaint;
    private Paint progressPaint;
    private Bitmap sharedLocationBitmap;
    private Bitmap scaledBitmap;
    private RectF rectF;

    private boolean isProgressSet;
    private float angle;

    public ShareLocationClock(Context context) {
        super(context);
        init();
    }

    public ShareLocationClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShareLocationClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {

        sharedLocationBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.share_location);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(getResources().getColor(R.color.genderRed));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (scaledBitmap == null)
            scaledBitmap = getScaledBitmap();

        if (rectF == null)
            setPaintWidth();

        canvas.drawBitmap(scaledBitmap, 0, 0, bitmapPaint);
        if (isProgressSet)
            canvas.drawArc(rectF, -90, angle, false, progressPaint);

    }

    public void setProgress(int progress) {
        isProgressSet = true;
        float ratio = ((float)progress / 100f);
        angle = ratio * 360f;
        postInvalidate();
    }


    private void setPaintWidth() {
        int paintWidth = width / 80;
        progressPaint.setStrokeWidth(paintWidth);

        rectF = new RectF(paintWidth, paintWidth, width - paintWidth, height - paintWidth);
    }

    private Bitmap getScaledBitmap() {
        Bitmap bitmap = Bitmap.createScaledBitmap(sharedLocationBitmap, width, height, false);
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
