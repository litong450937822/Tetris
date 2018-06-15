package com.example.administrator.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.administrator.tetris.view.TetrisView;


public class MainActivity extends AppCompatActivity {
    //父容器宽、高
    public int xWidth, yHeight;
    //游戏区域控件
    View gameView;

    //辅助线画笔
    Paint linePaint;
    //方块画笔
    Paint blockPaint;
    //背景
    boolean[][] background;
    //方块
    Point[] blocks;
    int blockSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化数据
        initData();
        //初始化视图
        initView();
        //初始化背景
        background = new boolean[10][20];
        //初始化方块
        blocks = new Point[]{new Point(3, 0), new Point(3, 1), new Point(4, 1), new Point(5, 1)};
        blockSize = xWidth / background.length;


    }

    private void initData() {
        //屏幕宽度
        int width = getScrennWidth(this);
        //设置游戏区域宽度 = 屏幕宽度*2/3
        xWidth = width * 2 / 3;
        //设置游戏区域的高度 = 宽度*2
        yHeight = xWidth * 2;

    }

    public void initView() {
        linePaint = new Paint();
        linePaint.setColor(0xff666666);
        linePaint.setAntiAlias(true);

        blockPaint = new Paint();
        blockPaint.setColor(0xff000000);
        blockPaint.setAntiAlias(true);
        //得到父容器
        FrameLayout layoutGame = findViewById(R.id.gameView);
        //实例化游戏区域
        gameView = new View(this) {
            //重写游戏区域绘制

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                //绘制方块
                for (Point block : blocks) {
                    canvas.drawRect(
                            block.x * blockSize,
                            block.y * blockSize,
                            block.x * blockSize + blockSize,
                            block.y * blockSize + blockSize,
                            blockPaint
                    );
                }
                //绘制辅助线
                for (int x = 0; x < background.length; x++) {
                    canvas.drawLine(x * blockSize, 0, x * blockSize, gameView.getHeight(), linePaint);
                }
                for (int y = 0; y < background[0].length; y++) {
                    canvas.drawLine(0, y * blockSize, gameView.getWidth(), y * blockSize, linePaint);
                }

            }
        };
        //设置游戏区域大小
        gameView.setLayoutParams(new FrameLayout.LayoutParams(xWidth, yHeight));
        gameView.setBackgroundColor(0x10000000);
        //添加到父容器
        layoutGame.addView(gameView);
    }

    //获得屏幕宽度
    public static int getScrennWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
