package ctjournal.repository;

import ctjournal.domain.AbstractExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<AbstractExercise, Long> {

}
