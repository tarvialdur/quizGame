package ee.tarvi.quizgameapp.rest;

import ee.tarvi.quizgameapp.dto.HealthCheckDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/health")
public class HealthCheckRestController {

    @GetMapping
    public HealthCheckDto healthCheck(){
        HealthCheckDto dto = new HealthCheckDto(true, "The app is running!");
        return dto;
    }
}
