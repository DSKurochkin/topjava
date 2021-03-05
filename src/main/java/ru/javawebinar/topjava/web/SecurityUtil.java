package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    static int tempUid;

    public static int authUserId() {
        return tempUid;
    }

    static void setAuthUserId(int userId) {
        tempUid = userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}