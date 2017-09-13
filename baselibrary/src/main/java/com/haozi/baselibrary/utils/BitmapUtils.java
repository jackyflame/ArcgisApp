package com.haozi.baselibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

/**
 * Created by Haozi on 2017/8/12.
 */

public class BitmapUtils {

    public static final String FILE_NAME_SUFFIX_PNG = ".png";

    /**
     * 得到缩略图
     * @param srcPath 图片源地址
     * @param destW 目标宽度
     * @param destH 目标高度
     * @return 缩略图
     */
    public static Bitmap getScaleBitmap(String srcPath, int destW, int destH) {

        BitmapFactory.Options bop = new BitmapFactory.Options();
        bop.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(srcPath, bop);

        bop.inJustDecodeBounds = false;

        int bopW = bop.outWidth / destW;
        int bopH = bop.outHeight / destH;

        int smpSize = bopW > bopH ? bopW : bopH;

        if (smpSize > 1) {
            bop.inSampleSize = smpSize;
            bmp = BitmapFactory.decodeFile(srcPath, bop);
        } else {
            bmp = BitmapFactory.decodeFile(srcPath);
        }

        if (bmp != null) {
            bmp = getScaleBitmap(bmp, destW, destH);
        }

        return bmp;
    }

    /**
     * 得到缩略图
     * @param maxBmp 图片源地址
     * @param destW 目标宽度
     * @param destH 目标高度
     * @return 缩略图
     */
    public static Bitmap getScaleBitmap(Bitmap maxBmp, int destW, int destH) {
        Bitmap bmp = maxBmp;
        if (maxBmp != null) {
            int curbmW = maxBmp.getWidth();
            int curbmH = maxBmp.getHeight();
            //横屏图片
            if (curbmW > curbmH) {
                int tarWidth = destW;
                //等比例缩小
                int tarHeight = (curbmH * tarWidth) / curbmW;
                //切图
                bmp = Bitmap.createScaledBitmap(maxBmp, tarWidth, tarHeight, false);
                //竖屏图片
            } else if (curbmW < curbmH) {
                int tarHeight = destH;
                //等比例缩小
                int tarWidth = (curbmW * tarHeight) / curbmH;
                //切图
                bmp = Bitmap.createScaledBitmap(maxBmp, tarWidth, tarHeight,false);
            } else {
                //切图
                bmp = Bitmap.createScaledBitmap(maxBmp, destW, destH, false);
            }
        }

        return bmp;
    }

    /**
     * Get round icon with border.
     * @param source
     * @param dstWidth
     * @param dstHeight
     * @param borderColor
     * @param borderWidth set <=0 means never draw border.
     * @return
     */
    public static Bitmap getRoundIcon(Bitmap source, int dstWidth, int dstHeight, int borderColor, int borderWidth) {

        Bitmap ret = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);

        source = getRoundBitmap(source, dstWidth, dstHeight, 999);

        Canvas canvas = new Canvas(ret);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        float radius = (dstWidth * 0.95f) / 2;

        radius -= (borderWidth > 0 ? borderWidth : 0);
        // 画出一个圆
        canvas.drawCircle(dstWidth / 2, dstHeight / 2, radius, paint);
        // 取两层绘制交集,显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 将图片画上去
        canvas.drawBitmap(source, 0, 0, paint);
        paint.reset();
        if (borderWidth > 0) {
            radius += 1.5f;
            paint.setAntiAlias(true);
            paint.setStrokeWidth(borderWidth);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(dstWidth / 2, dstHeight / 2, radius, paint);
        }
        return ret;
    }

    /**
     * 得到圆角图片
     * @param orgbmp 图片地址
     * @param roundPX 圆角值
     * @return 缩略图
     */
    public static Bitmap getRoundBitmap(Bitmap orgbmp, int destW, int destH,int roundPX) {

        Bitmap tarbmp = null;
        if (orgbmp != null) {
            Bitmap roundBmp = null;
            int w = orgbmp.getWidth();
            int h = orgbmp.getHeight();
            if (w > h) {
                int offX = (w - h) >> 1;
                roundBmp = Bitmap.createBitmap(orgbmp, offX, 0, h, h);
            } else if (h > w) {
                int offY = (h - w) >> 1;
                roundBmp = Bitmap.createBitmap(orgbmp, 0, offY, w, w);
            } else {
                roundBmp = orgbmp;
            }

            if (destW != w) {
                roundBmp = Bitmap.createScaledBitmap(roundBmp, destW, destH,false);
            }
            tarbmp = getRoundBitmap(roundBmp, roundPX);
        }

        return tarbmp;
    }

    /**
     * 得到缩略图
     * @param bitmap 原始图片
     * @param roundPX 圆角值
     * @return 缩略图
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, int roundPX) {
        if (roundPX > 0) {
            Bitmap obmp = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(obmp);

            final Paint pt = new Paint(Paint.ANTI_ALIAS_FLAG
                    | Paint.FILTER_BITMAP_FLAG);
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            pt.setColor(0xFF000000);
            c.drawARGB(0, 0, 0, 0);
            c.drawRoundRect(rectF, roundPX, roundPX, pt);

            pt.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            c.drawBitmap(bitmap, rect, rect, pt);

            return obmp;
        }

        return bitmap;
    }

    /**
     * 把图片写入SD卡
	 * @param bmp 图片
	 * @param file 目标文件
	 * @param quality 图片质量
	 * @return 是否成功
	 */
    public static boolean writeBmpToSDCard(Bitmap bmp, File file, int quality) {
        try {
            ByteArrayOutputStream baosm = new ByteArrayOutputStream();
            //如果文件路径分析出来后缀名为png，则以PNG格式编码存储
            if (file.getPath().toLowerCase(Locale.getDefault()).endsWith(FILE_NAME_SUFFIX_PNG)) {
                bmp.compress(Bitmap.CompressFormat.PNG, quality, baosm);
                //否则均以JEPG格式编码存储
            } else {
                bmp.compress(Bitmap.CompressFormat.JPEG, quality, baosm);
            }
            //转换为二进制流
            byte[] bts = baosm.toByteArray();
            //如果文件存在，则删除文件
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            //将文件写入缓存目录（以当前毫秒数为缓存文件名）
            File tempFile = new File(FileUtil.PROJECT_IMAGE_DIR,Long.toString(System.currentTimeMillis()));
            //开始写入文件
            FileOutputStream fosm = new FileOutputStream(tempFile);
            BufferedOutputStream bos = new BufferedOutputStream(fosm);
            bos.write(bts);
            bos.flush();
            bos.close();
            fosm.close();
            //将文件重命名至目标文件
            tempFile.renameTo(file);
            //返回写入成功
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回写入失败
        return false;
    }

    /**
     * 把图片写入SD卡
     * @param bmp 图片
     * @param filePath 目标文件地址
     * @param quality 图片质量
     * @return 是否成功
     */
    public static boolean writeBmpToSDCard(Bitmap bmp, String filePath,int quality) {
        return writeBmpToSDCard(bmp, new File(filePath), quality);
    }
}
