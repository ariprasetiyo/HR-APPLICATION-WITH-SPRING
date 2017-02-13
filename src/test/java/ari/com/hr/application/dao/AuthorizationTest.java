/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao;

import ari.com.hr.application.model.SysAuthorization;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ari-prasetiyo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationTest {

    Logger log = Logger.getLogger(AuthorizationTest.class);
    @Autowired
    SysAuthorizationDao dsSysAuthorization;

    //@Test
    public void getAuthorizationById() {
        SysAuthorization result = dsSysAuthorization.findOne(Long.valueOf(132));
        // log.debug("test--" + result.getSysMenu().getMenusName());
        Assert.assertNotNull(result.getSysMenu().getMenusName());
    }

    @Test
    public void getAuthorizationJoinSysMenu() {
        SysAuthorization result = dsSysAuthorization.getDataAuthorizationById(132);

        log.debug("test-- getAuthorizationJoinSysMenu : " + result.getSysMenu().getMenusName());
        Assert.assertEquals(result.getId(), Long.valueOf(132));
        Assert.assertNotNull(result.getSysMenu().getMenusName());
    }
}
