package com.mo.libsx.temp.animation.property;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.mo.libsx.temp.animation.KContacts;


/**
 * @ author：mo
 * @ data：2019/6/26:14:15
 * @ 功能：
 */
public class KObjectAnimatorUtil {
    /**
     * @param target       动画载体（一般传要执行此动画的view）
     * @param propertyName 动画属性名 （载体所具有的set get方法的属性一般都可以）如：
     *                     translationX：X轴位移
     *                     translationY：Y轴位移
     *                     rotation：旋转
     *                     rotationX：X轴旋转
     *                     rotationY：Y轴旋转
     *                     scaleX：X轴缩放
     *                     scaleY：Y轴缩放
     *                     X\Y:具体移动/缩放/旋转到某一个点
     *                     alpha:透明度
     * @param interpolator 动画插入器
     * @param values       动画属性参数，一般两个，第一个为：动画起点，第二个为终点
     *                     默认没有开启动画，有可能有其他的操作 需手动.start()
     */
    public static ObjectAnimator getObjectAnimator(Object target, String propertyName, Interpolator interpolator, long duration, float... values) {
        Long du = duration;
        ObjectAnimator objectAnimator = ObjectAnimator
                .ofFloat(target, propertyName, values)
                .setDuration(du <= 0 ? KContacts.defultDuration : du);
        if (interpolator != null) {
            objectAnimator.setInterpolator(interpolator);
        }

        return objectAnimator;
    }


    /**
     * 抛物线动画-同时旋转
     *
     * @param view       view
     * @param starX      起始X轴坐标
     * @param endX       结束X轴坐标
     * @param starY      起始Y轴坐标
     * @param endY       结束Y轴坐标
     * @param viewWidthX view的宽度，X轴的偏移量
     * @param viewhightY view的高度 Y轴的偏移量
     */
    public static void get(View view, int starX, int endX, int starY, int endY, int viewWidthX, int viewhightY) {
        //抛物线动画
        ObjectAnimator translateAnimationX = ObjectAnimator
                .ofFloat(view, "translationX", 0, -(starX - endX) - viewWidthX)
                .setDuration(1500);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        ObjectAnimator translateAnimationY = ObjectAnimator.ofFloat(view, "translationY", 0, endY - starY + viewhightY);
        translateAnimationY.setDuration(1500);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 720f);

        // 动画的持续时间，执行多久？
        rotation.setDuration(1500);
        //缩小动画
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0);
//        scaleX.setDuration(200);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0);
//        scaleY.setDuration(200);
//        scaleY.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                anim_mask_layout.removeView(readingIV);
////                setLvTouch(activity, false);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateAnimationX).with(translateAnimationY).with(rotation);
//        animatorSet.play(scaleX).with(scaleY).after(translateAnimationX);
        animatorSet.start();
    }
    /**
     * 渐变
     *
     * @param view       view
     * @param alphaStart 开始时的透明状态( 1f 为不透明，0f 为完全透明，取值 0f ~ 1f )
     * @param alphaEnd   结束时的透明状态( 1f 为不透明，0f 为完全透明，取值 0f ~ 1f )
     * @param duration   设置动画持续时间，单位:毫秒ms
     */
    public static void alpah(View view, float alphaStart, float alphaEnd, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", alphaStart, alphaEnd);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }
    public static void set(View view){
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view,"scaleY",0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view,"scaleX",0f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view,"alpha",1f,0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }
    public static void set2(View view){
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view,"scaleY",1f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view,"scaleX",1f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view,"alpha",0f,1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator1).with(objectAnimator2).before(objectAnimator3);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }
}
