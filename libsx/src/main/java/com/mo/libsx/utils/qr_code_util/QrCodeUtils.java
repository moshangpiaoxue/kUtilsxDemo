package com.mo.libsx.utils.qr_code_util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.mo.libsx.utils.file_utils.PathUtils;
import com.mo.libsx.utils.image.BitmapCompressUtil;

import java.util.Hashtable;
import java.util.Vector;


/**
 * @ author：mo
 * @ data：2020/7/2:15:36
 * @ 功能：二维码解析工具类
 */

public final class QrCodeUtils {
    /**
     * 二维码最大尺寸
     */
    public static final int QRCODE_BITMAP_MAX_SIZE = 400;

    private QrCodeUtils() {
        throw new UnsupportedOperationException("Do not need instantiate!");
    }

    /**
     * 解析二维码（接口回调返回结果）
     */
    public static void analyze(Bitmap bitmap, Callback callback) {
        Result rawResult = analyze(bitmap);
        if (rawResult != null) {
            if (callback != null) {
                callback.onSuccess(bitmap, rawResult.getText());
            }
        } else {
            if (callback != null) {
                callback.onFailed();
            }
        }
    }

    public static void analyze(String path, Callback callback) {
        analyze(BitmapCompressUtil.getBitmapSample(path, QRCODE_BITMAP_MAX_SIZE, QRCODE_BITMAP_MAX_SIZE), callback);
    }
    @SuppressLint("MissingPermission")
    public static void analyze(Uri uri, Callback callback) {
        analyze(PathUtils.getFilePathByUri(uri), callback);
    }

    /**
     * 解析二维码（简单返回结果，扫描失败返回空）
     *
     * @param qrCodePicPath 二维码图片的路径
     */
    public static String analyze(String qrCodePicPath) {
        Result rawResult = getAnalyzeQRCodeResult(qrCodePicPath);
        if (rawResult != null) {
            return rawResult.getText();
        } else {
            return "";
        }
    }

    /**
     * 获取解析二维码的结果
     *
     * @param qrCodePicPath 二维码图片的路径
     */
    public static Result getAnalyzeQRCodeResult(String qrCodePicPath) {
        return analyze(BitmapCompressUtil.getBitmapSample(qrCodePicPath, QRCODE_BITMAP_MAX_SIZE, QRCODE_BITMAP_MAX_SIZE));
    }

    //==================================//


    /**
     * 解析二维码
     *
     * @param bitmap 二维码图片
     * @return 解析结果
     */
    @Nullable
    public static Result analyze(Bitmap bitmap) {
        MultiFormatReader multiFormatReader = new MultiFormatReader();

        // 解码的参数
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(2);
        // 可以解析的编码类型
        Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
        if (decodeFormats.isEmpty()) {
            decodeFormats = new Vector<>();

            // 这里设置可扫描的类型，我这里选择了都支持
            //            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            //            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            //            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        }
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        // 设置继续的字符编码格式为UTF8
        // hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
        // 设置解析配置参数
        multiFormatReader.setHints(hints);

        // 开始对图像资源解码
        Result rawResult = null;
        try {
            rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new BitmapLuminanceSource(bitmap))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rawResult;
    }


    /**
     * 解析二维码结果
     */
    public interface Callback {

        /**
         * 解析成功
         *
         * @param bitmap 二维码图片
         * @param result 解析结果
         */
        void onSuccess(Bitmap bitmap, String result);

        /**
         * 解析失败
         */
        void onFailed();
    }

}
