package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void createEntityShouldBeEquals() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void deletedWithMissingIdShouldThrowException() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void getEntityShouldBeEqualsToRef() {
        Meal meal = service.get(MEAL_ID_OF_ADMIN, ADMIN_ID);
        assertMatch(meal, takeFromAdmin);
    }

    @Test
    public void getWithMissingIdShouldThrowException() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void updateEntityShouldBeEqualsToRef() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID_OF_USER, USER_ID), getUpdated());
    }

    @Test
    public void getListEntitiesShouldBeEqualsToRef() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, takeFromAdmin, takeFromAdminForAllTest);
    }


    @Test(expected = NotFoundException.class)
    public void getAnotherShouldThrowException() {

        service.get(MealTestData.takeFromUser.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherThrowException() {
        service.delete(MealTestData.takeFromAdmin.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherShouldThrowException() {
        service.update(MealTestData.takeFromUser, ADMIN_ID);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeCreate() {

        Meal duplicateMeal = new Meal(null, MealTestData.getUserDuplicateTime()
                , "Some description", 666);
        service.create(duplicateMeal, USER_ID);
    }
}