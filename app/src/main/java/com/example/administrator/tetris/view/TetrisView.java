package com.example.administrator.tetris.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.administrator.tetris.Config;
import com.example.administrator.tetris.R;
import com.example.administrator.tetris.control.GameControl;

/**
 * 此类目前没用
 */
public class TetrisView extends View {
    private GameControl gameControl;
    private TetrisView tetrisView = findViewById(R.id.gameView);
    private Button stop;



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
