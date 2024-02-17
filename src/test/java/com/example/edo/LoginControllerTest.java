package com.example.edo;

import com.example.edo.controllers.LoginController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
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
    @Test
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

    @Test
    public void badCredentialsShouldNotBeAllowedToLogIn() throws Exception{
        this.mockMvc.perform(post("/login").param("user", "Edo"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
