package com.example.administrator.tetris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.administrator.tetris.view.TetrisView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //    TetrisView tetrisView;
    //父容器宽、高
    public int xWidth, yHeight;
    //游戏区域控件
    View gameView;
    //辅助线画笔
    Paint linePaint;
    //方块画笔
    Paint blockPaint;
    //背景
    int[][] background;
    //方块
    Point[] blocks;
    int blockSize;
    Bitmap block_brown,block_blue,block_green,block_pink,block_purple,block_red,block_yellow;
    private Resources mResources;
    private Paint mBitPaint;
    private Rect mSrcRect, mDestRect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button left = findViewById(R.id.left);
        Button right = findViewById(R.id.right);
        Button down = findViewById(R.id.down);
        Button fallen = findViewById(R.id.fallen);
        Button rotate = findViewById(R.id.rotate);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        //初始化数据
        initData();
        //初始化视图
        initView();
        //初始化背景
        background = new int[10][20];
        //初始化方块
        blockSize = xWidth / background.length;
        blocks = new Point[]{new Point(4, 1), new Point(3, 0), new Point(3, 1), new Point(5, 1)};
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        down.setOnClickListener(this);
        fallen.setOnClickListener(this);
        rotate.setOnClickListener(this);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);

    }

    private void initData() {
        //屏幕宽度
        int width = getScrennWidth(this);
        //设置游戏区域宽度 = 屏幕宽度*2/3
        xWidth = width * 2 / 3;
        //设置游戏区域的高度 = 宽度*2
        yHeight = xWidth * 2;
        mResources = getResources();

        block_brown = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
        block_blue = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
        block_green = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
        block_pink = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
        block_purple = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
        block_red = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
        block_yellow = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
        int bitWidth = block_brown.getWidth();
        int bitHeight = block_brown.getHeight();
        mSrcRect = new Rect(0, 0, bitWidth, bitHeight);


    }

    public void initView() {
        linePaint = new Paint();
        linePaint.setColor(0xff666666);
        linePaint.setAntiAlias(true);

        blockPaint = new Paint();
        blockPaint.setColor(0xff000000);
        blockPaint.setAntiAlias(true);

        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
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
                    mDestRect = new Rect(block.x * blockSize, block.y * blockSize,
                            block.x * blockSize + blockSize,
                            block.y * blockSize + blockSize);
                    canvas.drawBitmap(block_brown,mSrcRect,mDestRect,mBitPaint);
//                    canvas.drawRect(
//                            block.x * blockSize,
//                            block.y * blockSize,
//                            block.x * blockSize + blockSize,
//                            block.y * blockSize + blockSize,
//                            blockPaint
//                    );
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

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        int vid = view.getId();
        switch (vid) {
            case R.id.left:
                move(-1, 0);
                break;
            case R.id.right:
                move(1, 0);
                break;
            case R.id.down:
                move(0, 1);
                break;
            case R.id.fallen:
                break;
            case R.id.rotate:
                rotate();
                break;
            case R.id.stop:
                break;
            case R.id.start:
                break;
        }
        //重绘view
        gameView.invalidate();
    }

    //移动
    public boolean move(int x, int y) {
        //遍历当前方块儿数组 每一块儿加上偏移量
        for (Point block : blocks) {
            if (checkBoundary(block.x + x, block.y + y))
                return false;
        }
        for (Point block : blocks) {
            block.x += x;
            block.y += y;
        }
        return true;
    }

    //旋转
    public boolean rotate() {
        for (Point block : blocks) {
            //笛卡尔公式 顺时针旋转90°
            int checkX = -block.y + blocks[0].y + blocks[0].x;
            int checkY = block.x - blocks[0].x + blocks[0].y;
            if (checkBoundary(checkX, checkY)) {
                return false;
            }
        }
        for (Point block : blocks) {
            int checkX = -block.y + blocks[0].y + blocks[0].x;
            int checkY = block.x - blocks[0].x + blocks[0].y;
            block.x = checkX;
            block.y = checkY;
        }
        return true;
    }

    //边界判断
    public boolean checkBoundary(int x, int y) {
        return (x < 0 || y < 0 || x >= background.length || y >= background[0].length);
    }
}
