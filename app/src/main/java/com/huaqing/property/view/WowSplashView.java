package com.huaqing.property.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;
import com.huaqing.property.R;

public class WowSplashView extends View {

    private Path mTowerDst;
    private Paint mPaint;

    public static final float SCALE = 2f;
    public static float translateX;
    public static float translateY;

    private int mTowerHeight = 600;
    private int mTowerWidth = 440;

    private Path[] mCouldPaths;
    private int couldCount = 4;

    private float[] mCouldX = {0f, 100f, 350f, 400f};
    private float[] mCouldY = {-5000f, -5000f, -5000f, -5000f};
    private float[] mCouldFinalY = {100f, 80f, 200f, 300f};

    //    private long mDuration = 1600;
    private int mWidth;
    private float mTitleY;
    private float mFinalTitleY = mTowerHeight + 100;

    private OnEndListener mListener;

    public WowSplashView(Context context) {
        this(context, null);
    }

    public WowSplashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WowSplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        setDrawingCacheEnabled(true);

        if (Build.VERSION.SDK_INT < 21) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        mPaint = new Paint();

        mTowerDst = new Path();

        mCouldPaths = new Path[couldCount];

        for (int i = 0; i < mCouldPaths.length; i++) {
            mCouldPaths[i] = new Path();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    private void setupCouldPath(Path path, int pos) {
        path.reset();
        path.moveTo(mCouldX[pos], mCouldY[pos]);
        path.lineTo(mCouldX[pos] + 30, mCouldY[pos]);
        path.quadTo(mCouldX[pos] + 30 + 30, mCouldY[pos] - 50, mCouldX[pos] + 30 + 60, mCouldY[pos]);
        path.lineTo(mCouldX[pos] + 30 + 60 + 30, mCouldY[pos]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.scale(SCALE, SCALE);
        translateX = (mWidth - mTowerWidth * SCALE) / 2 - 80;
        translateY = 100;
        canvas.translate(translateX, translateY);

        mTowerDst.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);

        mPaint.setAlpha(255);

        for (int i = 0; i < mCouldPaths.length; i++) {
            setupCouldPath(mCouldPaths[i], i);
            canvas.drawPath(mCouldPaths[i], mPaint);
        }

        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setTextSize(60);
        String title = "福 来 来";
        int length = (int) mPaint.measureText(title);
        int x = (mTowerWidth - length) / 2;
        canvas.drawText(title, x, mTitleY, mPaint);
    }

    public void startAnimate(long duration) {

        for (int i = 0; i < couldCount; i++) {
            mCouldY[i] = 0;
        }

        getTowerValueAnimator(duration).start();

        for (int i = 0; i < mCouldPaths.length; i++) {
            final ValueAnimator couldAnimator = getCouldValueAnimator(i);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    couldAnimator.start();
                }
            }, duration / 2);
        }

        getTitleAnimate(duration).start();
    }

    private ValueAnimator getTitleAnimate(long duration) {

        ValueAnimator va = ValueAnimator.ofFloat(0, mFinalTitleY);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTitleY = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        va.setInterpolator(new DecelerateInterpolator());
        va.setDuration(duration / 3);
        return va;
    }

    private ValueAnimator getCouldValueAnimator(final int pos) {
        ValueAnimator animator = ValueAnimator.ofFloat(getHeight(), mCouldFinalY[pos]);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCouldY[pos] = (float) valueAnimator.getAnimatedValue();
                postInvalidateDelayed(10);
            }
        });
        animator.setDuration(1500);
        animator.setInterpolator(new DecelerateInterpolator());
        return animator;
    }

    @NonNull
    private ValueAnimator getTowerValueAnimator(long duration) {
        final ValueAnimator towerAnimator = ValueAnimator.ofFloat(0, 1);
        towerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                postInvalidateDelayed(10);
            }
        });
        towerAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                invalidate();

                getAlphaAnimator().start();

                towerAnimator.removeAllUpdateListeners();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        towerAnimator.setInterpolator(new DecelerateInterpolator());
        towerAnimator.setDuration(duration);
        return towerAnimator;
    }

    private ValueAnimator getAlphaAnimator() {
        final ValueAnimator va = ValueAnimator.ofInt(0, 255);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                va.removeAllUpdateListeners();
                if (mListener != null) {
                    mListener.onEnd(WowSplashView.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        va.setDuration(500);
        return va;
    }

    public interface OnEndListener {
        void onEnd(WowSplashView wowSplashView);
    }

    public void setOnEndListener(OnEndListener listener) {
        mListener = listener;
    }
}
