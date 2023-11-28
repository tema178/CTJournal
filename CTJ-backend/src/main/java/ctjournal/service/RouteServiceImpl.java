package ctjournal.service;

import ctjournal.domain.Route;
import ctjournal.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository repository;

    @Override
    public Route save(Route route) {
        return repository.save(route);
    }

    @Override
    public Route findById(long id) {
        return repository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public List<Route> findByClimbingSessionId(long climbingSessionId) {
        return repository.findByClimbingSessionId(climbingSessionId);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
