package ctjournal.service;

import ctjournal.domain.Route;

import java.util.List;

public interface RouteService {
    Route save(Route route);

    Route findById(long id);

    List<Route> findByClimbingSessionId(long climbingSessionId);

    void delete(long id);
}
