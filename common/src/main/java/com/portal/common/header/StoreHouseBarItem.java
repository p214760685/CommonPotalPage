//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.portal.common.header;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import java.util.Random;

public class StoreHouseBarItem extends Animation {
    public PointF midPoint;
    public float translationX;
    public int index;
    private final Paint mPaint = new Paint();
    private float mFromAlpha = 1.0F;
    private float mToAlpha = 0.4F;
    private PointF mCStartPoint;
    private PointF mCEndPoint;

    public StoreHouseBarItem(int index, PointF start, PointF end, int color, int lineWidth) {
        this.index = index;
        this.midPoint = new PointF((start.x + end.x) / 2.0F, (start.y + end.y) / 2.0F);
        this.mCStartPoint = new PointF(start.x - this.midPoint.x, start.y - this.midPoint.y);
        this.mCEndPoint = new PointF(end.x - this.midPoint.x, end.y - this.midPoint.y);
        this.setColor(color);
        this.setLineWidth(lineWidth);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.STROKE);
    }

    public void setLineWidth(int width) {
        this.mPaint.setStrokeWidth((float)width);
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    public void resetPosition(int horizontalRandomness) {
        Random random = new Random();
        int randomNumber = -random.nextInt(horizontalRandomness) + horizontalRandomness;
        this.translationX = (float)randomNumber;
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float alpha = this.mFromAlpha;
        alpha += (this.mToAlpha - alpha) * interpolatedTime;
        this.setAlpha(alpha);
    }

    public void start(float fromAlpha, float toAlpha) {
        this.mFromAlpha = fromAlpha;
        this.mToAlpha = toAlpha;
        super.start();
    }

    public void setAlpha(float alpha) {
        this.mPaint.setAlpha((int)(alpha * 255.0F));
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(this.mCStartPoint.x, this.mCStartPoint.y, this.mCEndPoint.x, this.mCEndPoint.y, this.mPaint);
    }
}
