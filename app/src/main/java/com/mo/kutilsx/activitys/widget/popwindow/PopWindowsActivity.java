package com.mo.kutilsx.activitys.widget.popwindow;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mo.kutilsx.R;
import com.mo.kutilsx.action.ListAction;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.adapter.BaseRecycleViewAdapter;
import com.mo.libsx.base.adapter.KRecyclerAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.listener.click_listener.KOnItemClickListenerImpl;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.view_utils.PromptUtil;
import com.mo.libsx.view.popupWindow.BasePopModle;
import com.mo.libsx.view.popupWindow.BasePopWindow;
import com.mo.libsx.view.popupWindow.LayoutGravity;
import com.mo.libsx.view.recycle_view.KRecycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author：mo
 * @ data：2020/7/25:16:57
 * @ 功能：PopWindow相关
 */
public class PopWindowsActivity extends BaseActivity implements TitleBarAction, ListAction<String> {
    private ImageView iv_pop_tag;

    @Override
    protected boolean isStatusBarEnabled() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_popwindow;
    }

    @Override
    protected void initView() {
        setTitle("PopWindow相关");
        getRecycleView().setLayoutGrid(4);
        iv_pop_tag = findViewById(R.id.iv_pop_tag);
        setAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add(" 悬浮图片上");
        list.add(" 悬浮图片下");
        list.add(" 测试");
        return list;
    }

    @Override
    public KRecycleView getRecycleView() {
        return findViewById(R.id.krv_pop);
    }

    @Override
    public KRecyclerAdapter getWrapRecyclerAdapter() {
        return new KRecyclerAdapter(new BaseRecycleViewAdapter<String>(getActivity(), getList()) {
            @Override
            public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView mRecyclerView) {
                TextView tv_item_tv = holder.getView(R.id.tv_item_tv);
                tv_item_tv.setText(bean);
                holder.setItemClick(new KOnItemClickListenerImpl() {
                    @Override
                    public void onItemClick(View view, int position) {
                        super.onItemClick(view, position);
                        if (position==0){
                            PromptUtil.show(getActivity(),R.mipmap.aa,iv_pop_tag, PromptUtil.ENUM.TOP);
                        }else  if (position==1){
                            PromptUtil.show(getActivity(),R.mipmap.aa,iv_pop_tag, PromptUtil.ENUM.BOTTOM);
                        }else  if (position==2){
                            BasePopWindow.getInstance(getActivity()).setContent(R.layout.item_main3)
                                    .setOutsideTouchDismiss(true)
                                    .show(getContentView(), BasePopWindow.LOCATION_METHOD.SHOW_AT_PARENT,new LayoutGravity(Gravity.NO_GRAVITY),0,0);
                        }
                    }
                });
            }

            @Override
            public int getItemLayout(int viewType) {
                return R.layout.item_tv;
            }
        });
    }
}
