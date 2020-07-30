package com.mo.libsx.utils.systemUtils.fingerUtil;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mo.libsx.R;
import com.mo.libsx.base.ui.KDialogFragment;
import com.mo.libsx.utils.ResUtil;


/**
 * @ author：mo
 * @ data：2019/5/16:14:51
 * @ 功能：
 */
public class FingerDentifyDialog extends KDialogFragment {
    public static final int STATE_NORMAL = 1;
    public static final int STATE_FAILED = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_SUCCEED = 4;
    private TextView mStateTv;
    private TextView mCancelBtn;


    public static FingerDentifyDialog newInstance() {
        FingerDentifyDialog dialog = new FingerDentifyDialog();
        return dialog;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_biometric_prompt;
    }

    @Override
    protected void doWhat(Dialog dialog, Window window, View view) {

        mStateTv = view.findViewById(R.id.state_tv);
        mCancelBtn = view.findViewById(R.id.cancel_btn);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                if (callBack != null) {
                ////                    callBack.onCancel(FingerDentifyDialog.this);
                //                }
                dismiss();
            }
        });
    }

    @Override
    public boolean isCancelable() {
        return false;
    }


    public void setState(int state, String info) {
        switch (state) {
            case STATE_NORMAL:
                mStateTv.setTextColor(ResUtil.getColor("#CDCED0"));
                mStateTv.setText("验证您的指纹");
                mCancelBtn.setVisibility(View.VISIBLE);
                break;
            case STATE_FAILED:
                mStateTv.setTextColor(ResUtil.getColor("#FF5555"));
                mStateTv.setText("验证指纹失败,请重新验证");
                mCancelBtn.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                mStateTv.setTextColor(ResUtil.getColor("#FF5555"));
                mStateTv.setText(info);
                mCancelBtn.setVisibility(View.GONE);
                mStateTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 500);
                break;
            case STATE_SUCCEED:
                mStateTv.setTextColor(ResUtil.getColor("#82C785"));
                mStateTv.setText("验证指纹成功");
                mCancelBtn.setVisibility(View.VISIBLE);
                mStateTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 500);
                break;
            default:
                break;
        }
    }

}
