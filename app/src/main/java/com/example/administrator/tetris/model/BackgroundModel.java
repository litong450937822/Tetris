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
    //状态背景画笔
    private Paint stateBgPaint;
    //开始提示画笔
    private Paint startPaint;


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
        stateBgPaint = new Paint();
        stateBgPaint.setColor(0x90999999);
        stateBgPaint.setAntiAlias(true);
        startPaint = new Paint();
        startPaint.setColor(0xFFCCCCCC);
        startPaint.setAntiAlias(true);
        this.xWidth = xWidth;
        this.yHeight = yHeight;
    }

    //绘制辅助线
    public void drawLine(Canvas canvas) {
        for (int x = 0; x < Config.backgroundX + 1; x++) {
            canvas.drawLine(x * Config.blockSize + Config.frame, Config.frame,
                    x * Config.blockSize + Config.frame, yHeight, linePaint);
        }
        for (int y = 0; y < Config.backgroundY + 1; y++) {
            canvas.drawLine(Config.frame, y * Config.blockSize + Config.frame, xWidth + Config.frame * 3 / 4, y * Config.blockSize + Config.frame, linePaint);
        }
    }

    public void drawState(Canvas canvas, boolean isOver, boolean isStop) {
        //绘制结束状态
        if (isOver) {
            canvas.drawRect(Config.frame, Config.frame, Config.xWidth + Config.frame * 3 / 4, Config.yHeight, stateBgPaint);
            canvas.drawText("Game Over", xWidth / 2 - statePaint.measureText("Game Over") / 2, yHeight / 2, statePaint);
        }
        //绘制暂停状态
        if (isStop && !isOver) {
            canvas.drawRect(Config.frame, Config.frame, Config.xWidth + Config.frame * 3 / 4, Config.yHeight, stateBgPaint);
            canvas.drawText("Stop", xWidth / 2 - statePaint.measureText("Stop") / 2, yHeight / 2, statePaint);
        }
    }

    public void drawStartPrompt(Canvas canvas, int time) {
        if (time >= 0)
            return;
        canvas.drawRect(Config.frame, Config.frame, Config.xWidth + Config.frame * 3 / 4, Config.yHeight, startPaint);
        if (time < -2000)
            canvas.drawText("3", xWidth / 2 - statePaint.measureText("3") / 2, yHeight / 2, statePaint);
        if (time < -1000 && time >=-2000)
            canvas.drawText("2", xWidth / 2 - statePaint.measureText("2") / 2, yHeight / 2, statePaint);
        if (time >= -1000)
            canvas.drawText("1", xWidth / 2 - statePaint.measureText("1") / 2, yHeight / 2, statePaint);
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawRect(Config.frame, Config.frame, Config.xWidth + Config.frame * 3 / 4, Config.yHeight, backgroundPaint);
    }
}
