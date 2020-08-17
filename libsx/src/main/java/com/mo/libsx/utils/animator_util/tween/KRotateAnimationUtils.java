package com.mo.libsx.utils.animator_util.tween;

import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.mo.libsx.utils.animator_util.KAnimationUtil;
import com.mo.libsx.utils.animator_util.KAnimatorListener;

/**
 * @ author：mo
 * @ data：2020/8/14:10:55
 * @ 功能：旋转动画工具类
 */
public class KRotateAnimationUtils {
    /**
     * 以自身中心为原点旋转
     */
    public static RotateAnimation getRotateAnimation() {
        RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1000);//设置动画持续周期
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(10);//执行前的等待时间
        return rotate;
    }
    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param interpolator   动画插入器
     * @param durationMillis 动画持续时间
     * @param listener       动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static Animation getRotateAnimationByCenter(Interpolator interpolator, long durationMillis, KAnimatorListener listener) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, interpolator, durationMillis, listener);
    }

    /**
     * 获取一个旋转动画
     *
     * @param fromDegrees    开始角度
     * @param toDegrees      结束角度
     * @param pivotXType     旋转中心点X轴坐标相对类型
     * @param pivotXValue    旋转中心点X轴坐标
     * @param pivotYType     旋转中心点Y轴坐标相对类型
     * @param pivotYValue    旋转中心点Y轴坐标
     * @param interpolator   动画插入器
     * @param durationMillis 持续时间
     * @param listener       动画监听器
     * @return 一个旋转动画
     */
    public static Animation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue
            , Interpolator interpolator, long durationMillis, KAnimatorListener listener) {
        return KAnimationUtil.getAnimation(new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue),
                interpolator, durationMillis, listener);
    }
    public static Animation getRotateAnimation(float fromDegrees, float toDegrees, float pivotXValue, float pivotYValue
            , Interpolator interpolator, long durationMillis, KAnimatorListener listener) {
        return KAnimationUtil.getAnimation(new RotateAnimation(fromDegrees, toDegrees, pivotXValue, pivotYValue),
                interpolator, durationMillis, listener);
    }

}
