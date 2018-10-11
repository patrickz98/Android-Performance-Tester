package com.github.patrickz98.performancetester;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

public class Box extends FrameLayout
{
    private static final int SIZE = 200;

    public Box(@NonNull Context context)
    {
        super(context);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(SIZE, SIZE);
        params.topMargin = 200;
        params.leftMargin = 200;

        setLayoutParams(params);

        setBackgroundColor(0xff0000ff);
    }
}
