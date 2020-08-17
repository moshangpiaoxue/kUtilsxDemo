package com.mo.libsx.view.recycle_view.layout_manager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RvLayoutManager {
    /**
     * 设置垂直滚动
     */
    public static void setLayoutLinerVertical(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(linearLayoutManager);//跟scrollview兼容
    }

    /**
     * 设置水平滚动
     */
    public static void setLayoutLinerHorizontal(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext()) {
            @Override
            public boolean canScrollHorizontally() {
                return true;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
