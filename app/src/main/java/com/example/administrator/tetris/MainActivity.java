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

import com.example.administrator.tetris.control.GameControl;
import com.example.administrator.tetris.model.BackgroundModel;
import com.example.administrator.tetris.model.BlocksModel;
import com.example.administrator.tetris.model.StackingBlocksModel;
import com.example.administrator.tetris.view.NextView;
import com.example.administrator.tetris.view.TetrisView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TetrisView tetrisView;
    NextView nextView;
    //游戏控制器
    GameControl gameControl;

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
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        down.setOnClickListener(this);
        fallen.setOnClickListener(this);
        rotate.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        tetrisView.gameControl.onClick(view.getId());
        //重绘view
        tetrisView.invalidate();
        nextView.invalidate();
    }
}
