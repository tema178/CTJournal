package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.dto.ClimbingSession;
import ctjournal.telegrambot.dto.Route;
import ctjournal.telegrambot.dto.SendStyle;
import ctjournal.telegrambot.dto.WorkoutState;
import ctjournal.telegrambot.repository.RouteRepository;
import ctjournal.telegrambot.repository.StatesRepository;
import ctjournal.telegrambot.repository.WorkoutRepository;
import ctjournal.telegrambot.service.RouteService;
import ctjournal.telegrambot.service.WorkoutService;
import ctjournal.telegrambot.utils.KeyboardFactory;
import ctjournal.telegrambot.utils.RouteToStringTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.BiConsumer;

import static ctjournal.telegrambot.db.States.ROUTE_MENU;
import static ctjournal.telegrambot.db.States.WAITING_ATTEMPTS;
import static ctjournal.telegrambot.db.States.WAITING_ROUTE_NAME;
import static ctjournal.telegrambot.utils.KeyboardFactory.ADD_ROUTE;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_ATTEMPTS;
import static ctjournal.telegrambot.utils.KeyboardFactory.EDIT_SEND_STYLE;
import static ctjournal.telegrambot.utils.KeyboardFactory.SET_GRADE;
import static ctjournal.telegrambot.utils.KeyboardFactory.SET_SEND_STYLE;
import static java.lang.String.format;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class RouteAbility implements AbilityExtension {

    @Autowired
    private AbilityBot abilityBot;
    @Autowired
    private RouteService service;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private RouteRepository routeRepository;

    public Reply addRouteName() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            bot.silent().send("Введите название трассы:", id);
            statesRepository.save(getChatId(upd).toString(), WAITING_ROUTE_NAME);
        };
        return Reply.of(action, upd ->
                upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(ADD_ROUTE));
    }

    public Reply createRoute() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            Long id = getChatId(upd);
            String name = upd.getMessage().getText();
            WorkoutState workout = workoutRepository.findByUserId(id.toString());
            Route route = service.create(name, new ClimbingSession(workout.getClimbingSession()));
            routeRepository.save(id.toString(), route);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(format("Трасса %s добавлена", name));
            sendMessage.setChatId(upd.getMessage().getChatId());
            sendMessage.setReplyMarkup(KeyboardFactory.routeKeyboard(route));
            try {
                bot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        };
        return Reply.of(action, update ->
                update.hasMessage()
                        && statesRepository.findByUserId(getChatId(update).toString()) == WAITING_ROUTE_NAME
        );
    }

    public Reply editStyle() {
        return Reply.of(
                (bot, upd) -> {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Выберите стиль пролаза:");
                    sendMessage.setChatId(getChatId(upd));
                    sendMessage.setReplyMarkup(KeyboardFactory.viewStyles());
                    try {
                        bot.sender().execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_SEND_STYLE));

    }

    public Reply setStyle() {
        return Reply.of(
                (bot, upd) -> {
                    String sendStyle = upd.getCallbackQuery().getData().substring(SET_GRADE.length());
                    Long id = getChatId(upd);
                    Route route = routeRepository.findByUserId(id.toString());
                    route.setSendStyle(SendStyle.valueOf(sendStyle));
                    routeRepository.save(id.toString(), route);
                    service.update(route);

                    sendRouteKeyboard(bot, upd, route);
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().startsWith(SET_SEND_STYLE));

    }

    public Reply editAttempts() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    statesRepository.save(id.toString(), WAITING_ATTEMPTS);
                    bot.silent().send("Введите количество попыток:", getChatId(upd));
                },
                upd -> upd.hasCallbackQuery() && upd.getCallbackQuery().getData().equals(EDIT_ATTEMPTS));

    }

    public Reply setAttempts() {
        return Reply.of(
                (bot, upd) -> {
                    Long id = getChatId(upd);
                    short attempts;
                    try {
                        attempts = Short.parseShort(upd.getMessage().getText());
                        if (attempts < 0) {
                            bot.silent().send("Число не должно быть отрицательным. Попробуйте снова", id);
                        } else {
                            Route route = routeRepository.findByUserId(id.toString());
                            route.setAttempts(attempts);
                            routeRepository.save(id.toString(), route);
                            service.update(route);
                            statesRepository.save(id.toString(), ROUTE_MENU);

                            sendRouteKeyboard(bot, upd, route);
                        }
                    } catch (NumberFormatException e) {
                        bot.silent().send("Некорректное число. Попробуйте снова", id);
                    }
                },
                upd ->
                        upd.hasMessage()
                                && statesRepository.findByUserId(getChatId(upd).toString()) == WAITING_ATTEMPTS);

    }

    private static void sendRouteKeyboard(BaseAbilityBot bot, Update upd, Route route) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Данные изменены:\n" + RouteToStringTransformer.transform(route));
        sendMessage.setChatId(getChatId(upd));
        sendMessage.setReplyMarkup(KeyboardFactory.routeKeyboard(route));
        try {
            bot.sender().execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
