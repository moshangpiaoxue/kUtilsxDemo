package com.mo.kutilsx.modle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.mo.kutilsx.R;
import com.mo.kutilsx.bean.MainBean;
import com.mo.libsx.base.adapter.BaseRecycleViewAdapter;
import com.mo.libsx.base.adapter.KRecyclerAdapter;
import com.mo.libsx.modle.listener.click_listener.KOnItemClickListenerImpl;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.beng_utils.BengUtil;

import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:19
 * @ 功能：
 */
public class AdapterModle {
    public static KRecyclerAdapter getMainAdapter(final Activity mActivity, List<MainBean> list) {
         return new KRecyclerAdapter(new BaseRecycleViewAdapter<MainBean>(mActivity, list) {
            @Override
            public void doWhat(KRecycleViewHolder holder, MainBean bean, int position, int itemViewType, RecyclerView mRecyclerView) {
                ImageView iv_item_main = holder.getView(R.id.iv_item_main);
                TextView tv_item_main = holder.getView(R.id.tv_item_main);
                tv_item_main.setText(bean.getTitle());
                iv_item_main.setImageResource(bean.getDwrableId());
                holder.setItemClick(new KOnItemClickListenerImpl() {
                    @Override
                    public void onItemClick(View view, int postion) {
                        BengUtil.getBuilder(mActivity, bean.getActivity(), false).action();
                    }
                });
            }

            @Override
            public int getItemLayout(int viewType) {
                return R.layout.item_main;
            }
        }) {
            @Override
            public View getEmptyView() {
                View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_main3, null, false);
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                return inflate;
            }
        };

    }

}
