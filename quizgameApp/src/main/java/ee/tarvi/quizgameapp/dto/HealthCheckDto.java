package ee.tarvi.quizgameapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HealthCheckDto {
    private final boolean status;
    private final  String message;


}
