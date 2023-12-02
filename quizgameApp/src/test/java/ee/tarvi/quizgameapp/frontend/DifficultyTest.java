package ee.tarvi.quizgameapp.frontend;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static ee.tarvi.quizgameapp.frontend.Difficulty.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DifficultyTest {

    @Test
    void calculateNextDifficultyEASY() {
        EnumSet<Difficulty> givenDifficulty = EnumSet.of(EASY);
        Difficulty result = calculateNextDifficulty(givenDifficulty);
        assertEquals(MEDIUM, result);
    }

    @Test
    void calculateNextDifficultyNULL() {
        EnumSet<Difficulty> givenDifficulty = null;
        Difficulty result = calculateNextDifficulty(givenDifficulty);
        assertNull(result);
    }

    @Test
    void calculateNextDifficultyNONE() {
        EnumSet<Difficulty> givenDifficulty = EnumSet.noneOf(Difficulty.class);
        Difficulty result = calculateNextDifficulty(givenDifficulty);
        assertNull(result);
    }

    @Test
    void calculcateNextDifficultyEASY() {
        EnumSet<Difficulty> givenDifficulty = EnumSet.of(EASY);
        Difficulty result = calculateNextDifficulty(givenDifficulty);
        assertEquals(MEDIUM, result);
     }

    @Test
    void calculateNextDifficultyEASY_MEDIUM() {
        EnumSet<Difficulty> given = EnumSet.of(EASY, MEDIUM);
        Difficulty result = calculateNextDifficulty(given);
        assertEquals(HARD, result);
    }

    @Test
    void calculateNextDifficultyMEDIUM() {
        EnumSet<Difficulty> givenDifficulty = EnumSet.of(MEDIUM);
        Difficulty result = calculateNextDifficulty(givenDifficulty);
        assertEquals(HARD, result);
    }

    @Test
    void calculateNextDifficultyMEDIUM_HARD() {
        EnumSet<Difficulty> given = EnumSet.of(MEDIUM, HARD);
        Difficulty result = calculateNextDifficulty(given);
        assertEquals(EASY, result);
    }

    @Test
    void calculateNextDifficultyHARD() {
        EnumSet<Difficulty> given = EnumSet.of(HARD);
        Difficulty result = calculateNextDifficulty(given);
        assertEquals(MEDIUM, result);
    }

    @Test
    void calculateNextDifficultyHARD_EASY() {
        EnumSet<Difficulty> given = EnumSet.of(HARD, EASY);
        Difficulty result = calculateNextDifficulty(given);
        assertEquals(MEDIUM, result);
    }

    @Test
    void calculateNextDifficultyALL() {
        EnumSet<Difficulty> given = EnumSet.of(EASY, MEDIUM, HARD);
        Difficulty result = calculateNextDifficulty(given);
        assertNull(result);
    }
}