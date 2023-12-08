package ee.tarvi.quizgameapp.services;

import ee.tarvi.quizgameapp.dto.AnsweredQuestion;
import ee.tarvi.quizgameapp.dto.CategoriesDto;
import ee.tarvi.quizgameapp.dto.QuestionsDto;
import ee.tarvi.quizgameapp.frontend.Difficulty;
import ee.tarvi.quizgameapp.frontend.GameOptions;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class OngoingGameService {
    private GameOptions gameOptions;
    private int currentQuestionIndex;

    @Getter
    private int points;

    private List<QuestionsDto.QuestionDto> questions;

    @Autowired
    private QuizDataService quizDataService;

    @Getter
    private final List<AnsweredQuestion> answeredQuestions = new ArrayList<>();

    public void initialize(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
        this.currentQuestionIndex = 0;
        this.points = 0;
        this.questions = quizDataService.getQuizQuestions(gameOptions);
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionIndex+1;
    }

    public int getTotalQuestionNumber() {
        return questions.size();
    }

    public String getCurrentQuestion() {
        QuestionsDto.QuestionDto dto = questions.get(currentQuestionIndex);
        return dto.getQuestion();
    }

    public List<String> getCurrentQuestionAnswersInRandomOrder() {
        QuestionsDto.QuestionDto dto = questions.get(currentQuestionIndex);

        List<String> answers = new ArrayList<>();
        answers.add(dto.getCorrectAnswer());
        answers.addAll(dto.getIncorrectAnswers());

        Collections.shuffle(answers);
        return answers;
    }

    public void checkAnswerForCurrentQuestionAndUpdatePoints(String userAnswer) {

        QuestionsDto.QuestionDto dto = questions.get(currentQuestionIndex);
        boolean correct = dto.getCorrectAnswer().equals(userAnswer);
        if (correct) {
            points++;
        }
        AnsweredQuestion answeredQuestion = new AnsweredQuestion(dto, userAnswer, correct);
        answeredQuestions.add(answeredQuestion);
    }

    public void resetGame() {
        this.currentQuestionIndex = 0;
        this.points = 0;
        this.answeredQuestions.clear(); // Clear the list of answered questions in summary section
        this.questions = quizDataService.getQuizQuestions(gameOptions);
    }

    public boolean proceedToNextQuestion() {
        currentQuestionIndex++;
        return currentQuestionIndex < questions.size();
    }

    public Difficulty getDifficulty() {
        return gameOptions.getDifficulty();
    }

    public String getCategoryName() {
        Optional<String> category = quizDataService.getQuizCategories().stream()
                .filter(categoryDto -> categoryDto.getId() == gameOptions.getCategoryId())
                .map(CategoriesDto.CategoryDto::getName)
                .findAny();
        return category.orElse(null);
    }
}