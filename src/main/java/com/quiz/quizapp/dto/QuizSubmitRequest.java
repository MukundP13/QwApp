package com.quiz.quizapp.dto;

import java.util.Map;
import java.util.List;

public class QuizSubmitRequest {

    // questionId → selected options
    private Map<Integer, List<String>> answers;

    public Map<Integer, List<String>> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, List<String>> answers) {
        this.answers = answers;
    }
}