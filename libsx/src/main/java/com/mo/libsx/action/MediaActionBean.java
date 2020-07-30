package com.mo.libsx.action;

import android.net.Uri;

/**
 * @ author：mo
 * @ data：2020/7/30:13:21
 * @ 功能：
 */
public class MediaActionBean {
    /**
     * 拍照返回路径
     */
    private Uri imageUri;
    /**
     * 视频的质量，值为0-1
     */
    private int videoQuality = 1;
    /**
     * 视频的录制长度，s为单位
     */
    private int videoLength = 10;
    /**
     * 视频文件大小，字节为单位
     */
    private long videoSize = 20 * 1024 * 1024L;
    /**
     * 操作状态
     * 0==默认无操作
     * 1==拍照
     * 2==从相册选取图片
     * 3==录像
     * 4==从相册选取录像
     */

    private int phoneStatus = 0;

    public MediaActionBean() {
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public int getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(int videoQuality) {
        this.videoQuality = videoQuality;
    }

    public int getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }

    public long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(long videoSize) {
        this.videoSize = videoSize;
    }

    public int getPhoneStatus() {
        return phoneStatus;
    }

    public void setPhoneStatus(int phoneStatus) {
        this.phoneStatus = phoneStatus;
    }
}
