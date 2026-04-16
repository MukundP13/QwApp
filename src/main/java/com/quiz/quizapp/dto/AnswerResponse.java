package com.quiz.quizapp.dto;

public class AnswerResponse {

    private boolean correct;
    private int score;

    public AnswerResponse(boolean correct, int score) {
        this.correct = correct;
        this.score = score;
    }

    public boolean isCorrect() { return correct; }
    public int getScore() { return score; }
}