package com.mo.libx.base.action;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import java.lang.ref.WeakReference;


/**
 * @ author：mo
 * @ data：2020/7/20:11:06
 * @ 功能：Handler 意图处理
 */
public interface HandlerAction {
    Handler HANDLER = new Handler(Looper.getMainLooper());
//    public abstract  class MyHandler extends Handler {
//        WeakReference<Activity> weakReference;
//
//        public MyHandler(Activity activity) {
//            weakReference = new WeakReference<Activity>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (weakReference.get() != null) {
//                // update android ui
//                doWhat(weakReference.get(), msg);
//            }
//        }
//
//        protected abstract void doWhat(Activity activity, Message msg);
//
//    }
    /**
     * 获取 Handler
     */
    default Handler getHandler() {
        return HANDLER;
    }

    /**
     * 延迟执行
     */
    default boolean post(Runnable r) {
        return postDelayed(r, 0);
    }

    /**
     * 延迟一段时间执行
     */
    default boolean postDelayed(Runnable r, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(r, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间执行
     */
    default boolean postAtTime(Runnable r, long uptimeMillis) {
        // 发送和这个 Activity 相关的消息回调
        return HANDLER.postAtTime(r, this, uptimeMillis);
    }

    /**
     * 移除单个消息回调
     */
    default void removeCallbacks(Runnable r) {
        HANDLER.removeCallbacks(r);
    }

    /**
     * 移除全部消息回调
     */
    default void removeCallbacks() {
        HANDLER.removeCallbacksAndMessages(this);
    }
}
