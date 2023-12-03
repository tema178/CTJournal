package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.dto.ClimbingSession;
import ctjournal.telegrambot.dto.WorkoutState;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClimbingSessionService {

    public ClimbingSession create(WorkoutState workout) {
        return update(new ClimbingSession(workout));
    }

    public ClimbingSession update(ClimbingSession session) {
        try {
            RestTemplate template = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(session), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/session/climbing", request, ClimbingSession.class);
            return response.getBody();

        } catch (JacksonException e) {

        }
        return null;
    }

}
