package com.quiz.quizapp.dto;

import java.util.List;

public class AnswerRequest {

    private int questionId;
    private List<String> selectedOptions;

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public List<String> getSelectedOptions() { return selectedOptions; }
    public void setSelectedOptions(List<String> selectedOptions) { this.selectedOptions = selectedOptions; }
}