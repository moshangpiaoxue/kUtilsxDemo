package com.mo.libsx.base;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.billy.android.swipe.SmartSwipeBack;
import com.hjq.toast.ToastInterceptor;
import com.hjq.toast.ToastUtils;
import com.mo.libsx.action.SwipeAction;
import com.mo.libsx.k;
import com.mo.libsx.utils.activity_utils.ActivityStackManager;

/**
 * @ author：mo
 * @ data：2020/7/21:13:32
 * @ 功能：
 */
public final class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化kutils
        k.Ext.init(this);

        // 吐司工具类
        ToastUtils.init(this);

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Log.e("Toast", "空 Toast");
                } else {
                    Log.i("Toast", text.toString());
                }
                return intercept;
            }
        });

        // Activity 侧滑返回
        SmartSwipeBack.activitySlidingBack(this, activity -> {
            if (activity instanceof SwipeAction) {
                return ((SwipeAction) activity).isSwipeEnable();
            }
            return true;
        });
    }
}
