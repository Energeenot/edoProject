package com.example.edo;

import com.example.edo.models.User;
import com.example.edo.services.UserService;
import org.assertj.core.api.Assertions;
import org.hibernate.annotations.processing.SQL;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/applicationTest.properties")
public class DataBaseTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/createTablesBefore.sql"));
        tables.addScript(new ClassPathResource("/createUserBefore.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);
    }

    @After
//    @SQL(value = "/deleteTablesAfter.sql")
    public void dismantle(){
        System.out.println("Удаление таблиц");
//        JdbcTestUtils.dropTables(jdbcTemplate, "files");
        JdbcTestUtils.dropTables(jdbcTemplate, "user_roles");
        JdbcTestUtils.dropTables(jdbcTemplate, "user");
    }

    @Test
    public void userShouldExists(){
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
        assertThat("В базе данных колличество пользователей > 0", count, greaterThan(0));
    }

    @Test
    public void shouldBeThreeUsers(){
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
        assertThat("В базе данных не три пользователя", count, equalTo(3));
    }

    @Test
    public void usersDataShouldBeSaved(){
        User testUser = new User();
        testUser.setName("John Doe");
        testUser.setMail("john.doe@mail.ru");
        testUser.setPassword("$2a$08$Ak0lzwpHVcusnPrPHurxE..zzV3QkdrDmzVNFDhjZwNheADQmM7LO");
        userService.createUser(testUser);
//        Assertions.assertThat(testUser.getMail().equals("john.doe@mail.ru"));
        Assertions.assertThat(testUser.getMail()).isEqualTo("john.doe@mail.ru");

        List<User> users = new ArrayList<>();

            // извлекаем пользователей из базы данных и добавляем в список
            users = jdbcTemplate.query("SELECT * FROM user", (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setMail(rs.getString("mail"));
                return user;
            });

        // Выводим информацию о пользователях в консоль
        System.out.println("Список пользователей:");
        for (User user : users) {
            System.out.println("ID: " + user.getId());
            System.out.println("Имя: " + user.getName());
//            System.out.println("Имя пользователя: " + user.getUsername());
            System.out.println("Email: " + user.getMail());
            System.out.println();
        }
    }

    @Test
    public void usersDataShouldBeDeleted(){
        User testUser = new User();
        testUser.setName("John Doe");
        testUser.setMail("john.doe@mail.ru");
        testUser.setPassword("$2a$08$Ak0lzwpHVcusnPrPHurxE..zzV3QkdrDmzVNFDhjZwNheADQmM7LO");
        userService.createUser(testUser);

        jdbcTemplate.update("DELETE FROM user WHERE id = ?", 4);

        List<User> users = new ArrayList<>();

        // извлекаем пользователей из базы данных и добавляем в список
        users = jdbcTemplate.query("SELECT * FROM user", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setMail(rs.getString("mail"));
            return user;
        });

        // Выводим информацию о пользователях в консоль
        System.out.println("Список пользователей:");
        for (User user : users) {
            System.out.println("ID: " + user.getId());
            System.out.println("Имя: " + user.getName());
//            System.out.println("Имя пользователя: " + user.getUsername());
            System.out.println("Email: " + user.getMail());
            System.out.println();
        }
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
        assertThat("В базе данных не три пользователя", count, equalTo(3));
    }
}
