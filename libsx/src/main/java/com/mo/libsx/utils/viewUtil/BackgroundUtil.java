package com.mo.libsx.utils.viewUtil;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.view.View;

import com.mo.libsx.utils.colorsUtils.ColorUtils;
import com.mo.libsx.utils.dataUtil.dealUtil.DensityUtil;
import com.mo.libsx.utils.image.BitmapUtil;
import com.mo.libsx.utils.image.DrawableUtil;
import com.mo.libsx.view.drable.CircleDrawable;


/**
 * @ author：mo
 * @ data：2019/2/13:9:52
 * @ 功能：View 背景相关
 */
public class BackgroundUtil {
    /**
     * 设置圆角矩形背景
     *
     * @param mContext   Context
     * @param view       TargetView
     * @param dipRadius  circular radius
     * @param badgeColor background color
     */
    public static void setRoundRectBg(Context mContext, View view, int dipRadius, int badgeColor) {
        int radius = DensityUtil.dp2px(dipRadius);
        float[] radiusArray = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape roundRect = new RoundRectShape(radiusArray, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(roundRect);
        bgDrawable.getPaint().setColor(badgeColor);
        view.setBackgroundDrawable(bgDrawable);
    }

    /**
     * 设置矩形背景
     *
     * @param view       TargetView
     * @param badgeColor background color
     */
    public static void setRectBg(View view, int badgeColor) {
        RectShape rectShape = new RectShape();
        ShapeDrawable drawable = new ShapeDrawable(rectShape);
        drawable.getPaint().setColor(badgeColor);
        drawable.getPaint().setStyle(Paint.Style.FILL); //填充
        view.setBackgroundDrawable(drawable);
    }

    /**
     * 设置椭圆背景
     *
     * @param view       TargetView
     * @param badgeColor background color
     */
    public static void setOvalBg(View view, int badgeColor) {
        OvalShape ovalShape = new OvalShape();
        ShapeDrawable drawable = new ShapeDrawable(ovalShape);
        drawable.getPaint().setColor(badgeColor);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        //        drawable.getPaint().setStrokeWidth(1);
        //        drawable.setBounds(0, 0, view.getMeasuredWidth() * 2, view.getMeasuredHeight() * 2);
        //        drawable.getPaint().setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
        view.setBackgroundDrawable(drawable);
    }

    /**
     * 设置椭圆背景
     *
     * @param view       TargetView
     * @param badgeColor background color
     */
    public static void setCircleBg(View view, int badgeColor) {
        view.setBackgroundDrawable(new CircleDrawable(badgeColor));
    }
    /**
     * 给 View 设置背景, 用于简化 SDK 版本判断
     *
     * @param view     View
     * @param drawable 背景
     */
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 给 View 添加触按效果, 触按效果为混合一个透明度为 30% 的黑色背景
     *
     * @param views 指定添加效果的 View 或多个 View
     */
    public static void addPressedEffect(View... views) {
        for (View view : views) {
            addPressedEffect(view, 0x33333333);
        }
    }

    /**
     * 给 View 添加一个颜色或者颜色蒙层作为触按效果<br/>
     * 该方法将自动在原本背景的基础上给 View 添加一个触按状态下的颜色混合 (新状态颜色 = 原本颜色 + color)
     *
     * @param view  指定添加效果的 View
     * @param color 覆盖触按状态的颜色
     */
    public static void addPressedEffect(View view, int color) {
        Drawable pressedDr = null;
        Drawable background = view.getBackground();
        if (Color.alpha(color) == 0xFF) {
            if (background instanceof StateListDrawable) {
                ((StateListDrawable) background).addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
                return;
            } else {
                pressedDr = new ColorDrawable(color);
            }
        } else {
            if (background == null) {
                pressedDr = new ColorDrawable(color);
            } else if (background instanceof ColorDrawable) {
                int fgColor = ((ColorDrawable) background).getColor();
                int pressedColor = ColorUtils.coverColor(fgColor, color);
                pressedDr = new ColorDrawable(pressedColor);
            } else if (background instanceof BitmapDrawable) {
                Bitmap fgBitmap = ((BitmapDrawable) background).getBitmap();
                Bitmap pressedBitmap = BitmapUtil.getBitmapColorMask(fgBitmap, color);
                pressedDr = BitmapUtil.toDrawable(pressedBitmap);
            } else if (background instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) background;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ColorStateList stateList = gradientDrawable.getColor();
                    if (stateList != null) {
                        int normalColor = stateList.getColorForState(new int[]{}, Color.TRANSPARENT);
                        int enabledColor = stateList.getColorForState(new int[]{android.R.attr.state_enabled}, Color.TRANSPARENT);
                        int selectedColor = stateList.getColorForState(new int[]{android.R.attr.state_selected}, Color.TRANSPARENT);
                        int pressedColor = ColorUtils.coverColor(normalColor, color);
                        gradientDrawable.setColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_selected},
                                new int[]{android.R.attr.state_pressed}, new int[]{android.R.attr.state_enabled}, new int[]{}},
                                new int[]{selectedColor, pressedColor, enabledColor, normalColor}));
                    } else {
                        gradientDrawable.setColor(new ColorStateList(new int[][]{new int[]{android.R.attr.state_selected}}, new int[]{color}));
                    }
                    return;
                } else {
                    // TODO: 17/9/14  可以通过反射来获取 GradientState ?
                }
            } else if (background instanceof StateListDrawable) {
                StateListDrawable stateListDrawable = (StateListDrawable) background;

                stateListDrawable.setState(new int[]{});
                Drawable normalStateDr = stateListDrawable.getCurrent();
                pressedDr = DrawableUtil.addColorMask(normalStateDr, color);

                StateListDrawable newDrawable = new StateListDrawable();
                stateListDrawable.setState(new int[]{android.R.attr.state_selected});
                newDrawable.addState(new int[]{android.R.attr.state_selected}, stateListDrawable.getCurrent());
                newDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDr);
                stateListDrawable.setState(new int[]{android.R.attr.state_enabled});
                newDrawable.addState(new int[]{android.R.attr.state_enabled}, stateListDrawable.getCurrent());
                newDrawable.addState(new int[]{}, normalStateDr);
                setBackground(view, newDrawable);
                return;
            }
        }
        if (pressedDr != null) {
            StateListDrawable selector = XmlUtil.buildDrawableSelector()
                    .setPressedDrawable(pressedDr)
                    .setOtherStateDrawable(background)
                    .getSelector();
            setBackground(view, selector);
        }
    }
    /**
     * 给 Drawable 添触按状态下的颜色蒙层 (在原本状态的颜色基础上盖印新的颜色)
     *
     * @param background 需要添加触按状态的 Drawable
     * @param color      覆盖触按状态的颜色
     * @return 传入的 Drawable 对象
     */
    private static Drawable addPressedState(Drawable background, int color) {
        Drawable pressedBg = null;
        if (Color.alpha(color) == 0xFF) {
            if (background instanceof StateListDrawable) {
                ((StateListDrawable) background).addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
                return background;
            } else {
                pressedBg = new ColorDrawable(color);
            }
        } else {
            if (background == null) {
                pressedBg = new ColorDrawable(color);
            } else if (background instanceof ColorDrawable) {
                int fgColor = ((ColorDrawable) background).getColor();
                int pressedColor = ColorUtils.coverColor(fgColor, color);
                pressedBg = new ColorDrawable(pressedColor);
            } else if (background instanceof BitmapDrawable) {
                Bitmap fgBitmap = ((BitmapDrawable) background).getBitmap();
                Bitmap pressedBitmap = BitmapUtil.getBitmapColorMask(fgBitmap, color);
                pressedBg = BitmapUtil.toDrawable(pressedBitmap);
//            } else if (background instanceof GradientDrawable) {
            } else if (background instanceof StateListDrawable) {
                StateListDrawable stateListDrawable = (StateListDrawable) background;
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
                int[] oldState = stateListDrawable.getState();
                stateListDrawable.setState(new int[]{});
                Drawable current = stateListDrawable.getCurrent();
                addPressedState(current, color);
                return background;
            }
        }
        if (pressedBg != null) {
            return XmlUtil.buildDrawableSelector()
                    .setPressedDrawable(pressedBg)
                    .setOtherStateDrawable(background)
                    .getSelector();
        }
        return background;
    }
}


