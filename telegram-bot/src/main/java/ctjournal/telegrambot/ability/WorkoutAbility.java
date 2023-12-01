package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.db.States;
import ctjournal.telegrambot.dto.WorkoutState;
import ctjournal.telegrambot.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

import static ctjournal.telegrambot.db.States.FINISHED;
import static ctjournal.telegrambot.db.Tables.STATES;
import static ctjournal.telegrambot.db.Tables.WORKOUTS;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class WorkoutAbility implements AbilityExtension {

    @Autowired
    private AbilityBot abilityBot;
    @Autowired
    private WorkoutService workoutService;

    public Ability start() {
        return Ability.builder()
                .name("start")
                .info("Создать новую тренировку")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(this::createWorkout)
                .build();
    }

    private void createWorkout(MessageContext ctx) {
        if (getWorkout(ctx) == null || getState(ctx) == null || getState(ctx) == FINISHED) {
            long workout = workoutService.createWorkout();
            putWorkout(ctx, WorkoutState.builder().id(workout).build());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Где сегодня тренируемся?");
            sendMessage.setChatId(ctx.chatId());
            sendMessage.setReplyMarkup(KeyboardFactory.location());
            try {
                abilityBot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("У вас есть не завершенная тренировка");
            sendMessage.setChatId(ctx.chatId());
            sendMessage.setReplyMarkup(KeyboardFactory.mainKeyboard());
            try {
                abilityBot.sender().execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private WorkoutState getWorkout(MessageContext ctx) {
        Map<String, WorkoutState> map = abilityBot.db().getMap(WORKOUTS.name());
        return map.get(ctx.user().getId().toString());
    }

    private void putWorkout(MessageContext ctx, WorkoutState workoutState) {
        Map<String, WorkoutState> map = abilityBot.db().getMap(WORKOUTS.name());
        map.put(ctx.user().getId().toString(), workoutState);
    }

    private States getState(MessageContext ctx) {
        Map<String, States> map = abilityBot.db().getMap(STATES.name());
        return map.get(ctx.user().getId().toString());
    }
}
