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

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test(expected = NotFoundException.class)
    public void getAnotherShouldThrowException() {

        service.get(MealTestData.takeFromUser().getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherThrowException() {
        service.delete(MealTestData.takeFromAdmin().getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherShouldThrowException() {
        service.update(MealTestData.takeFromUser(), ADMIN_ID);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeCreate() {

        Meal duplicateMeal = new Meal(null, MealTestData.getUserDuplicateTime()
                , "Some description", 666);
        service.create(duplicateMeal, USER_ID);
    }
}