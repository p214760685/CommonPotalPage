//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.view;

import android.content.Context;
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
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

public class AnimationButton extends AppCompatButton {
    private float f = 10.0F;
    private boolean down = false;
    private final RectF roundRect = new RectF();
    private float rect_adius = 4.0F;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private boolean isAnimationEffect = true;
    private OnClickListener l;

    public AnimationButton(Context context) {
        super(context);
        this.init(context);
    }

    public AnimationButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context);
    }

    public AnimationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public void setOnClickListener(OnClickListener l) {
        if(this.getBackground() == null && this.getPaint().getFlags() != 8) {
            this.setBackgroundColor();
        }

        this.l = l;
    }

    private void init(Context context) {
        this.maskPaint.setAntiAlias(true);
        this.maskPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        this.zonePaint.setAntiAlias(true);
        this.zonePaint.setColor(-1);
        float density = context.getResources().getDisplayMetrics().density;
        this.rect_adius *= density;
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
        canvas.saveLayer(this.roundRect, this.zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(this.roundRect, this.rect_adius, this.rect_adius, this.zonePaint);
        canvas.saveLayer(this.roundRect, this.maskPaint,  Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }

    public void setBackgroundColor() {
    }

    public boolean isAnimationEffect() {
        return this.isAnimationEffect;
    }

    public void setAnimationEffect(boolean isAnimationEffect) {
        this.isAnimationEffect = isAnimationEffect;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(!this.isClickable()) {
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
                        if(AnimationButton.this.l != null && b) {
                            AnimationButton.this.l.onClick(AnimationButton.this);
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
