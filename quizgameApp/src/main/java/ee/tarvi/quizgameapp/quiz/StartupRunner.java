package ee.tarvi.quizgameapp.quiz;

import ee.tarvi.quizgameapp.entity.Player;
import ee.tarvi.quizgameapp.repositories.PlayerRepository;
import ee.tarvi.quizgameapp.services.QuizDataService;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class StartupRunner implements CommandLineRunner { // implements CommandLineRunner interface and and run() will be invoked after Spring boot app is invoked and starts handling incoming requests,

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private QuizDataService quizDataService;

    @Override
    public void run(String...args) {
        log.info("---> Startup  actions...");

        playerRepository.save(new Player("Tarvi"));

        log.info("--->List of players from database:");
        List<Player> playersFromDatabase = playerRepository.findAll();
        for (Player player : playersFromDatabase) {
            log.info("--->Retrieved player: " + player);
        }
        quizDataService.getQuizCategories();

    }

}

