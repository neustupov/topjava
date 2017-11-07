package ru.javawebinar.topjava.dao.daoImpl;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoImplInMemory implements MealDao {

    private static List<Meal> ourInstance;
    static AtomicInteger counter = new AtomicInteger(1);
    static int CALORIES_PER_DAY = 2000;

   /* static {
        ourInstance.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак",500, 1));
        incrementCounter();
        ourInstance.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, 2));
        incrementCounter();
        ourInstance.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, 3));
        incrementCounter();
        ourInstance.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, 4));
        incrementCounter();
        ourInstance.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, 5));
        incrementCounter();
        ourInstance.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, 6));
        incrementCounter();
    }*/

    public static List<Meal> getInstance() {
        if (ourInstance == null) {
            return new CopyOnWriteArrayList<>();
        }
        return ourInstance;
    }

    public MealDaoImplInMemory() {
    }

    static void incrementCounter() {
        counter.getAndIncrement();
    }

    static void decrementCounter() {
        counter.decrementAndGet();
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void add(Meal meal) {
        getInstance().add(meal);
        incrementCounter();
    }

    @Override
    public void edit(Meal meal) {
        getInstance().add(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        getInstance().remove(id);
        decrementCounter();
    }

    @Override
    public Meal getMeal(int id) {
        return getInstance().get(id);
    }

    @Override
    public List getAllMeals() {
        return MealsUtil.createWithExceedWithoutTime(getInstance(), CALORIES_PER_DAY);
    }
}
