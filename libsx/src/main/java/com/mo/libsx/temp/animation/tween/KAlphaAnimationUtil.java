package com.mo.libsx.temp.animation.tween;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import com.mo.libsx.utils.animator_util.KAnimationUtil;
import com.mo.libsx.utils.animator_util.KAnimatorListener;


/**
 * @ author：mo
 * @ data：2019/6/26:13:50
 * @ 功能：渐变透明度动画
 */
public class KAlphaAnimationUtil {

    /**
     * 渐变透明度动画:不可见==>完全显示
     *
     * @param interpolator 动画插入器
     * @param duration     持续时间
     * @param listener     动画监听器
     * @return Animation
     */
    public static Animation getShowAlphaAnimation(Interpolator interpolator, long duration, KAnimatorListener listener) {
        return getAlphaAnimation(0.0f, 1.0f, interpolator, duration, listener);
    }

    /**
     * 透明度渐变动画:完全显示==>不可见
     *
     * @param durationMillis 持续时间
     * @param listener       动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static Animation getHiddenAlphaAnimation(Interpolator interpolator, long durationMillis, KAnimatorListener listener) {
        return getAlphaAnimation(1.0f, 0.0f, interpolator, durationMillis, listener);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha    开始时的透明度  0.0完全透明 1.0完全不透明
     * @param toAlpha      结束时的透明度都
     * @param interpolator 动画插入器
     * @param duration     持续时间
     * @param listener     动画监听器
     * @return 一个透明度渐变动画
     */
    public static Animation getAlphaAnimation(float fromAlpha, float toAlpha, Interpolator interpolator, long duration, KAnimatorListener listener) {
        return KAnimationUtil.getAnimation(new AlphaAnimation(fromAlpha, toAlpha), interpolator, duration, listener);
    }

}
