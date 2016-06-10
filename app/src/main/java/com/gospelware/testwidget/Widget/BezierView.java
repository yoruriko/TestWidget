package com.gospelware.testwidget.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by ricogao on 29/04/2016.
 */
public class BezierView extends View {
    private Paint pointPaint;
    private Paint curvePaint;
    private Paint controlLinePaint;
    private Paint circlePaint;
    private Path mCurvePath;
    private Path mLinePath;
    private PointF startPoint;
    private PointF endPoint;
    private PointF controlPoint;

    private int centreX, centreY;

    private final int POINT_WIDTH = 15;
    private final int CURVE_WIDTH = 10;
    private final int LINE_WIDTH = 5;

    private final int circleRadius=20;

    private final static int touchOffset = 20;
    private PointF currentPoint = null;


    private final String TAG = BezierView.class.getSimpleName();


    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        curvePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        controlLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mCurvePath = new Path();
        mLinePath = new Path();
        startPoint = new PointF();
        endPoint = new PointF();
        controlPoint = new PointF();

        curvePaint.setDither(true);
        curvePaint.setColor(Color.RED);
        curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setStrokeWidth(CURVE_WIDTH);

        pointPaint.setColor(Color.GRAY);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(POINT_WIDTH);

        controlLinePaint.setDither(true);
        controlLinePaint.setColor(Color.GRAY);
        controlLinePaint.setStyle(Paint.Style.STROKE);
        controlLinePaint.setStrokeWidth(LINE_WIDTH);

        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(LINE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawControlLine(canvas);
        drawCurve(canvas);
        drawPoints(canvas);
        drawCircle(canvas);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centreX = w / 2;
        centreY = h / 2;

        startPoint.x = centreX - 300;
        startPoint.y = centreY;
        controlPoint.x = centreX;
        controlPoint.y = centreY;
        endPoint.x = centreX + 300;
        endPoint.y = centreY;

    }

    protected void drawControlLine(Canvas canvas) {
        mLinePath.reset();
        mLinePath.moveTo(startPoint.x, startPoint.y);
        mLinePath.lineTo(controlPoint.x, controlPoint.y);
        mLinePath.lineTo(endPoint.x, endPoint.y);
        canvas.drawPath(mLinePath, controlLinePaint);
    }

    protected void drawPoints(Canvas canvas) {
        canvas.drawPoint(controlPoint.x, controlPoint.y, pointPaint);
        canvas.drawPoint(startPoint.x, startPoint.y, pointPaint);
        canvas.drawPoint(endPoint.x, endPoint.y, pointPaint);
    }

    protected void drawCurve(Canvas canvas) {
        mCurvePath.reset();
        mCurvePath.moveTo(startPoint.x, startPoint.y);
        mCurvePath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(mCurvePath, curvePaint);
    }

    protected void drawCircle(Canvas canvas){
        if(currentPoint!=null){
            canvas.drawCircle(currentPoint.x,currentPoint.y,circleRadius,circlePaint);
        }
    }

    protected PointF whichPoint(float x, float y) {
        float dX = Math.abs(x - controlPoint.x);
        float dY = Math.abs(y - controlPoint.y);
        return (dX <= touchOffset && dY <= touchOffset) ? controlPoint : null;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPoint = whichPoint(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentPoint != null) {
                    controlPoint.x =  event.getX();
                    controlPoint.y =  event.getY();
                    Log.i(TAG, "AssistPoint Position: " + controlPoint.x + "," + controlPoint.y);
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
