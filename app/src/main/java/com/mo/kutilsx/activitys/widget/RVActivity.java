package com.mo.kutilsx.activitys.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.adapter.BaseRecycleViewAdapter;
import com.mo.libsx.base.adapter.KRecyclerAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.tips_utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class RVActivity extends BaseActivity implements TitleBarAction {

    @Override
    protected boolean isStatusBarEnabled() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView() {
        RecyclerView krv_main = findViewById(R.id.krv_main);
        LinearLayout ll_main = findViewById(R.id.ll_main);
        setTitle("主页");
        final List<String> list = new ArrayList<>();
        list.add("控件widget");
        KRecyclerAdapter adapter;
        krv_main.setLayoutManager(new LinearLayoutManager(this));
        krv_main.setAdapter(adapter = new KRecyclerAdapter(new BaseRecycleViewAdapter<String>(getActivity(), list) {
            @Override
            public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView mRecyclerView) {

            }


            @Override
            public int getItemLayout(int viewType) {
                return R.layout.item_main;
            }
        }) {
            @Override
            public View getEmptyView() {
                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_main3, null, false);
                inflate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < 5; i++) {
                            list.add("aaaa" + i);
                        }
                        LogUtil.i("SSSSSSSSSSSSSS");
                        notifyDataSetChanged();
                    }
                });
                return inflate;
            }
        });
        //        for (int i = 0; i < 10; i++) {
        //            list.add("aaaa" + i);
        //        }
        //        adapter.notifyDataSetChanged();
        adapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.item_main1, null, false));
        adapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.item_main1, null, false));
        adapter.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.item_main2, null, false));
        adapter.addFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.item_main2, null, false));

        //        {
        //
        //
        //            //            @Override
        ////            public int getItemViewType(int position) {
        ////                return position == 0 ? -1 : -2;
        ////
        ////            }
        ////
        ////            @Override
        ////            public int getItemLayout(int viewType) {
        ////                return viewType == -1 ? R.layout.item_main : R.layout.item_main2;
        ////            }
        //        });

    }

    @Override
    protected void initData() {

    }


    //    @Override
    //    protected void onCreate(Bundle savedInstanceState) {
    //        super.onCreate(savedInstanceState);
    //        setContentView(R.layout.activity_main);
    //        KRecyclerView krv_main = findViewById(R.id.krv_main);
    //        final List<String> list = new ArrayList<>();
    //        for (int i = 0; i < 10; i++) {
    //            list.add("aaaa" + i);
    //        }
    //        krv_main.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    //        krv_main.setAdapter(new KBaseAdapter<String>(MainActivity.this,list) {
    //
    //            @Override
    //            protected int getLayoutId(int viewType) {
    //                return viewType == -2 ? R.layout.item_main : R.layout.item_main2;
    //            }
    //
    //            @Override
    //            protected void doWhat(ViewHolder vhBaseAdapter, int position, int itemViewType) {
    //
    //            }
    //
    //            @Override
    //            public int getItemViewType(int position) {
    //                return position == 0 ? -1 : -2;
    //
    //            }
    //        });
    //    }
}
