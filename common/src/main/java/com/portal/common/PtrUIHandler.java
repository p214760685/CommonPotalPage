//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common;


import com.portal.common.indicator.PtrIndicator;

public interface PtrUIHandler {
    void onUIReset(PtrFrameLayout var1);

    void onUIRefreshPrepare(PtrFrameLayout var1);

    void onUIRefreshBegin(PtrFrameLayout var1);

    void onUIRefreshComplete(PtrFrameLayout var1);

    void onUIPositionChange(PtrFrameLayout var1, boolean var2, byte var3, PtrIndicator var4);
}
