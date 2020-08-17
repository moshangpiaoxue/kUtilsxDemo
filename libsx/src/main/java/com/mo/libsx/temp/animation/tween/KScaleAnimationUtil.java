package com.mo.libsx.temp.animation.tween;

import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;

import com.mo.libsx.utils.animator_util.KAnimationUtil;
import com.mo.libsx.utils.animator_util.KAnimatorListener;


/**
 * @ author：mo
 * @ data：2019/6/26:13:50
 * @ 功能：渐变尺寸缩放动画
 */
public class KScaleAnimationUtil {
    /**
     * 获取一个缩小动画
     *
     * @param interpolator   动画插入器
     * @param durationMillis 时间
     * @param listener       监听
     * @return 一个缩小动画
     */
    public static Animation getLessenScaleAnimation(Interpolator interpolator, long durationMillis, KAnimatorListener listener) {
        return KAnimationUtil.getAnimation(new ScaleAnimation(1.0f, 0.0f, 1.0f,
                0.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF), interpolator, durationMillis, listener);
    }

    /**
     * 获取一个放大动画
     *
     * @param interpolator   动画插入器
     * @param durationMillis 时间
     * @param listener       监听
     * @return 返回一个放大的效果
     */
    public static Animation getAmplificationAnimation(Interpolator interpolator, long durationMillis, KAnimatorListener listener) {
        return KAnimationUtil.getAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f,
                1.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF), interpolator, durationMillis, listener);
    }

    /**
     * @param fromX  开始时X轴坐标
     * @param toX    结束时X轴坐标
     * @param fromY  开始时Y轴坐标
     * @param toY    结束时Y轴坐标
     * @param pivotX 缩放时X轴的支点
     * @param pivotY 缩放时Y轴的支点
     * @return
     */
    public static ScaleAnimation getScaleAnimation(float fromX, float toX, float fromY, float toY,
                                                   float pivotX, float pivotY) {
        return new ScaleAnimation(fromX, toX, fromY, toY, pivotX, pivotY);

    }
}
