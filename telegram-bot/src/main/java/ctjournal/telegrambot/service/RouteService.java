package ctjournal.telegrambot.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ctjournal.telegrambot.dto.ClimbingSession;
import ctjournal.telegrambot.dto.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RouteService {

    public Route create(String name, ClimbingSession climbingSession) {
        Route route = new Route();
        route.setName(name);
        route.setClimbingSession(climbingSession.getId());
        return update(route);
    }

    public Route update(Route route) {
//        RouteDto routeDto = RouteDto.convertFromRoute(route);
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

}
