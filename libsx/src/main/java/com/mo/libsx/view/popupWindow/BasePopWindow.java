package com.mo.libsx.view.popupWindow;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BasePopWindow {
    private volatile static BasePopWindow instance;
    private PopupWindow mPopupWindow;
    private Activity mActivity;

    private static final float SHOW_WINDOW_ALPHA = 0.5f;
    private static final float DISMISS_WINDOW_ALPHA = 1.0f;
    private static final int INVALID_VALUE = -1;
    /** 展示主体view */
    private View mContentView;
    /** 主体view宽 */
    private int mWidth;
    /** 主体view高 */
    private int mHeight;
    /** 显示时背景透明度 */
    private float mWindowAlphaOnShow = SHOW_WINDOW_ALPHA;
    /** 消失时背景透明度 */
    private float mWindowAlphaOnDismiss = DISMISS_WINDOW_ALPHA;
    /** 点击外部是否消失 */
    private boolean mOutsideTouchDismiss;
    /** 输入模式 */
    private int mSoftInputMode = INVALID_VALUE;
    /** 动画风格 */
    private int mAnimationStyle = INVALID_VALUE;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LOCATION_METHOD {
        int SHOW_AT_PARENT = -1;
        int SHOW_BASH_ANCHOR = 1;
    }

    public BasePopWindow(Activity mActivity) {
        this.mActivity = mActivity;
        setLayoutWrapContent();
    }

    public static synchronized BasePopWindow getInstance(Activity mActivity) {
        if (instance == null) {
            synchronized (BasePopWindow.class) {
                if (instance == null) {
                    instance = new BasePopWindow(mActivity);
                }
            }
        }
        return instance;
    }
    public void show(View anchorView, @LOCATION_METHOD int locationMethod, LayoutGravity layoutGravity, int offsetx, int offsety) {
//        if (mPopupWindow != null || mPopupWindow.isShowing()) {
//            return;
//        }
        if (mContentView == null) {
            throw new IllegalStateException("view 都没有，够浪的啊");
        }
        mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);
//        setWindowAlpha(mBuild.windowAlphaOnShow);

        switch (locationMethod) {
            default:
            case LOCATION_METHOD.SHOW_AT_PARENT:
//                mActivity.findViewById(android.R.id.content)
//                mPopupWindow.showAtLocation(mActivity.findViewById(android.R.id.content),
//                        layoutGravity.getLayoutGravity(), offsetx, offsety);
                mPopupWindow.showAtLocation(anchorView, layoutGravity.getLayoutGravity(), offsetx, offsety);
                break;
            case LOCATION_METHOD.SHOW_BASH_ANCHOR:
                int[] offset = layoutGravity.getOffset(anchorView, mPopupWindow);
                mPopupWindow.showAsDropDown(anchorView, offset[0] + offsetx, offset[1] + offsety);
                break;
        }

//        if (mBuild.showListener != null) {
//            mBuild.showListener.onShow(mPopupWindow.getContentView(), this);
//        }
        mPopupWindow.update();
    }


    public BasePopWindow setLayoutSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        return this;
    }

    public BasePopWindow setLayoutWrapContent() {
        this.mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        return this;
    }

    public BasePopWindow setLayoutMatchParent() {
        this.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        this.mHeight = ViewGroup.LayoutParams.MATCH_PARENT;
        return this;
    }
    public BasePopWindow setContent(View contentView) {
        this.mContentView = contentView;
        return this;
    }
    public BasePopWindow setContent(int layoutResource) {
        this.mContentView = LayoutInflater.from(mActivity).inflate(layoutResource, null, false);
        return this;
    }
    public BasePopWindow setWindowAlphaOnShow(float mWindowAlphaOnShow) {
        this.mWindowAlphaOnShow = mWindowAlphaOnShow;
        return this;
    }

    public BasePopWindow setWindowAlphaOnDismiss(float mWindowAlphaOnDismiss) {
        this.mWindowAlphaOnDismiss = mWindowAlphaOnDismiss;
        return this;
    }

    public BasePopWindow setOutsideTouchDismiss(boolean mOutsideTouchDismiss) {
        this.mOutsideTouchDismiss = mOutsideTouchDismiss;
        return this;
    }

    public BasePopWindow setSoftInputMode(int mSoftInputMode) {
        this.mSoftInputMode = mSoftInputMode;
        return this;
    }

    public BasePopWindow setAnimationStyle(int mAnimationStyle) {
        this.mAnimationStyle = mAnimationStyle;
        return this;
    }
}
