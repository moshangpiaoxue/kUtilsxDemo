package com.mo.kutilsxdemo.activitys.function;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.mo.kutilsxdemo.R;
import com.mo.libsx.action.TitleBarAction;
import com.mo.libsx.base.ui.BaseActivity;
import com.mo.libsx.modle.constants.ConstansePermissionGroup;
import com.mo.libsx.utils.image.DrawableUtil;
import com.mo.libsx.utils.systemUtils.ScreenUtil;
import com.mo.libsx.view.signature_view.SignatureView;
import com.permissionx.guolindev.PermissionX;


/**
 * @ author：mo
 * @ data：2019/6/12:10:29
 * @ 功能：签字
 */
public class SignatureViewActivity extends BaseActivity implements TitleBarAction {
    private SignatureView sv_signature;
    private TextView tv_signature_cun;
    private TextView tv_signature_chu;


    @Override
    protected int getLayoutId() {
        return R.layout.act_ani_effect_signature;
    }


    @Override
    protected void initView() {
        setTitle("签字");
        ScreenUtil.setScreenOrientationLandscape(getActivity());
        sv_signature = findViewById(R.id.sv_signature);
        tv_signature_cun = findViewById(R.id.tv_signature_cun);
        tv_signature_chu = findViewById(R.id.tv_signature_chu);
        PermissionX.init((FragmentActivity) getActivity())
                .permissions(ConstansePermissionGroup.PERMISSIONS_STORAGE);
        sv_signature.setOnSignedListener(new SignatureView.OnSignedListener() {
            @Override
            public void onSigned() {
                tv_signature_cun.setEnabled(true);
                tv_signature_chu.setEnabled(true);
            }

            @Override
            public void onClear() {
                tv_signature_cun.setEnabled(false);
                tv_signature_chu.setEnabled(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signature_cun:
                showToast("保存");
                tv_signature_cun.setBackground(DrawableUtil.getDrawable(sv_signature.getSignatureBitmap()));
                break;
            case R.id.tv_signature_chu:
                showToast("清除");
                sv_signature.clear();
                break;
        }
    }

    @Override
    protected void initData() {

    }

}
