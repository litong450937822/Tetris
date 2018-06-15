package com.example.administrator.tetris.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class TetrisView extends View {
    //父容器宽、高
    public int xWidth, yHeight;
    //屏幕宽度
    private int width;
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

    public TetrisView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化数据
        initData();
        //初始化视图
        initView();
        //初始化背景
        background = new boolean[10][20];
        //初始化方块
        blocks = new Point[]{new Point(3, 0),new Point(3, 1),new Point(4, 1),new Point(5, 1)};
        blockSize = xWidth / background.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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

    private void initData() {
        //设置游戏区域宽度 = 屏幕宽度*2/3
        xWidth = width * 2 / 3;
        //设置游戏区域的高度 = 宽度*2
        yHeight = xWidth * 2;

    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void initView() {
        linePaint = new Paint();
        linePaint.setColor(0xff666666);
        linePaint.setAntiAlias(true);

        blockPaint = new Paint();
        blockPaint.setColor(0xff000000);
        blockPaint.setAntiAlias(true);
        //设置游戏区域大小
        gameView.setLayoutParams(new FrameLayout.LayoutParams(xWidth, yHeight));
        gameView.setBackgroundColor(0x10000000);
    }


}
