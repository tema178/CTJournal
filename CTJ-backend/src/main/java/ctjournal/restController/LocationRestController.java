package ctjournal.restController;

import ctjournal.domain.Location;
import ctjournal.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationRestController {

    private final LocationService service;

    @GetMapping("/api/location/{id}")
    public Location findById(long id) {
        return service.findById(id);
    }

    @GetMapping("/api/location/user/{name}")
    public List<Location> findAll(String name) {
        return service.findAll(name);
    }

    @PostMapping("/api/location/")
    public Location save(Location location) {
        return service.save(location);
    }

    @PatchMapping("/api/location/{id}/favourite")
    public Location setFavourite(@PathVariable long id, boolean favourite) {
        return service.setFavourite(id, favourite);
    }
}
