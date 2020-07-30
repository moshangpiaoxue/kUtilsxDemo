package com.mo.libsx.view.animation_effect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.mo.libsx.R;
import com.mo.libsx.utils.systemUtils.ScreenUtil;


/**
 * @ author：mo
 * @ data：2020/7/16:9:19
 * @ 功能：红旗飘动动画view
 */
public class BitMapMeshView extends View {
    private int HEIGHT = 200;
    private int WIDTH = 200;
    private int mHeight = 600;
    private int mWidth = 800;
    private Bitmap mbitmap;
    private int COUNT = (WIDTH + 1) * (HEIGHT + 1);
    private float[] verts = new float[COUNT * 2];  // 因为要保存一个点的个坐标（x,y）那么这个数组长度就要 *2
    private float[] origs = new float[COUNT * 2];
    private float k;

    public BitMapMeshView(Context context) {
        this(context, null);
    }

    public BitMapMeshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitMapMeshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    // 通用代码
    private void initView() {
        int index = 0;
        mbitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hongqi);
        mbitmap = setImgSize(mbitmap, ScreenUtil.getScreenWidth(), mHeight);
        float bitmapwidth = mbitmap.getWidth();
        float bitmapheight = mbitmap.getHeight();
        for (int i = 0; i < HEIGHT + 1; i++) {
            float fy = bitmapwidth / HEIGHT * i;
            for (int j = 0; j < WIDTH + 1; j++) {
                float fx = bitmapheight / WIDTH * j;
                //偶数位记录x坐标  奇数位记录Y坐标
                origs[index * 2 + 0] = verts[index * 2 + 0] = fx;
                origs[index * 2 + 1] = verts[index * 2 + 1] = fy;
                index++;
            }
        }
    }

    // 红旗摆动效果算法
    private void wave() {
        for (int i = 0; i < HEIGHT + 1; i++) {
            for (int j = 0; j < WIDTH + 1; j++) {
                //x坐标不变
                verts[(i * (WIDTH + 1) + j) * 2 + 0] += 0;
                //增加k值是为了让相位产生移动，从而可以飘动起来
                float offset = (float) Math.sin((float) j / WIDTH * 2 * Math.PI + k);
                //y坐标改变，呈现正弦曲线 幅度
                verts[(i * (WIDTH + 1) + j) * 2 + 1] = origs[(i * (WIDTH + 1) + j) * 2 + 1] + offset * 20;
            }
        }
        //速度
        k += 0.05f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        wave();
        canvas.drawBitmapMesh(mbitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        invalidate();
    }

    private void warp(float cx, float cy) {
        for (int i = 0; i < COUNT * 2; i += 2) {
            float dx = cx - origs[i + 0];
            float dy = cy - origs[i + 1];
            float dd = dx * dx + dy * dy;
            //计算每个座标点与当前点（cx、cy）之间的距离
            float d = (float) Math.sqrt(dd);
            //计算扭曲度，距离当前点（cx、cy）越远，扭曲度越小
            float pull = 5000 / ((float) (dd * d));
            //对verts数组（保存bitmap上21 * 21个点经过扭曲后的座标）重新赋值
            if (pull >= 1) {
                verts[i + 0] = cx;
                verts[i + 1] = cy;
            } else {
                //控制各顶点向触摸事件发生点偏移
                verts[i + 0] = origs[i + 0] + dx * pull;
                verts[i + 1] = origs[i + 1] + dy * pull;
            }
        }
        //通知View组件重绘
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //实现红旗飘动效果
        warp(event.getX(), event.getY());
        return true;
    }

    // 调整输入图片的大小
    public Bitmap setImgSize(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleHeight, scaleWidth);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}