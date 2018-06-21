package com.example.administrator.tetris.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.administrator.tetris.Config;

public class BackgroundModel {
    private int xWidth, yHeight;
    //辅助线画笔
    private Paint linePaint;
    //状态画笔
    private Paint statePaint;
    //背景色画笔
    private Paint backgroundPaint;


    public BackgroundModel(int xWidth, int yHeight) {
        linePaint = new Paint();
        linePaint.setColor(0xff666666);
        linePaint.setAntiAlias(true);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(0x10000000);
        backgroundPaint.setAntiAlias(true);
        statePaint = new Paint();
        statePaint.setColor(Color.rgb(255, 0, 0));
        statePaint.setTextSize(100);
        statePaint.setAntiAlias(true);
        this.xWidth = xWidth;
        this.yHeight = yHeight;
    }

    //绘制辅助线
    public void drawLine(Canvas canvas) {
        for (int x = 0; x < Config.backgroundX; x++) {
            canvas.drawLine(x * Config.blockSize, 0, x * Config.blockSize, yHeight, linePaint);
        }
        for (int y = 0; y < Config.backgroundY; y++) {
            canvas.drawLine(0, y * Config.blockSize, xWidth, y * Config.blockSize, linePaint);
        }
    }

    public void drawState(Canvas canvas, boolean isOver, boolean isStop) {
        //绘制结束状态
        if (isOver) {
            canvas.drawText("Game Over", xWidth / 2 - statePaint.measureText("Game Over") / 2, yHeight / 2, statePaint);
        }
        //绘制暂停状态
        if (isStop && !isOver) {
            canvas.drawText("Stop", xWidth / 2 - statePaint.measureText("Stop") / 2, yHeight / 2, statePaint);
        }
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawRect(0,0,Config.xWidth,Config.yHeight,backgroundPaint);
    }
}
