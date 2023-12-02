package ee.tarvi.quizgameapp.frontend;

import lombok.Data;

@Data
public class GameOptions {
    private int numberOfQuestions = 3;
    private Difficulty difficulty;
    private int categoryId;
}
