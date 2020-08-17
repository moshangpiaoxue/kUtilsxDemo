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
import com.mo.libsx.view.recycle_view.layout_manager.FlowLayoutManager;
import com.mo.libsx.view.tag_layout.FlowLayout;
import com.mo.libsx.view.tag_layout.TagAdapter;
import com.mo.libsx.view.tag_layout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：自动换行RecycleView
 */
public class RvAutoLineBreakActivity extends BaseActivity implements TitleBarAction {

    private KRecycleView krv_rv;
    List<String> list;
    private KRecyclerAdapter2<String> adapter2;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_recycle2;
    }


    @Override
    protected void initView() {
        setTitle("自动换行RecycleView");

        krv_rv = findViewById(R.id.krv_rv2);
        TagFlowLayout tfl = findViewById(R.id.tfl_rv2);
        list = new ArrayList<>();
        list.add("普通普通");
        list.add(" 普通 ");
        list.add("普通     普通");
        list.add("普通               普通");
        list.add(" 普通  普通");
        list.add("普");
        list.add(" 普普通普通通");
        list.add("  普普通普通普通普通通");
        list.add("  普普通普通通");


        //设置LayoutManager
//        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
//        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
//        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
//        //确定主轴与换行方式，这样的方式类似于一般的ListView的样式
//        krv_rv.setLayoutManager(flexboxLayoutManager);

        krv_rv.setLayoutManager(new FlowLayoutManager());
        krv_rv.setAdapter(adapter2 = new KRecyclerAdapter2<String>(getActivity(), list) {
            @Override
            protected int getItemLayout2(int viewType) {
                return R.layout.item_tv2;
            }

            @Override
            protected void toDo(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView mRecyclerView) {
                TextView tv_item_tv = holder.getView(R.id.tv_item_tv2);
                tv_item_tv.setText(bean);
            }

        });


        tfl.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout var1, int var2, String var3) {
                View view = ViewUtil.getView(getActivity(), R.layout.item_tv2, tfl);
                TextView tv_item_pop_nrear_station_screen_item = view.findViewById(R.id.tv_item_tv2);
                tv_item_pop_nrear_station_screen_item.setText(var3);
                return view;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                showToast("选中");
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                showToast("取消选中");
            }
        });
    }

    @Override
    protected void initData() {

    }

}
