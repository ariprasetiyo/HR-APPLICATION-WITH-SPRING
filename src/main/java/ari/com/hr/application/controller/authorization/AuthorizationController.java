/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.authorization;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.dao.SysMenusDao;
import ari.com.hr.application.dao.SysRolesDao;
import ari.com.hr.application.model.SysAuthorization;
import ari.com.hr.application.model.SysMenus;
import ari.com.hr.application.model.SysRoles;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/v1/authorization")
public class AuthorizationController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysAuthorizationDao dsAuthorization;

    @Autowired
    SysRolesDao dsSysRoles;

    @Autowired
    SysMenusDao dsSysMenuDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        viewSysRoles(model, null);
        return "/admin/v1/authorization/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String index(Model model, Pageable page,
            @RequestParam(value = "roles_id", required = false) Long idRole) {
        viewSysRoles(model, idRole);
        viewDataMenu(model, idRole);
        existingMenuInSysMenu(model);
        existingMenuInAuthorization(model, idRole);
        return "/admin/v1/authorization/index";
    }

    private void viewSysRoles(Model model, Long idRoles) {
        model.addAttribute("selectRoleValue", idRoles);
        List<SysRoles> listAllSysRole = (List<SysRoles>) dsSysRoles.findAll();
        model.addAttribute("listRoles", listAllSysRole);
    }

    private void existingMenuInSysMenu(Model model) {
        List<SysMenus> listAllMenu = (List<SysMenus>) dsSysMenuDao.findAll();
        model.addAttribute("listAllMenu", listAllMenu);
    }

    private void existingMenuInAuthorization(Model model, Long idRole) {
        List<SysAuthorization> listParentMenuAuthorization = dsAuthorization.getForScreenMenu(idRole);
        model.addAttribute("listAllMenuAuthorization", listParentMenuAuthorization);
    }

    private int recursifMethodCountParentId(long id) {
        Long a = dsAuthorization.getParentId(id);
        if (a == null) {
            return 0;
        }
        return recursifMethodCountParentId(a) + 1;
    }

    private void viewDataMenu(Model model, Long idRole) {

        List<SysAuthorization> SysAuthorities = (List<SysAuthorization>) dsAuthorization.getForScreenMenu(idRole);
        List<SysAuthorization> SysAuthoritiesNew = new ArrayList<>();
        Integer countParentId = 0;
        int countParentIdTemp = 0;
        StringBuilder parentSign = new StringBuilder();
        long idParent = 0;
        int levelMenu = 0;
        for (SysAuthorization sysAuthority : SysAuthorities) {

            idParent = (sysAuthority.getParent() == null) ? 0 : sysAuthority.getParent().getId();
            levelMenu = recursifMethodCountParentId(sysAuthority.getId());
            log.debug("result count parent id : " + sysAuthority.getId() + ". Level menu :" + levelMenu + ". Id : " + idParent);

            parentSign.delete(0, parentSign.length());
            for (int a = 0; a < levelMenu; a++) {
                parentSign.append("--- ");
            }

            SysMenus sysMenu = new SysMenus();
            sysMenu.setMenusName(parentSign.toString() + sysAuthority.getSysMenu().getMenusName());
            sysAuthority.setSysMenu(sysMenu);
            SysAuthoritiesNew.add(sysAuthority);
        }
        model.addAttribute("authorities", SysAuthoritiesNew);
    }
}
