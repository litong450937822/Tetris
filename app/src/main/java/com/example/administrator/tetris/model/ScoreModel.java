package com.example.administrator.tetris.model;

public class ScoreModel {
    public int score;
    public int bestScore;

    public ScoreModel() {

    }

    public void addScore(int line) {
        if (line ==0)
            return;
        score += line + (line - 1);
    }

    public void cleanScore() {
        if (score>bestScore)
            bestScore = score;
        score = 0;
    }
}
