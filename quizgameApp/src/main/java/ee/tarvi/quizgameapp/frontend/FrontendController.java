package ee.tarvi.quizgameapp.frontend;

import ee.tarvi.quizgameapp.dto.QuestionsDto;
import ee.tarvi.quizgameapp.services.OngoingGameService;
import ee.tarvi.quizgameapp.services.QuizDataService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
public class FrontendController {

    @Autowired
    private QuizDataService quizDataService;

    @Autowired
    private OngoingGameService ongoingGameService;



    @Autowired
    QuestionsDto.QuestionDto questionDto;

    @GetMapping("/")
    public String hello(Model model){
        model.addAttribute("message", "some message");
        return "index";
    }

    @GetMapping("/select")
    public String select(Model model) {

        model.addAttribute("gameOptions", new GameOptions());
        model.addAttribute("categories", quizDataService.getQuizCategories());
        return "select";
    }

    @PostMapping("/select")
    public String postSelectForm(@ModelAttribute GameOptions gameOptions) {
        log.info("Form submitted with data: " + gameOptions);
        ongoingGameService.init(gameOptions);
        return "redirect:game";
    }

    @GetMapping("/game")
    public String game(Model model) {
        model.addAttribute("userAnswer", new UserAnswer());
        model.addAttribute("currentQuestionNumber", ongoingGameService.getCurrentQuestionNumber());
        model.addAttribute("totalQuestionNumber", ongoingGameService.getTotalQuestionNumber());
        model.addAttribute("currentQuestion", ongoingGameService.getCurrentQuestion());
        model.addAttribute("currentQuestionAnswers", ongoingGameService.getCurrentQuestionAnswersInRandomOrder());
        return "game";
    }

    @PostMapping("/game")  // check if the user has given the right answer and move on to the next question. if no more questons are present, move to the homepage
    public String postSelectForm(@ModelAttribute UserAnswer userAnswer) {
        ongoingGameService.checkAnswerForCurrentQuestionAndUpdatePoints(userAnswer.getAnswer());
        boolean hasNextQuestion = ongoingGameService.proceedToNextQuestion();
        if (hasNextQuestion) {
            return "redirect:game";
        } else {
            return "redirect:summary";
        }
    }

    @GetMapping("/summary")
    public String summary(Model model) {
        model.addAttribute("difficulty", ongoingGameService.getDifficulty());
        model.addAttribute("categoryName", ongoingGameService.getCategoryName());
        model.addAttribute("points", ongoingGameService.getPoints());
        model.addAttribute("maxPoints", ongoingGameService.getTotalQuestionNumber());
        model.addAttribute("questions", ongoingGameService.getTotalQuestionNumber());
        model.addAttribute("answeredQuestions", ongoingGameService.getAnsweredQuestions());

        return "summary";
    }

    @PostMapping("/summary")
    public String playAgain() {
        ongoingGameService.resetGame();
        return "redirect:select";
    }
}
