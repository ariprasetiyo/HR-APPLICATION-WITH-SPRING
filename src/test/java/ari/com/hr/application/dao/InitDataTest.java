/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao;

import ari.com.hr.application.dto.SysAuthorizationDto;
import ari.com.hr.application.model.SysAuthorization;
import ari.com.hr.application.model.SysRoles;
import ari.com.hr.application.model.SysUser;
import ari.com.hr.application.model.SysUserRoles;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ari-prasetiyo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDataTest {

    @Autowired
    SysRolesDao sysRolesDao;

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysAuthorizationDao sysAuthorizationDao;

    @Autowired
    SysUserRolesDao sysUserRolesDao;

    //@Before
    @Ignore
    public void initDataRoles() {
        SysRoles sysRoles = new SysRoles();
        sysRoles.setRoleName("admin");
        sysRoles = sysRolesDao.save(sysRoles);
        Assert.assertNotNull(sysRoles.getId());

        sysRoles = new SysRoles();
        sysRoles.setRoleName("approval");
        sysRoles = sysRolesDao.save(sysRoles);
        Assert.assertNotNull(sysRoles.getId());

        sysRoles = new SysRoles();
        sysRoles.setRoleName("user");
        sysRoles = sysRolesDao.save(sysRoles);
        Assert.assertNotNull(sysRoles.getId());

        sysRoles = new SysRoles();
        sysRoles.setRoleName("public");
        sysRoles = sysRolesDao.save(sysRoles);
        Assert.assertNotNull(sysRoles.getId());
    }

    @Before
    //@Ignore
    public void initDataSysAuthorization() {
        sysAuthorizationDao.deleteAll();

        Long idRoles = sysRolesDao.getIdByName("admin");
        Assert.assertNotNull(idRoles);
        SysAuthorization sysAuthorization = new SysAuthorization();

        //Parent
        sysAuthorization.setPatternDispatcherUrl(null);
        sysAuthorization.setNameMenu("Dashboard");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setParent(null);
        sysAuthorization.setSysRoles(idRoles);
        SysAuthorization parentDashBoard = sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent child
        sysAuthorization.setPatternDispatcherUrl("/Dashboard/V1");
        sysAuthorization.setNameMenu("Dashboard V1");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorization.setParent(parentDashBoard);
        sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent child
        sysAuthorization.setPatternDispatcherUrl("/Dashboard/V2");
        sysAuthorization.setNameMenu("Dashboard V2");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorization.setParent(parentDashBoard);
        sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl(null);
        sysAuthorization.setNameMenu("Utility");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setParent(null);
        sysAuthorization.setSysRoles(idRoles);
        SysAuthorization parentUtility = sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent child
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl(null);
        sysAuthorization.setNameMenu("Setting User");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setParent(parentUtility);
        sysAuthorization.setSysRoles(idRoles);
        SysAuthorization parentSettingUser = sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent Parent child
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/Utility/SettingUser/User");
        sysAuthorization.setNameMenu("User");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setParent(parentSettingUser);
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent Parent child
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/Utility/SettingUser/Authorization");
        sysAuthorization.setNameMenu("Authorization");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setParent(parentSettingUser);
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent child
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/Utility/ProfileSetting");
        sysAuthorization.setNameMenu("Profile Setting");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setParent(parentUtility);
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent child
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/Utility/ReportSetting");
        sysAuthorization.setNameMenu("Report Setting");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setParent(parentUtility);
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        //Parent child
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/Utility/CalendarSetting");
        sysAuthorization.setNameMenu("Calendar Setting");
        sysAuthorization.setIsDelete(true);
        sysAuthorization.setIsInsert(true);
        sysAuthorization.setIsRead(true);
        sysAuthorization.setIsUpdate(true);
        sysAuthorization.setParent(parentUtility);
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);
        Assert.assertNotNull(sysAuthorization.getId());

        idRoles = sysRolesDao.getIdByName("user");
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/user/**");
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/dashboard/**");
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);

        Assert.assertNotNull(sysAuthorization.getId());

        idRoles = sysRolesDao.getIdByName("approval");
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/approval/**");
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/dashboard/**");
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);

        Assert.assertNotNull(sysAuthorization.getId());

        idRoles = sysRolesDao.getIdByName("public");
        sysAuthorization = new SysAuthorization();
        sysAuthorization.setPatternDispatcherUrl("/public/**");
        sysAuthorization.setSysRoles(idRoles);
        sysAuthorizationDao.save(sysAuthorization);

        Assert.assertNotNull(sysAuthorization.getId());
    }

    //@Test
    public void initDataSysUser() {
        sysUserRolesDao.deleteAll();
        sysUserDao.deleteAll();

        Long idSysRoles = sysRolesDao.getIdByName("admin");
        Assert.assertNotNull(idSysRoles);

        SysUser sysUser = new SysUser();
        sysUser.setUsername("ari");
        sysUser.setPassword("1234");
        sysUser.setEmail("prasetiyooo@gmail.com");
        sysUser.setIsActive(true);
        sysUser.setNoHp("085645480401");
        sysUser = sysUserDao.save(sysUser);
        Assert.assertNotNull(sysUser.getId());

        SysUserRoles sysUserRoles = new SysUserRoles();
        sysUserRoles.setSysUser(sysUser);
        sysUserRoles.setSysRoles(idSysRoles);
        sysUserRolesDao.save(sysUserRoles);
        Assert.assertNotNull(sysUserRoles.getId());

        sysUser = new SysUser();
        sysUser.setUsername("ari prasetiyo");
        sysUser.setPassword("12345");
        sysUser.setEmail("prasetiyooo2@gmail.com");
        sysUser.setIsActive(true);
        sysUser.setNoHp("085645480401");
        sysUser = sysUserDao.save(sysUser);
        Assert.assertNotNull(sysUser.getId());

        idSysRoles = sysRolesDao.getIdByName("approval");
        sysUserRoles = new SysUserRoles();
        sysUserRoles.setSysUser(sysUser);
        sysUserRoles.setSysRoles(idSysRoles);
        sysUserRolesDao.save(sysUserRoles);
        Assert.assertNotNull(sysUserRoles.getId());

        idSysRoles = sysRolesDao.getIdByName("public");
        sysUserRoles = new SysUserRoles();
        sysUserRoles.setSysUser(sysUser);
        sysUserRoles.setSysRoles(idSysRoles);
        sysUserRolesDao.save(sysUserRoles);
        Assert.assertNotNull(sysUserRoles.getId());

        sysUser = new SysUser();
        sysUser.setUsername("public");
        sysUser.setPassword("public");
        sysUser.setEmail("public@gmail.com");
        sysUser.setIsActive(true);
        sysUser.setNoHp("085645480401");
        sysUser = sysUserDao.save(sysUser);
        Assert.assertNotNull(sysUser.getId());

        idSysRoles = sysRolesDao.getIdByName("public");
        sysUserRoles = new SysUserRoles();
        sysUserRoles.setSysUser(sysUser);
        sysUserRoles.setSysRoles(idSysRoles);
        sysUserRolesDao.save(sysUserRoles);
        Assert.assertNotNull(sysUserRoles.getId());

    }

    @PersistenceContext
    public EntityManager em;

    @Test
    @Ignore
    @Transactional
    public void readRolesAndDipatcherUrl() {
//        SysAuthorizationImpl call = new SysAuthorizationImpl();
//        call.test();

        Iterable<SysAuthorizationDto> sysAuthorizations = sysAuthorizationDao.listRolenameAndDispatcherUrl();
        for (SysAuthorizationDto sysAuthorization : sysAuthorizations) {
            System.out.print(sysAuthorization.getRoleName());
            System.out.println(":" + sysAuthorization.getPatternDispatcherUrl());
        }

//        List<SysAuthorizationDto> sysAuthorizations = (List<SysAuthorizationDto>) em.createQuery("select a.patternDispatcherUrl as patternDispatcherUrl, b.roleName as roleName from SysAuthorization a left join a.sysRoles b ").getResultList();
//        for (SysAuthorizationDto sysAuthorization : sysAuthorizations) {
//            System.out.print(sysAuthorization.getRoleName());
//            System.out.println(":" + sysAuthorization.getPatternDispatcherUrl());
//        }
    }
}
