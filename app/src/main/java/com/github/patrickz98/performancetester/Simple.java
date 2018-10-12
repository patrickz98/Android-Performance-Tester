package com.github.patrickz98.performancetester;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

class Simple
{
    private static Random random = new Random();

    static int randomInt(int max, int min)
    {
        return random.nextInt((max - min) + 1) + min;
    }

    static boolean randomBool()
    {
        return random.nextBoolean();
    }

    static FloatBuffer toFloatBuffer(float[] vertices)
    {
        ByteBuffer cbb = ByteBuffer.allocateDirect(vertices.length * 4);
        cbb.order(ByteOrder.nativeOrder());

        FloatBuffer buffer = cbb.asFloatBuffer();
        buffer.put(vertices);
        buffer.position(0);

        return buffer;
    }
}
