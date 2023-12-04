package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {

    @Override
    public Route create(String name, ClimbingSession climbingSession) {
        Route route = new Route();
        route.setName(name);
        route.setClimbingSession(climbingSession.getId());
        return update(route);
    }

    @Override
    public Route update(Route route) {
        try {
            RestTemplate template = new RestTemplate();
            var headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(new ObjectMapper().writeValueAsString(route), headers);
            var response = template.postForEntity(
                    "http://localhost:9001/api/route", request, Route.class);
            return response.getBody();

        } catch (JacksonException e) {

        }
        return null;
    }

    @Override
    public List<Route> getRoutes(long climbingSessionId) {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<Route[]> response = template.getForEntity(
                "http://localhost:9001/api/route/session/" + climbingSessionId, Route[].class, urlPathVariables);
        return List.of(response.getBody());
    }

    @Override
    public Route getRoute(long id) {
        RestTemplate template = new RestTemplate();
        Map<String, String> urlPathVariables = new HashMap<>();
        ResponseEntity<Route> response = template.getForEntity(
                "http://localhost:9001/api/route/" + id, Route.class, urlPathVariables);
        return response.getBody();
    }
}
