package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.JdbcValidation(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) != 0) {
            deleteAllRoles(user.getId());
        } else {
            return null;
        }
        batchUpd(user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return deleteAllRoles(id)
                && jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRolesFromDB(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRolesFromDB(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        Map<Integer, User> users = new LinkedHashMap<>();
        String SQL = "SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day" +
                ", r.role FROM users as u LEFT JOIN user_roles as r ON u.id=r.user_id ORDER BY name, email";
        jdbcTemplate.query(SQL, rs -> {
                    System.out.println("Name " + rs.getString("name"));
                    do {
                        Integer id = rs.getInt("id");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        String password = rs.getString("password");
                        Date registered = rs.getDate("registered");
                        boolean enabled = rs.getBoolean("enabled");
                        int calories = rs.getInt("calories_per_day");
                        User user = users.get(id);
                        Collection<Role> roles = Set.of();
                        if (user == null) {
                            users.put(id, new User(id, name, email, password, calories, enabled, registered, roles));
                        }
                        users.get(id).getRoles().add(Role.valueOf(rs.getString("role")));
                    }
                    while (rs.next());
                }
        );
        return List.of(users.values().toArray(new User[]{}));
    }

    @Override
    @Transactional
    public void addRole(int id, Role role) {
        jdbcTemplate.update("INSERT INTO user_roles(user_id, role) VALUES (?,?)"
                , id
                , role.toString());
    }

    @Override
    @Transactional
    public void deleteRole(int id, Role role) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=? AND role=?"
                , id
                , role.toString());
    }

    @Override
    @Transactional
    public void editRole(int id, Role role) {
        jdbcTemplate.update("UPDATE user_roles SET role=? WHERE user_id=?"
                , role.toString()
                , id);
    }

    private List<Role> getRolesFromDB(int id) {
        List<Role> roles = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?"
                , new RowMapper<Role>() {
                    @Override
                    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return Role.valueOf(rs.getString("role"));
                    }
                }
                , id);
        return roles;
    }

    private User setRolesFromDB(User user) {
        if (user != null) {
            user.setRoles(getRolesFromDB(user.getId()));
        }
        return user;
    }

    private void batchUpd(User user) {
        List<Role> roles = new ArrayList<Role>(user.getRoles());
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role)  VALUES (?,?)"
                , new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, user.getId());
                        ps.setString(2, roles.get(i).toString());
                    }

                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }

    private boolean deleteAllRoles(int id) {
        return jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", id) != 0;

    }
}