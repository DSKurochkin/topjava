package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends UserServiceTest {
    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public void deleteNotFound() {
        super.deleteNotFound();
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void duplicateMailCreate() {
        super.duplicateMailCreate();
    }

    @Override
    public void delete() {
        super.delete();
    }

    @Override
    public void get() {
        super.get();
    }

    @Override
    public void getNotFound() {
        super.getNotFound();
    }

    @Override
    public void getByEmail() {
        super.getByEmail();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void getAll() {
        super.getAll();
    }
}
