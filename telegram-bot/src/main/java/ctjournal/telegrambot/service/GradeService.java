package ctjournal.telegrambot.service;

import ctjournal.telegrambot.dto.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GradeService {

    public List<Grade> getGrades() {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<Grade[]> response = template.getForEntity(
                "http://localhost:9001/api/grade", Grade[].class, urlPathVariables);
        return List.of(response.getBody());
    }

    public Grade getGrade(long id) {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<Grade> response = template.getForEntity(
                "http://localhost:9001/api/grade/" + id, Grade.class, urlPathVariables);
        return response.getBody();
    }
}
