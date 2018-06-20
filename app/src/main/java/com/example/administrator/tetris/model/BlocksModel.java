package com.example.administrator.tetris.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.administrator.tetris.Config;

import java.util.Random;

public class BlocksModel {
    //方块画笔
    private Paint mBitPaint;
    //方块
    public Point[] blocks;
    public int blockType;
    private int blockSize;
    //方块图片
    private Bitmap blockBitmap;
    //图片区域，绘制位置
    private Rect mSrcRect;
    private Resources mResources;
    private BitmpModel bitmpModel = new BitmpModel();

    public BlocksModel(int blockSize,Resources mResources) {
        this.mResources = mResources;
        blocks = new Point[]{};
        blockBitmap = bitmpModel.changeBitmp(1,mResources);
        int bitWidth = blockBitmap.getWidth();
        int bitHeight = blockBitmap.getHeight();
        mSrcRect = new Rect(0, 0, bitWidth, bitHeight);
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        this.blockSize = blockSize;
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
        bitmpModel.changeBitmp(blockType,mResources);
    }



    public void drawBlocks(Canvas canvas) {
        //绘制方块
        blockBitmap = bitmpModel.changeBitmp(blockType,mResources);
        for (Point block : blocks) {
            Rect mDestRect = new Rect(block.x * blockSize, block.y * blockSize,
                    (block.x * blockSize) + blockSize,
                    (block.y * blockSize) + blockSize);
            canvas.drawBitmap(blockBitmap, mSrcRect, mDestRect, mBitPaint);
        }
    }



    //移动
    public boolean move(int x, int y, boolean isStop, StackingBlocksModel stackingBlocksModel) {
        if (isStop)
            return false;
        //遍历当前方块儿数组 每一块儿加上偏移量
        for (Point block : blocks) {
            if (checkBoundary(block.x + x, block.y + y,stackingBlocksModel))
                return false;
        }
        for (Point block : blocks) {
            block.x += x;
            block.y += y;
        }
        return true;
    }

    //旋转
    public boolean rotate(boolean isStop, StackingBlocksModel stackingBlocksModel) {
        if (isStop)
            return false;
        if (blockType == 2)
            return false;
        for (Point block : blocks) {
            //笛卡尔公式 顺时针旋转90°
            int checkX = -block.y + blocks[0].y + blocks[0].x;
            int checkY = block.x - blocks[0].x + blocks[0].y;
            if (checkBoundary(checkX, checkY, stackingBlocksModel)) {
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
    private boolean checkBoundary(int x, int y, StackingBlocksModel stackingBlocksModel) {
        return (x < 0 || y < 0 || x >= Config.backgroundX || y >= Config.backgroundY
                ||stackingBlocksModel.StackingBlocks[x][y] != 0);
    }
}