package ctjournal.telegrambot.domain;

public enum States {

    WAITING_LOCATION_NAME,
    CREATING_LOCATION,
    WAITING_ROUTE_NAME_CREATE,
    WAITING_ROUTE_NAME,
    WAITING_ATTEMPTS,
    WAITING_REDPOINT_ATTEMPTS,
    WAITING_RAITING,
    WAITING_ROUTE_COMMENT,
    MAIN_MENU,
    CLIMBING_SESSION_MENU,
    ROUTE_MENU,
    FINISHED
}