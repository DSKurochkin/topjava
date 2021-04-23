package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import static ru.javawebinar.topjava.profile.Profiles.POSTGRES_DB;
import java.time.LocalDateTime;

@Repository
@Profile(POSTGRES_DB)
public class JdbcMealRepositoryForPostgres extends AbstractJdbcMealRepository {
    public JdbcMealRepositoryForPostgres(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public <T> T getNeedTime(LocalDateTime ldt) {
        return (T)ldt;
    }

}
