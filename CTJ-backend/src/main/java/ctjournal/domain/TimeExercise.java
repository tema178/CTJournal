package ctjournal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exercises")
public class TimeExercise extends AbstractExercise {

    private short durationSeconds;

}
