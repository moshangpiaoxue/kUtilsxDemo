package com.mo.libsx.base.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mo.libsx.modle.listener.click_listener.KOnItemClickListener;
import com.mo.libsx.modle.listener.scrolling_listener.OnScrollingListener;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView适配器基类
 * author   ：mo
 * data     ：2016/12/6
 * time     ：18:11
 * function :RecycleView适配器
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<KRecycleViewHolder> {
    /** 上下文 */
    protected Context mContext;
    /** 数据集合 */
    private List<T> mDatas;
    /** 上一个设置了动画的位置 */
    private int lastPosition = -1;
    /** item出现动画 */
    private Animation itemAnimation = null;
    /** 是否延时加载动画 */
    private boolean delayEnterAnimation = true;
    /** 子项点击监听 */
    private KOnItemClickListener mItemClickListener;
    /** 父布局 */
    private RecyclerView mRecyclerView;

    /**
     * 构造方法
     *
     * @param context
     * @param datas
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public BaseRecycleViewAdapter(Context context, List<T> datas) {
        mContext = context;
        if (mContext == null) {
            throw new IllegalArgumentException("你想干啥！");
        }
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas = new ArrayList<>();
        }

    }

    private RecyclerView.OnScrollListener mScrollingListener;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        // 添加滚动监听
        mRecyclerView.addOnScrollListener(mScrollingListener = new OnScrollingListener() {
            @Override
            protected void doWhat(boolean isBottom, int firstVisibleItemPosition, int lastVisibleItemPosition, int scrollState) {
                onScrolling(isBottom, firstVisibleItemPosition, lastVisibleItemPosition, scrollState);
            }
        });
        // 判断当前的布局管理器是否为空，如果为空则设置默认的布局管理器
        if (mRecyclerView.getLayoutManager() == null) {
            RecyclerView.LayoutManager layoutManager = getDefaultLayoutManager(mContext);
            if (layoutManager != null) {
                mRecyclerView.setLayoutManager(layoutManager);
            }
        }
    }
    /**
     * 设置 RecyclerView 条目滚动监听
     */
    public void setOnScrollingListener(RecyclerView.OnScrollListener listener) {
        mScrollingListener = listener;

        //如果当前已经有设置滚动监听，再次设置需要移除原有的监听器
            mRecyclerView.removeOnScrollListener(mScrollingListener);
        //用户设置了滚动监听，需要给RecyclerView设置监听
        if (mRecyclerView != null) {
            //添加滚动监听
            mRecyclerView.addOnScrollListener(mScrollingListener);
        }
    }
    /**
     * 生成默认的布局摆放器
     */
    protected RecyclerView.LayoutManager getDefaultLayoutManager(Context context) {
        return new LinearLayoutManager(context);
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
     * 是否开启加载动画，默认false不加载
     */
    private boolean isShow() {
        return false;
    }

    /**
     * 创建viewholder
     */
    @NotNull
    @Override
    public KRecycleViewHolder onCreateViewHolder(@NotNull final ViewGroup parent, int viewType) {
        //        if (this.parent == null) {
        //            this.parent = (RecyclerView) parent;
        //        }
        if (mContext != null) {
            return KRecycleViewHolder.getViewHolder(LayoutInflater.from(mContext).inflate(getItemLayout(viewType), mRecyclerView, false));
        }
        return null;
    }


    /**
     * 绑定 viewholder
     */
    @Override
    public void onBindViewHolder(@NotNull final KRecycleViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //是否展示加载动画
        if (isShow()) {
            setAnimation(holder.itemView, position);
        }
        //设置点击监听
        holder.setItemClick(new KOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, position);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemLongClick(view, position);
                }
            }
        });
        //都给你了，爱干啥干啥
        if (position < mDatas.size()) {
            doWhat(holder, mDatas.get(position), position, getItemViewType(position), mRecyclerView);
        }

    }


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
    private void onScrolling(boolean isBottom, int firstVisibleItemPosition, int lastVisibleItemPosition, int scrollState) {

    }


    /**
     * 设置动画
     */
    private void setAnimation(View itemView, int position) {
        if (position > lastPosition) {
            if (itemAnimation == null) {
                itemView.setTranslationY(1000);//相对于原始位置下方400
                itemView.setAlpha(0.f);//完全透明
                //                每个item项两个动画，从透明到不透明，从下方移动到原来的位置
                //                并且根据item的位置设置延迟的时间，达到一个接着一个的效果
                itemView.animate()
                        .translationY(0).alpha(1.f)
                        .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)//根据item的位置设置延迟时间，达到依次动画一个接一个进行的效果
                        .setInterpolator(new DecelerateInterpolator(0.5f))//设置动画效果为在动画开始的地方快然后慢
                        .setDuration(700)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                            }
                        })
                        .start();
            } else {
                itemView.startAnimation(itemAnimation);
            }

            lastPosition = position;
        }
    }

    /**
     * 填充数据及交互
     *
     * @param holder        view持有
     * @param bean          当前数据
     * @param position      当前位置
     * @param mRecyclerView 父view 也就是rv
     */
    public abstract void doWhat(KRecycleViewHolder holder, T bean, int position, int itemViewType, RecyclerView mRecyclerView);

    /**
     * 获取子项的type  默认是位置
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 获取数据长度
     */
    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    /**
     * item移除屏幕的时候清空动画
     */
    @Override
    public void onViewDetachedFromWindow(@NotNull KRecycleViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    /**
     * 获取item布局
     */
    public abstract int getItemLayout(int viewType);

    /**
     * 设置点击监听
     */
    public void setOnItemClick(KOnItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    /**
     * 设置动画
     */
    public void setAnimation(Animation itemAnimation) {
        this.itemAnimation = itemAnimation;
        notifyDataSetChanged();
    }


    /**
     * 刷新
     *
     * @param mDatas
     */
    public void refreshView(List<T> mDatas) {
        this.mDatas.clear();
        if (mDatas != null) {
            this.mDatas.addAll(mDatas);
        }
        lastPosition = -1;
        notifyDataSetChanged();

    }

    /**
     * 加载更多
     *
     * @param mDatas
     */
    public void loadMore(List<T> mDatas) {
        if (mDatas != null & mDatas.size() != 0) {
            this.mDatas.addAll(mDatas);
        }
        notifyDataSetChanged();

    }


    /**
     * 获取所有数据
     *
     * @return
     */
    public List<T> getDatas() {
        return mDatas;
    }

}