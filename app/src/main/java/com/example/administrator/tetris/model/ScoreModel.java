package com.example.administrator.tetris.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ScoreModel {
    public int score;
    public int bestScore;
    private String fileName = "bestScore.text";

    public ScoreModel() {

    }

    public void addScore(int line) {
        if (line == 0)
            return;
        score += line + (line - 1);
    }

    public void getBestScore(Context context) {
        String content = loadDataFromFile(context);
        if (content.equals("")) {
            bestScore = 0;
            return;
        }
        bestScore = Integer.parseInt(content);
    }

    public void updateBestScore(Context context) {
        if (score <= bestScore)
            return;
        bestScore = score;
        saveDataToFile(context, String.valueOf(bestScore));
    }

    private String loadDataFromFile(Context context) {
        FileInputStream fileInputStream;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            /*
              注意这里的fileName不要用绝对路径，只需要文件名就可以了，系统会自动到data目录下去加载这个文件
             */
            fileInputStream = context.openFileInput(fileName);
            bufferedReader = new BufferedReader(
                    new InputStreamReader(fileInputStream));
            String result;
            while ((result = bufferedReader.readLine()) != null) {
                stringBuilder.append(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    private void saveDataToFile(Context context, String data) {
        FileOutputStream fileOutputStream;
        BufferedWriter bufferedWriter = null;
        try {
            /*
              "data"为文件名,MODE_PRIVATE表示如果存在同名文件则覆盖，
              还有一个MODE_APPEND表示如果存在同名文件则会往里面追加内容
             */
            fileOutputStream = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cleanScore() {
        score = 0;
    }
}
