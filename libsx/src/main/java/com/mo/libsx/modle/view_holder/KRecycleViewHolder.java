package com.mo.libsx.modle.view_holder;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.mo.libsx.modle.listener.click_listener.KNoDoubleClickListener;
import com.mo.libsx.modle.listener.click_listener.KOnItemClickListener;
import com.mo.libsx.utils.tips_utils.LogUtil;


/**
 * @ author：mo
 * @ data：22016/12/6：18:14
 * @ 功能：通用Recycle的ViewHolder
 */
public class KRecycleViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    public KRecycleViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }


    public static KRecycleViewHolder getViewHolder(View itemView) {
        return new KRecycleViewHolder(itemView);
    }

    /**
     * 点击事件
     *
     * @param listener
     */
    public void setItemClick(final KOnItemClickListener listener) {
        itemView.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, getLayoutPosition());
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onItemLongClick(v, getLayoutPosition());
                }
                return false;
            }
        });
    }


    public View getRootView() {
        return itemView;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        //如果该View没有缓存过，则查找View并缓存
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        if (view == null) {
            LogUtil.i("View == null");
        }
        return (T) view;
    }
    /**
     * 关于事件的
     */
    public KRecycleViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public KRecycleViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public KRecycleViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }








}
