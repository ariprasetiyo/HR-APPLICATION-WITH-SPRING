/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.rest.menu;

import ari.com.hr.application.dao.SysMenusDao;
import ari.com.hr.application.model.SysMenus;
import java.util.List;
import javax.persistence.EntityManager;
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
}
