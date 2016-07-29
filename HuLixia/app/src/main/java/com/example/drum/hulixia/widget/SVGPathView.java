package com.example.drum.hulixia.widget;

/**
 * Created by hulixia on 2016/7/8.
 */
import java.util.ArrayList;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.drum.hulixia.R;
import com.example.drum.hulixia.other.SVGUtil;

/**
 * Created by HuaChao on 2016/6/17.
 */
public class SVGPathView extends SurfaceView implements SurfaceHolder.Callback {

    // 动画起始Path数据
    private ArrayList<String> svgStartDataList;
    // 动画结束时的Path数据
    private ArrayList<String> svgEndDataList;

    private SurfaceHolder surfaceHolder;
    // 用于SurfaceView显示的对象
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    // view的宽高
    private int mWidth;
    private int mHeight;
    // SVG path里面的数据中参考的宽高
    private int mViewWidth;
    private int mViewHeight;
    // 绘制线条的宽度
    private int mPaintWidth;

    // 用于等比放缩
    private float widthFactor;
    private float heightFactor;
    private int mPaintColor;

    public SVGPathView(Context context) {
        super(context);
        init();
    }

    public SVGPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SVGPathView);
        // 读取布局文件设置的起始Path数据和结束Path数据
        String svgStartPath = ta
                .getString(R.styleable.SVGPathView_svg_start_path);
        String svgEndPath = ta.getString(R.styleable.SVGPathView_svg_end_path);
        // 如果二者有一个没有设置，就将没有设置的那个设定为已经设置的数据
        if (svgStartPath == null && svgEndPath != null) {
            svgStartPath = svgEndPath;
        } else if (svgStartPath != null && svgEndPath == null) {
            svgEndPath = svgStartPath;
        }
        // 读取布局文件的配置
        mViewWidth = ta.getInteger(R.styleable.SVGPathView_svg_view_width, -1);
        mViewHeight = ta
                .getInteger(R.styleable.SVGPathView_svg_view_height, -1);
        mPaintWidth = ta.getInteger(R.styleable.SVGPathView_svg_paint_width, 5);
        mPaintColor = ta.getColor(R.styleable.SVGPathView_svg_color,
                Color.BLACK);
        // 将原始数据做预处理
        svgStartDataList = SVGUtil.getInstance().extractSvgData(svgStartPath);
        svgEndDataList = SVGUtil.getInstance().extractSvgData(svgEndPath);

        ta.recycle();
        init();
    }

    // 初始化
    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        mPaint = new Paint();
        mPaint.setStrokeJoin(Join.ROUND);
        mPaint.setStrokeCap(Cap.ROUND);
        mPaint.setColor(mPaintColor);

    }

    // 开始绘制
    public void drawPath() {
        clearCanvas();
        mPaint.setStyle(Style.STROKE);
        mPaint.setColor(mPaintColor);
        Path path = SVGUtil.getInstance().parsePath(svgStartDataList,
                widthFactor, heightFactor);
        mCanvas.drawPath(path, mPaint);
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    // 清屏
    private void clearCanvas() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Style.FILL);
        mCanvas.drawRect(0, 0, mWidth, mHeight, mPaint);

    }

    // 调用invalidate时，把Bitmap对象绘制到View中
    @Override
    public void invalidate() {
        super.invalidate();
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // 保存当前的View宽高
        mWidth = width;
        mHeight = height;
        // 如果没有设置Path的参考宽高，默认设置为View的宽高
        if (mViewWidth <= 0) {
            mViewWidth = width;
        }
        if (mViewHeight <= 0) {
            mViewHeight = height;
        }
        // 计算放缩倍数
        widthFactor = 1.f * width / mViewWidth;
        heightFactor = 1.f * height / mViewHeight;
        // 创建Bitmap对象，用于绘制到屏幕中
        mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        // 将画笔绘制线条的宽度设置为经过放缩后的宽度
        mPaint.setStrokeWidth(mPaintWidth * widthFactor);
        // 清屏
        clearCanvas();
        // 将清屏结果绘制到屏幕
        invalidate();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
