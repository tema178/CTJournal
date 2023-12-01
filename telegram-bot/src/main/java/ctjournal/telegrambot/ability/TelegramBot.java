package ctjournal.telegrambot.ability;

import ctjournal.telegrambot.config.BotConfig;
import ctjournal.telegrambot.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class TelegramBot extends AbilityBot {

    private final BotConfig botConfig;
    private final GradeService gradeService;

    @Autowired
    public TelegramBot(BotConfig botConfig, GradeService gradeService,
                       @Lazy WorkoutAbility workoutAbility,
                       @Lazy LocationAbility locationAbility) {
        super(botConfig.getToken(), botConfig.getBotName());
        this.botConfig = botConfig;
        this.gradeService = gradeService;
        addExtensions(locationAbility, workoutAbility);
    }

    @Override
    public long creatorId() {
        return botConfig.getCreatorId();
    }

    public Ability getGrades() {
        return Ability.builder()
                .name("grades")
                .info("says hello world!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(gradeService.getGrades(), ctx.chatId()))
                .build();

    }


}
