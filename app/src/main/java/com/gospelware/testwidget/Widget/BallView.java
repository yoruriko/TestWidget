package com.gospelware.testwidget.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ricogao on 03/05/2016.
 */
public class BallView extends View {

    private final int LINE_WIDTH = 3;
    private final int POINT_SIZE = 8;
    private final int CURVE_WIDTH = 5;
    private int radius = 200;

    private Paint curvePaint, pointPaint, linePaint, circlePaint;
    private Path curvePath;

    private float c = 0.551915024494f;

    private float touchOffset = 20;
    private int circleRadius = 20;

    //contains 4 point: bottom,right,top,left
    private List<PPoint> points = new ArrayList<>();

    private int centreX, centreY;

    private PointF currentPoint;


    class PPoint {
        PointF mainPoint, controlPoint1, controlPoint2;

        public PPoint(PointF mainPoint, PointF controlPoint1, PointF controlPoint2) {
            this.mainPoint = mainPoint;
            this.controlPoint1 = controlPoint1;
            this.controlPoint2 = controlPoint2;
        }
    }

    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        curvePath = new Path();
        curvePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        curvePaint.setDither(true);
        curvePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        curvePaint.setStrokeWidth(CURVE_WIDTH);
        curvePaint.setColor(Color.BLUE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(LINE_WIDTH);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(POINT_SIZE);
        pointPaint.setColor(Color.RED);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(LINE_WIDTH);
        circlePaint.setColor(Color.RED);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centreX = w / 2;
        centreY = h / 2;
        float changeC = c * radius;

        points.add(new PPoint(new PointF(centreX, centreY + radius), new PointF(centreX + changeC, centreY + radius), new PointF(centreX - changeC, centreY + radius)));
        points.add(new PPoint(new PointF(centreX + radius, centreY), new PointF(centreX + radius, centreY + changeC), new PointF(centreX + radius, centreY - changeC)));
        points.add(new PPoint(new PointF(centreX, centreY - radius), new PointF(centreX + changeC, centreY - radius), new PointF(centreX - changeC, centreY - radius)));
        points.add(new PPoint(new PointF(centreX - radius, centreY), new PointF(centreX - radius, centreY - changeC), new PointF(centreX - radius, centreY + changeC)));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCurve(canvas);
        drawLine(canvas);
        drawPoints(canvas);
        drawCircle(canvas);
    }

    protected void drawCurve(Canvas canvas) {
        curvePath.reset();

        curvePath.moveTo(points.get(0).mainPoint.x, points.get(0).mainPoint.y);

        curvePath.cubicTo(points.get(0).controlPoint1.x, points.get(0).controlPoint1.y,
                points.get(1).controlPoint1.x, points.get(1).controlPoint1.y,
                points.get(1).mainPoint.x, points.get(1).mainPoint.y);

        curvePath.cubicTo(points.get(1).controlPoint2.x, points.get(1).controlPoint2.y,
                points.get(2).controlPoint1.x, points.get(2).controlPoint1.y,
                points.get(2).mainPoint.x, points.get(2).mainPoint.y);

        curvePath.cubicTo(points.get(2).controlPoint2.x, points.get(2).controlPoint2.y,
                points.get(3).controlPoint1.x, points.get(3).controlPoint1.y,
                points.get(3).mainPoint.x, points.get(3).mainPoint.y);

        curvePath.cubicTo(points.get(3).controlPoint2.x, points.get(3).controlPoint2.y,
                points.get(0).controlPoint2.x, points.get(0).controlPoint2.y,
                points.get(0).mainPoint.x, points.get(0).mainPoint.y);

        canvas.drawPath(curvePath, curvePaint);
    }

    protected void drawLine(Canvas canvas) {

        for (PPoint p : points) {
            canvas.drawLine(p.controlPoint1.x, p.controlPoint1.y, p.mainPoint.x, p.mainPoint.y, linePaint);
            canvas.drawLine(p.controlPoint2.x, p.controlPoint2.y, p.mainPoint.x, p.mainPoint.y, linePaint);
        }

    }

    protected void drawPoints(Canvas canvas) {
        for (PPoint p : points) {
            canvas.drawPoint(p.mainPoint.x, p.mainPoint.y, pointPaint);
            canvas.drawPoint(p.controlPoint1.x, p.controlPoint1.y, pointPaint);
            canvas.drawPoint(p.controlPoint2.x, p.controlPoint2.y, pointPaint);
        }
    }

    protected void drawCircle(Canvas canvas) {
        if (currentPoint != null) {
            canvas.drawCircle(currentPoint.x, currentPoint.y, circleRadius, circlePaint);
        }
    }

    protected PointF whichPoint(float x, float y) {

        RectF rectF=new RectF(x-touchOffset,y-touchOffset,x+touchOffset,y+touchOffset);


        for (PPoint p : points) {
            if(rectF.contains(p.mainPoint.x,p.mainPoint.y)){
                return p.mainPoint;
            }else if(rectF.contains(p.controlPoint1.x,p.controlPoint1.y)){
                return p.controlPoint1;
            }else if(rectF.contains(p.controlPoint2.x,p.controlPoint2.y)){
                return p.controlPoint2;
            }
        }

        return null;
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
                    currentPoint.x = event.getX();
                    currentPoint.y = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentPoint != null) {
                    currentPoint = null;
                    invalidate();
                }
                break;
        }

        return true;

    }
}
