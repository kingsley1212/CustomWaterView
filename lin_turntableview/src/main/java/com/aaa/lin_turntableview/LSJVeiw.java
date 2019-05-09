package com.aaa.lin_turntableview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2 0002.
 */

public class LSJVeiw extends View {
    private Context context;
    private float mRadios;

    private List<String> listText;//泛型是String类型的
    //每个扇面的中心位置，让指针停在扇面的中心位置
    private List<Integer> listColor;
    private Paint paint;

    public LSJVeiw(Context context) {
        super(context);

    }

    public LSJVeiw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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

    public LSJVeiw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < listColor.size(); i++) {
            paint.setColor(listColor.get(i));
            float startRadios = 270 - mRadios / 2;

            Path path = new Path();
            RectF rectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            path.addArc(rectF, startRadios, mRadios);
            canvas.drawArc(rectF, startRadios, mRadios, true, paint);
            startRadios += mRadios;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = manager.getDefaultDisplay().getWidth();
        setMeasuredDimension(width, width);
    }
}
