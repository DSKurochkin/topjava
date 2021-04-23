package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import javax.persistence.OrderBy;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    List<Meal> getAllByUserIdOrderByDateTimeDesc(Integer id);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId " +
            "AND m.dateTime >= :start " +
            "AND m.dateTime < :end " +
            "ORDER BY m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(@Param("userId") int userId
            , @Param("start") LocalDateTime start
            , @Param("end") LocalDateTime end);

    @Modifying
    @Transactional
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userid);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user u WHERE m.id=:id AND m.user.id=:userId ")
    Meal getMealWithUser(@Param("id") int id, @Param("userId") int userId);
}
