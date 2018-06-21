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
        statePaint.setColor(Color.rgb(0, 0, 0));
        statePaint.setTextSize(100);
        statePaint.setAntiAlias(true);
        this.xWidth = xWidth;
        this.yHeight = yHeight;
    }

    //绘制辅助线
    public void drawLine(Canvas canvas) {
        for (int x = 0; x < Config.backgroundX+1; x++) {
            canvas.drawLine(x * Config.blockSize+Config.frame, Config.frame,
                    x * Config.blockSize+Config.frame, yHeight, linePaint);
        }
        for (int y = 0; y < Config.backgroundY+1; y++) {
            canvas.drawLine(Config.frame, y * Config.blockSize+Config.frame, xWidth+Config.frame*3/4, y * Config.blockSize+Config.frame, linePaint);
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
        canvas.drawRect(Config.frame,Config.frame,Config.xWidth+Config.frame*3/4,Config.yHeight,backgroundPaint);
    }
}
