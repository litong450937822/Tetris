package com.example.administrator.tetris.control;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.administrator.tetris.Config;
import com.example.administrator.tetris.R;
import com.example.administrator.tetris.model.BackgroundModel;
import com.example.administrator.tetris.model.BlocksModel;
import com.example.administrator.tetris.model.ScoreModel;
import com.example.administrator.tetris.model.StackingBlocksModel;
import com.example.administrator.tetris.utils.TimeUtil;

import java.util.Date;
import java.util.logging.SimpleFormatter;


public class GameControl {
    private Resources mResources;
    private Handler handler;
    //背景模型
    private BackgroundModel backgroundModel;
    //堆积块模型
    private StackingBlocksModel stackingBlocksModel;
    //当前块模型
    private BlocksModel blocksModel;
    //分数模型
    public ScoreModel scoreModel;
    private TimeUtil timeUtil;
    //暂停状态
    private boolean isStop;
    private boolean isOver;
    //自动下落线程
    private Thread downThread;
    public int time;

    public GameControl(android.os.Handler handler, Resources resources, Context context) {
        this.handler = handler;
        this.mResources = resources;
        initData(context);
    }


    //设置暂停状态
    @SuppressLint("SetTextI18n")
    private void setStop() {
        if (isStop) {
            isStop = false;
            Message msg = new Message();
            msg.obj = "stop";
            handler.sendMessage(msg);
        } else {
            isStop = true;
            Message msg = new Message();
            msg.obj = "continue";
            handler.sendMessage(msg);
        }
    }

    private void initData(Context context) {
        //屏幕宽度
        int width = getScrennWidth(context);
        //设置游戏区域宽度 = 屏幕宽度*2/3
        Config.xWidth = width * 2 / 3;
        //设置游戏区域的高度 = 宽度*2
        Config.yHeight = Config.xWidth * 2;
        Config.width = width - Config.xWidth;
        //初始化方块大小 = 游戏区域宽度/10
        Config.blockSize = Config.xWidth / Config.backgroundX;
        //实例化当前块模型
        blocksModel = new BlocksModel(mResources);
        //实例化背景模型
        backgroundModel = new BackgroundModel(Config.xWidth, Config.yHeight);
        //实例化堆积块模型
        stackingBlocksModel = new StackingBlocksModel(mResources);
        //实例化分数模型
        scoreModel = new ScoreModel();


    }

    //开始游戏
    @SuppressLint("SetTextI18n")
    private void startGame() {
        Message msg = new Message();
        msg.obj = "stop";
        time = 0;
        handler.sendMessage(msg);
        scoreModel.cleanScore();
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
                        time += 500;
                        Message msg = new Message();
                        msg.obj = "invalidate";
                        handler.sendMessage(msg);
                    }
                }
            };
            downThread.start();
        }
        //清除堆积块
        stackingBlocksModel.cleanStackingBlocks();
        //清除游戏状态
        isOver = false;
        isStop = false;
        //生成新方块
        blocksModel.newBlock();

    }


    //快速下降
    public boolean fallen() {
        while (true) {
            if (!(drop())) break;

        }
        return false;
    }

    //下降
    private boolean drop() {
        if (isStop || isOver)
            return false;
        if (blocksModel.move(0, 1, false, this.stackingBlocksModel))
            return true;
        //移动失败 堆积处理
        for (Point block : blocksModel.blocks) {
            this.stackingBlocksModel.StackingBlocks[block.x][block.y] = blocksModel.blockType;
        }
        //堆积完成 消行判断
        int line = this.stackingBlocksModel.cleanLine();
        scoreModel.addScore(line);
        //生成新的方块儿
        blocksModel.newBlock();
        isOver = checkOver();
        return false;
    }

    //结束判断
    private boolean checkOver() {
        for (Point block : blocksModel.blocks) {
            if (stackingBlocksModel.StackingBlocks[block.x][block.y] != 0)
                return true;
        }
        return false;
    }

    private static int getScrennWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    //绘制
    public void draw(Canvas canvas) {
        //绘制堆积方块儿
        stackingBlocksModel.drawSrackingBlocks(canvas);
        //绘制方块
        blocksModel.drawBlocks(canvas);
        //绘制辅助线
        backgroundModel.drawLine(canvas);
        //绘制游戏状态
        backgroundModel.drawState(canvas, isOver, isStop);
        //绘制背景
        backgroundModel.drawBackground(canvas);
    }

    public void drawNext(Canvas canvas) {
        blocksModel.drawNextBlocks(canvas);
    }

    public void onClick(int id) {
        switch (id) {
            case R.id.left:
                blocksModel.move(-1, 0, isStop, stackingBlocksModel);
                break;
            case R.id.right:
                blocksModel.move(1, 0, isStop, stackingBlocksModel);
                break;
            case R.id.down:
                drop();
                break;
            case R.id.fallen:
                fallen();
                break;
            case R.id.rotate:
                blocksModel.rotate(isStop, stackingBlocksModel);
                break;
            case R.id.stop:
                setStop();

                break;
            case R.id.start:
                startGame();
                break;
        }
    }
}
