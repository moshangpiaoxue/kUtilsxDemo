package com.mo.kutilsx.activitys.widget.list;

import android.Manifest;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.mo.kutilsx.R;
import com.mo.libsx.view.recycle_view.BGADivider;
import com.mo.libsx.view.recycle_view.BGARVVerticalScrollHelper;
import com.mo.libsx.modle.bean.CityBean;
import com.mo.kutilsx.modle.ListModle;
import com.mo.libsx.base.adapter.HoverRecycleViewAdapter;
import com.mo.libsx.base.adapter.KBaseRecycleViewAdapter;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.view_holder.KRecycleViewHolder;
import com.mo.libsx.utils.animator_util.tween.KRotateAnimationUtils;
import com.mo.libsx.utils.dataUtil.ListUtil;
import com.mo.libsx.utils.systemUtils.LocationUtils;
import com.mo.libsx.utils.viewUtil.click.OnMultiClickListener;
import com.mo.libsx.view.indext_view.IndexView;
import com.mo.libsx.view.recycle_view.KRecycleView;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.List;


/**
 * @ author：mo
 * @ data：2020/8/12:15:31
 * @ 功能：
 */
public class CityPickerActivity2 extends BaseActivity {
    private KRecycleView mDataRv;
    private IndexView mIndexView;
    private TextView mTipTv;


    private HoverRecycleViewAdapter<CityBean> adapter;
    private BGARVVerticalScrollHelper mRecyclerViewScrollHelper;


    @Override
    protected int getLayoutId() {
        return R.layout.act_list_city_picker2;
    }

    @Override
    protected void initView() {
        mDataRv = findViewById(R.id.rv_sticky_data);
        mDataRv.setHasFixedSize(true);
        mDataRv.setNestedScrollingEnabled(false);
        mIndexView = findViewById(R.id.iv_sticky_index);
        mTipTv = findViewById(R.id.tv_sticky_tip);
        mIndexView.setTipsView(mTipTv);
        PermissionX.init((FragmentActivity) getActivity()).permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            mDataRv.setAdapter(adapter = new HoverRecycleViewAdapter<CityBean>(getActivity(), ListModle.loadIndexModelData()) {
                                @Override
                                public void doWhat(KRecycleViewHolder holder, CityBean bean, int position, int itemViewType) {
                                    if (bean.getName().contains("定位")) {
                                        TextView tv_located_city = holder.getView(R.id.tv_located_city);
                                        ImageView iv_chognxindingwei = holder.getView(R.id.iv_chognxindingwei);
                                        holder.getView(R.id.tv_chognxindingwei).setOnClickListener(new OnMultiClickListener() {
                                            @Override
                                            public void onMultiClick(View v) {
                                                setLoctionView(tv_located_city, iv_chognxindingwei);
                                            }
                                        });
                                        setLoctionView(tv_located_city, iv_chognxindingwei);


                                    } else if (bean.getName().contains("热门")) {
                                        KRecycleView gridView = holder.getView(com.mo.libsx.R.id.gridview_hot_city);
                                        gridView.setLayoutGrid(4);
                                        gridView.setAdapter(new KBaseRecycleViewAdapter<CityBean>(mContext, ListModle.getHotCity()) {
                                            @Override
                                            public void doWhat(KRecycleViewHolder holder, CityBean bean, int position, int itemViewType) {
                                                TextView tv_hot_city_name = holder.getView(com.mo.libsx.R.id.tv_hot_city_name);
                                                tv_hot_city_name.setText(bean.getName());
                                            }

                                            @Override
                                            public int getItemLayout(int viewType) {
                                                return com.mo.libsx.R.layout.cp_item_hot_city_gridview;
                                            }
                                        });
                                    } else {
                                        TextView tv_item_index_city = holder.getView(R.id.tv_item_index_city);
                                        tv_item_index_city.setText(bean.getName());
                                    }
                                }

                                @Override
                                public int getItemLayout(int viewType) {
                                    if (getDatas().get(viewType).getName().contains("定位")) {
                                        return R.layout.item_city_loction;
                                    } else if (getDatas().get(viewType).getName().contains("热门")) {
                                        return R.layout.item_city_hot;
                                    } else {
                                        return com.mo.libsx.R.layout.cp_item_city_listview2;
                                    }
                                }

                                @Override
                                protected boolean isCategoryFistItem(int position, CityBean noeBean, CityBean beforeBean) {
                                    String currentTopc = getDatas().get(position).getTopc();
                                    String preTopc = getDatas().get(position - 1).getTopc();
                                    // 当前条目的分类和上一个条目的分类不相等时，当前条目为该分类下的第一个条目
                                    if (!TextUtils.equals(currentTopc, preTopc)) {
                                        return true;
                                    }
                                    return false;
                                }

                                @Override
                                protected int getPositionForCategory(int category, int position, CityBean bean) {
                                    String sortStr = bean.getTopc();
                                    char firstChar = sortStr.toUpperCase().charAt(0);
                                    if (firstChar == category) {
                                        return position;
                                    }
                                    return -1;
                                }
                            });
                            initStickyDivider();
                        }
                    }
                });
    }

    private void initStickyDivider() {
        final BGADivider.StickyDelegate stickyDelegate = new BGADivider.StickyDelegate() {

            @Override
            protected boolean isCategoryFistItem(int position) {
                return adapter.isCategoryFistItem(position);
            }

            @Override
            protected String getCategoryName(int position) {
                return adapter.getDatas().get(position).getTopc();
            }

            @Override
            protected int getFirstVisibleItemPosition() {
                return mRecyclerViewScrollHelper.findFirstVisibleItemPosition();
            }
        };

        mDataRv.addItemDecoration(new BGADivider(stickyDelegate));
        mRecyclerViewScrollHelper = BGARVVerticalScrollHelper.newInstance(mDataRv, new BGARVVerticalScrollHelper.SimpleDelegate() {
            @Override
            public int getCategoryHeight() {
                return stickyDelegate.getCategoryHeight();
            }
        });
        mIndexView.setSelectedListener(new IndexView.IndexViewSelectedListener() {
            @Override
            public void onIndexViewSelected(IndexView indexView, String text) {
                int position = adapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    mRecyclerViewScrollHelper.smoothScrollToPosition(position);
                }
            }
        });
        List<String> stringList = ListUtil.removeRepeat(ListModle.getToc());
        mIndexView.setData(stringList.toArray(new String[stringList.size()]));
        //        adapter.addHeaderView(ViewUtil.getView(getActivity(),R.layout.item_main));
        //        adapter.addFooterView(ViewUtil.getView(getActivity(),R.layout.item_main));
    }

    @Override
    protected void initData() {
    }


    private void setLoctionView(TextView tv_located_city, ImageView iv_chognxindingwei) {
        tv_located_city.setText("定位中..");
        iv_chognxindingwei.startAnimation(KRotateAnimationUtils.getRotateAnimation());
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_located_city.setText(LocationUtils.getCity());
                iv_chognxindingwei.clearAnimation();
            }
        }, 1500);

    }

}
