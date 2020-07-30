package com.mo.kutilsxdemo.activitys.animation_effect.floating;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：红旗飘
 */
public class FloatingActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_ani_effect_floating;
    }

    @Override
    protected void initView() {
        setTitle("红旗飘");
//        BitMapMeshView bmmv_ = findViewById(R.id.bmmv_);
//        bmmv_.setImgSize(ResUtil.getBitmap(R.mipmap.aa), ScreenUtil.getScreenWidth(), 200);
//        bmmv_.invalidate();
    }

    @Override
    protected void initData() {

    }
}
