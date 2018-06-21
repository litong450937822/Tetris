package com.example.administrator.tetris.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.example.administrator.tetris.R;

public class BitmpModel {
    //方块图片
    private Bitmap blockBitmap;

    public Bitmap changeBitmp(int blockType, Resources mResources) {
        switch (blockType) {
            case 1:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.blue_block)).getBitmap();
                break;
            case 2:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.yellow_block)).getBitmap();
                break;
            case 3:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.brown_block)).getBitmap();
                break;
            case 4:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.pink_block)).getBitmap();
                break;
            case 5:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.purple_block)).getBitmap();
                break;
            case 6:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.red_block)).getBitmap();
                break;
            case 7:
                blockBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.green_block)).getBitmap();
                break;
            default:
                break;
        }
        return blockBitmap;
    }
}
