package com.example.administrator.tetris.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.tetris.control.GameControl;

public class HoldView extends View {
    private GameControl gameControl;


    public HoldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(0xffeeeeee);
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameControl.drawHold(canvas);
    }
}

