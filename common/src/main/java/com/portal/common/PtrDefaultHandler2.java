//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common;

import android.os.Build.VERSION;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

public abstract class PtrDefaultHandler2 extends PtrDefaultHandler implements PtrHandler2 {
    public PtrDefaultHandler2() {
    }

    public static boolean canChildScrollDown(View view) {
        if(VERSION.SDK_INT < 14) {
            if(!(view instanceof AbsListView)) {
                if(view instanceof ScrollView) {
                    ScrollView scrollView1 = (ScrollView)view;
                    return scrollView1.getChildCount() == 0?false:scrollView1.getScrollY() < scrollView1.getChildAt(0).getHeight() - scrollView1.getHeight();
                } else {
                    return false;
                }
            } else {
                AbsListView scrollView = (AbsListView)view;
                return scrollView.getChildCount() > 0 && (scrollView.getLastVisiblePosition() < scrollView.getChildCount() - 1 || scrollView.getChildAt(scrollView.getChildCount() - 1).getBottom() > scrollView.getPaddingBottom());
            }
        } else {
            return view.canScrollVertically(1);
        }
    }

    public static boolean checkContentCanBePulledUp(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollDown(content);
    }

    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return checkContentCanBePulledUp(frame, content, footer);
    }
}
