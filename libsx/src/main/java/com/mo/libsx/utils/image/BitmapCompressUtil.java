package com.mo.libsx.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.IntRange;

import com.mo.libsx.k;
import com.mo.libsx.utils.file_utils.FileUtil2;
import com.mo.libsx.utils.systemUtils.ScreenUtil;
import com.mo.libsx.utils.tips_utils.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.mo.libsx.utils.image.BitmapUtil.getBitmapSize;
import static com.mo.libsx.utils.image.BitmapUtil.isEmptyBitmap;

/**
 * @ author：mo
 * @ data：2020/8/5:14:30
 * @ 功能：
 */
public class BitmapCompressUtil {


    //////////////////////////////////////  - 采样率压缩  -  //////////////////////////////////////////////////////////

    /**
     * 采样率压缩-计算采样大小
     *
     * @ param options   选项
     * @ param maxWidth  最大宽度
     * @ param maxHeight 最大高度
     */
    public static int getSizeSample(final BitmapFactory.Options options, final int maxWidth, final int maxHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((width >>= 1) >= maxWidth && (height >>= 1) >= maxHeight) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    /**
     * 采样率压缩
     */
    public static Bitmap getBitmapSample(final Bitmap bitmap, final int sampleSize) {
        return getBitmapSample(bitmap, sampleSize, false);
    }

    /**
     * 采样率压缩
     */
    public static Bitmap getBitmapSample(final Bitmap src, final int sampleSize, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        //        是否回收
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 采样率压缩
     *
     * @ param src       源图片
     * @ param maxWidth  最大宽度
     * @ param maxHeight 最大高度
     */
    public static Bitmap getBitmapSample(final Bitmap src, final int maxWidth, final int maxHeight) {
        return getBitmapSample(src, maxWidth, maxHeight, false);
    }

    /**
     * 采样率压缩
     *
     * @ param src       源图片
     * @ param maxWidth  最大宽度
     * @ param maxHeight 最大高度
     * @ param recycle   是否回收
     */
    public static Bitmap getBitmapSample(final Bitmap src, final int maxWidth, final int maxHeight, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = getSizeSample(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        if (recycle && !src.isRecycled()) {
            src.recycle();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 采样率压缩-根据路径获取图片
     *
     * @param filePath  文件路径
     * @param maxWidth  图片最大宽度
     * @param maxHeight 图片最大高度
     * @return bitmap
     */
    public static Bitmap getBitmapSample(final String filePath, final int maxWidth, final int maxHeight) {
        if (!FileUtil2.isFileExists(filePath)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = getSizeSample(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    //////////////////////////////////////  - 等比压缩  -  //////////////////////////////////////////////////////////

    /**
     * 等比压缩-获取压缩比例
     *
     * @ param options 选项
     * @ param maxWidth 最大宽度
     * @ param maxHeight 最大高度
     */
    public static int getSizeScaled(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;
        int inScaledSize = 0;
        if (rawHeight > maxHeight || rawWidth > maxWidth) {
            float ratioWidth = (float) rawWidth / maxWidth;
            float ratioHeight = (float) rawHeight / maxHeight;
            inScaledSize = (int) Math.min(ratioHeight, ratioWidth);
        }
        inScaledSize = Math.max(1, inScaledSize);
        return inScaledSize;
    }

    /**
     * 等比压缩-根据屏幕宽高
     */
    public static Bitmap getBitmapScaled(String imagePath) {
        return getBitmapScaled(BitmapFactory.decodeFile(imagePath), ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight());
    }

    /**
     * 等比压缩-根据屏幕宽高
     */
    public static Bitmap getBitmapScaled(Bitmap bitmap) {
        return getBitmapScaled(bitmap, ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight());
    }

    /**
     * 等比压缩-根据宽高
     */
    public static Bitmap getBitmapScaled(String imagePath, int dstWidth, int dstHeight) {
        return getBitmapScaled(BitmapFactory.decodeFile(imagePath), dstWidth, dstHeight);
    }

    /**
     * 根据宽高等比压缩图片
     * 策略：
     * 1、当原宽高均小于预设宽高时，不作处理
     * 2、宽高比不同时，说明如果根据设定的数据处理，图像会变形，处理
     * 3、其他情况，根据比例缩小，不用考虑放大的情况，1已经处理
     * 4、超长或超宽图没有考虑
     */
    public static Bitmap getBitmapScaled(Bitmap bitmap, int newWidth, int newHeight) {
        // 获得图片的宽高
        int oldWidth = bitmap.getWidth();
        int oldHeight = bitmap.getHeight();
        LogUtil.i("原宽==" + oldWidth + "\n原高==" + oldHeight);
        LogUtil.i("欲设宽==" + newWidth + "\n欲设高==" + newHeight);
        //1；原宽高均小于预设宽高
        if (oldWidth < newWidth && oldHeight < newHeight) {
            LogUtil.i("bitmap宽高均小于设定宽高。现宽高为：" + oldWidth + ":" + oldHeight + "欲设宽高为：" + newWidth + ":" + newHeight);
            return bitmap;
        }
        // 计算缩放比例
        float scaleWidth = 0;
        float scaleHeight = 0;
        float oldScale = oldWidth / oldHeight;
        float newScale = newWidth / newHeight;
        LogUtil.i("oldScale==" + oldScale);
        LogUtil.i("newScale==" + newScale);
        //2、形状方向
        if (oldWidth / oldHeight != newWidth / newHeight) {
            scaleWidth = ((float) newHeight) / oldWidth;
            scaleHeight = ((float) newWidth) / oldHeight;
            LogUtil.i("形状相反");
        } else {
            scaleWidth = ((float) newWidth) / oldWidth;
            scaleHeight = ((float) newHeight) / oldHeight;
            LogUtil.i("形状相同");
        }
        LogUtil.i("缩放比例宽==" + scaleWidth + "\n缩放比例高==" + scaleHeight);
        // 得到新的图片
        Bitmap newbm = BitmapUtil.getBitmapScale(bitmap, scaleWidth, scaleHeight);
        return newbm;
    }


    //////////////////////////////////////  - 质量压缩 （尺寸大小不变） -  //////////////////////////////////////////////////////////
    //    图片的大小是没有变的，因为质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度等，
    //    来达到压缩图片的目的，这也是为什么该方法叫质量压缩方法。那么，图片的长，宽，像素都不变，那么bitmap所占内存大小是不会变的。
    //    如果是bit.compress(CompressFormat.PNG, quality, baos);这样的png格式，quality就没有作用了，bytes.length不会变化，因为png图片是无损的，不能进行压缩。




    /**
     * 质量压缩--质量
     */
    public static Bitmap getBitmapQuality(final Bitmap src,  @IntRange(from = 0, to = 100) final int quality) {
        return getBitmapQuality(src, quality, false);
    }
    /**
     * 质量压缩--质量
     *
     */
    private static Bitmap getBitmapQuality(final Bitmap src, @IntRange(from = 0, to = 100) final int quality, final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) {src.recycle();}
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    /**
     * 质量压缩--最大值字节数
     *
     */
    public static Bitmap getBitmapQuality(final Bitmap src, final long maxByteSize) {
        return getBitmapQuality(src, maxByteSize, false);
    }
    /**
     * 质量压缩--最大值字节数
     */
    public static Bitmap getBitmapQuality(final Bitmap src,final long maxByteSize,  final boolean recycle) {
        if (isEmptyBitmap(src) || maxByteSize <= 0){ return null;}
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize) {// 最好质量的不大于最大字节，则返回最佳质量
            bytes = baos.toByteArray();
        } else {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            if (baos.size() >= maxByteSize) { // 最差质量不小于最大字节，则返回最差质量
                bytes = baos.toByteArray();
            } else {
                // 二分法寻找最佳质量
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize) {
                        break;
                    } else if (len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }
                if (end == mid - 1) {
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, st, baos);
                }
                bytes = baos.toByteArray();
            }
        }
        if (recycle && !src.isRecycled()) {src.recycle();}
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    /**
     * 质量压缩- 压缩图片到多少KB以内(单位KB)
     */
    public static File getFileQuality(String imagePath, long maxByteSize) {
        return getFileQuality(BitmapFactory.decodeFile(imagePath), maxByteSize);
    }
    /**
     * 质量压缩- 压缩图片到多少KB以内(单位KB)
     */
    public static File getFileQuality(Bitmap bitmap, long maxByteSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到字节流中
        LogUtil.i("初始大小===" + getBitmapSize(bitmap));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        long fileSize = baos.toByteArray().length / 1024;
        LogUtil.i("============================图片压缩前大小=======" + fileSize + "====================================");
        int options = 90;
        if (fileSize / maxByteSize > 5) {
            options = 10;
        } else if (fileSize / maxByteSize > 4) {
            options = 20;
        } else if (fileSize / maxByteSize > 3) {
            options = 30;
        } else if (fileSize / maxByteSize > 2) {
            options = 50;
        }
        // 循环判断如果压缩后图片是否大于1024kb,大于继续压缩
        while (fileSize > maxByteSize && options > 0) {
            LogUtil.i("============================图片压缩后还是大于规定大小，重新压缩=================");
            // 重置baos即清空baos
            baos.reset();
            // 这里压缩options%，把压缩后的数据存放到字节流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            LogUtil.i("===========================压缩到原来================" + options + "%");
            // 每次都减少10
            options -= 10;
            fileSize = baos.toByteArray().length / 1024;
        }
        LogUtil.i("============================图片压缩结束===" + fileSize + "========================================");
        // 把压缩后的字节存放到ByteArrayInputStream中
        InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        File appDir = new File(k.app().getExternalFilesDir(null), "temp");
        if (!appDir.exists() && appDir.mkdir()) {
            LogUtil.i(appDir.getName() + "目录创建成功");
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            LogUtil.i("============================压缩后的图片保存到SD===========================================");
            FileOutputStream fos = new FileOutputStream(file);
            int temp = isBm.available();
            int bytesRead;
            byte[] buffer = new byte[isBm.available()];
            while ((bytesRead = isBm.read(buffer, 0, temp)) != -1) {
                //                LogUtil.i("=======================================================================" + bytesRead);
                fos.write(buffer, 0, bytesRead);
            }
            fos.flush();
            fos.close();
            isBm.close();
            LogUtil.i("============================保存到SD成功===========================================");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
