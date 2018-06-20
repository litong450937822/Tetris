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
    public GameControl gameControl;
    private TetrisView tetrisView = findViewById(R.id.gameView);
    private Button stop;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        public void handleMessage(android.os.Message msg) {
            String type = (String) msg.obj;
            if (type == null) {
                return;
            }
            switch (type) {
                case "invalidate":
                    //刷新重绘view
                    tetrisView.invalidate();
                    break;
                case "stop":
//                    stop.setText("Stop");
                    break;
                case "continue":
//                    stop.setText("Continue");
                    break;
            }
        }
    };

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setLayoutParams(new WindowManager.LayoutParams(Config.xWidth, Config.yHeight));
        this.setBackgroundColor(0x10000000);
        Resources mResources = getResources();
        gameControl = new GameControl(handler, mResources, context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameControl.draw(canvas);

    }
}
