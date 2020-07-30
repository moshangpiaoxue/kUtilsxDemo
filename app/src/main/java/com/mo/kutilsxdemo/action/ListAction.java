package com.mo.kutilsxdemo.action;

import com.mo.libsx.base.adapter.KRecyclerAdapter;
import com.mo.libsx.view.recycle_view.KRecycleView;

import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:10
 * @ 功能：列表行为
 */
public interface ListAction<T> {
    List<T> getList();

    KRecycleView getRecycleView();

    KRecyclerAdapter getWrapRecyclerAdapter();

    default void setAdapter() {
        if (getRecycleView() != null && getWrapRecyclerAdapter() != null) {
            getRecycleView().setAdapter(getWrapRecyclerAdapter());
        }

    }
}
