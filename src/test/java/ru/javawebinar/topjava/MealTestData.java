package ru.javawebinar.topjava;

import org.springframework.test.context.ContextConfiguration;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID_OF_USER = START_SEQ + 2;
    public static final int MEAL_ID_OF_ADMIN = START_SEQ + 3;


    public static Meal takeFromUser() {
        System.out.println("hi, im from data");
        return new Meal(MEAL_ID_OF_USER, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0)
                , "Завтрак", 500);
    }

    public static Meal takeFromAdmin() {
        return new Meal(MEAL_ID_OF_ADMIN, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0)
                , "Завтрак", 510);
    }

    public static LocalDateTime getUserDuplicateTime() {
        return takeFromUser().getDateTime();
    }

}
