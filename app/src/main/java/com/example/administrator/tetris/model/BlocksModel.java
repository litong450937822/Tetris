package com.example.administrator.tetris.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.administrator.tetris.Config;

import java.util.Arrays;
import java.util.Random;

public class BlocksModel {
    //方块画笔
    private Paint mBitPaint;
    //方块投影画笔
    private Paint projectPaint;
    //当前方块
    public Point[] blocks;
    //下一个方块
    private Point[] nextBlocks;
    //投影方块
    private Point[] blocksProject;
    //当前方块类型
    public int blockType;
    //下一个方块类型
    private int nextBlockType;
    //方块图片
    private Bitmap blockBitmap;
    //图片区域，绘制位置
    private Rect mSrcRect;
    private Resources mResources;
    private BitmpModel bitmpModel = new BitmpModel();

    public BlocksModel(Resources mResources) {
        this.mResources = mResources;
        blocks = new Point[]{};
        blockBitmap = bitmpModel.changeBitmp(1, mResources);
        int bitWidth = blockBitmap.getWidth();
        int bitHeight = blockBitmap.getHeight();
        mSrcRect = new Rect(0, 0, bitWidth, bitHeight);
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        //开启抗锯齿
        mBitPaint.setAntiAlias(true);
        //开启防抖动
        mBitPaint.setDither(true);
        projectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        projectPaint.setFilterBitmap(true);
        //开启抗锯齿
        projectPaint.setAntiAlias(true);
        //开启防抖动
        projectPaint.setDither(true);
        projectPaint.setAlpha(100);
    }

    //生成新的方块儿
    public void newBlock(StackingBlocksModel stackingBlocksModel) {
        cleanBlocksProject();
        if (nextBlocks == null)
            //生成下一块
            newNextBlocks();
        //把下一块赋给当前块
        blockType = nextBlockType;
        blocksData(blockType);
        setBlocksProjection(stackingBlocksModel);
        //生成下一块
        newNextBlocks();
    }

    public void cleanBlocksProject() {
        blocksProject = new Point[]{};
    }

    //当前块投影
    private void setBlocksProjection(StackingBlocksModel stackingBlocksModel) {
        while (blocksProject(stackingBlocksModel)) {
            for (Point block : blocksProject) {
                block.y++;
            }
        }
    }

    private Boolean blocksProject(StackingBlocksModel stackingBlocksModel) {
        //预移动
        for (Point block : blocksProject) {
            if (checkBoundary(block.x, block.y + 1, stackingBlocksModel))
                return false;
        }
        return true;
    }

    private void blocksData(int blockType) {
        switch (blockType) {
            // 长条
            case 1:
                blocks = new Point[]{
                        new Point(5, 0),
                        new Point(3, 0),
                        new Point(4, 0),
                        new Point(6, 0)};
                blocksProject = new Point[]{
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
                blocksProject = new Point[]{
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
                blocksProject = new Point[]{
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
                blocksProject = new Point[]{
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
                blocksProject = new Point[]{
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
                blocksProject = new Point[]{
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
                blocksProject = new Point[]{
                        new Point(5, 1),
                        new Point(5, 0),
                        new Point(6, 0),
                        new Point(4, 1)};
                break;
        }
    }

    //生成下一块
    private void newNextBlocks() {
        Random random = new Random();
        nextBlockType = random.nextInt(7) + 1;
        switch (nextBlockType) {
            // 长条
            case 1:
                nextBlocks = new Point[]{
                        new Point(0, 1),
                        new Point(1, 1),
                        new Point(2, 1),
                        new Point(3, 1)};
                break;
            // 田
            case 2:
                nextBlocks = new Point[]{
                        new Point(1, 1),
                        new Point(2, 1),
                        new Point(1, 2),
                        new Point(2, 2)};
                break;
            //L
            case 3:
                nextBlocks = new Point[]{
                        new Point(2, 1),
                        new Point(0, 2),
                        new Point(1, 2),
                        new Point(2, 2)};
                break;
            //反L
            case 4:
                nextBlocks = new Point[]{
                        new Point(0, 1),
                        new Point(0, 2),
                        new Point(1, 2),
                        new Point(2, 2)};
                break;

            //T
            case 5:
                nextBlocks = new Point[]{
                        new Point(1, 1),
                        new Point(0, 2),
                        new Point(1, 2),
                        new Point(2, 2)};
                break;
            //Z
            case 6:
                nextBlocks = new Point[]{
                        new Point(0, 1),
                        new Point(1, 1),
                        new Point(1, 2),
                        new Point(2, 2)};
                break;
            //反Z
            case 7:
                nextBlocks = new Point[]{
                        new Point(1, 1),
                        new Point(2, 1),
                        new Point(0, 2),
                        new Point(1, 2)};
                break;
        }
    }

    //绘制当前方块
    public void drawBlocks(Canvas canvas) {
        //绘制方块
        blockBitmap = bitmpModel.changeBitmp(blockType, mResources);
        for (Point block : blocks) {
            Rect mDestRect = new Rect(block.x * Config.blockSize + Config.frame,
                    block.y * Config.blockSize + Config.frame,
                    (block.x * Config.blockSize) + Config.blockSize + Config.frame,
                    (block.y * Config.blockSize) + Config.blockSize + Config.frame);
            canvas.drawBitmap(blockBitmap, mSrcRect, mDestRect, mBitPaint);
        }
    }

    //绘制下一个方块
    public void drawNextBlocks(Canvas canvas) {
        if (nextBlocks == null)
            return;
        //绘制方块
        blockBitmap = bitmpModel.changeBitmp(nextBlockType, mResources);
        for (Point block : nextBlocks) {
            Rect mDestRect = new Rect(block.x * Config.blockSize + Config.blockSize / 2, block.y * Config.blockSize,
                    (block.x * Config.blockSize) + Config.blockSize + Config.blockSize / 2,
                    (block.y * Config.blockSize) + Config.blockSize);
            canvas.drawBitmap(blockBitmap, mSrcRect, mDestRect, mBitPaint);
        }
    }

    //绘制当前方块投影
    public void drawBlocksProject(Canvas canvas) {
        if (blocksProject == null)
            return;
        //绘制方块
        blockBitmap = bitmpModel.changeBitmp(blockType, mResources);
        for (Point block : blocksProject) {
            Rect mDestRect = new Rect(block.x * Config.blockSize + Config.frame,
                    block.y * Config.blockSize + Config.frame,
                    (block.x * Config.blockSize) + Config.blockSize + Config.frame,
                    (block.y * Config.blockSize) + Config.blockSize + Config.frame);
            canvas.drawBitmap(blockBitmap, mSrcRect, mDestRect, projectPaint);
        }
    }

    //移动
    public boolean move(int x, int y, boolean isStop, StackingBlocksModel stackingBlocksModel) {
        if (isStop)
            return false;
        //遍历当前方块儿数组 每一块儿加上偏移量
        for (Point block : blocks) {
            if (checkBoundary(block.x + x, block.y + y, stackingBlocksModel))
                return false;
        }
        for (int i = 0; i < blocks.length; i++) {
            Point block = blocks[i];
            Point project = blocksProject[i];
            block.x += x;
            block.y += y;
            project.x = block.x;
            project.y = block.y;
        }
        setBlocksProjection(stackingBlocksModel);
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
        //旋转后坐标存入blocks数组
        for (int i = 0; i < blocks.length; i++) {
            Point block = blocks[i];
            int checkX = -block.y + blocks[0].y + blocks[0].x;
            int checkY = block.x - blocks[0].x + blocks[0].y;
            block.x = checkX;
            block.y = checkY;
            blocksProject[i].x = checkX;
            blocksProject[i].y = checkY;
        }

        setBlocksProjection(stackingBlocksModel);
        return true;
    }

    //边界判断
    private boolean checkBoundary(int x, int y, StackingBlocksModel stackingBlocksModel) {
        return (x < 0 || y < 0 || x >= Config.backgroundX || y >= Config.backgroundY
                || stackingBlocksModel.StackingBlocks[x][y] != 0);
    }
}
