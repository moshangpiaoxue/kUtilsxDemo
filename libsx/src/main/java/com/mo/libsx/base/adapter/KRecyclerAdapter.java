package com.mo.libsx.base.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.tips_utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/20:13:28
 * @ 功能：采用装饰设计模式，将原有的适配器包装起来
 * 目前bug：当添加头尾后，想实现在没有数据的情况下显示空数据提示布局时，会把尾布局顶没，
 * 所以使用的时候空布局和尾布局不能同时使用
 */

public class KRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /** 头部条目类型 */
    private static final int HEADER_VIEW_TYPE = Integer.MIN_VALUE >> 1;
    /** 底部条目类型 */
    private static final int FOOTER_VIEW_TYPE = Integer.MAX_VALUE >> 1;
    /** 空数据条目类型 */
    private static final int EMPTY_VIEW_TYPE = 200000;

    /** 原有的适配器 */
    private RecyclerView.Adapter mRealAdapter;
    /** 头部View集合 */
    private final List<View> mHeaderViews = new ArrayList<>();
    /** 底部View集合 */
    private final List<View> mFooterViews = new ArrayList<>();
    /** 底部View集合 */
    private View mEmptyViews = null;
    /** 当前调用的位置 */
    private int mCurrentPosition;

    /** RecyclerView对象 */
    private RecyclerView mRecyclerView;

    /** 数据观察者对象 */
    private KAdapterDataObserver mObserver;

    public KRecyclerAdapter(RecyclerView.Adapter mRealAdapter) {
        setRealAdapter(mRealAdapter);
    }

    public void setRealAdapter(RecyclerView.Adapter adapter) {
        if (mRealAdapter != adapter) {
            if (mRealAdapter != null) {
                if (mObserver != null) {
                    // 为原有的RecyclerAdapter移除数据监听对象
                    mRealAdapter.unregisterAdapterDataObserver(mObserver);
                }
            }

            mRealAdapter = adapter;
            if (mRealAdapter == null || mRealAdapter.getItemCount() == 0) {
                mEmptyViews = getEmptyView();
            } else {
                mEmptyViews = null;
            }
            if (mRealAdapter != null) {
                if (mObserver == null) {
                    mObserver = new KAdapterDataObserver(this);
                }
                // 为原有的RecyclerAdapter添加数据监听对象
                mRealAdapter.registerAdapterDataObserver(mObserver);
                // 适配器不是第一次被绑定到RecyclerView上需要发送通知，因为第一次绑定会自动通知
                if (mRecyclerView != null) {
                    notifyDataSetChanged();

                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mRealAdapter != null) {
            return getHeaderViewsCount() + (mRealAdapter.getItemCount() == 0 ? 1 : mRealAdapter.getItemCount()) + getFooterViewsCount();
        } else {
            return getHeaderViewsCount() + getFooterViewsCount() + 1;
        }
    }

    @SuppressWarnings("all")
    @Override
    public int getItemViewType(int position) {
        mCurrentPosition = position;
        // 获取头部布局的总数
        int headerCount = getHeaderViewsCount();
        // 获取原有适配器的总数
        int adapterCount = mRealAdapter != null ? mRealAdapter.getItemCount() : 0;
        // 获取在原有适配器上的位置
        int adjPosition = position - headerCount;


        //当前位置小于头布局数量，认为他是头布局
        if (position < headerCount) {
            return HEADER_VIEW_TYPE;
            //当前位置减去可能存在的头布局数量小于等于原适配器数量，则认为他是真正的列表布局
        } else if (adjPosition < adapterCount) {
            return mRealAdapter.getItemViewType(adjPosition);
            //当前位置减去可能存在的头布局数量大于原适配器数量，则认为他是尾布局
        } else if (mRealAdapter.getItemCount() == 0 && position == getItemCount() - headerCount - 1) {
            return EMPTY_VIEW_TYPE;
        } else {
            return FOOTER_VIEW_TYPE;
        }
    }

    public int getPosition() {
        return mCurrentPosition;
    }

    @SuppressWarnings("all")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_VIEW_TYPE:
                //                return newWrapViewHolder(mHeaderViews.get(getPosition()));
                return KRecycleViewHolder.getViewHolder(mHeaderViews.get(getPosition()));
            case FOOTER_VIEW_TYPE:
                //                return newWrapViewHolder(mFooterViews.get(getPosition() - getHeaderViewsCount() -
                //                        (mRealAdapter != null&& mRealAdapter.getItemCount()!=0 ? mRealAdapter.getItemCount() : 1)));
                return KRecycleViewHolder.getViewHolder(mFooterViews.get(getPosition() - getHeaderViewsCount() -
                        (mRealAdapter != null && mRealAdapter.getItemCount() != 0 ? mRealAdapter.getItemCount() : 1)));
            case EMPTY_VIEW_TYPE:
                //                return newWrapViewHolder(mEmptyViews);
//                KRecycleViewHolder viewHolder = KRecycleViewHolder.getViewHolder(mEmptyViews);

                final int[] partHeight = {0};
                mEmptyViews.post(new Runnable() {
                    @Override
                    public void run() {
                         partHeight[0] = mEmptyViews.getHeight();
                        LogUtil.i(partHeight[0] + "");
                    }
                });
//                mEmptyViews.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                        partHeight[0]*3));

//
                return KRecycleViewHolder.getViewHolder(mEmptyViews);
            default:
                int itemViewType = mRealAdapter.getItemViewType(getPosition() - getHeaderViewsCount());
                if (itemViewType == HEADER_VIEW_TYPE || itemViewType == FOOTER_VIEW_TYPE) {
                    throw new IllegalStateException("Please do not use this type as itemType");
                }
                if (mRealAdapter != null) {
                    return mRealAdapter.onCreateViewHolder(parent, itemViewType);
                } else {
                    return null;
                }
        }
    }

    public View getEmptyView() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEADER_VIEW_TYPE:
            case FOOTER_VIEW_TYPE:
            case EMPTY_VIEW_TYPE:
                break;
            default:
                if (mRealAdapter != null) {
                    mRealAdapter.onBindViewHolder(holder, getPosition() - getHeaderViewsCount());
                }
                break;
        }
    }

    public WrapViewHolder newWrapViewHolder(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            // IllegalStateException: ViewHolder views must not be attached when created.
            // Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)
            ((ViewGroup) parent).removeView(view);
        }
        return new WrapViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        if (mRealAdapter != null && position > getHeaderViewsCount() - 1 && position < getHeaderViewsCount() + mRealAdapter.getItemCount()) {
            return mRealAdapter.getItemId(position - getHeaderViewsCount());
        } else {
            return super.getItemId(position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        if (mRealAdapter != null) {
            mRealAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = null;
        if (mRealAdapter != null) {
            mRealAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof WrapViewHolder) {
            // 防止这个 ViewHolder 被 RecyclerView 拿去复用
            holder.setIsRecyclable(false);
            return;
        }
        if (mRealAdapter != null) {
            mRealAdapter.onViewRecycled(holder);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        if (mRealAdapter != null) {
            return mRealAdapter.onFailedToRecycleView(holder);
        }
        return super.onFailedToRecycleView(holder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (mRealAdapter != null) {
            mRealAdapter.onViewAttachedToWindow(holder);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        if (mRealAdapter != null) {
            mRealAdapter.onViewDetachedFromWindow(holder);
        }
    }

    /**
     * 添加头部View
     */
    public void addHeaderView(View view) {
        // 不能添加同一个View对象，否则会导致RecyclerView复用异常
        if (!mHeaderViews.contains(view) && !mFooterViews.contains(view)) {
            mHeaderViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除头部View
     */
    public void removeHeaderView(View view) {
        if (mHeaderViews.remove(view)) {
            notifyDataSetChanged();
        }
    }

    /**
     * 添加底部View
     */
    public void addFooterView(View view) {
        // 不能添加同一个View对象，否则会导致RecyclerView复用异常
        if (!mFooterViews.contains(view) && !mHeaderViews.contains(view)) {
            mFooterViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除底部View
     */
    public void removeFooterView(View view) {
        if (mFooterViews.remove(view)) {
            notifyDataSetChanged();
        }
    }

    /**
     * 获取头部View总数
     */
    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    /**
     * 获取底部View总数
     */
    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    /**
     * 获取头部View集合
     */
    public List<View> getHeaderViews() {
        return mHeaderViews;
    }

    /**
     * 获取底部View集合
     */
    public List<View> getFooterViews() {
        return mFooterViews;
    }


    public final class WrapViewHolder extends RecyclerView.ViewHolder {

        public WrapViewHolder(View itemView) {
            super(itemView);
        }
    }
}