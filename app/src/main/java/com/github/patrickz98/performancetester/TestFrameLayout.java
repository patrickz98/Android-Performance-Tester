package com.github.patrickz98.performancetester;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class TestFrameLayout extends FrameLayout
{
    private class Vector
    {
        int xDirection;
        int yDirection;
        int xSpeed;
        int ySpeed;

        Vector()
        {
            xDirection = Simple.randomBool() ? -1 : 1;
            yDirection = Simple.randomBool() ? -1 : 1;

            xSpeed = Simple.randomInt(10, 1);
            ySpeed = Simple.randomInt(10, 1);
        }

        Vector(int xDirection, int yDirection)
        {
            this.xDirection = xDirection;
            this.yDirection = yDirection;

            xSpeed = Simple.randomInt(10, 1);
            ySpeed = Simple.randomInt(10, 1);
        }

        void changeXDirection()
        {
            xDirection *= -1;
        }

        void changeYDirection()
        {
            yDirection *= -1;
        }
    }

    private FrameLayout[] boxes;
    private static final Handler handler = new Handler();

    private int layoutWidth = 900;
    private int layoutHeight = 1500;

    public TestFrameLayout(@NonNull Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        setLayoutParams(
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.TOP
                )
        );

        setBackgroundColor(0xff000000);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                layoutWidth = getMeasuredWidth();
                layoutHeight = getMeasuredHeight();
            }
        });

        createBoxes(100);
    }

    public void createBoxes(int count)
    {
        boxes = new FrameLayout[count];

        for (int inx = 0; inx < count; inx++)
        {
            FrameLayout.LayoutParams boxParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            boxParams.topMargin = Simple.randomInt(layoutHeight, 0);
            boxParams.leftMargin = Simple.randomInt(layoutWidth, 0);

            FrameLayout box = new FrameLayout(getContext());
            box.setLayoutParams(boxParams);
            box.setTag(new Vector());
            box.addView(new DrawView(getContext()));

            this.addView(box);

            boxes[ inx ] = box;
        }

        handler.postDelayed(modifiy, 40);
    }

    private final Runnable modifiy = new Runnable()
    {
        @Override
        public void run()
        {
            for (FrameLayout box : boxes)
            {
                Vector vector = (Vector) box.getTag();

                FrameLayout.LayoutParams boxParams = (FrameLayout.LayoutParams) box.getLayoutParams();
                boxParams.topMargin  += vector.yDirection * vector.ySpeed;
                boxParams.leftMargin += vector.xDirection * vector.xSpeed;

                if (boxParams.leftMargin < 0)
                {
                    boxParams.leftMargin = 0;
                    vector.changeXDirection();
                }

                if (boxParams.topMargin < 0)
                {
                    boxParams.topMargin = 0;
                    vector.changeYDirection();
                }

                int maxY = layoutHeight - DrawView.SIZE;
                if (boxParams.topMargin > maxY)
                {
                    boxParams.topMargin = maxY;
                    vector.changeYDirection();
                }

                int maxX = layoutWidth - DrawView.SIZE;
                if (boxParams.leftMargin > maxX)
                {
                    boxParams.leftMargin = maxX;
                    vector.changeXDirection();
                }

                box.setTag(vector);
                box.setLayoutParams(boxParams);
            }

            invalidate();

            handler.postDelayed(modifiy, 0);
        }
    };

}
