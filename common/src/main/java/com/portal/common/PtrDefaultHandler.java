//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common;

import android.os.Build.VERSION;
import android.view.View;
import android.widget.AbsListView;

public abstract class PtrDefaultHandler implements PtrHandler {
    public PtrDefaultHandler() {
    }

    public static boolean canChildScrollUp(View view) {
        if(VERSION.SDK_INT < 14) {
            if(!(view instanceof AbsListView)) {
                return view.getScrollY() > 0;
            } else {
                AbsListView absListView = (AbsListView)view;
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            }
        } else {
            return view.canScrollVertically(-1);
        }
    }

    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }
}
