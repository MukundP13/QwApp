package com.quiz.quizapp.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class Question {

    private int id;
    private String question;
    private Map<String, String> options;
    private List<String> answer;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public Map<String, String> getOptions() { return options; }
    public void setOptions(Map<String, String> options) { this.options = options; }

    public List<String> getAnswer() { return answer; }
    public void setAnswer(List<String> answer) { this.answer = answer; }

    @JsonSetter("answer")
    public void setAnswerFromJson(JsonNode answerNode) {
        if (answerNode == null || answerNode.isNull()) {
            this.answer = null;
            return;
        }

        if (answerNode.isTextual()) {
            this.answer = List.of(answerNode.asText());
            return;
        }

        if (answerNode.isArray()) {
            List<String> parsed = new ArrayList<>();
            for (JsonNode node : answerNode) {
                parsed.add(node.asText());
            }
            this.answer = parsed;
        }
    }
}