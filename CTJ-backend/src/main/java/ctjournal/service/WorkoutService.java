package ctjournal.service;

import ctjournal.domain.Workout;
import ctjournal.exceptions.BookServiceException;

import java.util.List;

public interface WorkoutService {
    Workout save(Workout book) throws BookServiceException;

    List<Workout> getAll();

    Workout getById(long id);

    void deleteById(long id);
}
