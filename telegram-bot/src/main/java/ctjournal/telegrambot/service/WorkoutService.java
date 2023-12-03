package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.dto.ClimbingSession;
import ctjournal.telegrambot.dto.Location;
import ctjournal.telegrambot.dto.WorkoutDto;
import ctjournal.telegrambot.dto.WorkoutState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WorkoutService {

    public WorkoutDto createWorkout() {
        WorkoutDto workout = new WorkoutDto(Date.valueOf(LocalDate.now()), System.currentTimeMillis());
        return updateWorkout(workout);
    }

    public WorkoutDto updateWorkout(WorkoutDto workout) {
        try {
            RestTemplate template = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(workout), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/workout", request, WorkoutDto.class);
            return response.getBody();

        } catch (JacksonException e) {

        }
        return null;
    }

    public WorkoutDto findById(long id) {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<WorkoutDto> response = template.getForEntity(
                "http://localhost:9001/api/workout/" + id, WorkoutDto.class, urlPathVariables);
        return response.getBody();
    }

    public WorkoutDto updateLocation(WorkoutState workoutState) {
        WorkoutDto workoutDto = findById(workoutState.getId());
        workoutDto.setLocation(new Location(workoutState.getId()));
        return updateWorkout(workoutDto);
    }

    public WorkoutDto updateClimbingSession(WorkoutState workoutState) {
        WorkoutDto workoutDto = findById(workoutState.getId());
        workoutDto.setClimbingSession(new ClimbingSession(workoutState.getClimbingSession()));
        return updateWorkout(workoutDto);
    }
}
