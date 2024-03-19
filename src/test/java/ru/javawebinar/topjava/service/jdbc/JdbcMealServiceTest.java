package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import java.util.Arrays;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
    @Autowired
    Environment environment;

    @Before
    public void checkProfile() {
        Assume.assumeFalse(Arrays.asList(environment.getActiveProfiles()).contains("jdbc"));
    }

    @Override
    @Ignore
    public void createWithException() throws Exception {
    }
}