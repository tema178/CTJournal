package ctjournal.restController;

import ctjournal.domain.AbstractExercise;
import ctjournal.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ExerciseRestController {

    private final ExerciseService service;

    @PostMapping("/api/exercise")
    public AbstractExercise save(AbstractExercise exercise) {
        return service.save(exercise);
    }

    @GetMapping("/api/exercise/{id}")
    public AbstractExercise findById(long id) {
        return service.findById(id);
    }

    @GetMapping("/api/exercise/session/{exerciseSessionId}")
    public List<AbstractExercise> findAll(long exerciseSessionId) {
        return service.findAll(exerciseSessionId);
    }

    @DeleteMapping("/api/exercise/{id}")
    public void delete(long id) {
        service.delete(id);
    }
}
