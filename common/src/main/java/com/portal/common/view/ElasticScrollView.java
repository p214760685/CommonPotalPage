//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class ElasticScrollView extends ScrollView {
    private static final int size = 4;
    private View inner;
    private float y;
    private Rect normal = new Rect();
    private boolean DOWN = true;

    public ElasticScrollView(Context context) {
        super(context);
        this.init(context);
    }

    private void init(Context context) {
    }

    public ElasticScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    protected void onFinishInflate() {
        if(this.getChildCount() > 0) {
            this.inner = this.getChildAt(0);
        }

    }

    public boolean onTouchEvent(MotionEvent ev) {
        if(this.inner == null) {
            return super.onTouchEvent(ev);
        } else {
            this.commOnTouchEvent(ev);
            return super.onTouchEvent(ev);
        }
    }

    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch(action) {
            case 1:
            case 3:
                if(!this.normal.isEmpty()) {
                    TranslateAnimation preY1 = new TranslateAnimation(0.0F, 0.0F, (float)this.inner.getTop(), (float)this.normal.top);
                    preY1.setDuration(200L);
                    this.inner.startAnimation(preY1);
                    this.inner.layout(this.normal.left, this.normal.top, this.normal.right, this.normal.bottom);
                    this.normal.setEmpty();
                }

                this.DOWN = true;
                break;
            case 2:
                if(this.DOWN) {
                    this.y = ev.getY();
                    this.DOWN = false;
                }

                float preY = this.y;
                float nowY = ev.getY();
                int deltaY = (int)(preY - nowY) / 4;
                this.y = nowY;
                if(!this.isNeedMove()) {
                    return;
                }

                if(this.normal.isEmpty()) {
                    this.normal.set(this.inner.getLeft(), this.inner.getTop(), this.inner.getRight(), this.inner.getBottom());
                    return;
                }

                int yy = this.inner.getTop() - deltaY;
                this.inner.layout(this.inner.getLeft(), yy, this.inner.getRight(), this.inner.getBottom() - deltaY);
        }

    }

    public boolean isNeedMove() {
        int offset = this.inner.getMeasuredHeight() - this.getHeight();
        int scrollY = this.getScrollY();
        return scrollY == 0 || scrollY == offset;
    }
}
