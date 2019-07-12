package com.cjd.base.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


/**
 * bitmap处理
 * Created by chenjidong on 2017-04-14.
 */
public class BitmapUtil {

    public static Bitmap bitmap = null;

    /**
     * 压缩图片尺寸
     *
     * @param pathName     绝对路径
     * @param targetWidth  目标的宽度
     * @param targetHeight 目标高度
     * @return
     */
    public static Bitmap compressByFile(String pathName, int targetWidth,
                                        int targetHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(pathName, opts);

        opts.inSampleSize = calculateInSampleSize(opts, targetWidth, targetHeight);
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, opts);
        return bitmap;
    }

    /**
     * 根据 id 缩放图片
     *
     * @param resources
     * @param resId        R.drawable.xxx 或 R.mipmap.xxx
     * @param targetWidth  缩放的宽
     * @param targetHeight 缩放的高
     * @return Bitmap
     */
    public static Bitmap compressByResId(Resources resources, int resId, int targetWidth,
                                         int targetHeight) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, opts);

        opts.inSampleSize = calculateInSampleSize(opts, targetWidth, targetHeight);
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, opts);

    }


    /**
     * 保存bitmap 至指定文件 默认 jpeg格式
     *
     * @param bm       图片
     * @param fileName 文件路径
     */
    public static void saveFile(Bitmap bm, String fileName) throws Exception {
        File dirFile = new File(fileName);

        if (dirFile.exists()) {
            dirFile.delete();
        }
        File myCaptureFile = new File(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
    }

    /**
     * 根据传入的宽和高，计算出合适的inSampleSize值
     *
     * @param options
     * @param reqWidth  需要的宽
     * @param reqHeight 需要的高
     * @return 比例
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


        return bitmap;
    }


    public static Bitmap matrixScale(String path, int offsetX, int offsetY, int targetW, int targetH) {
        // 构建原始位图
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        return matrixScale(bitmap, offsetX, offsetY, targetW, targetH);
    }

    public static Bitmap matrixScaleByResId(Resources resources, int resId, int offsetX, int offsetY, int targetW, int targetH) {
        Bitmap bitmap = compressByResId(resources, resId, targetW, targetH);
        return matrixScale(bitmap, offsetX, offsetY, targetW, targetH);
    }

    /**
     * <br>
     * 这个方法作用非常多，详细解释一下各个参数的意义！
     * bitmap：原始位图
     * 第二到第五个参数，即截取原图哪一部分构建新位图，
     * offsetX和offsetY代表在X轴和Y轴上的像素偏移量，即从哪个位置开始截取
     * width和height代表截取多少个像素，但是要注意，offsetX+width应该小于等于原图的宽度
     * offsetY+height小于等于原图高度，要不然会报错，因为截到原图外面去了
     * 像下面这样填写，就代表截取整个原图，
     * Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
     * 如果填写100,100,200,200，就代表
     * 从原图左上角往右和下各偏移100像素，然后往后和往下各截取200构建新位图
     * matrix：缩放矩阵
     * 最后一个参数表示如果矩阵里面还存放了过滤条件，是否按条件过滤（如果matrix里面只放了平移数据），最后一个参数设置成什么都不会生效
     * </br>
     *
     * @param offsetX 截取开始点在X轴偏移量
     * @param offsetY 截取开始点在Y轴偏移量
     * @param targetW 截取多宽（像素）
     * @param targetH 截取多高（像素）
     * @return 缩放异常返回原始bitmap
     */
    public static Bitmap matrixScale(Bitmap bitmap, int offsetX, int offsetY, int targetW, int targetH) {

        try {
            // 获取原始宽高
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            // 计算宽高缩放比例，targetW，targetH即期待缩放完成后位图的宽高
            float scaleW = (float) targetW / width;
            float scaleH = (float) targetH / height;
            // 将缩放比例放进矩阵
            Matrix matrix = new Matrix();
            matrix.postScale(scaleW, scaleH);
            return Bitmap.createBitmap(bitmap, offsetX, offsetY, width, height, matrix, false);
        } catch (Exception e) {
            LogUtils.d("" + e.getMessage());
        }
        return bitmap;
    }

    /**
     * 图片质量压缩
     *
     * @param path    图片路径
     * @param quality 质量 0-100,100表示原图
     * @return
     */
    public static Bitmap losslessScale(String path, int quality) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        LogUtils.d("losslessScale 最终大小" + baos.toByteArray().length);
        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
                baos.toByteArray(), 0, baos.toByteArray().length);
        return compressedBitmap;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

}
