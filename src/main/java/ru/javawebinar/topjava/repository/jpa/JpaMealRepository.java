package ru.javawebinar.topjava.repository.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(JpaMealRepository.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.getReference(User.class, userId);
        if (meal.isNew()) {
            log.info("save meal {} from user {}", meal, userId);
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else {
            log.info("update meal {} from user {}", meal, userId);
            if (get(meal.getId(), userId) != null) {
                meal.setUser(user);
                return em.merge(meal);
            } else {
                return null;
            }
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        log.info("delete meal with id {} from user {}", id, userId);
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal with id {} from user {}", id, userId);
        Meal meal = em.find(Meal.class, id);
        return meal == null || meal.getUser().getId() != userId ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll from user {}", userId);
        return em.createNamedQuery(Meal.ALL, Meal.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        log.info("getBetweenHalfOpen");
        return em.createNamedQuery(Meal.ALL_FILTERED, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startTime", startDateTime)
                .setParameter("endTime", endDateTime).getResultList();
    }
}