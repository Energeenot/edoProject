package com.example.edo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void connectionToDatabaseShouldBeMade() {
        try {
            assertNotNull(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void registrationShouldBeOk() throws Exception {
        this.mockMvc.perform(post("/registration")
                        .with(csrf())
                .param("username", "testUser")
                .param("email", "test@mail.com")
                .param("password", "testPassword"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void logoutShouldRedirectToLoginPage() throws Exception {
        this.mockMvc.perform(post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

//        // Проверяем, что сеанс пользователя завершён
//        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        MockHttpServletResponse response = new MockHttpServletResponse();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        logoutHandler.logout(request, response, auth); // для завершения сеанса пользователя. Это удалит информацию об аутентификации из контекста безопасности
//
//        // Убеждаемся, что пользователь перенаправлен на страницу входа или на другую страницу, где доступ только для авторизованных пользователей
//        this.mockMvc.perform(get("/validator"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    public void testLogoutFailureWhenNotAuthenticated() throws Exception {
        this.mockMvc.perform(post("/logout"))
                .andExpect(status().isForbidden());
    }
}