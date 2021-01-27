//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class AnimationLinearLayout extends LinearLayout {
    private float f = 10.0F;
    private TextView tv;
    private boolean down = false;
    private boolean isClickEvent = true;
    private final RectF roundRect = new RectF();
    private float rect_adius = 0.0F;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private boolean isAnimationEffect = true;
    private OnClickListener l;

    public AnimationLinearLayout(Context context) {
        super(context);
        this.init(context);
    }

    public AnimationLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    @SuppressLint({"NewApi"})
    public AnimationLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }

    public void setTextSize(float size) {
        this.tv.setTextSize(size);
    }

    public void setTextSize(int unit, float size) {
        this.tv.setTextSize(unit, size);
    }

    public void setTextColor(int color) {
        this.tv.setTextColor(color);
    }

    public void setTextColor(ColorStateList colors) {
        this.tv.setTextColor(colors);
    }

    public final void setText(int resid) {
        this.tv.setText(resid);
        this.addView(this.tv, new LayoutParams(-2, -2));
    }

    public final void setText(int resid, BufferType type) {
        this.tv.setText(resid, type);
        this.addView(this.tv, new LayoutParams(-2, -2));
    }

    public final void setText(CharSequence text) {
        this.tv.setText(text);
        this.addView(this.tv, new LayoutParams(-2, -2));
    }

    public void setText(CharSequence text, BufferType type) {
        this.tv.setText(text, type);
        this.addView(this.tv, new LayoutParams(-2, -2));
    }

    private void init(Context context) {
        this.maskPaint.setAntiAlias(true);
        this.maskPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        this.zonePaint.setAntiAlias(true);
        this.zonePaint.setColor(-1);
        float density = context.getResources().getDisplayMetrics().density;
        this.rect_adius *= density;
        this.tv = new TextView(context);
    }

    public void setRectAdius(float adius) {
        this.rect_adius = adius;
        this.invalidate();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = this.getWidth();
        int h = this.getHeight();
        this.roundRect.set(0.0F, 0.0F, (float)w, (float)h);
    }

    public void draw(Canvas canvas) {
        canvas.saveLayer(this.roundRect, this.zonePaint, 31);
        canvas.drawRoundRect(this.roundRect, this.rect_adius, this.rect_adius, this.zonePaint);
        canvas.saveLayer(this.roundRect, this.maskPaint, 31);
        super.draw(canvas);
        canvas.restore();
    }

    public boolean isClickEvent() {
        return this.isClickEvent;
    }

    public void setClickEvent(boolean isClickEvent) {
        this.isClickEvent = isClickEvent;
    }

    public boolean isAnimationEffect() {
        return this.isAnimationEffect;
    }

    public void setAnimationEffect(boolean isAnimationEffect) {
        this.isAnimationEffect = isAnimationEffect;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(!this.isClickEvent) {
            return super.onTouchEvent(event);
        } else {
            ScaleAnimation acaleAnimation = null;
            Rect viewRect = new Rect();
            this.getLocalVisibleRect(viewRect);
            boolean b = event.getX() < (float)viewRect.right && event.getX() > (float)viewRect.left && event.getY() < (float)viewRect.bottom && event.getY() > (float)viewRect.top;
            switch(event.getAction()) {
                case 0:
                    if(!this.down) {
                        acaleAnimation = new ScaleAnimation(1.0F, this.getF(), 1.0F, this.getF(), 1, 0.5F, 1, 0.5F);
                        acaleAnimation.setDuration(100L);
                        acaleAnimation.setFillAfter(true);
                        if(this.isAnimationEffect) {
                            this.startAnimation(acaleAnimation);
                        }

                        this.down = true;
                        this.setPressed(true);
                    }
                    break;
                case 1:
                    this.clearAnimation(acaleAnimation, b, true);
                    break;
                case 2:
                    if(!b) {
                        this.clearAnimation(acaleAnimation, b, false);
                    }
                    break;
                case 3:
                default:
                    this.clearAnimation(acaleAnimation, b, false);
            }

            return true;
        }
    }

    public float getF() {
        return 1.0F - (float)Math.round(TypedValue.applyDimension(1, this.f, this.getResources().getDisplayMetrics())) / (float)this.getWidth();
    }

    private void clearAnimation(ScaleAnimation acaleAnimation, final boolean b, boolean up) {
        this.setPressed(false);
        if(this.down) {
            this.down = false;
            acaleAnimation = new ScaleAnimation(this.getF(), 1.0F, this.getF(), 1.0F, 1, 0.5F, 1, 0.5F);
            acaleAnimation.setDuration(100L);
            if(up) {
                acaleAnimation.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationEnd(Animation paramAnimation) {
                        if(AnimationLinearLayout.this.l != null && b) {
                            AnimationLinearLayout.this.l.onClick(AnimationLinearLayout.this);
                        }

                    }
                });
            }

            if(this.isAnimationEffect) {
                this.startAnimation(acaleAnimation);
            } else if(up && this.l != null && b) {
                this.l.onClick(this);
            }
        }

    }
}
