package com.mo.libsx.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.mo.libsx.R;


/**
 * @ author：mo
 * @ data：2019/5/13:9:05
 * @ 功能：
 */
public class HttpDialogLoading {
    private static volatile HttpDialogLoading mInstance;
    public static HttpDialogLoading getInstance() {
        if (mInstance == null) {
            synchronized (HttpDialogLoading.class) {
                if (mInstance == null) {
                    mInstance = new HttpDialogLoading();
                }
            }
        }
        return mInstance;
    }

    private float dialogDim = 0.3f;
    private BaseDialog loadingDialog;

    public void show(Activity mActivity, final String tips) {
        if (loadingDialog == null) {
            loadingDialog = new BaseDialog(mActivity) {
                @Override
                protected int getLayoutId() {
                    return R.layout.dialog_loading;
                }

                @Override
                protected void doWhat(Dialog dialog, View view) {

                    TextView tv_dialog_loading = view.findViewById(R.id.tv_dialog_loading);
                    if (tips.isEmpty()) {
                        tv_dialog_loading.setVisibility(View.GONE);
                    } else {
                        tv_dialog_loading.setVisibility(View.VISIBLE);
                        tv_dialog_loading.setText(tips);
                    }
                }

                @Override
                protected float setDimAmount() {
                    return dialogDim;
                }
            };
        }
        if (!mActivity.isFinishing()) {
            loadingDialog.show();
        }
    }

    public void setDialogDim(float dialogDim) {
        this.dialogDim = dialogDim;
    }

    public void dismiss() {
        loadingDialog.dismiss();
        loadingDialog = null;
    }
}
