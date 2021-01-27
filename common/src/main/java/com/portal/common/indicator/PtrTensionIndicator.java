//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.indicator;


public class PtrTensionIndicator extends PtrIndicator {
    private float DRAG_RATE = 0.5F;
    private float mDownY;
    private float mDownPos;
    private float mOneHeight = 0.0F;
    private float mCurrentDragPercent;
    private int mReleasePos;
    private float mReleasePercent = -1.0F;

    public PtrTensionIndicator() {
    }

    public void onPressDown(float x, float y) {
        super.onPressDown(x, y);
        this.mDownY = y;
        this.mDownPos = (float)this.getCurrentPosY();
    }

    public void onRelease() {
        super.onRelease();
        this.mReleasePos = this.getCurrentPosY();
        this.mReleasePercent = this.mCurrentDragPercent;
    }

    public void onUIRefreshComplete() {
        this.mReleasePos = this.getCurrentPosY();
        this.mReleasePercent = this.getOverDragPercent();
    }

    public void setHeaderHeight(int height) {
        super.setHeaderHeight(height);
        this.mOneHeight = (float)height * 4.0F / 5.0F;
    }

    protected void processOnMove(float currentX, float currentY, float offsetX, float offsetY) {
        if(currentY < this.mDownY) {
            super.processOnMove(currentX, currentY, offsetX, offsetY);
        } else {
            float scrollTop = (currentY - this.mDownY) * this.DRAG_RATE + this.mDownPos;
            float currentDragPercent = scrollTop / this.mOneHeight;
            if(currentDragPercent < 0.0F) {
                this.setOffset(offsetX, 0.0F);
            } else {
                this.mCurrentDragPercent = currentDragPercent;
                float boundedDragPercent = Math.min(1.0F, Math.abs(currentDragPercent));
                float extraOS = scrollTop - this.mOneHeight;
                float tensionSlingshotPercent = Math.max(0.0F, Math.min(extraOS, this.mOneHeight * 2.0F) / this.mOneHeight);
                float tensionPercent = (float)((double)(tensionSlingshotPercent / 4.0F) - Math.pow((double)(tensionSlingshotPercent / 4.0F), 2.0D)) * 2.0F;
                float extraMove = this.mOneHeight * tensionPercent / 2.0F;
                int targetY = (int)(this.mOneHeight * boundedDragPercent + extraMove);
                int change = targetY - this.getCurrentPosY();
                this.setOffset(currentX, (float)change);
            }
        }
    }

    private float offsetToTarget(float scrollTop) {
        float currentDragPercent = scrollTop / this.mOneHeight;
        this.mCurrentDragPercent = currentDragPercent;
        float boundedDragPercent = Math.min(1.0F, Math.abs(currentDragPercent));
        float extraOS = scrollTop - this.mOneHeight;
        float tensionSlingshotPercent = Math.max(0.0F, Math.min(extraOS, this.mOneHeight * 2.0F) / this.mOneHeight);
        float tensionPercent = (float)((double)(tensionSlingshotPercent / 4.0F) - Math.pow((double)(tensionSlingshotPercent / 4.0F), 2.0D)) * 2.0F;
        float extraMove = this.mOneHeight * tensionPercent / 2.0F;
        int var10000 = (int)(this.mOneHeight * boundedDragPercent + extraMove);
        return 0.0F;
    }

    public int getOffsetToKeepHeaderWhileLoading() {
        return this.getOffsetToRefresh();
    }

    public int getOffsetToRefresh() {
        return (int)this.mOneHeight;
    }

    public float getOverDragPercent() {
        return this.isUnderTouch()?this.mCurrentDragPercent:(this.mReleasePercent <= 0.0F?1.0F * (float)this.getCurrentPosY() / (float)this.getOffsetToKeepHeaderWhileLoading():this.mReleasePercent * (float)this.getCurrentPosY() / (float)this.mReleasePos);
    }
}
