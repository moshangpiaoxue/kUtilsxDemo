package com.mo.libsx.action;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.mo.libsx.utils.beng_utils.NextActivityUtil;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/03/08
 * desc   : Activity 相关意图
 */
public interface ActivityAction {
    /** 为了在接口里可以改动变量的值，只能能通过bean类糊弄一下系统，不然无法赋值，在activity销毁的时候置空 */
    ActivityBean mActivityBean = new ActivityBean(-1);

    Context getContext();

    default ActivityBean getActivityBean() {
        return mActivityBean;
    }

    default int getCurrentTabInde() {
        return getActivityBean() == null ? -1 : getActivityBean().getmCurrentTabInde();
    }

    /**
     * 获取 Activity
     */
    default Activity getActivity() {
        Context context = getContext();
        do {
            if (context instanceof Activity) {
                return (Activity) context;
            } else if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                return null;
            }
        } while (context != null);
        return null;
    }


    /**
     * 启动一个 Activity（简化版）
     */
    default void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    /**
     * 启动一个 Activity
     */
    default void startActivity(Intent intent) {
        if (!(getContext() instanceof Activity)) {
            // 如果当前的上下文不是 Activity，调用 startActivity 必须加入新任务栈的标记
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        getContext().startActivity(intent);
    }

    /**
     * 关闭当前activity
     */

    default void finishActivity() {
        NextActivityUtil.finishActivity(getActivity());
    }

    default void finishActivity(Activity activity) {
        NextActivityUtil.finishActivity(activity);
    }

    /**
     * 添加fragment
     *
     * @param fragmentLayoutId 承载布局id
     * @param fragment         碎片实例
     * @param isShow           是否显示
     */
    default void addFragment(int fragmentLayoutId, Fragment fragment, Boolean isShow) {
        if (fragment != null && fragmentLayoutId != 0) {
            FragmentTransaction transaction = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            transaction.add(fragmentLayoutId, fragment);
            if (isShow) {
                transaction.show(fragment);
            }
            transaction.commit();
        }
    }

    /**
     * 移除fragment
     */
    default void removeFragment() {
        if (((FragmentActivity) getActivity()).getSupportFragmentManager().getBackStackEntryCount() > 1) {
            ((FragmentActivity) getActivity()).getSupportFragmentManager().popBackStack();
        } else {
            getActivity().finish();
        }
    }

    /**
     * 碎片变化
     */
    default void changeFragments(int fragmentLayoutId, Fragment[] fragments, int index) {
        if (getActivityBean().getmCurrentTabInde() != index) {
            FragmentTransaction trx = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[getActivityBean().getmCurrentTabInde()]);
            if (!fragments[index].isAdded()) {
                trx.add(fragmentLayoutId, fragments[index]);
            } else {
                trx.show(fragments[index]);
            }
            trx.commit();
        }
        getActivityBean().setmCurrentTabInde(index);
    }
}