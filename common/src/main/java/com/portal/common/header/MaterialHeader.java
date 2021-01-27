//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;

import com.portal.common.PtrFrameLayout;
import com.portal.common.PtrUIHandler;
import com.portal.common.PtrUIHandlerHook;
import com.portal.common.indicator.PtrIndicator;

public class MaterialHeader extends View implements PtrUIHandler {
    private MaterialProgressDrawable mDrawable;
    private float mScale = 1.0F;
    private PtrFrameLayout mPtrFrameLayout;
    private Animation mScaleAnimation = new Animation() {
        public void applyTransformation(float interpolatedTime, Transformation t) {
            MaterialHeader.this.mScale = 1.0F - interpolatedTime;
            MaterialHeader.this.mDrawable.setAlpha((int)(255.0F * MaterialHeader.this.mScale));
            MaterialHeader.this.invalidate();
        }
    };

    public MaterialHeader(Context context) {
        super(context);
        this.initView();
    }

    public MaterialHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public MaterialHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    public void setPtrFrameLayout(PtrFrameLayout layout) {
        final PtrUIHandlerHook mPtrUIHandlerHook = new PtrUIHandlerHook() {
            public void run() {
                MaterialHeader.this.startAnimation(MaterialHeader.this.mScaleAnimation);
            }
        };
        this.mScaleAnimation.setDuration(200L);
        this.mScaleAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mPtrUIHandlerHook.resume();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mPtrFrameLayout = layout;
        this.mPtrFrameLayout.setRefreshCompleteHook(mPtrUIHandlerHook);
    }

    private void initView() {
        this.mDrawable = new MaterialProgressDrawable(this.getContext(), this);
        this.mDrawable.setBackgroundColor(-1);
        this.mDrawable.setCallback(this);
    }

    public void invalidateDrawable(Drawable dr) {
        if(dr == this.mDrawable) {
            this.invalidate();
        } else {
            super.invalidateDrawable(dr);
        }

    }

    public void setColorSchemeColors(int[] colors) {
        this.mDrawable.setColorSchemeColors(colors);
        this.invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = this.mDrawable.getIntrinsicHeight() + this.getPaddingTop() + this.getPaddingBottom();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, 1073741824);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int size = this.mDrawable.getIntrinsicHeight();
        this.mDrawable.setBounds(0, 0, size, size);
    }

    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        Rect rect = this.mDrawable.getBounds();
        int l = this.getPaddingLeft() + (this.getMeasuredWidth() - this.mDrawable.getIntrinsicWidth()) / 2;
        canvas.translate((float)l, (float)this.getPaddingTop());
        canvas.scale(this.mScale, this.mScale, rect.exactCenterX(), rect.exactCenterY());
        this.mDrawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    public void onUIReset(PtrFrameLayout frame) {
        this.mScale = 1.0F;
        this.mDrawable.stop();
    }

    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    public void onUIRefreshBegin(PtrFrameLayout frame) {
        this.mDrawable.setAlpha(255);
        this.mDrawable.start();
    }

    public void onUIRefreshComplete(PtrFrameLayout frame) {
        this.mDrawable.stop();
    }

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        float percent = Math.min(1.0F, ptrIndicator.getCurrentPercent());
        if(status == 2) {
            this.mDrawable.setAlpha((int)(255.0F * percent));
            this.mDrawable.showArrow(true);
            float strokeStart = percent * 0.8F;
            this.mDrawable.setStartEndTrim(0.0F, Math.min(0.8F, strokeStart));
            this.mDrawable.setArrowScale(Math.min(1.0F, percent));
            float rotation = (-0.25F + 0.4F * percent + percent * 2.0F) * 0.5F;
            this.mDrawable.setProgressRotation(rotation);
            this.invalidate();
        }

    }
}
