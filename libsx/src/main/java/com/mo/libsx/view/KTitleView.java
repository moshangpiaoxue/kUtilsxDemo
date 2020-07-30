package com.mo.libsx.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mo.libsx.R;
import com.mo.libsx.modle.listener.click_listener.KNoDoubleClickListener;
import com.mo.libsx.utils.dataUtil.dealUtil.DensityUtil;


/**
 * @ author：mo
 * @ data：2018/12/18
 * @ 功能：titleView
 */
public class KTitleView extends FrameLayout {
    private TitleBarClickListener listener;
    private RelativeLayout title;

    private LinearLayout left;
    private ImageView iv_title_left;

    private TextView midle;

    private LinearLayout right;
    private TextView tv_title_right;
    private ImageView iv_title_right;

    public void setListener(TitleBarClickListener listener) {
        this.listener = listener;
    }

    public KTitleView(@NonNull Context context) {
        super(context, null);
    }

    public KTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_layout_view, this, true);
        title = (RelativeLayout) findViewById(R.id.rl_title_view);

        left = (LinearLayout) findViewById(R.id.ll_title_left);
        iv_title_left = findViewById(R.id.iv_title_left);

        midle = (TextView) findViewById(R.id.tv_title_midle);

        right = (LinearLayout) findViewById(R.id.ll_title_right);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        iv_title_right = findViewById(R.id.iv_title_right);

        //        //左侧监听
        left.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.leftClick(v);
                }
            }
        });
        //中间监听
        midle.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.midleClick(v);
                }
            }
        });
        //右侧监听
        right.setOnClickListener(new KNoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (listener != null) {
                    listener.rightClick(v);
                }
            }
        });
    }

    /**
     * 设置显示状态
     */
    public void setShowStatus(boolean isShow1) {
        title.setVisibility(isShow1 ? VISIBLE : GONE);
    }


    /**
     * 拿到title整体
     */
    public RelativeLayout getTitleView() {
        return title;
    }

    public TextView getMidleTextView() {
        return midle;
    }

    public ImageView getRightImageView() {
        return iv_title_right;
    }

    public TextView getRightTextView() {
        return tv_title_right;
    }

    /**
     * 左侧隐藏
     */
    public void setLeftHind() {
        left.setVisibility(GONE);
    }

    /**
     * 设置左侧图标
     */
    public void setLeftSrc(int leftSrc) {
        iv_title_left.setImageResource(leftSrc);
    }

    /**
     * 设置中间文字 String
     */
    public void setMidleText(String midleText) {
        midle.setText(midleText);
    }

    public void setMidleTextColor(int color) {
        midle.setTextColor(color);
    }

    /**
     * 设置中间文字 String
     */
    public void setMidleText(String midleText, int color) {
        midle.setTextColor(color);
        midle.setText(midleText);
    }

    /**
     * 设置右侧文字
     */
    public void setRightText(CharSequence text) {
        tv_title_right.setText(text);
        tv_title_right.setVisibility(VISIBLE);
    }

    public void setRightText(String text, int color) {
        setRightText(text);
        tv_title_right.setTextColor(color);
        tv_title_right.setVisibility(VISIBLE);
    }

    /**
     * 设置右侧图标
     */
    public void setRightSrc(int srcId) {
        iv_title_right.setImageResource(srcId);
        iv_title_right.setVisibility(VISIBLE);
        tv_title_right.setVisibility(GONE);
    }

    public void setRightSrc(int srcId, int width, int height) {
        iv_title_right.setImageResource(srcId);
        ViewGroup.LayoutParams params = iv_title_right.getLayoutParams();
        params.width = DensityUtil.dp2px(width);
        params.height = DensityUtil.dp2px(height);
        iv_title_right.setLayoutParams(params);
        iv_title_right.setVisibility(VISIBLE);
        tv_title_right.setVisibility(GONE);
    }

    /**
     * 监听接口
     */
    private interface TitleBarClickListener {
        void leftClick(View v);

        void midleClick(View v);

        void rightClick(View v);
    }

    /**
     * 抽象监听方法
     */
    public static abstract class KTitleBarClickListenerImpl implements TitleBarClickListener {
        @Override
        public void leftClick(View v) {
            if ( ((Activity) v.getContext())!=null){
                ((Activity) v.getContext()).onBackPressed();
            }
        }

        @Override
        public void midleClick(View v) {

        }

        @Override
        public void rightClick(View v) {

        }
    }
}
