package ctjournal.telegrambot.service;

import ctjournal.telegrambot.dto.GradeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class GradeService {

    public String getGrades() {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<GradeDto[]> response = template.getForEntity(
                "http://localhost:9001/api/grade", GradeDto[].class, urlPathVariables);
        return Arrays.toString(response.getBody());
    }
}
