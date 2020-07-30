package com.mo.kutilsxdemo.activitys.widget.image_view;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：ImageView相关
 */
public class ImagesViewActivity extends BaseActivity implements TitleBarAction {


    @Override
    protected int getLayoutId() {
        return R.layout.act_view_imagesview;
    }

    @Override
    protected void initView() {
        setTitle("ImageView相关");
    }

    @Override
    protected void initData() {

    }

}
