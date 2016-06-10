package com.gospelware.testwidget.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ricogao on 29/04/2016.
 */
public class CubicBezierView extends View {

    private Paint pointPaint, curvePaint, linePaint, circlePaint;
    private int centreX, centreY;
    private PointF startPoint, endPoint, controlPoint1, controlPoint2;
    private Path curvePath;

    private final int POINT_WIDTH = 15;
    private final int CURVE_WIDTH = 10;
    private final int LINE_WIDTH = 5;

    private final int circleRadius = 20;

    private final static int touchOffset = 20;
    private PointF currentPoint = null;


    public CubicBezierView(Context context) {
        this(context, null);
    }

    public CubicBezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CubicBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        curvePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        curvePath = new Path();
        startPoint = new PointF();
        endPoint = new PointF();
        controlPoint1 = new PointF();
        controlPoint2 = new PointF();

        curvePaint.setDither(true);
        curvePaint.setColor(Color.RED);
        curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setStrokeWidth(CURVE_WIDTH);

        pointPaint.setColor(Color.GRAY);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(POINT_WIDTH);

        linePaint.setDither(true);
        linePaint.setColor(Color.GRAY);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(LINE_WIDTH);

        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(LINE_WIDTH);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centreX = w / 2;
        centreY = h / 2;
        startPoint.x = centreX - 300;
        startPoint.y = centreY;
        endPoint.x = centreX + 300;
        endPoint.y = centreY;
        controlPoint1.x = centreX - 150;
        controlPoint1.y = centreY;
        controlPoint2.x = centreX + 150;
        controlPoint2.y = centreY;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawLine(canvas);
        drawCurve(canvas);
        drawPoint(canvas);
        drawCircle(canvas);
        super.onDraw(canvas);
    }

    protected void drawPoint(Canvas canvas) {
        canvas.drawPoint(startPoint.x, startPoint.y, pointPaint);
        canvas.drawPoint(controlPoint1.x, controlPoint1.y, pointPaint);
        canvas.drawPoint(controlPoint2.x, controlPoint2.y, pointPaint);
        canvas.drawPoint(endPoint.x, endPoint.y, pointPaint);
    }

    protected void drawCurve(Canvas canvas) {
        curvePath.reset();
        curvePath.moveTo(startPoint.x, startPoint.y);
        curvePath.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, endPoint.x, endPoint.y);
        canvas.drawPath(curvePath, curvePaint);
    }

    protected void drawLine(Canvas canvas) {
        canvas.drawLine(startPoint.x, startPoint.y, controlPoint1.x, controlPoint1.y, linePaint);
        canvas.drawLine(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, linePaint);
        canvas.drawLine(controlPoint2.x, controlPoint2.y, endPoint.x, endPoint.y, linePaint);
    }

    protected void drawCircle(Canvas canvas) {
        if (currentPoint != null) {
            canvas.drawCircle(currentPoint.x, currentPoint.y, circleRadius, circlePaint);
        }
    }

    protected PointF whichControlPoint(float x, float y) {

        RectF rectF = new RectF(x - touchOffset, y - touchOffset, x + touchOffset, y + touchOffset);
        if(rectF.contains(controlPoint1.x,controlPoint1.y)){
            return controlPoint1;
        }else if(rectF.contains(controlPoint2.x,controlPoint2.y)){
            return controlPoint2;
        }

        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPoint = whichControlPoint(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentPoint != null) {
                    currentPoint.x = event.getX();
                    currentPoint.y = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                currentPoint = null;
                invalidate();
                break;
        }

        return true;
    }
}
