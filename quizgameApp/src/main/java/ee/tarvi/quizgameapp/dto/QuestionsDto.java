package ee.tarvi.quizgameapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class QuestionsDto {
    @JsonProperty("response_code")
    private int responseCode;
    private List<QuestionDto> results;


    @NoArgsConstructor
    @Getter
    @ToString
    @Log
    public static class QuestionDto {
        private String category;
        private String type;
        private String difficulty;
        private String question;
        @JsonProperty("correct_answer")
        private String correctAnswer;
        @JsonProperty("incorrect_answers")
        private List<String> incorrectAnswers;

        public void setQuestion(String question) {
            this.question = HtmlUtils.htmlUnescape(question);
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = HtmlUtils.htmlUnescape(correctAnswer);
        }

        public void setIncorrectAnswers(List<String> incorrectAnswers) {
            List<String> newIncorrectAnswers = incorrectAnswers.stream()
                    .map(answer -> HtmlUtils.htmlUnescape(answer))
                    .collect(Collectors.toList());
            this.incorrectAnswers = newIncorrectAnswers;
        }


        public void getQuizQuestions() {
            RestTemplate restTemplate = new RestTemplate();

            URI uri = UriComponentsBuilder.fromHttpUrl("https://opentdb.com/api.php")
                    .queryParam("amount", 2)
                    .queryParam("category", 25)
                    .queryParam("difficulty", "medium")
                    .build()
                    .toUri();
            log.info(("Quiz questions retrieve URL: " + uri));

            QuestionsDto result = restTemplate.getForObject(uri, QuestionsDto.class);
            assert result != null;
            log.info("Quiz questions: " + result.getResults());

        }
    }
}
