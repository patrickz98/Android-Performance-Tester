package com.github.patrickz98.performancetester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new TestFrameLayout(getApplicationContext()));
//        setContentView(new GLTest(getApplicationContext()));
    }
}
