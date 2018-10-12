package com.github.patrickz98.performancetester;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class GLTest extends GLSurfaceView
{
    GLTest(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
        GLRenderer renderer = new GLRenderer();
        setRenderer(renderer);
    }
}
