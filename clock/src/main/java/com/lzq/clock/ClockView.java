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
 * @author https://www.jianshu.com/p/ad2b2b3cfdc8
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
        this(context, attrs, -1);
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
        //绘制刻度
        for (int i = 0; i < 60; i++) {
            //五分钟整除
            if (i % 5 == 0) {
                //绘制刻度路径
                float[] dialKdPaths = getDetailPaths(halfMinLength, halfMinLength, halfMinLength - 8, halfMinLength * 5 / 6, -i * 6);
                canvas.drawLines(dialKdPaths, paintKd30);
                //绘制刻度文字
                float[] dialPathsStr = getDetailPaths(halfMinLength, halfMinLength, halfMinLength - 8, halfMinLength * 3 / 4, -i * 6);
                canvas.drawText(strKedu[i / 5], dialPathsStr[2] - 16, dialPathsStr[3] + 14, paintKd30Text);
                continue;
            }
            //其他具体细分刻度
            float[] dialKdPaths = getDetailPaths(halfMinLength, halfMinLength, halfMinLength - 8, halfMinLength * 7 / 8, -i * 6);
            canvas.drawLines(dialKdPaths, paintKdSecond);
        }
        //指针绘制
        //时针绘制
        canvas.save(); //保存之前内容
        canvas.rotate(angleHour, halfMinLength, halfMinLength); //旋转的是画布,从而得到指针旋转的效果
        canvas.drawLine(halfMinLength, halfMinLength, halfMinLength, halfMinLength * 3 / 4, paintHour);
        canvas.restore(); //恢复
        //绘制分针
        canvas.save();
        canvas.rotate(angleMinute, halfMinLength, halfMinLength); //旋转的是画布,从而得到指针旋转的效果
        canvas.drawLine(halfMinLength, halfMinLength, halfMinLength, halfMinLength / 2, paintMinute);
        paintCircleBar.setColor(Color.rgb(75, 75, 75));
        paintCircleBar.setShadowLayer(4, 4, 8, Color.argb(70, 40, 40, 40));
        canvas.drawCircle(halfMinLength, halfMinLength, 24, paintCircleBar);
        canvas.restore();
        //绘制秒针
        canvas.save();
        canvas.rotate(angleSecond, halfMinLength, halfMinLength); //旋转的是画布,从而得到指针旋转的效果
        canvas.drawLine(halfMinLength, halfMinLength + 40, halfMinLength, halfMinLength / 4 - 20, paintSecond);
        paintCircleBar.setColor(Color.rgb(178, 34, 34));
        paintCircleBar.setShadowLayer(4, 4, 8, Color.argb(50, 80, 0, 0));
        canvas.drawCircle(halfMinLength, halfMinLength, 12, paintCircleBar);
        canvas.restore();
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

        paintKdSecond = new Paint();
        paintKdSecond.setStrokeWidth(6);
        paintKdSecond.setColor(Color.rgb(75, 75, 75));
        paintKdSecond.setAntiAlias(true);
        paintKdSecond.setDither(true);
        paintKdSecond.setStrokeCap(Paint.Cap.ROUND);
        paintKdSecond.setShadowLayer(4, 5, 10, Color.argb(50, 80, 80, 80));

        paintHour = new Paint();
        paintHour.setStrokeWidth(30);
        paintHour.setColor(Color.rgb(75, 75, 75));
        paintHour.setAntiAlias(true);
        paintHour.setDither(true);
        paintHour.setStrokeCap(Paint.Cap.ROUND);
        paintHour.setShadowLayer(4, 5, 10, Color.argb(50, 80, 80, 80));

        paintCircleBar = new Paint();
        paintCircleBar.setStrokeWidth(6);
//        paintCircleBar.setColor(Color.rgb(178,34,34));
        paintCircleBar.setAntiAlias(true);
        paintCircleBar.setDither(true);
        paintCircleBar.setStrokeCap(Paint.Cap.ROUND);
//        paintCircleBar.setShadowLayer(4,5,10,Color.argb(100,80,80,80));

        paintMinute = new Paint();
        paintMinute.setStrokeWidth(30);
        paintMinute.setColor(Color.rgb(75, 75, 75));
        paintMinute.setAntiAlias(true);
        paintMinute.setDither(true);
        paintMinute.setStrokeCap(Paint.Cap.ROUND);
        paintMinute.setShadowLayer(4, 5, 10, Color.rgb(80, 80, 80));

        paintSecond = new Paint();
        paintSecond.setStrokeWidth(6);
        paintSecond.setColor(Color.rgb(180, 30, 30));
        paintSecond.setAntiAlias(true);
        paintSecond.setDither(true);
        paintSecond.setStrokeCap(Paint.Cap.ROUND);
        paintSecond.setShadowLayer(4, 2, 10, Color.argb(100, 90, 90, 90));
    }

    private void initTime() {
        mCalendar = Calendar.getInstance();
        cuurHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        cuurMinute = mCalendar.get(Calendar.MINUTE);
        cuurSecond = mCalendar.get(Calendar.SECOND);
        if (cuurHour >= 12) {
            cuurHour = cuurHour - 12;
            isMorning = false;
        } else {
            isMorning = true;
        }
        angleSecond = cuurSecond * 6f;
        angleMinute = cuurMinute * 6f;
        angleHour = cuurHour * 6f * 5f;
    }

    /**
     * 停止绘制
     */
    public void stopDrawing() {
        drawable = false;
    }

    /**
     * 更新时分秒针的角度,开始绘制
     */
    public void startRun() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (drawable) {
                    try {
                        Thread.sleep(1000); // 睡1s
                        updateAngleSecond(); //更新秒针角度
                        updateAngleMinute(); //更新分针角度
                        updateAngleHour(); //更新时针角度
                        postInvalidate(); //重新绘制
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
        //更新秒针角度
        angleSecond = angleSecond + 6;
        cuurSecond += 1;
        if (angleSecond >= 360){
            angleSecond = 0;
            cuurSecond = 0;
            cuurMinute += 1;
            //一分钟同步一次本地时间
            mCalendar = Calendar.getInstance();
            cuurHour = mCalendar.get(Calendar.HOUR_OF_DAY);
            cuurMinute = mCalendar.get(Calendar.MINUTE);
            cuurSecond = mCalendar.get(Calendar.SECOND);
            if (cuurHour >= 12){
                cuurHour = cuurHour - 12;
                isMorning = false;
            }else{
                isMorning = true;
            }
            angleSecond = cuurSecond * 6f;
            angleMinute = cuurMinute * 6f;
            angleHour = cuurHour * 6f * 5f;
        }
    }

    /**
     * @param x         圆心坐标x
     * @param y         圆心坐标y
     * @param outRadius 外圈圆半径
     * @param inRadius  内圈圆半径
     * @param angle     角度
     * @return 返回路径坐标集合
     */
    private float[] getDetailPaths(int x, int y, int outRadius, int inRadius, int angle) {
        float[] paths = new float[4];
        paths[0] = (float) (x + outRadius * Math.cos(angle * Math.PI / 180));
        paths[1] = (float) (y + outRadius * Math.sin(angle * Math.PI / 180));
        paths[2] = (float) (x + inRadius * Math.cos(angle * Math.PI / 180));
        paths[3] = (float) (y + inRadius * Math.sin(angle * Math.PI / 180));
        return paths;
    }
}
