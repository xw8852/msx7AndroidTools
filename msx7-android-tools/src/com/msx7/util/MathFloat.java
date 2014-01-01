package com.msx7.util;


public class MathFloat {
    public static final float pow(float value, int sqk) {
        float sum = 1.0f;
        for (int i = 0; i < sqk; i++) {
            sum=sum*value;
        }
        return sum;
    }
}
