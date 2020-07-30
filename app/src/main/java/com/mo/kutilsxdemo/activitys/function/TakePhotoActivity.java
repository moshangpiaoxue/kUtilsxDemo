package com.mo.kutilsxdemo.activitys.function;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.action.MediaAction;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.utils.tips_utils.LogUtil;
import com.mo.libsx.view.dialog.PhoneTakeDialog;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：拍照
 */
public class TakePhotoActivity extends BaseActivity implements TitleBarAction, MediaAction {
    private ImageView iv_take_photo;


    @Override
    protected int getLayoutId() {
        return R.layout.act_ani_effect_take_photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityMediaResult(requestCode,resultCode,data);
    }

    @Override
    public void setMediaResult(int phontoType, Bitmap bitmap, String path, Intent data) {
        iv_take_photo.setImageBitmap(bitmap);
        LogUtil.i("SSSSSSSSSSSSSSSSSSSSSSS");
    }

    @Override
    protected void initView() {
        setTitle("拍照");
        iv_take_photo = findViewById(R.id.iv_take_photo);
    }

    @Override
    public void onClick(View v) {
        new PhoneTakeDialog(getActivity()) {
            @Override
            protected void actionPhoneChoose() {
                actionMediaChoosePic();
            }

            @Override
            protected void actionPhoneTake() {
                actionMediaTakePic();
            }
        }.show();
    }

    @Override
    protected void initData() {

    }

}
