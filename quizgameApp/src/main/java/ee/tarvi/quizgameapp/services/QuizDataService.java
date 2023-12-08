package ee.tarvi.quizgameapp.services;


import ee.tarvi.quizgameapp.dto.CategoriesDto;
import ee.tarvi.quizgameapp.dto.CategoryQuestionCountInfoDto;
import ee.tarvi.quizgameapp.dto.QuestionsDto;
import ee.tarvi.quizgameapp.frontend.Difficulty;
import ee.tarvi.quizgameapp.frontend.GameOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@Log4j2
public class QuizDataService {

    //get quiz categories from open trivia db
    public List<CategoriesDto.CategoryDto> getQuizCategories() {
        RestTemplate restTemplate = new RestTemplate();
        CategoriesDto result = restTemplate.getForObject("https://opentdb.com/api_category.php", CategoriesDto.class);
        assert result != null;
        log.info("Quiz categories: " + result.getCategories());
        return result.getCategories();
    }


    public List<QuestionsDto.QuestionDto> getQuizQuestions(GameOptions gameOptions) {
        CategoryQuestionCountInfoDto categoryQuestionCount = getCategoryQuestionCount(gameOptions.getCategoryId());
        int availableQuestionCount = categoryQuestionCount.getQuestionCountForDifficulty(gameOptions.getDifficulty());
        if (availableQuestionCount >= gameOptions.getNumberOfQuestions()) {
            return getQuizQuestions(gameOptions.getNumberOfQuestions(), gameOptions.getCategoryId(), gameOptions.getDifficulty());
        } else {
            return getQuizQuestions(availableQuestionCount, gameOptions.getCategoryId(), gameOptions.getDifficulty());
        }
    }

    //Overloading
    //
    private List<QuestionsDto.QuestionDto> getQuizQuestions(int numberOfQuestions, int categoryId, Difficulty difficulty) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder.fromHttpUrl("https://opentdb.com/api.php")
                .queryParam("amount", numberOfQuestions)
                .queryParam("category", categoryId)
                .queryParam("difficulty", difficulty.name().toLowerCase())
                .build().toUri();
        log.info("Quiz questions: " + uri);

        QuestionsDto result = restTemplate.getForObject(uri, QuestionsDto.class);
        assert result != null;
        return result.getResults();
    }

    private CategoryQuestionCountInfoDto getCategoryQuestionCount(int categoryId) {
        RestTemplate restTemplate = new RestTemplate();

        URI uri = UriComponentsBuilder.fromHttpUrl("https://opentdb.com/api_count.php")
                .queryParam("category", categoryId)
                .build().toUri();
        CategoryQuestionCountInfoDto result = restTemplate.getForObject(uri, CategoryQuestionCountInfoDto.class);
        return result;
    }
}
