package com.mo.libsx.temp.animation.property;

import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;

import com.mo.libsx.temp.animation.KContacts;


/**
 * @ author：mo
 * @ data：2019/6/27:14:04
 * @ 功能：
 */
public class KValueAnimatorUtil {

    public interface ChangeListener {
        void onChange(int count);
    }

    /**
     * @param animator 动画
     *                 ValueAnimator.ofInt（int … values）//处理整形参数
     *                 ValueAnimator.ofFloat（float … values）//处理浮点型
     *                 ValueAnimator. ofArgb(int… values) //处理颜色
     *                 ValueAnimator.ofObject（TypeEvaluator evaluator, Object… values）//处理object对象，需要自定义估值器
     *                 ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder… values) //处理PropertyValuesHolder
     * @param duration 时长
     * @param listener 监听
     * @return
     */
    public static ValueAnimator getValueAnimator(ValueAnimator animator, long duration, ValueAnimator.AnimatorUpdateListener listener) {
        Long du = duration;
        if (listener != null) {
            animator.addUpdateListener(listener);
        }
        animator.setDuration(du <= 0 ? KContacts.defultDuration : du);
        return animator;

    }

    /**
     * 颜色渐变动画
     *
     * @param beforeColor 变化之前的颜色
     * @param afterColor  变化之后的颜色
     * @param listener    变化事件监听，数据处理在回调监听里做： textView.setTextColor(intValue); 回调值为两个颜色中间的颜色值
     */
    public static void getColorGradient(int beforeColor, int afterColor, final ChangeListener listener) {
        ValueAnimator valueAnimator = getValueAnimator(
                ValueAnimator.ofObject(new ArgbEvaluator(), beforeColor, afterColor), 3000, (new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        listener.onChange((Integer) animation.getAnimatedValue());
                    }
                }));
        valueAnimator.start();
    }

    /**
     * 获取倒计时动画（没有动画效果，只是实现功能）
     *
     * @param start    开始数
     * @param end      结束数
     * @param listener 数据变化监听
     * @return
     */
    public static ValueAnimator getCountDwon(int start, int end, final ChangeListener listener) {
        ValueAnimator valueAnimator = getValueAnimator(ValueAnimator.ofInt(start, end), Math.abs(start - end) * 1000, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                listener.onChange((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        return valueAnimator;

    }
}
