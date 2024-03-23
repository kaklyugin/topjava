package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;
import static ru.javawebinar.topjava.UserTestData.admin;
import static ru.javawebinar.topjava.UserTestData.getNew;
import static ru.javawebinar.topjava.UserTestData.getUpdated;
import static ru.javawebinar.topjava.UserTestData.guest;
import static ru.javawebinar.topjava.UserTestData.user;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractUserServiceTest extends AbstractServiceTest {
    @Autowired
    protected UserService service;
    
    @Autowired
    private CacheManager cacheManager;
    
    @Before
    public void setup() {
        cacheManager.getCache("users").clear();
    }
    
    @Test
    public void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }
    
    @Test
    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }
    
    @Test
    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }
    
    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }
    
    @Test
    public void test_b() {
//        SessionFactory sessionFactory =
//                entityManagerFactory.unwrap(SessionFactory.class);
//        Session session = sessionFactory.getCurrentSession();
//
//        if (session != null) {
//            session.clear(); // internal cache clear
//        }
//
//        Cache cache = sessionFactory.getCache();
//
//        if (cache != null) {
//            cache.evictAllRegions(); // Evict data from all query regions.
//        }
        User user = service.get(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }
    
    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }
    
    @Test
    public void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        USER_MATCHER.assertMatch(user, admin);
    }
    
    @Test
    public void test_a() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }
    
    @Test
    public void getAll() {
        List<User> all = service.getAll();
        USER_MATCHER.assertMatch(all, admin, guest, user);
    }
    
    @Test
    @Override
    public void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "  ", "password", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Set.of())));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Set.of())));
    }
}