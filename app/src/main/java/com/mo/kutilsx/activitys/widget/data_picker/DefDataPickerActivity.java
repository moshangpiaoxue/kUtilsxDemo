package com.mo.kutilsx.activitys.widget.data_picker;

import android.widget.DatePicker;

import com.mo.kutilsx.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：系统控件DatePicker
 */
public class DefDataPickerActivity extends BaseActivity implements TitleBarAction {
    private DatePicker dp_view_datepicker_def;

    @Override
    protected int getLayoutId() {
        return R.layout.act_view_datepicker_def;
    }

    @Override
    protected void initView() {
        setTitle("系统控件DatePicker");
        dp_view_datepicker_def = findViewById(R.id.dp_view_datepicker_def);
    }

    @Override
    protected void initData() {

    }

}
