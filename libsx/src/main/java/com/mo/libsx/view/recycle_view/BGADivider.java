package com.mo.libsx.view.recycle_view; /**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.mo.libsx.utils.dataUtil.dealUtil.DensityUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/6 上午4:04
 * 描述:RecyclerView 分隔线
 */
public class BGADivider extends RecyclerView.ItemDecoration {
    private int mOrientation = LinearLayout.VERTICAL;
    private Delegate mDelegate;

    public BGADivider(Delegate delegate) {
        this.mDelegate = delegate;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        int childAdapterPosition = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();

        if (mDelegate != null && mDelegate.isNeedCustom(childAdapterPosition, itemCount)) {
            mDelegate.getItemOffsets(this, childAdapterPosition, itemCount, outRect);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }


    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        handleDraw(canvas, parent, true);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        handleDraw(canvas, parent, false);
    }

    private void handleDraw(Canvas canvas, RecyclerView parent, boolean isDrawOver) {
        if (parent.getLayoutManager() == null || parent.getAdapter() == null) {
            return;
        }

        int itemCount = parent.getAdapter().getItemCount();
        // 除去 header 和 footer 后中间部分真实 item 的总数
        int realItemCount = itemCount;

        if (mOrientation == LinearLayout.VERTICAL) {
            drawVertical(canvas, parent,  itemCount, realItemCount, isDrawOver);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent,  int itemCount, int realItemCount, boolean
            isDrawOver) {
        int dividerLeft = parent.getPaddingLeft() + 0;
        int dividerRight = parent.getWidth() - parent.getPaddingRight() - 0;
        View itemView;
        RecyclerView.LayoutParams itemLayoutParams;

        for (int childPosition = 0; childPosition < itemCount; childPosition++) {
            itemView = parent.getChildAt(childPosition);
            if (itemView == null || itemView.getLayoutParams() == null) {
                continue;
            }
            int childAdapterPosition = parent.getChildAdapterPosition(itemView);
            itemLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            int dividerBottom = itemView.getTop() - itemLayoutParams.topMargin;

            if (mDelegate != null && mDelegate.isNeedCustom(childAdapterPosition, realItemCount)) {
                if (isDrawOver) {
                    mDelegate.drawOverVertical(this, canvas, dividerLeft, dividerRight, dividerBottom, childAdapterPosition, realItemCount);
                } else {
                    mDelegate.drawVertical(this, canvas, dividerLeft, dividerRight, dividerBottom, childAdapterPosition, realItemCount);
                }
            } else if (!isDrawOver) {
            }
        }
    }


    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
    }

    public interface Delegate {
        boolean isNeedSkip(int position, int itemCount);

        boolean isNeedCustom(int position, int itemCount);

        void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect);

        void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount);

        void drawOverVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount);
    }

    public static class SimpleDelegate implements Delegate {
        protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public SimpleDelegate() {
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
            initAttr();
        }

        protected void initAttr() {
        }

        @Override
        public boolean isNeedSkip(int position, int itemCount) {
            return false;
        }

        @Override
        public boolean isNeedCustom(int position, int itemCount) {
            return false;
        }

        @Override
        public void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect) {
        }

        @Override
        public void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
        }

        @Override
        public void drawOverVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
        }
    }

    /**
     * 继承该类实现自定义悬浮分类样式
     */
    public static abstract class StickyDelegate extends SimpleDelegate {
        protected int mCategoryBackgroundColor;
        protected int mCategoryTextColor;
        protected int mCategoryPaddingLeft;
        protected int mCategoryTextSize;
        protected int mCategoryHeight;
        protected float mCategoryTextOffset;

        @Override
        protected void initAttr() {
            mCategoryBackgroundColor = Color.parseColor("#F2F2F2");
            mCategoryTextColor = Color.parseColor("#848484");
            mCategoryPaddingLeft = DensityUtil.dp2px(16);
            mCategoryTextSize = DensityUtil.sp2px(14);
            mCategoryHeight = DensityUtil.dp2px(32);

            initCategoryAttr();

            mPaint.setStyle(Paint.Style.FILL);

            calculateCategoryTextOffset();
        }

        /**
         * 计算文字底部偏移量
         */
        public void calculateCategoryTextOffset() {
            mPaint.setTextSize(mCategoryTextSize);

            Rect rect = new Rect();
            mPaint.getTextBounds("TT", 0, 2, rect);
            int textHeight = rect.height();
            mCategoryTextOffset = (mCategoryHeight - textHeight) / 2.0f;
        }

        /**
         * 需要自定义分类属性时重写该方法
         */
        protected void initCategoryAttr() {
        }

        @Override
        public boolean isNeedCustom(int position, int itemCount) {
            // 每一项都自定义
            return true;
        }

        @Override
        public void getItemOffsets(BGADivider divider, int position, int itemCount, Rect outRect) {
            if (isCategoryFistItem(position)) {
                // 如果是分类则设置高度为分类高度
                outRect.set(0, mCategoryHeight, 0, 0);
            } else {
                // 如果不是分类则调用 BGADivider 的 getVerticalItemOffsets 来设置 item 高度
                outRect.set(0, 0, 0, 0);
            }
        }

        @Override
        public void drawVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
            if (isCategoryFistItem(position)) {
                if (position == getFirstVisibleItemPosition() && itemCount > 1) {
                    // 避免悬浮分类透明时重影
                    return;
                }
                // 是分类下的第一个条目，绘制分类
                drawCategory(divider, canvas, dividerLeft, dividerRight, dividerBottom, getCategoryName(position));
            } else {
                // 不是分类下的第一个条目，绘制分割线
                //                divider.drawVerticalDivider(canvas, dividerLeft, dividerRight, dividerBottom);
            }
        }

        @Override
        public void drawOverVertical(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, int position, int itemCount) {
            if (position == getFirstVisibleItemPosition() + 1) {
                // 绘制悬浮分类
                int suspensionBottom = mCategoryHeight;
                int offset = mCategoryHeight * 2 - dividerBottom;
                if (offset > 0 && isCategoryFistItem(position)) {
                    suspensionBottom -= offset;
                }
                drawOverCategory(divider, canvas, dividerLeft, dividerRight, suspensionBottom, getCategoryName(getFirstVisibleItemPosition()));
            }
        }

        /**
         * 指定索引位置是否是其对应分类下的第一个条目
         *
         * @param position
         * @return
         */
        protected abstract boolean isCategoryFistItem(int position);

        /**
         * 获取指定索引位置的分类名称
         *
         * @param position
         * @return
         */
        protected abstract String getCategoryName(int position);

        /**
         * 获取第一个可见条目的索引位置
         *
         * @return
         */
        protected abstract int getFirstVisibleItemPosition();

        /**
         * 绘制悬浮分类
         */
        protected void drawOverCategory(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, String category) {
            drawCategory(divider, canvas, dividerLeft, dividerRight, dividerBottom, category);
        }

        /**
         * 绘制普通分类
         */
        protected void drawCategory(BGADivider divider, Canvas canvas, int dividerLeft, int dividerRight, int dividerBottom, String category) {
            // 绘制背景
            mPaint.setColor(mCategoryBackgroundColor);
            canvas.drawRect(dividerLeft - 0, dividerBottom - mCategoryHeight, dividerRight + 0, dividerBottom,
                    mPaint);

            // 绘制文字
            mPaint.setColor(mCategoryTextColor);
            canvas.drawText(category, 0, category.length(), mCategoryPaddingLeft, dividerBottom - mCategoryTextOffset, mPaint);
        }

        /**
         * 获取分类高度
         *
         * @return
         */
        public int getCategoryHeight() {
            return mCategoryHeight;
        }
    }
}