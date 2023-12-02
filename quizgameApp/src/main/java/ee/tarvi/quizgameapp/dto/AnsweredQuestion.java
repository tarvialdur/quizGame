package ee.tarvi.quizgameapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class AnsweredQuestion {
    private final String question;
    private final String userAnswer;
    private final boolean correct;
    private final String correctAnswer;

    public AnsweredQuestion(QuestionsDto.QuestionDto questionDto, String userAnswer, boolean correct) {
        this.question = questionDto.getQuestion();
        this.userAnswer = userAnswer;
        this.correct = correct;
        this.correctAnswer = questionDto.getCorrectAnswer();
    }
}