//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.header;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Transformation;

import com.portal.common.PtrFrameLayout;
import com.portal.common.PtrUIHandler;
import com.portal.common.indicator.PtrIndicator;
import com.portal.common.util.PtrLocalDisplay;

import java.util.ArrayList;

public class StoreHouseHeader extends View implements PtrUIHandler {
    public ArrayList<StoreHouseBarItem> mItemList = new ArrayList();
    private int mLineWidth = -1;
    private float mScale = 1.0F;
    private int mDropHeight = -1;
    private float mInternalAnimationFactor = 0.7F;
    private int mHorizontalRandomness = -1;
    private float mProgress = 0.0F;
    private int mDrawZoneWidth = 0;
    private int mDrawZoneHeight = 0;
    private int mOffsetX = 0;
    private int mOffsetY = 0;
    private float mBarDarkAlpha = 0.4F;
    private float mFromAlpha = 1.0F;
    private float mToAlpha = 0.4F;
    private int mLoadingAniDuration = 1000;
    private int mLoadingAniSegDuration = 1000;
    private int mLoadingAniItemDuration = 400;
    private Transformation mTransformation = new Transformation();
    private boolean mIsInLoading = false;
    private StoreHouseHeader.AniController mAniController = new StoreHouseHeader.AniController();
    private int mTextColor = -1;

    public StoreHouseHeader(Context context) {
        super(context);
        this.initView();
    }

    public StoreHouseHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
    }

    public StoreHouseHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
    }

    private void initView() {
        PtrLocalDisplay.init(this.getContext());
        this.mLineWidth = PtrLocalDisplay.dp2px(1.0F);
        this.mDropHeight = PtrLocalDisplay.dp2px(40.0F);
        this.mHorizontalRandomness = PtrLocalDisplay.SCREEN_WIDTH_PIXELS / 2;
    }

    private void setProgress(float progress) {
        this.mProgress = progress;
    }

    public int getLoadingAniDuration() {
        return this.mLoadingAniDuration;
    }

    public void setLoadingAniDuration(int duration) {
        this.mLoadingAniDuration = duration;
        this.mLoadingAniSegDuration = duration;
    }

    public StoreHouseHeader setLineWidth(int width) {
        this.mLineWidth = width;

        for(int i = 0; i < this.mItemList.size(); ++i) {
            ((StoreHouseBarItem)this.mItemList.get(i)).setLineWidth(width);
        }

        return this;
    }

    public StoreHouseHeader setTextColor(int color) {
        this.mTextColor = color;

        for(int i = 0; i < this.mItemList.size(); ++i) {
            ((StoreHouseBarItem)this.mItemList.get(i)).setColor(color);
        }

        return this;
    }

    public StoreHouseHeader setDropHeight(int height) {
        this.mDropHeight = height;
        return this;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = this.getTopOffset() + this.mDrawZoneHeight + this.getBottomOffset();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mOffsetX = (this.getMeasuredWidth() - this.mDrawZoneWidth) / 2;
        this.mOffsetY = this.getTopOffset();
        this.mDropHeight = this.getTopOffset();
    }

    private int getTopOffset() {
        return this.getPaddingTop() + PtrLocalDisplay.dp2px(10.0F);
    }

    private int getBottomOffset() {
        return this.getPaddingBottom() + PtrLocalDisplay.dp2px(10.0F);
    }

    public void initWithString(String str) {
        this.initWithString(str, 25);
    }

    public void initWithString(String str, int fontSize) {
        ArrayList pointList = StoreHousePath.getPath(str, (float)fontSize * 0.01F, 14);
        this.initWithPointList(pointList);
    }

    public void initWithStringArray(int id) {
        String[] points = this.getResources().getStringArray(id);
        ArrayList pointList = new ArrayList();

        for(int i = 0; i < points.length; ++i) {
            String[] x = points[i].split(",");
            float[] f = new float[4];

            for(int j = 0; j < 4; ++j) {
                f[j] = Float.parseFloat(x[j]);
            }

            pointList.add(f);
        }

        this.initWithPointList(pointList);
    }

    public float getScale() {
        return this.mScale;
    }

    public void setScale(float scale) {
        this.mScale = scale;
    }

    public void initWithPointList(ArrayList<float[]> pointList) {
        float drawWidth = 0.0F;
        float drawHeight = 0.0F;
        boolean shouldLayout = this.mItemList.size() > 0;
        this.mItemList.clear();

        for(int i = 0; i < pointList.size(); ++i) {
            float[] line = (float[])pointList.get(i);
            PointF startPoint = new PointF((float)PtrLocalDisplay.dp2px(line[0]) * this.mScale, (float)PtrLocalDisplay.dp2px(line[1]) * this.mScale);
            PointF endPoint = new PointF((float)PtrLocalDisplay.dp2px(line[2]) * this.mScale, (float)PtrLocalDisplay.dp2px(line[3]) * this.mScale);
            drawWidth = Math.max(drawWidth, startPoint.x);
            drawWidth = Math.max(drawWidth, endPoint.x);
            drawHeight = Math.max(drawHeight, startPoint.y);
            drawHeight = Math.max(drawHeight, endPoint.y);
            StoreHouseBarItem item = new StoreHouseBarItem(i, startPoint, endPoint, this.mTextColor, this.mLineWidth);
            item.resetPosition(this.mHorizontalRandomness);
            this.mItemList.add(item);
        }

        this.mDrawZoneWidth = (int)Math.ceil((double)drawWidth);
        this.mDrawZoneHeight = (int)Math.ceil((double)drawHeight);
        if(shouldLayout) {
            this.requestLayout();
        }

    }

    private void beginLoading() {
        this.mIsInLoading = true;
        this.mAniController.start();
        this.invalidate();
    }

    private void loadFinish() {
        this.mIsInLoading = false;
        this.mAniController.stop();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float progress = this.mProgress;
        int c1 = canvas.save();
        int len = this.mItemList.size();

        for(int i = 0; i < len; ++i) {
            canvas.save();
            StoreHouseBarItem storeHouseBarItem = (StoreHouseBarItem)this.mItemList.get(i);
            float offsetX = (float)this.mOffsetX + storeHouseBarItem.midPoint.x;
            float offsetY = (float)this.mOffsetY + storeHouseBarItem.midPoint.y;
            if(this.mIsInLoading) {
                storeHouseBarItem.getTransformation(this.getDrawingTime(), this.mTransformation);
                canvas.translate(offsetX, offsetY);
            } else {
                if(progress == 0.0F) {
                    storeHouseBarItem.resetPosition(this.mHorizontalRandomness);
                    continue;
                }

                float startPadding = (1.0F - this.mInternalAnimationFactor) * (float)i / (float)len;
                float endPadding = 1.0F - this.mInternalAnimationFactor - startPadding;
                if(progress != 1.0F && progress < 1.0F - endPadding) {
                    float realProgress;
                    if(progress <= startPadding) {
                        realProgress = 0.0F;
                    } else {
                        realProgress = Math.min(1.0F, (progress - startPadding) / this.mInternalAnimationFactor);
                    }

                    offsetX += storeHouseBarItem.translationX * (1.0F - realProgress);
                    offsetY += (float)(-this.mDropHeight) * (1.0F - realProgress);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(360.0F * realProgress);
                    matrix.postScale(realProgress, realProgress);
                    matrix.postTranslate(offsetX, offsetY);
                    storeHouseBarItem.setAlpha(this.mBarDarkAlpha * realProgress);
                    canvas.concat(matrix);
                } else {
                    canvas.translate(offsetX, offsetY);
                    storeHouseBarItem.setAlpha(this.mBarDarkAlpha);
                }
            }

            storeHouseBarItem.draw(canvas);
            canvas.restore();
        }

        if(this.mIsInLoading) {
            this.invalidate();
        }

        canvas.restoreToCount(c1);
    }

    public void onUIReset(PtrFrameLayout frame) {
        this.loadFinish();

        for(int i = 0; i < this.mItemList.size(); ++i) {
            ((StoreHouseBarItem)this.mItemList.get(i)).resetPosition(this.mHorizontalRandomness);
        }

    }

    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    public void onUIRefreshBegin(PtrFrameLayout frame) {
        this.beginLoading();
    }

    public void onUIRefreshComplete(PtrFrameLayout frame) {
        this.loadFinish();
    }

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        float currentPercent = Math.min(1.0F, ptrIndicator.getCurrentPercent());
        this.setProgress(currentPercent);
        this.invalidate();
    }

    private class AniController implements Runnable {
        private int mTick;
        private int mCountPerSeg;
        private int mSegCount;
        private int mInterval;
        private boolean mRunning;

        private AniController() {
            this.mTick = 0;
            this.mCountPerSeg = 0;
            this.mSegCount = 0;
            this.mInterval = 0;
            this.mRunning = true;
        }

        private void start() {
            this.mRunning = true;
            this.mTick = 0;
            this.mInterval = StoreHouseHeader.this.mLoadingAniDuration / StoreHouseHeader.this.mItemList.size();
            this.mCountPerSeg = StoreHouseHeader.this.mLoadingAniSegDuration / this.mInterval;
            this.mSegCount = StoreHouseHeader.this.mItemList.size() / this.mCountPerSeg + 1;
            this.run();
        }

        public void run() {
            int pos = this.mTick % this.mCountPerSeg;

            for(int i = 0; i < this.mSegCount; ++i) {
                int index = i * this.mCountPerSeg + pos;
                if(index <= this.mTick) {
                    index %= StoreHouseHeader.this.mItemList.size();
                    StoreHouseBarItem item = (StoreHouseBarItem)StoreHouseHeader.this.mItemList.get(index);
                    item.setFillAfter(false);
                    item.setFillEnabled(true);
                    item.setFillBefore(false);
                    item.setDuration((long)StoreHouseHeader.this.mLoadingAniItemDuration);
                    item.start(StoreHouseHeader.this.mFromAlpha, StoreHouseHeader.this.mToAlpha);
                }
            }

            ++this.mTick;
            if(this.mRunning) {
                StoreHouseHeader.this.postDelayed(this, (long)this.mInterval);
            }

        }

        private void stop() {
            this.mRunning = false;
            StoreHouseHeader.this.removeCallbacks(this);
        }
    }
}
