package com.mo.libsx.utils.systemUtils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import com.mo.libsx.k;


/**
 * @ author：mo
 * @ data：2019/1/10：14:44
 * @ 功能：剪贴板工具类
 */
public class ClipboardUtil {

    /**
     * 判断版本
     */
    private static Boolean isNew() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 获取剪贴板管理器
     */
    private static ClipboardManager getClipboardManager() {
        return (ClipboardManager) k.app().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /**
     * 填充剪贴板数据
     */
    public static void setText(String text) {
        if (isNew()) {
            getClipboardManager().setPrimaryClip(ClipData.newPlainText("Label", text));
        } else {
            getClipboardManager().setText(text);
        }
    }

    /**
     * 获取剪贴板数据
     */
    public static String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isNew()) {
            if (!getClipboardManager().hasPrimaryClip()) {
                return stringBuilder.toString();
            } else {
                ClipData clipData = getClipboardManager().getPrimaryClip();
                int count = clipData.getItemCount();
                for (int i = 0; i < count; i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    stringBuilder.append(item.coerceToText(k.app()));
                }
            }
        } else {
            stringBuilder.append(getClipboardManager().getText());
        }
        return stringBuilder.toString();
    }

}