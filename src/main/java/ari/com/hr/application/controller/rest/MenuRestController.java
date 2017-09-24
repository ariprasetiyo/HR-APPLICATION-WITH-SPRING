/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.rest;

import ari.com.hr.application.dao.SysMenusDao;
import ari.com.hr.application.model.SysMenus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ari-prasetiyo
 */
@RestController
@RequestMapping("/admin/v1/api/screen_menu")
public class MenuRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysMenusDao sysMenusDao;

    @Autowired
    private EntityManager em;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<List<SysMenus>> listMenu(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam(value = "search", required = false) String keySearch
    ) {

        List<SysMenus> listSysMenus = em
                .createQuery("from SysMenus where menusName like :searchUserName order by menusName asc")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("searchUserName", "%" + keySearch + "%")
                .getResultList();

        return new ResponseEntity(listSysMenus, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> saveMenu(
            @RequestParam("textMenuName") String textNameMenu,
            @RequestParam("textUrl") String textUrl,
            @RequestParam("checkBoxIsActive") Boolean checkBoxIsActive,
            @RequestParam(value = "idMenu", required = false) Long idMenu
    ) {
        logger.debug("textNameMenu : " + textNameMenu + ", textUrl : " + textUrl + ", checkBoxIsActive : " + checkBoxIsActive + ", idMenu : " + idMenu);
        SysMenus sysMenus = new SysMenus();
        sysMenus.setMenusName(textNameMenu);
        sysMenus.setUrl(textUrl);
        sysMenus.setDisabled(checkBoxIsActive);
        if (idMenu == null) {
            sysMenus.setId(idMenu);
        }
        sysMenus.setId(idMenu);
        sysMenus = sysMenusDao.save(sysMenus);

        boolean isSuccessSave = false;
        if (sysMenus.getModifiedTime() != null) {
            isSuccessSave = true;
        }

        Map<String, Object> mapJson = new HashMap<String, Object>();
        mapJson.put("isSuccessSave", isSuccessSave);
        return mapJson;
    }
}
