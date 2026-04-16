package com.quiz.quizapp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.quizapp.model.Question;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {
    private int score = 0;
    private List<Question> questions;
    private List<Question> nquestions;

    public QuestionService() {
        try {
            questions = loadQuestionsFromResource("/questions.json");
            nquestions = loadQuestionsFromResource("/nquestions.json");

            System.out.println("Loaded questions: " + questions.size());
            System.out.println("Loaded nquestions: " + nquestions.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Question> loadQuestionsFromResource(String resourcePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream(resourcePath);

        if (is == null) {
            throw new IllegalStateException("Missing resource file: " + resourcePath);
        }

        return mapper.readValue(is, new TypeReference<List<Question>>() {});
    }

    private List<Question> getQuestionBank(String bank) {
        if ("nquestions".equalsIgnoreCase(bank)) {
            return nquestions == null ? Collections.emptyList() : nquestions;
        }

        return questions == null ? Collections.emptyList() : questions;
    }

    public List<Question> getAllQuestions() {
        return questions;
    }

    public List<Question> getAllQuestions(String bank) {
        return getQuestionBank(bank);
    }

    public Question getRandomQuestion() {
        int index = (int) (Math.random() * questions.size());
        return questions.get(index);
    }

    public Question getRandomQuestion(String bank) {
        List<Question> selectedBank = getQuestionBank(bank);
        int index = (int) (Math.random() * selectedBank.size());
        return selectedBank.get(index);
    }
    public boolean checkAnswer(int questionId, List<String> selected) {

        for (Question q : questions) {
            if (q.getId() == questionId) {

                List<String> correct = q.getAnswer();

                boolean isCorrect = selected.size() == correct.size() &&
                        selected.containsAll(correct);

                if (isCorrect) score++;

                return isCorrect;
            }
        }

        return false;
    }public List<Question> getQuestionsFromId(int startId) {

        List<Question> result = new java.util.ArrayList<>();

        for (Question q : questions) {
            if (q.getId() >= startId) {
                result.add(q);
            }
        }

        return result;
    }

    public List<Question> getQuestionsFromId(int startId, String bank) {

        List<Question> selectedBank = getQuestionBank(bank);
        List<Question> result = new java.util.ArrayList<>();

        for (Question q : selectedBank) {
            if (q.getId() >= startId) {
                result.add(q);
            }
        }

        return result;
    }

    public int calculateScore(Map<Integer, List<String>> userAnswers) {

        int score = 0;

        for (var entry : userAnswers.entrySet()) {

            int qid = entry.getKey();
            List<String> selected = entry.getValue();

            for (Question q : questions) {
                if (q.getId() == qid) {

                    List<String> correct = q.getAnswer();

                    boolean isCorrect = selected.size() == correct.size() &&
                            selected.containsAll(correct);

                    if (isCorrect) score++;
                }
            }
        }

        return score;
    }

    public int calculateScore(Map<Integer, List<String>> userAnswers, String bank) {

        int score = 0;
        List<Question> selectedBank = getQuestionBank(bank);

        for (var entry : userAnswers.entrySet()) {

            int qid = entry.getKey();
            List<String> selected = entry.getValue();

            for (Question q : selectedBank) {
                if (q.getId() == qid) {

                    List<String> correct = q.getAnswer();

                    boolean isCorrect = selected.size() == correct.size() &&
                            selected.containsAll(correct);

                    if (isCorrect) score++;
                }
            }
        }

        return score;
    }

    public List<Question> getRandomQuestions(int count) {

        List<Question> shuffled = new java.util.ArrayList<>(questions);
        java.util.Collections.shuffle(shuffled);

        if (count <= 0) {
            return shuffled;
        }

        int safeCount = Math.min(count, shuffled.size());

        return shuffled.subList(0, safeCount);
    }

    public List<Question> getRandomQuestions(int count, String bank) {

        List<Question> selectedBank = getQuestionBank(bank);
        List<Question> shuffled = new java.util.ArrayList<>(selectedBank);
        Collections.shuffle(shuffled);

        if (count <= 0) {
            return shuffled;
        }

        int safeCount = Math.min(count, shuffled.size());

        return shuffled.subList(0, safeCount);
    }

    public int getScore() {
        return score;
    }
}