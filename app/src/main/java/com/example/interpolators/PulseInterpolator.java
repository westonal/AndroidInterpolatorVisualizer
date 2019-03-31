package com.example.interpolators;

import android.view.animation.Interpolator;

public final class PulseInterpolator implements Interpolator {

    private final int duration;
    private final int offFor;

    /**
     * Creates a single pulse signal that has a linear climb from 0..1 over 1 unit of time.
     * Then it stays at 1 for {@param onFor} units of time.
     * Then it has a linear drop from 1..0 over 1 unit of time.
     * Then it stays at 0 for {@param offFor} units of time.
     * <p>
     * Total duration of animation {@param onFor} + {@param offFor} + 2 units.
     *
     * @param onFor  Units of time to stay on for, relative to the duration of the climb/drop.
     * @param offFor Units of time to stay off for, relative to the duration of the climb/drop.
     */
    public PulseInterpolator(int onFor, int offFor) {
        this.duration = onFor + offFor + 2;
        this.offFor = offFor;
    }

    @Override
    public float getInterpolation(float v) {
        v *= duration;
        if (v > 1) {
            v = duration - v - offFor;
        }
        return Math.max(0, Math.min(1, v));
    }
}
