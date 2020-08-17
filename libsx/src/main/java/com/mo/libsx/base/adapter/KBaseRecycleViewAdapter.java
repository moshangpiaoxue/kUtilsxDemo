package com.mo.libsx.base.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mo.libsx.R;
import com.mo.libsx.modle.listener.click_listener.KOnItemClickListener;
import com.mo.libsx.modle.listener.scrolling_listener.OnScrollingListener;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.viewUtil.ViewUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView适配器基类
 * author   ：mo
 * data     ：2016/12/6
 * time     ：18:11
 * function :RecycleView适配器
 */

public abstract class KBaseRecycleViewAdapter<T> extends RecyclerView.Adapter<KRecycleViewHolder> {
    /** 头部条目类型 */
    private static final int HEADER_VIEW_TYPE = Integer.MIN_VALUE >> 1;
    /** 底部条目类型 */
    private static final int FOOTER_VIEW_TYPE = Integer.MAX_VALUE >> 1;
    /** 空数据条目类型 */
    private static final int EMPTY_VIEW_TYPE = 200000;
    /** 空数据条目类型 */
    private static final int LOADING_VIEW_TYPE = 300000;
    /** 空数据条目类型 */
    private static final int ERROR_VIEW_TYPE = 400000;
    /** 头部View集合 */
    private final List<View> mHeaderViews = new ArrayList<>();
    /** 底部View集合 */
    private final List<View> mFooterViews = new ArrayList<>();
    /** 上下文 */
    protected Context mContext;
    /** 数据集合 */
    private List<T> mDatas;
    /** 当前调用的位置 */
    private int mCurrentPosition;
    /** 父布局 */
    private RecyclerView mRecyclerView;
    /** rv滑动监听 */
    private RecyclerView.OnScrollListener mScrollingListener;
    /** 子项点击监听 */
    private KOnItemClickListener mItemClickListener;
    /** type==0：加载中 type==1 数据空 type==3 报错 */
    private int mStateType = 0;

    /**
     * 构造方法
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected KBaseRecycleViewAdapter(Context context, List<T> datas) {
        mContext = context;
        if (mContext == null) {
            throw new IllegalArgumentException("你想干啥！");
        }
        if (datas == null) {
            mStateType = 3;
        } else {
            mDatas = datas;
            mStateType = 0;
        }
    }

    /**
     * 获取子项的type  默认是位置
     */
    @Override
    public int getItemViewType(int position) {
        mCurrentPosition = position;
        // 获取头部布局的总数
        int headerCount = getHeaderViewsCount();
        // 获取原有数据数量
        int adapterCount = mDatas != null ? mDatas.size() : 0;
        // 列表数据真正所处的的位置
        int adjPosition = position - headerCount;
        //当前位置小于头布局数量，认为他是头布局
        if (isHeaderView(position)) {
            return HEADER_VIEW_TYPE;
            //当前位置减去可能存在的头布局数量小于等于原适配器数量，则认为他是真正的列表布局，返回当前数据所处列表的位置
        } else if (adjPosition < adapterCount) {
            return adjPosition;
            //数据总数为0,当前位置与头view长度相等，
        } else if ((mDatas == null || mDatas.size() == 0) && position == headerCount) {
            if (mStateType == 0) {
                return LOADING_VIEW_TYPE;
            } else if (mStateType == 1) {
                return EMPTY_VIEW_TYPE;
            } else if (mStateType == 2) {
                return ERROR_VIEW_TYPE;
            } else {
                return EMPTY_VIEW_TYPE;
            }
        } else {
            return FOOTER_VIEW_TYPE;
        }
    }

    /**
     * 索引为 position 的 item 是否是 header
     */
    public boolean isHeaderView(int position) {
        return position < getHeaderViewsCount();
    }

    /**
     * 索引为 position 的 item 是否是 footer
     */
    public boolean isFooterView(int position) {
        return position >= getHeaderViewsCount() + (mDatas != null ? mDatas.size() : 1);
    }

    /**
     * 索引为 position 的 item 是否是 header 或 footer
     */
    public boolean isHeaderViewOrFooterView(int position) {
        return isHeaderView(position) || isFooterView(position);
    }

    /**
     * 创建viewholder
     */
    @NonNull
    @Override
    public KRecycleViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        switch (viewType) {
            //头
            case HEADER_VIEW_TYPE:
                return KRecycleViewHolder.getViewHolder(mHeaderViews.get(getPosition()));
            //尾
            case FOOTER_VIEW_TYPE:
                return KRecycleViewHolder.getViewHolder(mFooterViews.get(getPosition() - getHeaderViewsCount() -
                        (mDatas != null && mDatas.size() != 0 ? mDatas.size() : 1)));
            //空
            case EMPTY_VIEW_TYPE:
                return KRecycleViewHolder.getViewHolder(getStateView(1) == null ? getDefView(1) : getStateView(1));
            //加载中
            case LOADING_VIEW_TYPE:
                return KRecycleViewHolder.getViewHolder(getStateView(0) == null ? getDefView(0) : getStateView(0));
            //错
            case ERROR_VIEW_TYPE:
                return KRecycleViewHolder.getViewHolder(getStateView(2) == null ? getDefView(2) : getStateView(2));
            //数据列表
            default:
                return KRecycleViewHolder.getViewHolder(LayoutInflater.from(mContext).inflate(getItemLayout(viewType), mRecyclerView, false));
        }
    }

    /**
     * 绑定 viewholder
     */
    @Override
    public void onBindViewHolder(@NonNull final KRecycleViewHolder holder, @SuppressLint("RecyclerView") int position) {

        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEADER_VIEW_TYPE:
            case FOOTER_VIEW_TYPE:
            case EMPTY_VIEW_TYPE:
            case LOADING_VIEW_TYPE:
            case ERROR_VIEW_TYPE:
                break;
            default:
                if (mItemClickListener != null) {
                    holder.setItemClick(mItemClickListener);
                }
                doWhat(holder, mDatas.get(viewType), viewType, viewType);
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        if (isHasScrollListener()) {
            if (mScrollingListener != null) {
                mRecyclerView.removeOnScrollListener(mScrollingListener);
            }
            mRecyclerView.addOnScrollListener(mScrollingListener = new OnScrollingListener() {
                @Override
                protected void doWhat(boolean isBottom, int firstVisibleItemPosition, int lastVisibleItemPosition, int scrollState) {
                    onScrolling(isBottom, firstVisibleItemPosition, lastVisibleItemPosition, scrollState);
                }
            });
        }
        // 判断当前的布局管理器是否为空，如果为空则设置默认的布局管理器
        if (mRecyclerView.getLayoutManager() == null) {
            RecyclerView.LayoutManager layoutManager = getDefaultLayoutManager(mContext);
            if (layoutManager != null) {
                mRecyclerView.setLayoutManager(layoutManager);
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        // 移除滚动监听
        if (mScrollingListener != null) {
            mRecyclerView.removeOnScrollListener(mScrollingListener);
        }
        mRecyclerView = null;
    }

    /**
     * 填充数据及交互
     *
     * @param holder   view持有
     * @param bean     当前数据
     * @param position 当前位置
     */
    public abstract void doWhat(KRecycleViewHolder holder, T bean, int position, int itemViewType);

    /**
     * 获取item布局
     */
    public abstract int getItemLayout(int viewType);

    /**
     * 滑动监听
     *
     * @param isBottom                 是否到底了
     * @param firstVisibleItemPosition 当前可见的第一个item的位置
     * @param lastVisibleItemPosition  当前可见的最后一个item的位置
     * @param scrollState              scrollState=SCROLL_STATE_IDLE=0：当前的recycleView不滑动(滑动已经停止时)，
     *                                 scrollState=SCROLL_STATE_DRAGGING =1：当前的recycleView被拖动滑动，
     *                                 scrollState=SCROLL_STATE_SETTLING  =2：当前的recycleView在滚动到某个位置的动画过程,但没有被触摸滚动.调用 scrollToPosition(int) 应该会触发这个状态
     */
    public void onScrolling(boolean isBottom, int firstVisibleItemPosition, int lastVisibleItemPosition, int scrollState) {
    }

    /**
     * 刷新
     */
    public void loadList(List<T> mDatas) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     */
    public void loadMore(List<T> mDatas) {
        if (mDatas != null & mDatas.size() != 0) {
            if (this.mDatas == null) {
                this.mDatas = new ArrayList<>();
            }
            this.mDatas.addAll(mDatas);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取数据长度
     */
    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + (mDatas == null || mDatas.size() == 0 ? 1 : mDatas.size()) + getFooterViewsCount();
    }

    /**
     * 获取所有数据
     */
    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 获取当前展示view的位置（没啥用）
     */
    public int getPosition() {
        return mCurrentPosition;
    }

    /**
     * item移除屏幕的时候清空动画
     */
    @Override
    public void onViewDetachedFromWindow(KRecycleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
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
     * 获取头部View总数
     */
    public int getHeaderViewsCount() {
        return mHeaderViews.size();
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
     * 获取底部View总数
     */
    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    /**
     * 获取除去 header 和 footer 后真实的 item 总数
     *
     * @return
     */
    public int getRealItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }
    /**
     * 获取真实 item 的索引
     *
     * @param position
     * @return
     */
    public int getRealItemPosition(int position) {
        return position +getHeaderViewsCount();
    }

    /**
     * 是否添加欢动监听
     */
    private boolean isHasScrollListener() {
        return false;
    }

    /**
     * 生成默认的布局摆放器
     */
    private RecyclerView.LayoutManager getDefaultLayoutManager(Context context) {
        return new LinearLayoutManager(context);
    }

    /**
     * 获取默认状态view
     */
    private View getDefView(int type) {
        int mStateLayout = 0;
        if (type == 0) {
            mStateLayout = R.layout.base_loading;
        } else if (type == 1) {
            mStateLayout = R.layout.base_empty;
        } else if (type == 2) {
            mStateLayout = R.layout.base_error;
        }
        View view = ViewUtil.getView(mContext, mStateLayout);
        //        int height = mHeaderViews.size() > 0 ? mHeaderViews.get(0).getHeight() : 0;
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    /**
     * 展示加载中
     */
    public void showLoading() {
        mStateType = 0;
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 展示空数据view
     */
    public void showEmpty() {
        mStateType = 1;
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        } else {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 展示加载错误
     */
    public void showError() {
        mStateType = 2;
        mDatas = null;
        notifyDataSetChanged();
    }

    /**
     * 获取状态view（type==0：加载中 type==1 数据空 type==3 报错）默认空，会调用默认的状态view
     */
    private View getStateView(int type) {
        return null;
    }

    /**
     * 设置点击监听
     */
    public void setOnItemClick(KOnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}