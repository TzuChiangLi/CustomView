package com.lzq.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * @author liziqiang@ftrend.cn
 */

public class ClockView extends View {
    private static final String TAG = "ClockView";
    private boolean drawable = true; //是否可以绘制
    private int halfMinLength; //最小宽/高的一半长度
    private Paint paintKd30; //时针刻度线画笔
    private Paint paintKd30Text; // 时针数字画笔
    private Paint paintKdSecond; //秒针刻度线画笔
    private Paint paintHour;  //时针画笔
    private Paint paintCircleBar;//指针圆心画笔
    private Paint paintMinute; //分针画笔
    private Paint paintSecond; //秒针画笔
    private float angleHour; //时针旋转角度
    private float angleMinute; //分针旋转角度
    private float angleSecond; //秒针旋转角度
    private int cuurSecond; //当前秒
    private int cuurMinute; //当前分
    private int cuurHour; //当前时
    private Calendar mCalendar;
    private boolean isMorning = true; //上午/下午
    private String[] strKedu = {"3", "2", "1", "12", "11", "10", "9", "8", "7", "6", "5", "4"};

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, -1);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(); //初始化画笔
        initTime(); //初始化时间
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        halfMinLength = Math.min(width, height) / 2;
        Log.d(TAG, "onMeasure: " + halfMinLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private void initPaint() {
        //时针刻度线画笔
        paintKd30 = new Paint();
        paintKd30.setStrokeWidth(8);
        paintKd30.setColor(Color.rgb(75, 75, 75));
        paintKd30.setAntiAlias(true);
        paintKd30.setDither(true);
        paintKd30.setStrokeCap(Paint.Cap.ROUND);
        // 时针数字画笔
        paintKd30Text = new Paint();
        paintKd30Text.setTextAlign(Paint.Align.LEFT); //左对齐
        paintKd30Text.setStrokeWidth(6); //设置宽度
        paintKd30Text.setTextSize(40); //文字大小
        paintKd30Text.setTypeface(Typeface.DEFAULT_BOLD); //加粗
        paintKd30Text.setColor(Color.rgb(75, 75, 75)); //画笔颜色
        paintKd30Text.setAntiAlias(true); //抗锯齿
        paintKd30Text.setDither(true); //抖动
        paintKd30Text.setStrokeCap(Paint.Cap.ROUND); //笔尖圆角
        paintKd30Text.setShadowLayer(4, 2, 4, Color.argb(60, 90, 90, 90)); //阴影

    }

    private void initTime() {
    }


    /**
     * 更新时针角度
     */
    private void updateAngleHour() {
        //变量大于360的时候重新归零
        angleHour = angleHour + (30f / 3600);
        if (angleHour >= 360) {
            angleHour = 0;
            cuurHour = 0;
        }
    }

    private void updateAngleMinute() {
        angleMinute = angleMinute + 0.1f;
    }

    private void updateAngleSecond() {
    }

    /**
     * @param x         圆心坐标x
     * @param y         圆心坐标y
     * @param outRadius 外圈圆半径
     * @param inRadius  内圈圆半径
     * @param angle     角度
     * @return 返回路径坐标集合
     */
    private float[] getDetailTime(int x, int y, int outRadius, int inRadius, int angle) {
        float[] paths = new float[4];
        paths[0] = (float) (x + outRadius * Math.cos(angle * Math.PI / 180));
        paths[1] = (float) (y + outRadius * Math.sin(angle * Math.PI / 180));
        paths[2] = (float) (x + inRadius * Math.cos(angle * Math.PI / 180));
        paths[3] = (float) (y + inRadius * Math.sin(angle * Math.PI / 180));
        return paths;
    }
}
