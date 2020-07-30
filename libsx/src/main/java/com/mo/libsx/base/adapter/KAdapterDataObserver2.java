package com.mo.libsx.base.adapter;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @ author：mo
 * @ data：2020/7/20:13:29
 * @ 功能：
 */
public final class KAdapterDataObserver2 extends RecyclerView.AdapterDataObserver {

    private final KRecyclerAdapter2 mWrapAdapter;

    public KAdapterDataObserver2(KRecyclerAdapter2 adapter) {
        mWrapAdapter = adapter;
    }

    @Override
    public void onChanged() {
        mWrapAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        onItemRangeChanged(mWrapAdapter.getHeaderViewsCount() + positionStart, itemCount);
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeChanged(mWrapAdapter.getHeaderViewsCount() + positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeInserted(mWrapAdapter.getHeaderViewsCount() + positionStart, itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        mWrapAdapter.notifyItemRangeRemoved(mWrapAdapter.getHeaderViewsCount() + positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        mWrapAdapter.notifyItemMoved(mWrapAdapter.getHeaderViewsCount() + fromPosition, toPosition);
    }
}