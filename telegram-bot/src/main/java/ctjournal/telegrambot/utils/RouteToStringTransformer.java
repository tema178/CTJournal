package ctjournal.telegrambot.utils;

import ctjournal.telegrambot.dto.Route;

import java.util.List;

public class RouteToStringTransformer {

    private RouteToStringTransformer() {
    }

    public static String transform(Route route) {
        return "Имя: " + route.getName() + '\n' +
                "Тип: " + route.getType() + '\n' +
                "Категория: " + route.getGrade() + '\n' +
                "Стиль прохождения: " + route.getSendStyle() + '\n' +
                "Количество попыток: " + route.getAttempts() + '\n' +
                "Количество попыток RedPoint: " + route.getAttemptsForRedPoint() + '\n' +
                "Оценка: " + route.getRating() + '\n' +
                "Сложность: " + route.getDifficultyLevel() + '\n' +
                "Комментарий: " + route.getComment();
    }

    public static String transform(List<Route> routes) {
        if (routes == null || routes.isEmpty()) {
            return "Трассы не найдены";
        }
        StringBuilder builder = new StringBuilder();
        routes.forEach(route -> builder.append(transform(route))
                .append("\n___________________________________________\n"));
        return builder.toString();
    }
}
