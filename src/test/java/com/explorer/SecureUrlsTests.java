package com.explorer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.NoSuchFileException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
/**
 * Created by Michael on 17.07.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml", "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class SecureUrlsTests {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilter;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).addFilter(springSecurityFilter).build();
    }

    /**
     * Проверка доступа к различным путям для анонимных пользователей
     * @throws Exception
     */
    @Test
    public void testAnonymous() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isFound()).andExpect(redirectedUrl("/index"));
        mockMvc.perform(get("/index")).andExpect(status().isOk()).andExpect(view().name("index"));
        mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("index"));
        mockMvc.perform(get("/server")).andExpect(status().isFound()).andExpect(redirectedUrl("http://localhost/login"));
        mockMvc.perform(get("/home")).andExpect(status().isFound()).andExpect(redirectedUrl("http://localhost/login"));
        mockMvc.perform(get("/shared")).andExpect(status().isFound()).andExpect(redirectedUrl("http://localhost/login"));
    }

    /**
     * Проверка доступа к различным путям для авторизованных пользователей с правами обычного пользователя
     * (NoSuchFileException - нормально т.к. не находится папка пользователя на диске машины на которой проводится тестирование)
     * @throws Exception
     */
    @Test
    public void testUser() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "password");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        mockMvc.perform(get("/index").session(session)).andExpect(status().isOk()).andExpect(view().name("index"));
        mockMvc.perform(get("/home").session(session)).andExpect(status().isOk()).andExpect(view().name("files"));
        mockMvc.perform(get("/shared").session(session)).andExpect(status().isOk()).andExpect(view().name("files"));
        mockMvc.perform(get("/server").session(session)).andExpect(status().isForbidden());
    }

    /**
     * Проверка доступа к различным путям для авторизованных пользователей с правами администратора
     * (NoSuchFileException - нормально т.к. не находится папка пользователя на диске машины на которой проводится тестирование)
     * @throws Exception
     */
    @Test
    public void testAdmin() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken("admin", "adminpass");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        mockMvc.perform(get("/index").session(session)).andExpect(status().isOk()).andExpect(view().name("index"));
        mockMvc.perform(get("/home").session(session)).andExpect(status().isOk()).andExpect(view().name("files"));
        mockMvc.perform(get("/shared").session(session)).andExpect(status().isOk()).andExpect(view().name("files"));
        mockMvc.perform(get("/server").session(session)).andExpect(status().isOk()).andExpect(view().name("files"));
    }
}
