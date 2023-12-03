package ctjournal.telegrambot.repository;

import ctjournal.telegrambot.dto.WorkoutState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.telegram.abilitybots.api.db.DBContext;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class WorkoutRepository {

    private static final String WORKOUTS = "WORKOUTS";
    private final DBContext db;

    private Map<String, WorkoutState> getTable() {
        return db.getMap(WORKOUTS);
    }

    public void save(String userId, WorkoutState workoutState) {
        getTable().put(userId, workoutState);
    }

    public WorkoutState findByUserId(String userId) {
        return getTable().get(userId);
    }
}
