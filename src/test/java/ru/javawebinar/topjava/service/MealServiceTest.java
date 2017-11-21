package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.MealTestData;

import java.time.LocalDateTime;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService mealService;

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(100002, 100001);
        MealTestData.assertMatch(meal, MealTestData.MEAL_OF_USER1);
    }

    @Test
    public void delete() throws Exception {
        mealService.delete(100002, 100001);
        MealTestData.assertMatch(mealService.getAll(100000), MealTestData.MEAL_OF_USER2);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(100003);
        updated.setDateTime(LocalDateTime.now());
        updated.setDescription("updated");
        updated.setCalories(100500);
        mealService.update(updated, 100001);
        MealTestData.assertMatch(mealService.get(100004, 100001), updated);
    }

}