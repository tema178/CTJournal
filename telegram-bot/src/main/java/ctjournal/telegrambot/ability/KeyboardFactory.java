package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.dto.Location;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardFactory {

    private KeyboardFactory() {
    }

    public static final String NEW_LOCATION = "new location";
    public static final String VIEW_LOCATIONS = "view locations";

    public static ReplyKeyboard mainKeyboard() {
        InlineKeyboardButton warmUp = InlineKeyboardButton.builder()
                .text("Добавить разминку")
                .callbackData("add warmUp")
                .build();
        InlineKeyboardButton climbingSession = InlineKeyboardButton.builder()
                .text("Добавить лазательную сессию")
                .callbackData("add climbing session")
                .build();
        InlineKeyboardButton coolDown = InlineKeyboardButton.builder()
                .text("Добавить заминку")
                .callbackData("add coolDown")
                .build();
        InlineKeyboardButton comment = InlineKeyboardButton.builder()
                .text("Добавить комментарий")
                .callbackData("add comment")
                .build();
        InlineKeyboardButton changeLocation = InlineKeyboardButton.builder()
                .text("Изменить место")
                .callbackData("change location")
                .build();
        InlineKeyboardButton addRate = InlineKeyboardButton.builder()
                .text("Добавить оценку")
                .callbackData("add rate")
                .build();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        matrix.add(List.of(warmUp));
        matrix.add(List.of(climbingSession));
        matrix.add(List.of(coolDown));
        matrix.add(List.of(comment));
        matrix.add(List.of(changeLocation));
        matrix.add(List.of(addRate));
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard location() {
        InlineKeyboardButton newLocation = InlineKeyboardButton.builder()
                .text("Указать новое место")
                .callbackData(NEW_LOCATION)
                .build();
        InlineKeyboardButton viewLocations = InlineKeyboardButton.builder()
                .text("Выбрать место")
                .callbackData(VIEW_LOCATIONS)
                .build();
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        matrix.add(List.of(newLocation));
        matrix.add(List.of(viewLocations));
        return new InlineKeyboardMarkup(matrix);
    }

    public static ReplyKeyboard viewLocation(List<Location> locations) {
        List<List<InlineKeyboardButton>> matrix = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(locations.get(i).getName())
                    .callbackData(VIEW_LOCATIONS + i)
                    .build();
            matrix.add(List.of(button));
        }
        InlineKeyboardButton newLocation = InlineKeyboardButton.builder()
                .text("Указать новое место")
                .callbackData(NEW_LOCATION)
                .build();
        matrix.add(List.of(newLocation));
        return new InlineKeyboardMarkup(matrix);
    }
}
