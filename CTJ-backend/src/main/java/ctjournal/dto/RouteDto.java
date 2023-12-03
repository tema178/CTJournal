package ctjournal.dto;

import ctjournal.domain.ClimbingSession;
import ctjournal.domain.DifficultyLevel;
import ctjournal.domain.Grade;
import ctjournal.domain.Route;
import ctjournal.domain.SendStyle;
import ctjournal.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RouteDto {

    private long id;

    private String name;

    private Type type;

    private long grade;

    private SendStyle sendStyle;

    private short attempts;

    private short attemptsForRedPoint;

    private byte rating;

    private DifficultyLevel difficultyLevel;

    private String comment;

    private long climbingSession;

    public static RouteDto domainToDto(Route route) {
        long newGrade = route.getGrade() == null ? 0 : route.getGrade().getId();
        return new RouteDto(route.getId(), route.getName(), route.getType(), newGrade,
                route.getSendStyle(), route.getAttempts(), route.getAttemptsForRedPoint(), route.getRating(),
                route.getDifficultyLevel(), route.getComment(), route.getClimbingSession().getId());
    }

    public Route toDomain() {
        Grade newGrade = this.getGrade() == 0 ? null : new Grade(this.getGrade());
        return new Route(this.getId(), this.getName(), this.getType(), newGrade,
                this.getSendStyle(), this.getAttempts(), this.getAttemptsForRedPoint(), this.getRating(),
                this.getDifficultyLevel(), this.getComment(), new ClimbingSession(this.getClimbingSession()));
    }

    public static List<RouteDto> domainToDto(@NonNull List<Route> routes) {
        var routeDtos = new ArrayList<RouteDto>();
        routes.forEach(route -> routeDtos.add(domainToDto(route)));
        return routeDtos;
    }

}
