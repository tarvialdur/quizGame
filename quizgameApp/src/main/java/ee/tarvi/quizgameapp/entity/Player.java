package ee.tarvi.quizgameapp.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@ToString
@Entity(name = "PLAYERS")
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    public Player(String name) {
        this.name = name;
    }
}