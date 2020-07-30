package com.mo.libsx.modle.listener.scrolling_listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ author：mo
 * @ data：2020/7/21:15:45
 * @ 功能：RecyclerView的滑动监听2
 */
public abstract class OnScrollingListener2 extends RecyclerView.OnScrollListener {

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

        if (newState == RecyclerView.SCROLL_STATE_IDLE) {

            if (!recyclerView.canScrollVertically(1)) {
                // 已经到底了
                doWhat(false, false);
            } else if (!recyclerView.canScrollVertically(-1)) {
                // 已经到顶了
                doWhat(false, true);
            }
        } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            // 正在滚动中
            doWhat(true, false);
        }
    }

    abstract void doWhat(boolean isScroling, boolean isTop);
}
