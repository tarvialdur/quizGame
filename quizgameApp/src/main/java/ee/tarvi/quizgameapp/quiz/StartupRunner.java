package ee.tarvi.quizgameapp.quiz;

import ee.tarvi.quizgameapp.entity.Player;
import ee.tarvi.quizgameapp.repositories.PlayerRepository;
import ee.tarvi.quizgameapp.services.QuizDataService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private QuizDataService quizDataService;


    @Override
    public void run(String...args) throws Exception {
        log.info("Executing startup actions...");

        playerRepository.save(new Player("John"));
        playerRepository.save(new Player("Harry"));
        playerRepository.save(new Player("George"));

        log.info("List of players from database:");
        List<Player> playersFromDatabase = playerRepository.findAll();
        for (Player player : playersFromDatabase) {
            log.info("Retrieved player: " + player);
        }
        quizDataService.getQuizCategories();
        //quizDataService.getQuizQuestions();

    }

}

