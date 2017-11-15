package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {

    Meal save(Meal meal);

    void delete(int id) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    Collection<MealWithExceed> getAll(int userId);

    Collection<Meal> getAllWithTimeAndDate(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, int idUser);
}