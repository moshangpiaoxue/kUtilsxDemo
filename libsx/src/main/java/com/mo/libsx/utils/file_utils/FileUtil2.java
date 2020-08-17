package com.mo.libsx.utils.file_utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.mo.libsx.k;
import com.mo.libsx.utils.activity_utils.ActivitysUtil;
import com.mo.libsx.utils.dataUtil.stringUtils.StringUtil;

import java.io.File;

/**
 * @ author：mo
 * @ data：2020/8/5:14:03
 * @ 功能：文件工具类 太多了，一点点嫁过来吧
 */
public class FileUtil2 {
    /**
     * 检查文件权限 (外部存储读写权限)
     */
    public static boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(k.app(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(k.app(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    /**
     * 检查并申请文件权限 (外部存储读写权限)
     */
    public static boolean checkPermissionAndRequest() {
        boolean check = checkPermission();
        if (!check) {
            Activity activity = ActivitysUtil.getTopActivity().get();
            if (activity != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }
            }
        }
        return check;
    }

    /**
     * 文件是否存在
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(FileUtil2.getFile(filePath));
    }

    /**
     * 文件是否存在
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 文件是否可用
     * 1.如果是外部文件, 判断外部存储是否可用以及是否有外部储存权限, 通过即为可用, 否则为不可用
     * 2.如果是内部文件, 则为可用
     */
    public static boolean checkFile(File file) {
        if (file == null) return false;
        boolean isStorageFile = file.getAbsolutePath().contains(Environment.getExternalStorageDirectory().getAbsolutePath());
        return !isStorageFile || Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && checkPermissionAndRequest();
    }

    /**
     * 文件 是否可用, 如果可用将自动创建父文件夹
     * 1.文件不可用或没有权限, 返回 false
     * 2.文件不存在, 如果父文件夹已存在, 返回 true; 如果父文件夹不存在将自动创建, 创建成功返回 true, 失败返回 false
     * 3.文件存在, 对象为文件对象返回 true, 为文件夹对象返回 false
     */
    public static boolean checkFileAndMakeDirs(File file) {
        if (!checkFile(file)) return false;
        if (!file.exists()) {
            File dir = file.getParentFile();
            return dir != null && (dir.exists() ? dir.isDirectory() : dir.mkdirs());
        }
        return !file.isDirectory();
    }

    /**
     * 文件夹 是否可用, 如果可用将自动创建该文件夹及其父文件夹
     * 1.文件不可用或没有权限, 返回 false
     * 2.文件不存在, 将创建该文件夹及其父文件夹, 创建成功返回 true, 失败返回 false
     * 3.文件存在, 对象为文件夹对象返回 true, 为文件对象返回 false
     */
    public static boolean checkDirAndMakeDirs(File file) {
        if (!checkFile(file)) {
            return false;
        }
        if (file.exists()) {
            return file.isDirectory();
        }
        return file.mkdirs();
    }

    /**
     * 获取文件-根据路径
     */
    public static File getFile(final String filePath) {
        return StringUtil.isSpace(filePath) ? null : new File(filePath);
    }

}
