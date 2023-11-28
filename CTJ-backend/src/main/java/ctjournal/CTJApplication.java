package ctjournal;

import ctjournal.repository.WorkoutRepository;
import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ctjournal.repository.ExerciseRepository;
import ctjournal.repository.LocationRepository;


@SpringBootApplication
public class CTJApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(CTJApplication.class);
        WorkoutRepository bean = run.getBean(WorkoutRepository.class);
        LocationRepository locationRepository = run.getBean(LocationRepository.class);
        ExerciseRepository exerciseRepository = run.getBean(ExerciseRepository.class);

//        TimeExercise timeExercise = new TimeExercise();
//        timeExercise.setId(1);
//        timeExercise.setDurationSeconds((short)123);
//        timeExercise.setName("run");
//        Workout workout = new Workout();
//        workout.setId(1);
//        timeExercise.setWorkout(workout);
//        RepeatsExercise repeatsExercise = new RepeatsExercise();
//        repeatsExercise.setRepeats((short) 324);
//        repeatsExercise.setId(2);
//        repeatsExercise.setName("pullUps");
//        repeatsExercise.setWorkout(workout);
//        Location location = new Location(1, "name", "ru", null, false);
//        locationRepository.save(location);
//
//        bean.save(new Workout(0,
//                new Date(),
//                System.currentTimeMillis(),
//                System.currentTimeMillis(),
//                location,
//                List.of(timeExercise), null, List.of(repeatsExercise), DifficultyLevel.EASY, ""));
//        exerciseRepository.save(timeExercise);
//        exerciseRepository.save(repeatsExercise);
//        Optional<AbstractExercise> byId = exerciseRepository.findById(1l);
//        Optional<AbstractExercise> byId1 = exerciseRepository.findById(2l);
//        Optional<Workout> byId2 = bean.findById(1l);
        Console.main(args);
    }
}
