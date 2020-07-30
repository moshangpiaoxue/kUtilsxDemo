package com.mo.libsx.utils.file_utils;

import android.net.Uri;

import androidx.core.content.FileProvider;

import com.mo.libsx.k;

import java.io.File;


/**
 * @ author：mo
 * @ data：2019/2/1:14:55
 * @ 功能：FileProvider工具类
 */
public class FileProviderUtils {
    /**
     * Android N 以上获取文件 Uri (通过 FileProvider)
     */
    public static Uri getUriForFile(File file) {
        return mFileProvider.getUriForFile(k.app(), getFileProviderAuthority(), file);
    }

    /**
     * 获取本应用 FileProvider 授权 包名+.fileprovider，在清单里声明的时候设置
     */
    public static String getFileProviderAuthority() {
        return k.app().getPackageName() + ".fileprovider";
    }

    public static class mFileProvider extends FileProvider {
    }
}
