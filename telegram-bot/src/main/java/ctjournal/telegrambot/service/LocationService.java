package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.dto.Location;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {

    public long createLocation(String name) {
        try {
            RestTemplate template = new RestTemplate();
            Location location = new Location(0, name, "", null, false);
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(location), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/location", request, Location.class);
            return response.getBody().getId();

        } catch (JacksonException e) {

        }
        return 0;
    }

    public List<Location> viewLocations(long userId) {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<Location[]> response = template.getForEntity(
                "http://localhost:9001/api/location", Location[].class, urlPathVariables);
        return Arrays.asList(response.getBody());
    }
}
