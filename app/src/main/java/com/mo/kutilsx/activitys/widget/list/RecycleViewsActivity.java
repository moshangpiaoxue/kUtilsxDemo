package com.mo.kutilsx.activitys.widget.list;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.adapter.KRecyclerAdapter2;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.viewUtil.ViewUtil;
import com.mo.libsx.view.recycle_view.KRecycleView;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：RecycleView
 */
public class RecycleViewsActivity extends BaseActivity implements TitleBarAction {

    private KRecycleView krv_rv;
    List<String> list;
private KRecyclerAdapter2<String> adapter2;
    @Override
    protected int getLayoutId() {
        return R.layout.act_view_recycle;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rv_kong:
                list = new ArrayList<String>();
                adapter2.notifyDataSetChanged1();
                break;
            case R.id.tv_rv_add:
                list.add("追加");
                adapter2.notifyDataSetChanged1();
                break;
            case R.id.tv_pull_zhong:
                break;
            case R.id.tv_pull_hao:
                break;
        }
    }

    @Override
    protected void initView() {
        setTitle("RecycleView(数据变化有问题，一般使用无碍)");

        krv_rv = findViewById(R.id.krv_rv);

        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("普通加载数据：aaaaa" + i);
        }
        krv_rv.setAdapter(adapter2=new KRecyclerAdapter2<String>(getActivity(), list) {
            @Override
            protected int getItemLayout2(int viewType) {
                return R.layout.item_tv;
            }

            @Override
            protected void toDo(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView mRecyclerView) {
                TextView tv_item_tv = holder.getView(R.id.tv_item_tv);
                tv_item_tv.setText(bean);
            }

            @Override
            public View getEmptyView() {
                return ViewUtil.getView(getActivity(), R.layout.test_empty);
            }
        });
    }

    @Override
    protected void initData() {

    }

}
