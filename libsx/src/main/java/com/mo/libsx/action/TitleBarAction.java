package com.mo.libsx.action;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mo.libsx.R;
import com.mo.libsx.utils.viewUtil.ViewUtil;
import com.mo.libsx.view.KTitleView;

/**
 * @ author：mo
 * @ data：2020/7/30:14:52
 * @ 功能：
 */
public interface TitleBarAction {
    Context getContext();

    default KTitleView getTitleView2() {
        ViewGroup rootView = ((Activity) getContext()).findViewById(android.R.id.content);
        KTitleView titleView = rootView.findViewById(R.id.ktv_);
        if (titleView == null) {
            ((LinearLayout) rootView.getChildAt(0)).addView(ViewUtil.getView(getContext(), R.layout.title_layout2), 0);
            titleView = rootView.findViewById(R.id.ktv_);
        }
        titleView.setListener(new KTitleView.KTitleBarClickListenerImpl() {
            @Override
            public void leftClick(View v) {
                super.leftClick(v);
                onTitleViewLeftClick(v);
            }

            @Override
            public void midleClick(View v) {
                super.midleClick(v);
                onTitleViewMidleClick(v);
            }

            @Override
            public void rightClick(View v) {
                super.rightClick(v);
                onTitleViewRightClick(v);
            }
        });
        return titleView;
    }

    default void setTitle(String title){
        getTitleView2().setMidleText(title);
    }

    default void onTitleViewLeftClick(View v) {
    }

    default void onTitleViewMidleClick(View v) {
    }

    default void onTitleViewRightClick(View v) {
    }


}
