package ru.javawebinar.topjava;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {

    private static int id;

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public int getId() {
        return id;
    }

    public static void setId(int id) {
        id = id;
    }
}