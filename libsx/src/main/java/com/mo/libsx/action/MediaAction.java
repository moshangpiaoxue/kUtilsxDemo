package com.mo.libsx.action;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.hjq.toast.ToastUtils;
import com.mo.libsx.modle.constants.ConstansePermissionGroup;
import com.mo.libsx.modle.constants.KConstans;
import com.mo.libsx.utils.dataUtil.KUriUtil;
import com.mo.libsx.utils.image.BitmapCompressUtil;
import com.mo.libsx.utils.image.BitmapUtil;
import com.mo.libsx.utils.systemUtils.CameraUtil;
import com.mo.libsx.utils.systemUtils.storageUtil.SDCardUtil;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @ author：mo
 * @ data：2020/7/28:11:40
 * @ 功能：多媒体意图
 */
public interface MediaAction {

    MediaActionBean mMediaBean = new MediaActionBean();

    Context getContext();

    default MediaActionBean getMediaBean() {
        return mMediaBean;
    }

    /**
     * 复写此方法，处理拿到的数据
     *
     * @ param phontoType    操作类型
     * @ param bitmap        图片
     * @ param path          路径
     * @ param data          数据
     */
    default void setMediaResult(int phontoType, Bitmap bitmap, String path, Intent data) {

    }

    /**
     * 处理回调数据 在activity的onActivityResult方法里必须调用此方法
     */
    default void onActivityMediaResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            //拍照
            case KConstans.MEDIA_TAKE_PIC:
                if (getMediaBean().getPhoneStatus() == KConstans.MEDIA_TAKE_PIC) {
                    if (resultCode == RESULT_OK) {
                        setMediaResult(getMediaBean().getPhoneStatus(), BitmapUtil.getBitmap(getMediaBean().getImageUri()), null, data);
                    }
                }
                break;
            //从相册选图片
            case KConstans.MEDIA_CHOOSE_PIC:
                if (getMediaBean().getPhoneStatus() == KConstans.MEDIA_CHOOSE_PIC) {
                    if (resultCode == RESULT_OK) {
                        String imagePath;
                        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                            ContentResolver resolver = getContext().getContentResolver();
                            //照片的原始资源地址
                            Uri originalUri = data.getData();
                            try {
                                //使用ContentProvider通过URI获取原始图片
                                Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                                if (photo != null) {
                                    //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                                    //                                    Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                                    //                                    //释放原始图片占用的内存，防止out of memory异常发生
                                    //                                    photo.recycle();
                                    //                                    iv_image.setImageBitmap(smallBitmap);
                                    setMediaResult(getMediaBean().getPhoneStatus(), photo, "", data);
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            imagePath = CameraUtil.handlerImageChooseResult(data);
                            setMediaResult(getMediaBean().getPhoneStatus(), BitmapCompressUtil.getBitmapScaled(BitmapFactory.decodeFile(imagePath)), imagePath, data);
                        }
                        //                        setMediaResult(phoneStatus, BitmapFactory.decodeFile(imagePath), imagePath, data);
                    }
                }
                break;
            //录像
            case KConstans.MEDIA_TAKE_VIDEO:
                if (getMediaBean().getPhoneStatus() == KConstans.MEDIA_TAKE_VIDEO) {
                    if (resultCode == RESULT_OK) {
                        if (null != data && data.getData() != null) {
                            Cursor c = getContext().getContentResolver().query(data.getData(), new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
                            if (c != null && c.moveToFirst()) {
                                setMediaResult(getMediaBean().getPhoneStatus(), null, c.getString(0), data);
                            }
                        }
                        //                        setMediaResult(phoneStatus, null, UriUtil.getPath(imageUri));
                    }
                }
                break;
            //从相册选录像
            case KConstans.MEDIA_CHOOSE_VIDEO:
                if (getMediaBean().getPhoneStatus() == KConstans.MEDIA_CHOOSE_VIDEO) {
                    if (resultCode == RESULT_OK) {
                        setMediaResult(getMediaBean().getPhoneStatus(), null, KUriUtil.getPath(data.getData()), data);
                    }
                }
                break;
            //录音
            case KConstans.MEDIA_TAKE_SOUND:
                if (getMediaBean().getPhoneStatus() == KConstans.MEDIA_TAKE_SOUND) {
                    if (resultCode == RESULT_OK) {
                        if (null != data && data.getData() != null) {
                            setMediaResult(getMediaBean().getPhoneStatus(), null, "", data);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 开启拍照
     */
    default void actionMediaTakePic() {
        if (CameraUtil.isExistCamera()) {
            getMediaBean().setPhoneStatus(KConstans.MEDIA_TAKE_PIC);
            PermissionX.init((FragmentActivity) getContext())
                    .permissions(ConstansePermissionGroup.PERMISSIONS_CAMERA)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                            if (allGranted) {
                                getMediaBean().setImageUri(CameraUtil.actionPhoneTake((Activity) getContext()));
                            } else {
                                actionMediaTakePic();
                            }
                        }
                    });

        } else {
            ToastUtils.show("没有找到拍照设备！");
        }
    }

    /**
     * 开启从相册选取图片
     */
    default void actionMediaChoosePic() {
        if (SDCardUtil.isEnable()) {
            getMediaBean().setPhoneStatus(KConstans.MEDIA_CHOOSE_PIC);
            PermissionX.init((FragmentActivity) getContext())
                    .permissions(ConstansePermissionGroup.PERMISSIONS_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                            if (allGranted) {
                                CameraUtil.actionPhoneChoose((Activity) getContext());
                            } else {
                                actionMediaChoosePic();
                            }
                        }
                    });
        } else {
            ToastUtils.show("SD卡不可用");
        }
    }

    /**
     * 开启录像
     * 默认调用系统录像，可先调用setVideoParame（）方法设置录像质量，最大长度和文件大小
     */
    default void actionMediaTakeVideo() {
        if (CameraUtil.isExistCamera()) {
            getMediaBean().setPhoneStatus(KConstans.MEDIA_TAKE_VIDEO);
            PermissionX.init((FragmentActivity) getContext())
                    .permissions(ConstansePermissionGroup.PERMISSIONS_CAMERA)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                            if (allGranted) {
                                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, getMediaBean().getVideoQuality());
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, getMediaBean().getVideoLength());
                                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, getMediaBean().getVideoSize());
                                ((Activity) getContext()).startActivityForResult(intent, KConstans.MEDIA_TAKE_VIDEO);
                            } else {
                                actionMediaTakeVideo();
                            }
                        }
                    });

        } else {
            ToastUtils.show("没有找到录像设备！");
        }
    }

    /**
     * 从相册选视频
     */
    default void actionMediaChooseVideo() {
        if (SDCardUtil.isEnable()) {
            getMediaBean().setPhoneStatus(KConstans.MEDIA_CHOOSE_VIDEO);
            PermissionX.init((FragmentActivity) getContext())
                    .permissions(ConstansePermissionGroup.PERMISSIONS_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                            if (allGranted) {
                                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                                intent.setType("video/*");
                                ((Activity) getContext()).startActivityForResult(intent, KConstans.MEDIA_CHOOSE_VIDEO);
                            } else {
                                actionMediaChooseVideo();
                            }
                        }
                    });
        } else {
            ToastUtils.show("SD卡不可用！");
        }
    }

    /**
     * 开启录音
     */
    default void actionMediaTakeSound() {
        getMediaBean().setPhoneStatus(KConstans.MEDIA_TAKE_SOUND);
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        ((Activity) getContext()).startActivityForResult(intent, KConstans.MEDIA_TAKE_SOUND);

    }
}
