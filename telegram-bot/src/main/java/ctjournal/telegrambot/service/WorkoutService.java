package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.dto.WorkoutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class WorkoutService {

    public long createWorkout() {
        try {
            RestTemplate template = new RestTemplate();
            WorkoutDto workout = new WorkoutDto(Date.valueOf(LocalDate.now()), System.currentTimeMillis());
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(workout), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/workout", request, WorkoutDto.class);
            return response.getBody().getId();

        } catch (JacksonException e) {

        }
        return 0;
    }
}
