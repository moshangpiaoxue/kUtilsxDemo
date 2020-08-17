package com.mo.libsx.view.indext_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.mo.libsx.utils.dataUtil.dealUtil.DensityUtil;

/**
 * @ author：mo
 * @ data：2018/8/13:16:54
 * @ 功能：索引view
 */
public class IndexView extends View {
    /** 索引备选数据1 */
    public static final String[] mData1 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
            , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    /** 索引备选数据2 */
    public static final String[] mData2 = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
            , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    /** 真正的索引数据，默认不带定位热门 */
    public String[] mData = mData1;
    /** 默认不选中 */
    private int mSelected = -1;
    /** 画笔 */
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /** 选中监听 */
    private IndexViewSelectedListener mSelectedListener;
    /** 关联的提示tv */
    private TextView mTipsView;
    /** 正常显示的颜色 */
    private int mNormalTextColor;
    /** 按中时显示的颜色 */
    private int mPressedTextColor;
    /** 提示tv的颜色 */
    private int mTipsTextColor;

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mNormalTextColor = Color.parseColor("#3A8EFF");
        mPressedTextColor = Color.parseColor("#666666");
        mTipsTextColor = Color.parseColor("#f46444");

        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(DensityUtil.sp2px(14));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int singleHeight = getHeight() / mData.length;

        for (int i = 0; i < mData.length; i++) {
            mPaint.setColor(mNormalTextColor);
            mPaint.setTypeface(Typeface.DEFAULT);
            // 选中的状态
            if (i == mSelected) {
                mPaint.setColor(mPressedTextColor);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width / 2 - mPaint.measureText(mData[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(mData[i], xPos, yPos, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int newSelected = (int) (event.getY() / getHeight() * mData.length);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mSelected = -1;
                invalidate();
                if (mTipsView != null) {
                    mTipsView.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                if (mSelected != newSelected) {
                    if (newSelected >= 0 && newSelected < mData.length) {
                        if (mSelectedListener != null) {
                            mSelectedListener.onIndexViewSelected(this, mData[newSelected]);
                        }
                        if (mTipsView != null) {
                            mTipsView.setText(mData[newSelected]);
                            mTipsView.setVisibility(View.VISIBLE);
                        }
                        mSelected = newSelected;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 设置选中监听
     */
    public void setSelectedListener(IndexViewSelectedListener selectedListener) {
        mSelectedListener = selectedListener;
    }

    /**
     * 设置关联TV
     */
    public void setTipsView(TextView tipTv) {
        mTipsView = tipTv;
        mTipsView.setTextColor(mTipsTextColor);
    }

    /**
     * 设置索引数据可自定义但必须是排好序的
     */
    public void setData(String[] mData) {
        this.mData = mData;
        invalidate();
    }

    public interface IndexViewSelectedListener {
        void onIndexViewSelected(IndexView indexView, String text);
    }

}