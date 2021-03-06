package com.example.administrator.tetris;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.tetris.control.GameControl;
import com.example.administrator.tetris.utils.TimeUtil;
import com.example.administrator.tetris.view.HoldView;
import com.example.administrator.tetris.view.NextView;
import com.example.administrator.tetris.view.TetrisView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TetrisView tetrisView;
    NextView nextView;
    HoldView holdView;
    TextView score;
    //游戏控制器
    GameControl gameControl;
    TimeUtil timeUtil;

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
                    nextView.invalidate();
                    ((TextView)findViewById(R.id.score)).setText(gameControl.scoreModel.score+"");
                    ((TextView)findViewById(R.id.maxScore)).setText(gameControl.scoreModel.bestScore+"");
                    ((TextView)findViewById(R.id.gameTime)).setText(timeUtil.stringForTime(gameControl.time));
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

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        tetrisView = findViewById(R.id.gameView);
        nextView = findViewById(R.id.nextBlockView);
        holdView = findViewById(R.id.holdBlockView);
        Button left = findViewById(R.id.left);
        Button right = findViewById(R.id.right);
        Button down = findViewById(R.id.down);
        Button fallen = findViewById(R.id.fallen);
        Button rotate = findViewById(R.id.rotate);
        Button hold = findViewById(R.id.hold);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        score = findViewById(R.id.score);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        down.setOnClickListener(this);
        fallen.setOnClickListener(this);
        rotate.setOnClickListener(this);
        hold.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        Resources mResources = getResources();
        gameControl = new GameControl(handler, mResources, this);
        tetrisView.setGameControl(gameControl);
        nextView.setGameControl(gameControl);
        holdView.setGameControl(gameControl);
        timeUtil = new TimeUtil();
        ((TextView)findViewById(R.id.maxScore)).setText(gameControl.scoreModel.bestScore+"");
    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        gameControl.onClick(view.getId());
        //重绘view
        tetrisView.invalidate();
        nextView.invalidate();
        holdView.invalidate();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
