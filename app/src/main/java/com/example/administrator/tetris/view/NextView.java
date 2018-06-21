package com.example.administrator.tetris.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.tetris.Config;

public class NextView extends View {
    private Paint linePaint;


    public NextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(0x10000000);
        linePaint = new Paint();
        linePaint.setColor(0xff666666);
        linePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int x = 0; x < 5; x++) {
            canvas.drawLine(x * Config.blockSize + Config.blockSize / 2, 0, x * Config.blockSize + Config.blockSize / 2, 1000, linePaint);
        }
        for (int y = 0; y < 4; y++) {
            canvas.drawLine(0, y * Config.blockSize, Config.width, y * Config.blockSize, linePaint);
        }
//        gameControl.drawNext();
    }
}
