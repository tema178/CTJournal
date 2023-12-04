package ctjournal.telegrambot.service;

import ctjournal.telegrambot.domain.Location;

import java.util.List;

public interface LocationService {
    long createLocation(String name);

    List<Location> viewLocations(long userId);
}
