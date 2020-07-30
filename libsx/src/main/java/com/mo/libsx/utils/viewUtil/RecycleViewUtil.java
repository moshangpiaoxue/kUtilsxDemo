package com.mo.libsx.utils.viewUtil;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mo.libsx.utils.tips_utils.LogUtil;


/**
 * @ author：mo
 * @ data：2019/4/19:15:26
 * @ 功能：KRecycleView工具类
 */
public class RecycleViewUtil {
    /**
     * 获取整体滑动的距离
     */
    public static int getScollYDistance(RecyclerView recyclerView) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
            View firstVisiableChildView = ((LinearLayoutManager) manager).findViewByPosition(position);
            int itemHeight = firstVisiableChildView.getHeight();
            return (position) * itemHeight - firstVisiableChildView.getTop();
        } else {
            LogUtil.i("只能计算LinearLayoutManager下的滑动距离");
            return 0;
        }

    }

}
