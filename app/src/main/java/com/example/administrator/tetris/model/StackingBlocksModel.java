package com.example.administrator.tetris.model;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.administrator.tetris.Config;

public class StackingBlocksModel {
    private Paint mBitPaint;
    //背景
    public int[][] StackingBlocks;
    //方块图片
    private Bitmap blockBitmap;
    //图片区域，绘制位置
    private Rect mSrcRect;
    private Resources mResources;
    private BitmpModel bitmpModel = new BitmpModel();

    public StackingBlocksModel(Resources mResources) {
        this.mResources = mResources;
        //初始化堆积数组
        this.StackingBlocks = new int[Config.backgroundX][Config.backgroundY];
        blockBitmap = bitmpModel.changeBitmp(1, mResources);
        int bitWidth = blockBitmap.getWidth();
        int bitHeight = blockBitmap.getHeight();
        mSrcRect = new Rect(0, 0, bitWidth, bitHeight);
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
    }

    public void drawSrackingBlocks(Canvas canvas) {
        for (int x = 0; x < StackingBlocks.length; x++) {
            for (int y = 0; y < StackingBlocks[x].length; y++) {
                if (StackingBlocks[x][y] != 0) {
                    blockBitmap = bitmpModel.changeBitmp(StackingBlocks[x][y], mResources);
                    Rect mDestRect = new Rect(x * Config.blockSize + Config.frame,
                            y * Config.blockSize + Config.frame,
                            (x * Config.blockSize) + Config.blockSize + Config.frame,
                            (y * Config.blockSize) + Config.blockSize + Config.frame);
                    canvas.drawBitmap(blockBitmap, mSrcRect, mDestRect, mBitPaint);
                }
            }
        }
    }

    public void cleanStackingBlocks() {
        //清除堆积块
        for (int x = 0; x < StackingBlocks.length; x++) {
            for (int y = 0; y < StackingBlocks[0].length; y++) {
                StackingBlocks[x][y] = 0;
            }
        }
    }

    //消行
    public int cleanLine() {
        int line = 0;
        for (int y = 0; y < StackingBlocks[0].length; y++) {
            if (checkLine(y)) {
                deleteLine(y);
                line++;
            }
        }
        return line;
    }

    private void deleteLine(int dy) {
        for (int y = dy; y > 0; y--) {
            for (int x = 0; x < StackingBlocks.length; x++) {
                StackingBlocks[x][y] = StackingBlocks[x][y - 1];
            }
        }
    }

    //消行判断
    private boolean checkLine(int y) {
        for (int[] aBackground : StackingBlocks) {
            if (aBackground[y] == 0)
                return false;
        }
        return true;
    }

}
