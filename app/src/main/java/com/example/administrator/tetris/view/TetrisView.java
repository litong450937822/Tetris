package com.example.administrator.tetris.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class TetrisView extends View {
    //父容器宽、高
    public int xWidth, yHeight;
    //屏幕宽度
    private int width;

    //辅助线画笔
    Paint linePaint;
    //方块画笔
    Paint blockPaint;
    //背景
    boolean[][] background;
    //方块
    Point[] blocks;
    int blockSize;

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化视图
        initView();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();

        //初始化数据
        initData();

        //初始化背景
        background = new boolean[10][20];
        //初始化方块
        blocks = new Point[]{new Point(3, 0), new Point(3, 1), new Point(4, 1), new Point(5, 1)};
        blockSize = xWidth / background.length;
        //绘制辅助线
        for (int x = 0; x < background.length; x++) {
            canvas.drawLine(x * blockSize, 0, x * blockSize, canvas.getHeight(), linePaint);
        }
        for (int y = 0; y < background[0].length; y++) {
            canvas.drawLine(0, y * blockSize, width, y * blockSize, linePaint);
        }
        for (Point block : blocks) {
            canvas.drawRect(
                    block.x * blockSize,
                    block.y * blockSize,
                    block.x * blockSize + blockSize,
                    block.y * blockSize + blockSize,
                    blockPaint
            );
        }
    }

    private void initData() {
        //设置游戏区域宽度 = 屏幕宽度*2/3
        xWidth = width * 2 / 3;
        //设置游戏区域的高度 = 宽度*2
        yHeight = xWidth * 2;

    }


    public void initView() {
        linePaint = new Paint();
        linePaint.setColor(0xff666666);
        linePaint.setAntiAlias(true);

        blockPaint = new Paint();
        blockPaint.setColor(0xff000000);
        blockPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
