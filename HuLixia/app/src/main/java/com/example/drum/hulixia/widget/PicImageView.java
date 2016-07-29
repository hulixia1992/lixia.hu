package com.example.drum.hulixia.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import static android.graphics.Paint.Style.STROKE;

/**
 * Created by hulixia on 2016/7/15.
 * 自定义专辑图片
 */
public class PicImageView extends ImageView {
    private int width;
    private int height;
    private Bitmap b;
    private Matrix matrix;
    private int length;
    private Rect rect;
    private Path mPath;
    private Path mPath1;
    private Path mPath2;
    private Paint mPaint;


    public PicImageView(Context context) {
        super(context);
    }

    public PicImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PicImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        size();
        scaleBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setStyle(STROKE);
        mPaint.setStrokeWidth(2);
        mPath = new Path();
        mPath1 = new Path();
        mPath2 = new Path();

        canvas.translate(width / 2, height / 2);//将画布坐标原点移动到中心位置

        pathFigure();
        mPath2.op(mPath, mPath1, Path.Op.DIFFERENCE);
        canvas.clipPath(mPath2, Region.Op.INTERSECT);//切割
        mPath.reset();
        canvas.drawBitmap(b, rect, rect, mPaint);
    }

    private void scaleBitmap() {
        Drawable drawable = getDrawable();//获取图片
        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        if (!(drawable instanceof BitmapDrawable)) {
            return;
        }
        b = ((BitmapDrawable) drawable).getBitmap();//获取bitmap
        if (null == b) {
            return;
        }
        float scaleWidth = (float) length / b.getWidth();
        float scaleHeight = (float) length / b.getHeight();
        matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);//缩放矩阵
        b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);//压缩图片
    }

    protected void size() {
        length = Math.min(width, height) / 2;
        rect = new Rect(-(int) length, -(int) length, (int) length, (int) length);//绘制图片矩阵
    }

    protected Path pathFigure() {
        mPath.addCircle(0, 0, length, Path.Direction.CW);//增加圆的path，顺时针闭合圆
        mPath1.addCircle(0, 0, length / 3, Path.Direction.CW);//增加圆的path，顺时针闭合圆
        return mPath;
    }
}
