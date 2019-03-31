package com.example.interpolators;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public final class RepeatInterpolator implements Interpolator {

    private final double repeats;
    private final Interpolator interpolator;

    public RepeatInterpolator(double repeats) {
        this(new LinearInterpolator(), repeats);
    }

    public RepeatInterpolator(Interpolator interpolator, double repeats) {
        this.interpolator = interpolator;
        this.repeats = repeats;
    }

    @Override
    public float getInterpolation(float v) {
        double v1 = v % (1.0 / repeats);
        return interpolator.getInterpolation((float) (v1 * repeats));
    }
}
