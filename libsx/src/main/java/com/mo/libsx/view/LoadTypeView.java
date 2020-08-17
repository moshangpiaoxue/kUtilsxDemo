package com.mo.libsx.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mo.libsx.R;

/**
 * @ author：mo
 * @ data：2020/7/31:10:17
 * @ 功能：
 */
public class LoadTypeView extends FrameLayout {

    /** 加载中 */
    private LinearLayout loading_layout;
    private TextView loading_tv;

    /** 空 */
    private LinearLayout empty_layout;
    private ImageView empty_iv;
    private TextView empty_tv;

    /** 空 */
    private LinearLayout error_layout;
    private ImageView error_iv;
    private TextView error_tv;


    public LoadTypeView(Context context) {
        super(context);
        initView(context);
    }

    public LoadTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.load_type_layout, this, true);

        loading_layout = findViewById(R.id.ll_base_loading);
        loading_tv = findViewById(R.id.tv_base_loading);

        empty_layout = findViewById(R.id.ll_base_empty);
        empty_iv = findViewById(R.id.iv_base_empty);
        empty_tv = findViewById(R.id.tv_base_empty);

        error_layout = findViewById(R.id.ll_base_error);
        error_iv = findViewById(R.id.iv_base_error);
        error_tv = findViewById(R.id.tv_base_error);
    }

    public void showLoading() {
        loading_layout.setVisibility(VISIBLE);
        empty_layout.setVisibility(GONE);
        error_layout.setVisibility(GONE);
    }

    public void setLoadingText(String loadingText) {
        loading_tv.setText(loadingText);
    }

    public void setLoadingView(View loadingView) {
        loading_layout.removeAllViews();
        loading_layout.addView(loadingView);
    }

    public void showEmpty() {
        loading_layout.setVisibility(GONE);
        empty_layout.setVisibility(VISIBLE);
        error_layout.setVisibility(GONE);
    }

    public void setEmptyText(String emptyText, Bitmap bitmap) {
        empty_tv.setText(emptyText);
        if (bitmap != null) {
            empty_iv.setImageBitmap(bitmap);
        }
    }

    public void setEmptyView(View emptyView) {
        empty_layout.removeAllViews();
        empty_layout.addView(emptyView);
    }

    public void showError() {
        loading_layout.setVisibility(GONE);
        empty_layout.setVisibility(GONE);
        error_layout.setVisibility(VISIBLE);
    }

    public void loadSuccess() {
        loading_layout.setVisibility(GONE);
        empty_layout.setVisibility(GONE);
        error_layout.setVisibility(GONE);
    }
}
