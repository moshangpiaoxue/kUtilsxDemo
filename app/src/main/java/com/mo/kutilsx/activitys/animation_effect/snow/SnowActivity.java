package com.mo.kutilsx.activitys.animation_effect.snow;

import android.view.View;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.view.animation_effect.snow.SnowView;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：落雪
 */
public class SnowActivity extends BaseActivity implements TitleBarAction {

    private SnowView sv_;

    @Override
    protected boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_ani_effect_snow;
    }

    @Override
    protected void initView() {
        setTitle("落雪");
        sv_ = findViewById(R.id.sv_);
        sv_.startSnowAnim(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_snow_1:
                sv_.startSnowAnim(1);
                break;
            case R.id.tv_snow_2:
                sv_.startSnowAnim(2);
                break;
            case R.id.tv_snow_3:
                sv_.startSnowAnim(3);
                break;
        }
    }

    @Override
    protected void initData() {

    }}
