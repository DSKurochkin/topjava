package ru.javawebinar.topjava;

import org.springframework.test.context.ContextConfiguration;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    public static final int NOT_FOUND = 11;
    private static final LocalDateTime testDateTime = LocalDateTime.of(2021, Month.MARCH, 15, 10, 0);

    public static final Meal takeFromUser = new Meal(MEAL_ID_OF_USER, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0)
            , "Завтрак", 500);
    public static final Meal takeFromAdmin = new Meal(MEAL_ID_OF_ADMIN, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0)
            , "Админ ланч", 510);
    public static final Meal takeFromAdminForAllTest = new Meal(MEAL_ID_OF_ADMIN + 1, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0)
            , "Админ ужин", 1500);

    public static Meal getNew() {
        return new Meal(null, testDateTime, "New meal", 555);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(takeFromUser);
        updated.setDateTime(testDateTime);
        updated.setDescription("UpdatedDescr");
        updated.setCalories(777);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Collection<Meal> actual, Meal... expected) {
        List<Meal> actualMeal = new ArrayList<>(actual);
        for (int i = 0; i < actualMeal.size(); i++) {
            assertMatch(actualMeal.get(i), expected[i]);
        }
    }

    public static LocalDateTime getUserDuplicateTime() {
        return takeFromUser.getDateTime();
    }

}
