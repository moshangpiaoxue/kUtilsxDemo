package com.mo.libx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mo.libx.adapter.recycler.WrapRecyclerAdapter;

import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/20:13:26
 * @ 功能：
 */
public final class KRecyclerView extends RecyclerView {

    /** 原有的适配器 */
    private RecyclerView.Adapter mRealAdapter;

    /** 支持添加头部和底部的适配器 */
    private final WrapRecyclerAdapter mWrapAdapter = new WrapRecyclerAdapter();

    public KRecyclerView(Context context) {
        super(context);
    }

    public KRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mRealAdapter = adapter;
        // 偷梁换柱
        mWrapAdapter.setRealAdapter(mRealAdapter);
        // 禁用条目动画
        setItemAnimator(null);
        super.setAdapter(mWrapAdapter);
    }

    @Override
    public Adapter getAdapter() {
        return mRealAdapter;
    }

    /**
     * 添加头部View
     */
    public void addHeaderView(View view) {
        mWrapAdapter.addHeaderView(view);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V addHeaderView(@LayoutRes int id) {
        View headerView = LayoutInflater.from(getContext()).inflate(id, this, false);
        addHeaderView(headerView);
        return (V) headerView;
    }

    /**
     * 移除头部View
     */
    public void removeHeaderView(View view) {
        mWrapAdapter.removeHeaderView(view);
    }

    /**
     * 添加底部View
     */
    public void addFooterView(View view) {
        mWrapAdapter.addFooterView(view);
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V addFooterView(@LayoutRes int id) {
        View footerView = LayoutInflater.from(getContext()).inflate(id, this, false);
        addFooterView(footerView);
        return (V) footerView;
    }

    /**
     * 移除底部View
     */
    public void removeFooterView(View view) {
        mWrapAdapter.removeFooterView(view);
    }

    /**
     * 获取头部View总数
     */
    public int getHeaderViewsCount() {
        return mWrapAdapter.getHeaderViewsCount();
    }

    /**
     * 获取底部View总数
     */
    public int getFooterViewsCount() {
        return mWrapAdapter.getFooterViewsCount();
    }

    /**
     * 获取头部View集合
     */
    public List<View> getHeaderViews() {
        return mWrapAdapter.getHeaderViews();
    }

    /**
     * 获取底部View集合
     */
    public List<View> getFooterViews() {
        return mWrapAdapter.getFooterViews();
    }

    /**
     * 刷新头部和底部布局所有的 View 的状态
     */
    public void refreshHeaderFooterViews() {
        mWrapAdapter.notifyDataSetChanged();
    }

    /**
     * 设置在 GridLayoutManager 模式下头部和尾部都是独占一行的效果
     */
    public void adjustSpanSize() {

        final RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

                @Override
                public int getSpanSize(int position) {
                    return (position < mWrapAdapter.getHeaderViewsCount()
                            || position >= mWrapAdapter.getHeaderViewsCount() + (mRealAdapter == null ? 0 : mRealAdapter.getItemCount()))
                            ? ((GridLayoutManager) layoutManager).getSpanCount() : 1;
                }
            });
        }
    }
}