/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.rest;

import ari.com.hr.application.dao.SysAuthorizationDao;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationApiTest {

    Logger log = Logger.getLogger(AuthorizationApiTest.class);

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private SysAuthorizationDao dsSysAuthorization;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    //@Test
    public void apiUpdateTest() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/admin/v1/api/authorization/update/{id}", 69)
                //.header("_csrf","2972413a-2daa-4e92-aacf-ed85d0a99e90")
                //.with((RequestPostProcessor) csrfTokenRepository())
                .contentType(MediaType.APPLICATION_JSON)
                //.content("{\"username\":\"ari\",\"password\":\"1234\"}")
                .param("vInsert", "1")
                .param("vUpdate", "1")
                .param("vDelete", "1")
                .param("vDisable", "1"))
                .andDo(print())
                //.andExpect(forwardedUrl("login"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("69"))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
    }

    @Test
    public void apiAddAuthorizationTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/admin/v1/api/authorization/addMenu/{id}", Long.valueOf(2))
                .contentType(MediaType.APPLICATION_JSON)
                .param("vInsert", "true")
                .param("vUpdate", "true")
                .param("vDelete", "true")
                .param("vDisable", "true")
                .param("modelMenuId", "2")
                .param("modelParentMenuId", "183"))
                .andDo(print())
                //   .andExpect(status().isUnauthorized())
                // .andExpect(status().reason(containsString("Bad credentials")))
                //                .andExpect(unauthenticated())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        log.debug(content);
        JSONObject objDetailUser = new JSONObject(content);

        Object idNewMenu = objDetailUser.get("id");

        Object menuName = objDetailUser.get("menuName");
        Assert.assertNotNull(idNewMenu);
        log.debug("test-- idNewMenu : " + idNewMenu);
        log.debug("test-- menu name : " + menuName);
        dsSysAuthorization.delete(Long.valueOf(idNewMenu.toString()));

    }

}
