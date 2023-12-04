package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.ClimbingSession;
import ctjournal.telegrambot.domain.Route;

import java.util.List;

public interface RouteService {
    Route create(String name, ClimbingSession climbingSession);

    Route update(Route route);

    List<Route> getRoutes(long climbingSessionId);

    Route getRoute(long id);
}
