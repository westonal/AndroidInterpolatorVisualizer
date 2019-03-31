package com.example.interpolatorvisualizer;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public final class InterpolatorView extends View {

    protected static final String TAG = "InterpolatorView";
    private final Paint paint = new Paint();
    private final Converter converter = new Converter();
    private Interpolator interpolator = new LinearInterpolator();
    private ValueAnimator animator;
    protected float position = -1;

    public InterpolatorView(Context context) {
        super(context);
    }

    public InterpolatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterpolatorView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setTextSize(40);

        int width = getWidth();
        int height = getHeight();
        converter.setWidth(width);
        converter.setHeight(height);

        canvas.drawColor(Color.BLACK);

        drawline(canvas, Color.LTGRAY, -2, 0, 2, 0);
        int boundColor = Color.GRAY;
        drawline(canvas, boundColor, 0, -2, 0, 2);
        drawline(canvas, boundColor, 1, -2, 1, 2);
        drawline(canvas, boundColor, -2, 1, 2, 1);

        drawText(canvas, Color.WHITE, "0", 0, 0, 0, paint.getTextSize());
        drawText(canvas, Color.WHITE, "1", 1, 0, 0, paint.getTextSize());
        drawText(canvas, Color.WHITE, "1", 0, 1);

        for (int x = 0; x < width - 1; x++) {
            float x1 = converter.fromScreen(x);
            float x2 = converter.fromScreen(x + 1);
            float y1 = interpolator.getInterpolation(x1);
            float y2 = interpolator.getInterpolation(x2);

            int color = Color.RED;
            if ((x1 >= 0 || x2 >= 0) && (x1 <= 1 || x2 <= 1))
                color = Color.GREEN;
            paint.setColor(color);

            final float screenY1 = converter.toScreenY(y1);
            final float screenY2 = converter.toScreenY(y2);

            if (Math.abs(screenY1 - screenY2) < 100) {
                canvas.drawLine(x, screenY1, x + 1,
                        screenY2, paint);
            }
        }

        paint.setColor(Color.WHITE);
        canvas.drawText(interpolator.getClass().getSimpleName(), 0,
                paint.getTextSize(), paint);

        if (position >= 0) {
            float x1 = position;
            float y1 = interpolator.getInterpolation(x1);
            canvas.drawCircle(converter.toScreenX(x1), converter.toScreenY(y1),
                    10, paint);
            canvas.drawCircle(converter.toScreenX(0), converter.toScreenY(y1),
                    10, paint);
            canvas.drawCircle(converter.toScreenX(x1), converter.toScreenY(0),
                    10, paint);
            paint.setAlpha(Math.max(0, Math.min(255, (int) (y1 * 255))));
            canvas.drawCircle(converter.toScreenX(-0.5f), converter.toScreenY(0.5f),
                    20, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            play();
            return true;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();
        setMeasuredDimension(measuredWidth, measuredWidth);
    }

    private void drawText(Canvas canvas, int color, String string, float x,
                          float y) {
        paint.setColor(color);
        canvas.drawText(string, converter.toScreenX(x), converter.toScreenY(y),
                paint);
    }

    private void drawText(Canvas canvas, int color, String string, float x,
                          float y, float screenDeltaX, float screenDeltaY) {
        paint.setColor(color);
        canvas.drawText(string, converter.toScreenX(x) + screenDeltaX,
                converter.toScreenY(y) + screenDeltaY, paint);
    }

    private void drawline(Canvas canvas, int color, int fromX, int fromY,
                          int toX, int toY) {
        paint.setColor(color);
        canvas.drawLine(converter.toScreenX(fromX), converter.toScreenY(fromY),
                converter.toScreenX(toX), converter.toScreenY(toY), paint);
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        invalidate();
    }

    private static class Converter {

        private float scaleX;
        private float scaleY;

        public float toScreenX(float x) {
            return (x + 2) * scaleX;
        }

        public float fromScreen(float x) {
            return x / scaleX - 2f;
        }

        public float toScreenY(float y) {
            return (4 - y - 2) * scaleY;
        }

        public void setWidth(float width) {
            scaleX = width / 4f;
        }

        public void setHeight(float height) {
            scaleY = height / 4f;
        }

    }

    public void play() {
        if (animator != null)
            animator.cancel();
        animator = ValueAnimator.ofFloat(0, 1);
		animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                position = ((Float) animator.getAnimatedValue()).floatValue();
                Log.d(TAG, String.format("Pos: %.2f", position));
                invalidate();
            }
        });
        animator.start();
    }

}
