package com.mo.kutilsxdemo.activitys.animation_effect.bubble;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.view.animation_effect.BubbleLayout;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：气泡从水底升起动画
 */
public class IconBubbleActivity extends BaseActivity implements TitleBarAction {

    private BubbleLayout bl_;

    @Override
    protected boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_ani_effect_bublle_icon;
    }

    @Override
    protected void initView() {
        setTitle("气泡从水底升起动画");
//        bl_ = findViewById(R.id.bl_);
    }

    @Override
    protected void onResume() {
//        bl_.setIsVisible(true);
//        bl_.setStarting(false);
//        bl_.invalidate();
        super.onResume();
    }

    @Override
    protected void onStop() {
//        bl_.setIsVisible(true);
//        bl_.setStarting(false);
//        bl_.invalidate();
        super.onStop();
    }

    @Override
    protected void initData() {

    }
}
