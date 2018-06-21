package com.example.administrator.tetris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.tetris.control.GameControl;
import com.example.administrator.tetris.model.BackgroundModel;
import com.example.administrator.tetris.model.BlocksModel;
import com.example.administrator.tetris.model.StackingBlocksModel;
import com.example.administrator.tetris.view.NextView;
import com.example.administrator.tetris.view.TetrisView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TetrisView tetrisView;
    NextView nextView;
    TextView score;
    //游戏控制器
    GameControl gameControl;

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
                    ((TextView)findViewById(R.id.score)).setText(gameControl.scoreModel.score+"");
                    ((TextView)findViewById(R.id.maxScore)).setText(gameControl.scoreModel.bestScore+"");
//                    Log.e("score", ":" + gameControl.scoreModel.score);
                    break;
                case "stop":
                    ((Button)findViewById(R.id.stop)).setText("Stop");
                    break;
                case "continue":
                    ((Button)findViewById(R.id.stop)).setText("Continue");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        tetrisView = findViewById(R.id.gameView);
        nextView = findViewById(R.id.nextBlockView);
        Button left = findViewById(R.id.left);
        Button right = findViewById(R.id.right);
        Button down = findViewById(R.id.down);
        Button fallen = findViewById(R.id.fallen);
        Button rotate = findViewById(R.id.rotate);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        score = findViewById(R.id.score);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        down.setOnClickListener(this);
        fallen.setOnClickListener(this);
        rotate.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        Resources mResources = getResources();
        gameControl = new GameControl(handler, mResources, this);
        tetrisView.setGameControl(gameControl);
        nextView.setGameControl(gameControl);
    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        gameControl.onClick(view.getId());
        //重绘view
        tetrisView.invalidate();
        nextView.invalidate();
    }
}
