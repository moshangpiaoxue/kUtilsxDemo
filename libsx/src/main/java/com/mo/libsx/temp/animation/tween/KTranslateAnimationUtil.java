package com.mo.libsx.temp.animation.tween;

import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

import com.mo.libsx.utils.animator_util.KAnimationUtil;
import com.mo.libsx.utils.animator_util.KAnimatorListener;


/**
 * @ author：mo
 * @ data：2019/6/26:13:50
 * @ 功能：位置移动动画
 */
public class KTranslateAnimationUtil {
    /**
     * 从右到左出现
     */
    public static TranslateAnimation showAnim = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f);
    /**
     * 从左到右隐藏
     */
    public static TranslateAnimation hideAnim = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f);
    /**
     * 以自身为基点，从自身的正常位置移动到顶点,向上移动一个身位
     */
    public static TranslateAnimation self2Top = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f);
    /**
     * 以自身为基点，从自身的顶点移动到正常位置
     */
    public static TranslateAnimation top2Self = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f);
    /**
     * 以自身为基点，从自身的正常位置移动到底部,向下移动一个身位
     */
    public static TranslateAnimation self2Bottom = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f);
    /**
     * 以自身为基点，从自身的底部移动到正常位置
     */
    public static TranslateAnimation bottom2Self = new TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.0f);

    /**
     * 获取位移动画
     *
     * @param fromXDelta   X轴起点
     * @param toXDelta     X轴终点
     * @param fromYDelta   Y轴起点
     * @param toYDelta     Y轴终点
     * @param interpolator 动画插入器
     * @param duration     持续时间
     * @param listener     监听
     * @return animation
     */
    public static Animation getTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, Interpolator interpolator, long duration, final KAnimatorListener listener) {
        return KAnimationUtil.getAnimation(new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta), interpolator,  duration, listener);
    }



}
