package com.mo.kutilsx.activitys.widget.viewpage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.kutilsx.modle.ListModle;
import com.mo.libsx.base.adapter.BaseRecycleViewAdapter;
import com.mo.libsx.base.adapter.KPageAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.listener.click_listener.KOnItemClickListenerImpl;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.view.recycle_view.KRecycleView;
import com.mo.libsx.view.viewpage.KViewPage;
import com.mo.libsx.view.viewpage.page_transforme.AlphaPageTransformer;
import com.mo.libsx.view.viewpage.page_transforme.RotateDownPageTransformer;
import com.mo.libsx.view.viewpage.page_transforme.RotateUpPageTransformer;
import com.mo.libsx.view.viewpage.page_transforme.RotateYTransformer;
import com.mo.libsx.view.viewpage.page_transforme.ScaleInTransformer;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：KViewPage
 */
public class KViewPageActivity extends BaseActivity implements TitleBarAction {

    private KViewPage vp_vp;
    private KRecycleView krv_vp;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_kvp;
    }


    @Override
    protected void initView() {
        setTitle("KViewPage");

        vp_vp = findViewById(R.id.vp_vp);
        krv_vp = findViewById(R.id.krv_vp);
        vp_vp.setAdapter(new KPageAdapter<String>(getContext(), ListModle.getList(5), R.layout.item_main3) {
            @Override
            protected void doWhat(ViewGroup container, View view, int position) {

            }
        });

        krv_vp.setAdapter(new BaseRecycleViewAdapter<String>(getActivity(), ListModle.getList(6)) {
            @Override
            public void doWhat(KRecycleViewHolder holder, String bean, int position, int itemViewType, RecyclerView mRecyclerView) {
                TextView tv_item_tv = holder.getView(R.id.tv_item_tv);
                if (position == 0) {
                    tv_item_tv.setText("切换滑动开关,当前状态=" + vp_vp.isCanScroll());
                } else if (position == 1) {
                    tv_item_tv.setText("切换效果=" + "中间颜色重两边颜色浅");
                } else if (position == 2) {
                    tv_item_tv.setText("切换效果=" + "中间直立两边以底部为基点倾斜");
                }else if (position == 3) {
                    tv_item_tv.setText("切换效果=" + "中间直立两边以顶部为基点倾斜");
                }else if (position == 4) {
                    tv_item_tv.setText("切换效果=" + "中间直立两边以Y轴为基点旋转");
                }else if (position == 5) {
                    tv_item_tv.setText("切换效果=" + "中间大两边小");
                }

                holder.setItemClick(new KOnItemClickListenerImpl() {
                    @Override
                    public void onItemClick(View view, int position) {
                        super.onItemClick(view, position);
                        if (position == 0) {
                            vp_vp.setScanScroll(!vp_vp.isCanScroll());
                            tv_item_tv.setText("切换滑动开关,当前状态=" + vp_vp.isCanScroll());
                        } else if (position == 1) {
                            vp_vp.setPageTransformer(true, new AlphaPageTransformer());
                        }else if (position == 2) {
                            vp_vp.setPageTransformer(true, new RotateDownPageTransformer());
                        }else if (position == 3) {
                            vp_vp.setPageTransformer(true, new RotateUpPageTransformer());
                        }else if (position == 4) {
                            vp_vp.setPageTransformer(true, new RotateYTransformer());
                        }else if (position == 5) {
                            vp_vp.setPageTransformer(true, new ScaleInTransformer());
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

    @Override
    protected void initData() {

    }

}
