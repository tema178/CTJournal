package ctjournal.telegrambot.service;

import ctjournal.telegrambot.dto.WorkoutDto;
import ctjournal.telegrambot.domain.WorkoutState;

public interface WorkoutService {
    WorkoutDto createWorkout();

    WorkoutDto updateWorkout(WorkoutDto workout);

    WorkoutDto findById(long id);

    WorkoutDto updateLocation(WorkoutState workoutState);

    WorkoutDto updateClimbingSession(WorkoutState workoutState);
}
