package com.mo.kutilsx.activitys.widget.text_view;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.view.text_view.BadgeViewPro;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：提示TextView
 */
public class BadgeTextViewActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_textview_badge;
    }

    @Override
    protected void initView() {
        setTitle("提示TextView");

        TextView tv_badge = findViewById(R.id.tv_badge);
        Button btn_badge = findViewById(R.id.btn_badge);
        RelativeLayout rl_badge = findViewById(R.id.rl_badge);

        new BadgeViewPro(this).setStrColor(Color.parseColor("#ffffff"))//文本字体颜色
                .setStrSize(10)//文本字体大小
                .setMargin(15, 0, 15, 0)//目标View的Margin
                .setStrBgColor(Color.parseColor("#000000"))//文本背景颜色
                .setStrText("99+")//设置文本
                .setShape(BadgeViewPro.SHAPE_OVAL)//文本背景形状
                .setBgGravity(Gravity.RIGHT|Gravity.TOP)//文本背景位置
                .setTargetView(tv_badge);
        new BadgeViewPro(this).setStrColor(Color.parseColor("#ffffff"))//文本字体颜色
                .setStrSize(10)//文本字体大小
                .setMargin(15, 0, 15, 0)//目标View的Margin
                .setStrBgColor(Color.parseColor("#000000"))//文本背景颜色
                .setStrText("99+")//设置文本
                .setShape(BadgeViewPro.SHAPE_OVAL)//文本背景形状
                .setBgGravity(Gravity.RIGHT|Gravity.TOP)//文本背景位置
                .setTargetView(btn_badge);
        new BadgeViewPro(this).setStrColor(Color.parseColor("#ffffff"))//文本字体颜色
                .setStrSize(10)//文本字体大小
                .setMargin(15, 0, 15, 0)//目标View的Margin
                .setStrBgColor(Color.parseColor("#000000"))//文本背景颜色
                .setStrText("99+")//设置文本
                .setShape(BadgeViewPro.SHAPE_OVAL)//文本背景形状
                .setBgGravity(Gravity.CENTER)//文本背景位置
                .setTargetView(rl_badge);
    }

    @Override
    protected void initData() {

    }

}
