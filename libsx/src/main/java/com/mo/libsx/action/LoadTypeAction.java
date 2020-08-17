package com.mo.libsx.action;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mo.libsx.R;
import com.mo.libsx.utils.viewUtil.ViewUtil;
import com.mo.libsx.view.LoadTypeView;

/**
 * @ author：mo
 * @ data：2020/7/31:10:01
 * @ 功能：加载状态意图，必须配合标题意图使用，因为他们都是基于根布局的添加的，当前是把状态布局加到了第二个位置，不联合使用的话，状态布局会在正常数据布局的下面显示
 */
public interface LoadTypeAction extends TitleBarAction {
    Context getContext();

    default LoadTypeView getLoadTypeView() {
        ViewGroup rootView = ((Activity) getContext()).findViewById(android.R.id.content);
        LoadTypeView loadTypeView = rootView.findViewById(R.id.ltv_);
        if (loadTypeView == null) {
            ((LinearLayout) rootView.getChildAt(0)).addView(ViewUtil.getView(getContext(), R.layout.load_type_view), 1);
            loadTypeView = rootView.findViewById(R.id.ltv_);
        }
        return loadTypeView;
    }

    /**
     * 加载完成，把加载状态布局从根布局删除，不然自定义状态的view只是隐藏，但是他依然还在那占着位置，这个时候页面是空白的，
     * 当然，也可以吧LoadTypeView设为gone，但是他既然已经没用了，还留它干啥，唯一可能会有问题的是要再次调用加载中的时候视图会出问题，先这么用着
     */
    default void loadSuccess() {
        ViewGroup rootView = ((Activity) getContext()).findViewById(android.R.id.content);
        ((LinearLayout) rootView.getChildAt(0)).removeViewAt(1);
    }

}
