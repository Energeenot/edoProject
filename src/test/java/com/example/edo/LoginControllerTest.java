package com.example.edo;

import com.example.edo.controllers.LoginController;
import com.example.edo.models.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginController loginController;

    @Autowired
    private Validator validator;

    @BeforeClass
    public static void testingAnnotationBeforeClass(){
//        Любые поля которые в этом методе инициализируются должны быть статическими
        System.out.println("Метод с аннотацией beforeClass запускается один раз перед выполнением тестов");
    }

    @Before
    public void testingAnnotationBefore(){
        System.out.println("Метод с аннотацией before запускается перед каждым тестовым методом");
    }

    @After
    public void testingAnnotationAfter(){
        System.out.println("Метод с аннотацией after запускается после каждого тестового метода");
    }

    @AfterClass
    public static void testingAnnotationAfterClass(){
        System.out.println("Метод с аннотацией afterClass запускается один раз после всех тестовых методов");
    }

    @Test(timeout = 1000)
    public void loginControllerShouldNotBeEmpty() throws Exception{
        assertThat(loginController).isNotNull();
    }

    @Test
    public void loginControllerShouldContainMail() throws Exception{
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Эл. почта")));
    }

    @Test
    public void validatorPageShouldRedirectToTheLoginPage() throws Exception{
        this.mockMvc.perform(get("/validator"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testAccountShouldLogIn() throws Exception{
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("pochta@yandex.ru").password("1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/personalAccount"));
    }

    @Ignore // лучше не использовать, если надо чтобы заваленные тесты игнорились лучше использовать класс assume и его методы
    @Test(expected = BadCredentialsException.class)
    public void testAccountShouldNotLogin() throws Exception {
        this.mockMvc.perform(post("/login").param("user", "pochta@yandex.ru"))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("pochta@yandex.ru").password("123"))
                .andDo(print());
    }

    @Test()
    public void badCredentialsShouldNotBeAllowedToLogIn() throws Exception{
        this.mockMvc.perform(post("/login").param("user", "Edo"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void mailShouldBeValid() throws Exception{
        String validMail = "test@mail.ru";
        Set<ConstraintViolation<User>> violations = Validation.buildDefaultValidatorFactory()
                .getValidator().validateValue(User.class, "mail", validMail);
        assertTrue("Valid email should not produce any violations", violations.isEmpty());
    }

    @Test
    public void mailShouldBeInvalid() throws Exception{
        String invalidMail = "invalidmail.ru";
        Set<ConstraintViolation<User>> violations = validator.validateValue(User.class, "mail", invalidMail);
        assertFalse("Invalid email should produce violations", violations.isEmpty());
        assertEquals("Only one violation is expected for invalid email", 1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Invalid email message should match", "Введите корректный почтовый адрес",violation.getMessage());
    }

    @Test
    public void passwordShouldBeValid() throws Exception{
        String validPassword = "1";
        Set<ConstraintViolation<User>> violations = validator.validateValue(User.class, "password", validPassword);
        assertTrue("Valid password should not produce any violations", violations.isEmpty());
    }

    @Test
    public void passwordShouldBeInvalid() throws Exception{
        String invalidPassword = "";
        Set<ConstraintViolation<User>> violations = validator.validateValue(User.class, "password", invalidPassword);
        assertFalse("Invalid password should produce violations", violations.isEmpty());
        assertEquals("Only one violation is expected for invalid password", 1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Invalid password message should match", "Поле пароль должно быть заполнено", violation.getMessage());
    }
}
