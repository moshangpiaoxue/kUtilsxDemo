package com.mo.libsx.view.grid_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * @ author：mo
 * @ data：2017/8/17:10:03
 * @ 功能：
 */
public class KGridView extends GridView {
    public KGridView(Context context) {
        this(context, null);
    }

    public KGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
