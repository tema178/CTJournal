package ctjournal.service;

import ctjournal.exceptions.BookServiceException;
import ctjournal.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ctjournal.domain.Workout;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;


    @Override
    public Workout save(Workout book) throws BookServiceException {
        return null;
    }

    @Override
    public List<Workout> getAll() {
        return null;
    }

    @Override
    public Workout getById(long id) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}
