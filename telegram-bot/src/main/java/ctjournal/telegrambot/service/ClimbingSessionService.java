package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.WorkoutState;

public interface ClimbingSessionService {
    ClimbingSession create(WorkoutState workout);

    ClimbingSession update(ClimbingSession session);
}
