package com.example.interpolatorvisualizer;

import android.view.animation.Interpolator;

public class DecelerateAccelerateInterpolator implements Interpolator {
    private float mFactor = 1.0f;

    public DecelerateAccelerateInterpolator() {
    }

    public DecelerateAccelerateInterpolator(float factor) {
        mFactor = factor;
    }

    public float getInterpolation(float x) {
        float result;
        if (x < 0.5) {
            result = (float) (1.0f - Math.pow((1.0f - 2 * x), 2 * mFactor)) / 2;
        } else {
            result = (float) Math.pow((x - 0.5) * 2, 2 * mFactor) / 2 + 0.5f;
        }
        return result;
    }
}