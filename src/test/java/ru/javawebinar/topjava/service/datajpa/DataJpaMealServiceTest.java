package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;
import static ru.javawebinar.topjava.profile.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
    @Test
    public void getMealWithUserOnlyDataJPA(){
    Meal meal = service.getMealWithUser(ADMIN_MEAL_ID, ADMIN_ID);
    MEAL_MATCHER.assertMatch(meal, adminMeal1);
    USER_MATCHER.assertMatch(meal.getUser(), UserTestData.admin);

}
}
