package ctjournal.restController;

import ctjournal.domain.Route;
import ctjournal.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RouteRestController {

    private final RouteService service;

    @PostMapping("/api/route")
    public Route save(Route route) {
        return service.save(route);
    }

    @GetMapping("/api/route/{id}")
    public Route findById(long id) {
        return service.findById(id);
    }

    @GetMapping("/api/route/session/{climbingSessionId}")
    public List<Route> findAll(long climbingSessionId) {
        return service.findByClimbingSessionId(climbingSessionId);
    }

    @DeleteMapping("/api/route/{id}")
    public void delete(long id) {
        service.delete(id);
    }
}
