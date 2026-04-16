package com.quiz.quizapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.quizapp.dto.AnswerRequest;
import com.quiz.quizapp.dto.QuizSession;
import com.quiz.quizapp.dto.QuizSubmitRequest;
import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.service.QuestionService;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    @Autowired
    private QuestionService service;

    @GetMapping("/random")
    public Question getRandomQuestion(@RequestParam(defaultValue = "main") String bank) {

        Question original = service.getRandomQuestion(bank);

        Question safe = new Question();
        safe.setId(original.getId());
        safe.setQuestion(original.getQuestion());
        safe.setOptions(original.getOptions());
        safe.setAnswer(null); // hide answer

        return safe;
    }
    @PostMapping("/check")
    public Question checkAndReveal(
            @RequestParam(defaultValue = "main") String bank,
            @RequestBody AnswerRequest request) {

        Question q = service.getAllQuestions(bank)
                .stream()
                .filter(x -> x.getId() == request.getQuestionId())
                .findFirst()
                .orElse(null);

        return q; // returns correct answer
    }@GetMapping("/test")
    public String test() {
        return "working";
    }
    @GetMapping("/start")
    public QuizSession startQuiz(
            @RequestParam(defaultValue = "20") int count,
            @RequestParam(defaultValue = "main") String bank) {

        List<Question> list = service.getRandomQuestions(count, bank);

        // hide answers
        List<Question> safeList = new java.util.ArrayList<>();

        for (Question q : list) {
            Question copy = new Question();
            copy.setId(q.getId());
            copy.setQuestion(q.getQuestion());
            copy.setOptions(q.getOptions());
            copy.setAnswer(null);

            safeList.add(copy);
        }

        return new QuizSession(safeList);
    }
    @GetMapping("/set/{id}")
    public List<Question> getSet(
            @PathVariable int id,
            @RequestParam(defaultValue = "main") String bank) {

        return service.getQuestionsFromId(id, bank);
    }
    @PostMapping("/submit")
    public String submitQuiz(
            @RequestParam(defaultValue = "main") String bank,
            @RequestBody QuizSubmitRequest request) {

        int score = service.calculateScore(request.getAnswers(), bank);
        int total = request.getAnswers() == null ? 0 : request.getAnswers().size();

        return "Score: " + score + " / " + total;
    }
}