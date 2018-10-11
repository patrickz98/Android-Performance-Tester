package com.github.patrickz98.performancetester;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

public class DrawView extends View
{
    private class BezierPoint
    {
        private static final int DIS = 20;

        final int maxX;
        final int maxY;
        final int minX;
        final int minY;

        int x;
        int y;

        BezierPoint(int startX, int startY)
        {
            maxX = startX + DIS;
            maxY = startY + DIS;
            minX = startX - DIS;
            minY = startY - DIS;

            x = startX;
            y = startY;
        }

        void updateXY()
        {
            x += Simple.randomInt(8, -8);
            y += Simple.randomInt(8, -8);

            if (x > maxX) x = maxX;
            if (y > maxY) y = maxY;

            if (x < minX) x = minX;
            if (y < minY) y = minY;
        }
    }

    private static final String LOGTAG = DrawView.class.getSimpleName();

    private static Random random = new Random();

    private static final Handler handler = new Handler();

    private Path path;
    private Paint paint;

    private BezierPoint bezierPoint1;
    private BezierPoint bezierPoint2;
    private BezierPoint bezierPoint3;
    private BezierPoint bezierPoint4;

    public static final int SIZE = 200;

    private static int count;
    private static int last;
    private static int numshapes;

    public DrawView(Context context)
    {
        super(context);
        setScaleX(1.0f);
        setScaleY(1.0f);
        init();
    }

    public DrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void updatePath()
    {
        float half = SIZE / 2f;

        path = new Path();
        path.moveTo(0, half);

        // p1
        path.cubicTo(0, half, bezierPoint1.x, bezierPoint1.y, half, 0);

        // p2
        path.cubicTo(half, 0, bezierPoint2.x, bezierPoint2.y, SIZE, half);

        // p3
        path.cubicTo(SIZE, half, bezierPoint3.x, bezierPoint3.y, half, SIZE);

        // p4
        path.cubicTo(half, SIZE, bezierPoint4.x, bezierPoint4.y, 0, half);
    }

    private void init()
    {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(
                new LinearGradient(
                        0,
                        0,
                        0,
                        300,
                        randomColor(),
                        randomColor(),
                        Shader.TileMode.CLAMP
                )
        );


        bezierPoint1 = new BezierPoint(0, 0);
        bezierPoint2 = new BezierPoint(SIZE, 0);
        bezierPoint3 = new BezierPoint(SIZE, SIZE);
        bezierPoint4 = new BezierPoint(0, SIZE);

        updatePath();

        handler.postDelayed(modifiy, 40);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        count++;
    }

    int smallRand()
    {
//        return random.nextInt(10) - 5;
        return random.nextInt(40) - 20;
    }

    private void updateBezierPoints()
    {
        BezierPoint[] points = new BezierPoint[]{
                bezierPoint1,
                bezierPoint2,
                bezierPoint3,
                bezierPoint4,
        };

        for (BezierPoint point : points)
        {
            point.updateXY();
        }

//        int maxDis = 100;
//        bezierPoint.x = bezierPoint.x + smallRand();
//        bezierPoint.y = bezierPoint.y + smallRand();
//
//        int maxX = SIZE + maxDis;
//        int maxY = maxDis;
//        bezierPoint.x = (bezierPoint.x > maxX) ? maxX : bezierPoint.x;
//        bezierPoint.y = (bezierPoint.y > maxY) ? maxY : bezierPoint.y;
//
//        int minX = SIZE - maxDis;
//        int minY = -maxDis;
//        bezierPoint.x = (bezierPoint.x < minX) ? minX : bezierPoint.x;
//        bezierPoint.y = (bezierPoint.y < minY) ? minY : bezierPoint.y;
    }

    private final Runnable modifiy = new Runnable()
    {
        @Override
        public void run()
        {
            updateBezierPoints();
            updatePath();

            invalidate();

            handler.postDelayed(modifiy, 0);
        }
    };

    public static final Runnable showcount = new Runnable()
    {
        @Override
        public void run()
        {
            Log.d("LOGTAG", "FPS: count=" + ((count - last) / DrawView.numshapes));

            handler.postDelayed(showcount, 1000);

            last = count;
        }
    };

    private static int randomColor()
    {
        return Simple.randomInt(0xffffffff, 0xdddddddd);
    }
}
