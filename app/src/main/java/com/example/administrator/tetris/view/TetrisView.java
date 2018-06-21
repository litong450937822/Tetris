package com.example.administrator.tetris.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.example.administrator.tetris.control.GameControl;

/**
 * 此类目前没用
 */
public class TetrisView extends View {
    private GameControl gameControl;

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        this.setLayoutParams(new WindowManager.LayoutParams(Config.xWidth, Config.yHeight));
//        this.setBackgroundColor(0x10000000);
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameControl.draw(canvas);

    }
}
