
package com.portal.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.portal.common.indicator.PtrIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PtrClassicDefaultHeader extends FrameLayout implements PtrUIHandler {
    private static final String KEY_SharedPreferences = "cube_ptr_classic_last_update";
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mRotateAniTime = 150;
    protected RotateAnimation mFlipAnimation;
    protected RotateAnimation mReverseFlipAnimation;
    protected TextView mTitleTextView;
    private View mRotateView;
    private View mProgressBar;
    private long mLastUpdateTime = -1L;
    private TextView mLastUpdateTextView;
    private String mLastUpdateTimeKey;
    private boolean mShouldShowLastUpdate;
    private PtrClassicDefaultHeader.LastUpdateTimeUpdater mLastUpdateTimeUpdater = new PtrClassicDefaultHeader.LastUpdateTimeUpdater();

    public PtrClassicDefaultHeader(Context context) {
        super(context);
        this.initViews((AttributeSet)null);
    }

    public PtrClassicDefaultHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initViews(attrs);
    }

    public PtrClassicDefaultHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        TypedArray arr = this.getContext().obtainStyledAttributes(attrs, R.styleable.PtrClassicHeader, 0, 0);
        if(arr != null) {
            this.mRotateAniTime = arr.getInt(R.styleable.PtrClassicHeader_ptr_rotate_ani_time, this.mRotateAniTime);
        }

        this.buildAnimation();
        View header = LayoutInflater.from(this.getContext()).inflate(R.layout.cube_ptr_classic_default_header, this);
        this.mRotateView = header.findViewById(R.id.ptr_classic_header_rotate_view);
        this.mTitleTextView = (TextView)header.findViewById(R.id.ptr_classic_header_rotate_view_header_title);
        this.mLastUpdateTextView = (TextView)header.findViewById(R.id.ptr_classic_header_rotate_view_header_last_update);
        this.mProgressBar = header.findViewById(R.id.ptr_classic_header_rotate_view_progressbar);
        this.resetView();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(this.mLastUpdateTimeUpdater != null) {
            this.mLastUpdateTimeUpdater.stop();
        }

    }

    public void setRotateAniTime(int time) {
        if(time != this.mRotateAniTime && time != 0) {
            this.mRotateAniTime = time;
            this.buildAnimation();
        }
    }

    public void setLastUpdateTimeKey(String key) {
        if(!TextUtils.isEmpty(key)) {
            this.mLastUpdateTimeKey = key;
        }
    }

    public void setLastUpdateTimeRelateObject(Object object) {
        this.setLastUpdateTimeKey(object.getClass().getName());
    }

    protected void buildAnimation() {
        this.mFlipAnimation = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
        this.mFlipAnimation.setInterpolator(new LinearInterpolator());
        this.mFlipAnimation.setDuration((long)this.mRotateAniTime);
        this.mFlipAnimation.setFillAfter(true);
        this.mReverseFlipAnimation = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
        this.mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        this.mReverseFlipAnimation.setDuration((long)this.mRotateAniTime);
        this.mReverseFlipAnimation.setFillAfter(true);
    }

    private void resetView() {
        this.hideRotateView();
        this.mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideRotateView() {
        this.mRotateView.clearAnimation();
        this.mRotateView.setVisibility(4);
    }

    public void onUIReset(PtrFrameLayout frame) {
        this.resetView();
        this.mShouldShowLastUpdate = true;
        this.tryUpdateLastUpdateTime();
    }

    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        this.mShouldShowLastUpdate = true;
        this.tryUpdateLastUpdateTime();
        this.mLastUpdateTimeUpdater.start();
        this.mProgressBar.setVisibility(View.VISIBLE);
        this.mRotateView.setVisibility(0);
        this.mTitleTextView.setVisibility(0);
        if(frame.isPullToRefresh()) {
            this.mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        } else {
            this.mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_down));
        }

    }

    public void onUIRefreshBegin(PtrFrameLayout frame) {
        this.mShouldShowLastUpdate = false;
        this.hideRotateView();
        this.mProgressBar.setVisibility(View.VISIBLE);
        this.mTitleTextView.setVisibility(0);
        this.mTitleTextView.setText(R.string.cube_ptr_refreshing);
        this.tryUpdateLastUpdateTime();
        this.mLastUpdateTimeUpdater.stop();
    }

    public void onUIRefreshComplete(PtrFrameLayout frame) {
        this.hideRotateView();
        this.mProgressBar.setVisibility(View.VISIBLE);
        this.mTitleTextView.setVisibility(0);
        this.mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_refresh_complete));
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences("cube_ptr_classic_last_update", 0);
        if(!TextUtils.isEmpty(this.mLastUpdateTimeKey)) {
            this.mLastUpdateTime = (new Date()).getTime();
            sharedPreferences.edit().putLong(this.mLastUpdateTimeKey, this.mLastUpdateTime).commit();
        }

    }

    private void tryUpdateLastUpdateTime() {
        if(!TextUtils.isEmpty(this.mLastUpdateTimeKey) && this.mShouldShowLastUpdate) {
            String time = this.getLastUpdateTime();
            if(TextUtils.isEmpty(time)) {
                this.mLastUpdateTextView.setVisibility(8);
            } else {
                this.mLastUpdateTextView.setVisibility(0);
                this.mLastUpdateTextView.setText(time);
            }
        } else {
            this.mLastUpdateTextView.setVisibility(8);
        }

    }

    private String getLastUpdateTime() {
        if(this.mLastUpdateTime == -1L && !TextUtils.isEmpty(this.mLastUpdateTimeKey)) {
            this.mLastUpdateTime = this.getContext().getSharedPreferences("cube_ptr_classic_last_update", 0).getLong(this.mLastUpdateTimeKey, -1L);
        }

        if(this.mLastUpdateTime == -1L) {
            return null;
        } else {
            long diffTime = (new Date()).getTime() - this.mLastUpdateTime;
            int seconds = (int)(diffTime / 1000L);
            if(diffTime < 0L) {
                return null;
            } else if(seconds <= 0) {
                return null;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(this.getContext().getString(R.string.cube_ptr_last_update));
                if(seconds < 60) {
                    sb.append(seconds + this.getContext().getString(R.string.cube_ptr_seconds_ago));
                } else {
                    int minutes = seconds / 60;
                    if(minutes > 60) {
                        int hours = minutes / 60;
                        if(hours > 24) {
                            Date date = new Date(this.mLastUpdateTime);
                            sb.append(sDataFormat.format(date));
                        } else {
                            sb.append(hours + this.getContext().getString(R.string.cube_ptr_hours_ago));
                        }
                    } else {
                        sb.append(minutes + this.getContext().getString(R.string.cube_ptr_minutes_ago));
                    }
                }

                return sb.toString();
            }
        }
    }

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int mOffsetToRefresh = frame.getOffsetToRefresh();
        int currentPos = ptrIndicator.getCurrentPosY();
        int lastPos = ptrIndicator.getLastPosY();
        if(currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if(isUnderTouch && status == 2) {
                this.crossRotateLineFromBottomUnderTouch(frame);
                if(this.mRotateView != null) {
                    this.mRotateView.clearAnimation();
                    this.mRotateView.startAnimation(this.mReverseFlipAnimation);
                }
            }
        } else if(currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh && isUnderTouch && status == 2) {
            this.crossRotateLineFromTopUnderTouch(frame);
            if(this.mRotateView != null) {
                this.mRotateView.clearAnimation();
                this.mRotateView.startAnimation(this.mFlipAnimation);
            }
        }

    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if(!frame.isPullToRefresh()) {
            this.mTitleTextView.setVisibility(0);
            this.mTitleTextView.setText(R.string.cube_ptr_release_to_refresh);
        }

    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        this.mTitleTextView.setVisibility(0);
        if(frame.isPullToRefresh()) {
            this.mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        } else {
            this.mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_down));
        }

    }

    private class LastUpdateTimeUpdater implements Runnable {
        private boolean mRunning;

        private LastUpdateTimeUpdater() {
            this.mRunning = false;
        }

        private void start() {
            if(!TextUtils.isEmpty(PtrClassicDefaultHeader.this.mLastUpdateTimeKey)) {
                this.mRunning = true;
                this.run();
            }
        }

        private void stop() {
            this.mRunning = false;
            PtrClassicDefaultHeader.this.removeCallbacks(this);
        }

        public void run() {
            PtrClassicDefaultHeader.this.tryUpdateLastUpdateTime();
            if(this.mRunning) {
                PtrClassicDefaultHeader.this.postDelayed(this, 1000L);
            }

        }
    }
}
