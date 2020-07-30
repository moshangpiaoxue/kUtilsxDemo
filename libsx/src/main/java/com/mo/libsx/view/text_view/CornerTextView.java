package com.mo.libsx.view.text_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.mo.libsx.R;


/**
 * @ author：mo
 * @ data：2018/1/19 0019
 * @ 功能：圆角TextView
 */

public class CornerTextView extends AppCompatTextView {
    /**
     * 边框宽
     */
    private int mBorderWidth = 1;
    /**
     * 边框颜色
     */
    private int mBorderWidthColor = Color.YELLOW;
    /**
     * 圆角 默认以px为单位
     */
    private int mCornersize = 8;
    //            /边框画笔
    private Paint mCornerPaint;


    @Override
    public boolean removeCallbacks(Runnable action) {
        return super.removeCallbacks(action);
    }

    public CornerTextView(Context context) {
        this(context, null);
    }

    public CornerTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);

    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CornerTextView);
        mBorderWidth = (int) array.getDimension(R.styleable.CornerTextView_borderWidth, mBorderWidth);
        mBorderWidthColor = array.getColor(R.styleable.CornerTextView_borderWidthColor, mBorderWidthColor);
        mCornersize = (int) array.getDimension(R.styleable.CornerTextView_cornersize, mCornersize);
        array.recycle();

        mCornerPaint = new Paint();
        mCornerPaint.setAntiAlias(true);
        mCornerPaint.setDither(true);
        mCornerPaint.setStrokeWidth(mBorderWidth);
        //空心 只画边框
        mCornerPaint.setStyle(Paint.Style.STROKE);
        mCornerPaint.setColor(mBorderWidthColor);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(mBorderWidth / 2, mBorderWidth / 2, getMeasuredWidth() - mBorderWidth, getMeasuredHeight() - mBorderWidth);
        canvas.drawRoundRect(rectF, mCornersize, mCornersize, mCornerPaint);
        super.onDraw(canvas);
    }


}