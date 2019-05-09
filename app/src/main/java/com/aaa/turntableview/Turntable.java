package com.aaa.turntableview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/1 0001.
 */

public class Turntable extends View {
    //定义上下文
    private Context context;
    //轮盘起始位置
    private final float StrartLocation = 270;
    //画笔
    private Paint paint;
    //绘制的角度
    private float mRadios;
    //显示的文字
    private List<String> listText;//泛型是String类型的
    //每个扇面的中心位置，让指针停在扇面的中心位置
    private List<Float> listRadios;
    //绘制的次数
    private int mCount;
    //每个扇面的颜色
    private List<Integer> listColor;

    //
    public Turntable(Context context) {
        super(context);
    }

    public Turntable(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        Path path = new Path();
        listText = new ArrayList<>();
        listRadios = new ArrayList<>();
        listColor = new ArrayList<>();
        //颜色，a:透明度，r:红，g:绿 b:蓝
        listColor.add(0xffff0000);//红色
        listColor.add(0xffff7f00);//橙色
        listColor.add(0xffffff00);//黄色
        listColor.add(0xff00ff00);//绿色
        listColor.add(0xff00ffff);//青色
        listColor.add(0xff0000ff);//蓝色
        listColor.add(0xff8b0000);//紫色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (listText != null && listText.size() > 0) {
            //实例化文字画笔
            Paint mTextPaint = new Paint();
            //文字画笔的颜色
            mTextPaint.setColor(Color.WHITE);
            //文字画笔开始抗锯齿
            mTextPaint.setAntiAlias(true);
            //文字画笔抗抖动
            mTextPaint.setDither(true);
            //level 20 以上可以设置字符间距
            if (Build.VERSION.SDK_INT > 20) {
                mTextPaint.setLetterSpacing(0.1f);
            }
            //设置文本对齐方式
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            //设置文字大小
            mTextPaint.setTextSize(80);
            //初始化打印页
            Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
            //设置文字画笔打印页
            mTextPaint.setTypeface(typeface);

            //设置开始绘制角度
            float startRadios = 270 - mRadios / 2;
            //初始化画图的区域
            RectF rectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            //开始绘制轮盘

            for (int i = 0; i < listText.size(); i++) {
                //获取当前扇面的颜色
                paint.setColor(listColor.get(i));
                //实例化轮廓
                Path path = new Path();
                //绘制一个扇形区域
                path.addArc(rectF, startRadios, mRadios);
                //获得文字高度
                Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
                //获取文字上边缘
                float assent = metrics.ascent;
                //文字的下边缘
                float descent = metrics.descent;
                //计算初文字高度
                float height = descent - assent;
                //绘制扇形
                canvas.drawArc(rectF, startRadios, mRadios, true, paint);
                //绘制文字
                canvas.drawTextOnPath(listText.get(i), path, 0, height, mTextPaint);
                //改变绘制起始角度
                startRadios += mRadios;
                //
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取windowmanger
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的宽度
        int width = manager.getDefaultDisplay().getWidth();
        //设置绘制区域的宽高
        setMeasuredDimension(width, width);
    }

    //activity 传入的数组，就是要显示的奖项的文字
    public void setText(String[] text) {
        try {
            mCount = text.length;
            if (mCount < 1) {
                mCount = 1;
            }
            for (int i = 0; i < text.length; i++) {
                listText.add(text[i]);

            }
            //计算每个扇形所占的弧度
            mRadios = 360.0f / mCount;
            for (int i = 0; i < listText.size(); i++) {
                float start = 270 + mRadios * i;
                if (start < 360) {
                    listRadios.add(start);
                } else {
                    listRadios.add(start - 360);
                }
            }
            invalidate();
        } catch (Exception e) {
            Log.e("绘制问题", "传入的数组为空");
        }

    }

    //获取中心位置，
    public List<Float> getRadioList() {
        return listRadios;
    }

    public Turntable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
