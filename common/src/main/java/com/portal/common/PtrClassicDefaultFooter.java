//
// Source code recreated from a .class file by IntelliJ IDEA
//

package com.portal.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.RotateAnimation;

public class PtrClassicDefaultFooter extends PtrClassicDefaultHeader {
    public PtrClassicDefaultFooter(Context context) {
        super(context);
    }

    public PtrClassicDefaultFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrClassicDefaultFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void buildAnimation() {
        super.buildAnimation();
        RotateAnimation tmp = this.mFlipAnimation;
        this.mFlipAnimation = this.mReverseFlipAnimation;
        this.mReverseFlipAnimation = tmp;
    }

    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        super.onUIRefreshPrepare(frame);
        if(frame.isPullToRefresh()) {
            this.mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_up_to_load));
        } else {
            this.mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_up));
        }

    }
}
