package com.centiglobe.centiglobe_assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Test cases that verify the URL level of security by using the Spring MVC test framework.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 * @author Divya Srivastava
 */
@SpringBootTest
class UrlLevelSecurityTests {

    private static final String PAYLOAD = "{\"id\": 1, \"memberName\": \"saruman\", \"maxConnections\": 2, "
            + "\"status\": \"Active\"}";

    private static final String UPDATE_PAYLOAD = "{\"memberName\": \"galadriel\", \"maxConnections\": 22, "
            + "\"status\": \"Active\"}";

    @Autowired
    WebApplicationContext context;
    @Autowired
    FilterChainProxy filterChain;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {

        this.mvc = webAppContextSetup(context).addFilters(filterChain).build();

        SecurityContextHolder.clearContext();
    }

    @Test
    void allowsAccessToRootResource() throws Exception {

        mvc.perform(get("/api").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON));
    }

    @Test
    void rejectsPutAccessToCollectionResource() throws Exception {
        mvc.perform(put("/api/configs").content(PAYLOAD).accept(MediaTypes.HAL_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void allowsPostRequestsButRejectsDeleteForUser() throws Exception {

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.getEncoder().encodeToString(("john:john").getBytes()));

        var location = mvc.perform(post("/api/configs").headers(headers).content(PAYLOAD))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assert location != null;
        mvc.perform(delete(location)).andExpect(status().isUnauthorized());
    }

    @Test
    void allowsPostAndUpdateRequestsForAdmin() throws Exception {

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.getEncoder().encodeToString(("sam:sam").getBytes()));

        var location = mvc.perform(post("/api/configs").headers(headers).content(PAYLOAD))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        assert location != null;
        mvc.perform(put(location).headers(headers).content(UPDATE_PAYLOAD)).andExpect(status().isOk());
    }
}