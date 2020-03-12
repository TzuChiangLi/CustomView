package com.lzq.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author liziqiang@ftrend.cn
 */

public class ClockCopyView extends View {
    private static final String TAG = "ClockCopyView";
    private Paint paintMinute;
    private Paint paintSecond;
    private int halfWidth;

    public ClockCopyView(Context context) {
        this(context, null);
    }

    public ClockCopyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ClockCopyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initTime();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        halfWidth = Math.min(width, height) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private void initTime() {
        //初始化时间
    }

    private void initPaint() {
        //初始化画笔
        //时针刻度线画笔
        paintMinute = new Paint();
        paintMinute.setStrokeWidth(8);
        paintMinute.setColor(Color.rgb(75, 75, 75));
        paintMinute.setAntiAlias(true);
        paintMinute.setDither(true);
        paintMinute.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * @param x         圆心x坐标
     * @param y         圆心y坐标
     * @param inRadius  内圆半径
     * @param outRadius 外圆半径
     * @param angle     角度
     * @return 坐标集合
     */
    private float[] getPaths(int x, int y, int inRadius, int outRadius, int angle) {
        float[] paths = new float[4];
        paths[0] = (float) (x + outRadius * Math.cos(angle * Math.PI / 180));
        paths[1] = (float) (y + outRadius * Math.sin(angle * Math.PI / 180));
        paths[2] = (float) (x + inRadius * Math.cos(angle * Math.PI / 180));
        paths[3] = (float) (y + inRadius * Math.sin(angle * Math.PI / 180));
        return paths;
    }
}
