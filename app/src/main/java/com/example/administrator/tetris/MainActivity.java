package com.example.administrator.tetris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button stop;
    //    TetrisView tetrisView;
    //父容器宽、高
    public int xWidth, yHeight;
    //游戏区域控件
    View gameView;
    //辅助线画笔
    Paint linePaint;
    //方块画笔
    Paint mBitPaint;
    //状态画笔
    Paint statePaint;
    //背景
    int[][] StackingBlocks;
    //方块
    Point[] blocks;
    int blockSize;
    int blockType;
    //方块图片
    Bitmap blockBitmap;
    //图片区域，绘制位置
    Rect mSrcRect, mDestRect;
    //自动下落线程
    public Thread downThread;
    //
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            gameView.invalidate();
        }
    };
    //暂停状态
    public boolean isStop;
    public boolean isOver;

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
        stop = findViewById(R.id.stop);
        //初始化数据
        initData();
        //初始化视图
        initView();
        //初始化背景
        StackingBlocks = new int[10][20];
        //初始化方块大小 = 游戏区域宽度/10
        blockSize = xWidth / StackingBlocks.length;
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
        blocks = new Point[]{};
        changeBitmp(1);
        int bitWidth = blockBitmap.getWidth();
        int bitHeight = blockBitmap.getHeight();
        mSrcRect = new Rect(0, 0, bitWidth, bitHeight);
    }

    //生成新的方块儿
    public void newBlock() {
        //随机生成新的方块儿
        Random random = new Random();
        blockType = random.nextInt(7) + 1;
        switch (blockType) {
            // 长条
            case 1:
                blocks = new Point[]{
                        new Point(5, 0),
                        new Point(3, 0),
                        new Point(4, 0),
                        new Point(6, 0)};
                break;
            // 田
            case 2:
                blocks = new Point[]{
                        new Point(3, 0),
                        new Point(3, 1),
                        new Point(4, 0),
                        new Point(4, 1)};
                break;
            //L
            case 3:
                blocks = new Point[]{
                        new Point(4, 1),
                        new Point(5, 0),
                        new Point(5, 1),
                        new Point(3, 1)};
                break;
            //反L
            case 4:
                blocks = new Point[]{
                        new Point(4, 1),
                        new Point(3, 0),
                        new Point(3, 1),
                        new Point(5, 1)};
                break;
            //T
            case 5:
                blocks = new Point[]{
                        new Point(4, 1),
                        new Point(3, 1),
                        new Point(5, 1),
                        new Point(4, 0)};
                break;
            //Z
            case 6:
                blocks = new Point[]{
                        new Point(4, 1),
                        new Point(3, 0),
                        new Point(4, 0),
                        new Point(5, 1)};
                break;
            //反Z
            case 7:
                blocks = new Point[]{
                        new Point(5, 1),
                        new Point(5, 0),
                        new Point(6, 0),
                        new Point(4, 1)};
                break;
        }
        changeBitmp(blockType);
    }

    public void changeBitmp(int blockType) {
        Resources mResources = getResources();
        switch (blockType) {
            case 1:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.blue_block)).getBitmap();
                break;
            case 2:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.yellow_block)).getBitmap();
                break;
            case 3:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.green_block)).getBitmap();
                break;
            case 4:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.pink_block)).getBitmap();
                break;
            case 5:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.red_block)).getBitmap();
                break;
            case 6:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
                break;
            case 7:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.purple_block)).getBitmap();
                break;


        }
    }

    public void initView() {
        linePaint = new Paint();
        linePaint.setColor(0xff666666);
        linePaint.setAntiAlias(true);

        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        statePaint = new Paint();
        statePaint.setColor(Color.rgb(255, 0, 0));
        statePaint.setTextSize(100);
        statePaint.setAntiAlias(true);
        //得到父容器
        FrameLayout layoutGame = findViewById(R.id.gameView);
        //实例化游戏区域
        gameView = new View(this) {
            //重写游戏区域绘制

            @SuppressLint("DrawAllocation")
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                //绘制堆积方块儿
                for (int x = 0; x < StackingBlocks.length; x++) {
                    for (int y = 0; y < StackingBlocks[x].length; y++) {
//                        if (StackingBlocks[x][y] != 0) {
                        if (StackingBlocks[x][y] != 0) {
                            changeBitmp(StackingBlocks[x][y]);
                            mDestRect = new Rect(x * blockSize, y * blockSize,
                                    (x * blockSize) + blockSize,
                                    (y * blockSize) + blockSize);
                            canvas.drawBitmap(blockBitmap, mSrcRect, mDestRect, mBitPaint);
                        }
                    }
                }
                //绘制方块
                changeBitmp(blockType);
                for (Point block : blocks) {
                    mDestRect = new Rect(block.x * blockSize, block.y * blockSize,
                            (block.x * blockSize) + blockSize,
                            (block.y * blockSize) + blockSize);
                    canvas.drawBitmap(blockBitmap, mSrcRect, mDestRect, mBitPaint);
                }
                //绘制辅助线
                for (int x = 0; x < StackingBlocks.length; x++) {
                    canvas.drawLine(x * blockSize, 0, x * blockSize, gameView.getHeight(), linePaint);
                }
                for (int y = 0; y < StackingBlocks[0].length; y++) {
                    canvas.drawLine(0, y * blockSize, gameView.getWidth(), y * blockSize, linePaint);
                }
                //绘制结束状态
                if (isOver) {
                    canvas.drawText("Game Over", gameView.getWidth() / 2 - statePaint.measureText("Game Over") / 2, gameView.getHeight() / 2, statePaint);
                }
                //绘制暂停状态
                if (isStop && !isOver) {
                    canvas.drawText("Stop", gameView.getWidth() / 2 - statePaint.measureText("Stop") / 2, gameView.getHeight() / 2, statePaint);
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

    @SuppressLint({"WrongConstant", "SetTextI18n"})
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
                drop();
                break;
            case R.id.fallen:
                fallen();
                break;
            case R.id.rotate:
                rotate();
                break;
            case R.id.stop:
                setStop();

                break;
            case R.id.start:
                startGame();
                break;
        }
        //重绘view
        gameView.invalidate();
    }

    //设置暂停状态
    @SuppressLint("SetTextI18n")
    private void setStop() {
        if (isStop) {
            isStop = false;
            stop.setText("Stop");
        } else {
            isStop = true;
            stop.setText("Continue");
        }
    }

    //开始游戏
    @SuppressLint("SetTextI18n")
    private void startGame() {
        stop.setText("Stop");
        if (downThread == null) {
            downThread = new Thread() {
                public void run() {
                    super.run();
                    while (true) {
                        try {
                            //休眠500毫秒
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //判断是否处于结束状态
                        //判断是否处于暂停状态
                        if (isOver || isStop)
                            continue;
                        //休眠完毕执行一次下落
                        drop();
                        //通知主线程刷新view
                        handler.sendEmptyMessage(0);
                    }
                }
            };
            downThread.start();
        }
        //清除地图
        for (int x = 0; x < StackingBlocks.length; x++) {
            for (int y = 0; y < StackingBlocks[0].length; y++) {
                StackingBlocks[x][y] = 0;
            }
        }
        isOver = false;
        isStop = false;
        //生成新方块
        newBlock();

    }

    //快速下降
    public boolean fallen() {
        while (true) {
            if (!(drop())) break;

        }
        return false;
    }

    //下降
    public boolean drop() {
        if (isStop || isOver)
            return false;
        if (move(0, 1))
            return true;
        //移动失败 堆积处理
        for (Point block : blocks) {
            StackingBlocks[block.x][block.y] = blockType;
        }
        //堆积完成 消行判断
        cleanLine();
        //生成新的方块儿
        newBlock();
        isOver = checkOver();
        return false;
    }

    //结束判断
    public boolean checkOver() {
        for (Point block : blocks) {
            if (StackingBlocks[block.x][block.y] != 0)
                return true;
        }
        return false;
    }

    //消行
    public void cleanLine() {
        for (int y = 0; y < StackingBlocks[0].length; y++) {
            if (checkLine(y))
                deleteLine(y);
        }
    }

    private void deleteLine(int dy) {
        for (int y = dy; y > 0; y--) {
            for (int x = 0; x < StackingBlocks.length; x++) {
                StackingBlocks[x][y] = StackingBlocks[x][y - 1];
            }
        }
    }

    //消行判断
    public boolean checkLine(int y) {
        for (int[] aBackground : StackingBlocks) {
            if (aBackground[y] == 0)
//            if (!aBackground[y])
                return false;
        }
        return true;
    }

    //移动
    public boolean move(int x, int y) {
        if (isStop)
            return false;
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
        if (isStop)
            return false;
        if (blockType == 2)
            return false;
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
        return (x < 0 || y < 0 || x >= StackingBlocks.length || y >= StackingBlocks[0].length
                || StackingBlocks[x][y] != 0);
    }
}
