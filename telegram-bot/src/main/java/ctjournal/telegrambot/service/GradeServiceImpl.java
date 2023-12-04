package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

    @Override
    public List<Grade> getGrades() {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<Grade[]> response = template.getForEntity(
                "http://localhost:9001/api/grade", Grade[].class, urlPathVariables);
        return List.of(response.getBody());
    }

    @Override
    public Grade getGrade(long id) {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<Grade> response = template.getForEntity(
                "http://localhost:9001/api/grade/" + id, Grade.class, urlPathVariables);
        return response.getBody();
    }
}
