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
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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
            @RequestParam(value = "roles_id", required = false) Long roles_id) {
        viewSysRoles(model, roles_id);
        List<SysAuthorization> SysAuthorities = (List<SysAuthorization>) dsAuthorization.getForScreenMenu(roles_id);
        model.addAttribute("authorities", SysAuthorities);
        viewSysMenu(model);
        //return dsAuthorization.findAll(page);
        return "/admin/v1/authorization/index";
    }

    @RequestMapping(value = "/Save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void authorizationSave() {

    }

    @RequestMapping("/Edit")
    public void authorizationEdit() {

    }

    @RequestMapping("/Delete")
    public void authorizationDelete() {

    }

    private void viewSysRoles(Model model, Long idRoles) {
        model.addAttribute("selectRoleValue", idRoles);
        List<SysRoles> listAllSysRole = (List<SysRoles>) dsSysRoles.findAll();
        model.addAttribute("listRoles", listAllSysRole);
    }

    private void viewSysMenu(Model model) {
        List<SysMenus> listAllMenu = (List<SysMenus>) dsSysMenuDao.findAll();
        model.addAttribute("listAllMenu", listAllMenu);
    }
}
