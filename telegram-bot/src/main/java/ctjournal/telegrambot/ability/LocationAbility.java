package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.db.States;
import ctjournal.telegrambot.dto.Location;
import ctjournal.telegrambot.dto.WorkoutState;
import ctjournal.telegrambot.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static ctjournal.telegrambot.ability.KeyboardFactory.NEW_LOCATION;
import static ctjournal.telegrambot.ability.KeyboardFactory.VIEW_LOCATIONS;
import static ctjournal.telegrambot.db.States.WAITING_LOCATION_NAME;
import static ctjournal.telegrambot.db.Tables.STATES;
import static ctjournal.telegrambot.db.Tables.WORKOUTS;
import static java.lang.String.format;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class LocationAbility implements AbilityExtension {

    @Autowired
    private AbilityBot abilityBot;
    @Autowired
    private LocationService locationService;

    public Reply saveLocationName() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            bot.db().getMap(STATES.name()).put(id.toString(), States.CREATING_LOCATION);
            String place = upd.getMessage().getText();
            long location = locationService.createLocation(place);
            Map<String, WorkoutState> workouts = bot.db().getMap(WORKOUTS.name());
            WorkoutState workoutState = workouts.getOrDefault(id.toString(), new WorkoutState());
            workoutState.setLocation(location);
            workouts.put(id.toString(), workoutState);
            bot.db().getMap(STATES.name()).put(id.toString(), States.MAIN_MENU);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(format("Место %s создано", place));
            sendMessage.setChatId(upd.getMessage().getChatId());
            sendMessage.setReplyMarkup(KeyboardFactory.mainKeyboard());
            try {
                bot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        };
        return Reply.of(action, update ->
                update.hasMessage()
                        && abilityBot.db().getMap(STATES.name()).get(getChatId(update).toString()) == WAITING_LOCATION_NAME
        );
    }

    public Reply newLocation() {
        return Reply.of(
                (bot, upd) -> {
                    bot.silent().send("Введите название места:", getChatId(upd));
                    Map<String, States> states = bot.db().getMap(STATES.name());
                    states.put(getChatId(upd).toString(), WAITING_LOCATION_NAME);
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(NEW_LOCATION));

    }

    public Reply viewLocations() {
        return Reply.of(
                (bot, upd) -> {
                    List<Location> locations = locationService.viewLocations(getChatId(upd));
                    String text = locations.isEmpty() ? "У вас еще нет добавленных мест:" : "Выберите место:";
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(text);
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.viewLocation(locations));
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(VIEW_LOCATIONS));

    }
}
