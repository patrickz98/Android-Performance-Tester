package com.github.patrickz98.performancetester;

import java.util.Random;

public class Simple
{
    private static Random random = new Random();

    public static int randomInt(int max, int min)
    {
        return random.nextInt((max - min) + 1) + min;
    }

    public static boolean randomBool()
    {
        return random.nextBoolean();
    }
}
