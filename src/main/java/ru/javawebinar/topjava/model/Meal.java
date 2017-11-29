package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.user.id =:userId AND m.id=:id"),
        @NamedQuery(name = Meal.WITH_ID, query = "SELECT m FROM Meal m WHERE m.user=:userId AND m.id=:id"),
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m  ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
})

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "datetime"}, name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String WITH_ID = "Meal.getWithId";
    public static final String ALL_SORTED = "Meal.getSorted";
    public static final String BETWEEN = "Meal.getBetween";

    @Column(name = "datetime", nullable = false, unique = true)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
