package com.quiz.quizapp.dto;

import com.quiz.quizapp.model.Question;
import java.util.List;

public class QuizSession {

    private List<Question> questions;

    public QuizSession(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}