package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
        MealTestData.MEAL_MATCHER.assertMatch(user.getMeals(), MealTestData.meals);
    }

    @Test
    public void getWithMealsNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithMeals(1));
    }

    @Test
    public void getAdminWithMeals() {
        User admin = service.getWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(admin, admin);
        MealTestData.MEAL_MATCHER.assertMatch(admin.getMeals()
                , List.of(MealTestData.adminMeal2, MealTestData.adminMeal1));
    }
}