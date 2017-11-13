package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = null;
        if (repository.get(id).getUserId() == userId) {
            meal = repository.get(id);
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values();
    }

    @Override
    public Collection<Meal> getAllWithTimeAndDate(LocalDateTime startDate, LocalDateTime startTime, LocalDateTime endDate, LocalDateTime endTime) {
        return null;
    }
}

