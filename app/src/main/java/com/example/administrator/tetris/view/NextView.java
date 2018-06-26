package com.example.administrator.tetris.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.tetris.Config;
import com.example.administrator.tetris.control.GameControl;

public class NextView extends View {
    private GameControl gameControl;
    private Paint startPaint;


    public NextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(0xffeeeeee);
        startPaint = new Paint();
        startPaint.setColor(0xFFCCCCCC);
        startPaint.setAntiAlias(true);
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameControl.drawNext(canvas);
        if (gameControl.time < 0) {
            canvas.drawRect(0,0, this.getWidth() , this.getHeight(), startPaint);
        }
    }
}
