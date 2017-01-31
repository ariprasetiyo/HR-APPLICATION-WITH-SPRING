/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.authorization;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.model.SysAuthorization;
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
@RequestMapping("/admin/v1")
public class AuthorizationController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysAuthorizationDao dsAuthorization;

    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String index(Model model) {
        List<SysAuthorization> SysAuthorities = (List<SysAuthorization>) dsAuthorization.getForScreenMenu(1);
        model.addAttribute("authorities", SysAuthorities);
        //return dsAuthorization.findAll(page);
        return "/admin/v1/authorization/index";
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public String index(Model model, Pageable page, @RequestParam(value = "roles_id", required = false) String roles_id) {
        List<SysAuthorization> SysAuthorities = (List<SysAuthorization>) dsAuthorization.getForScreenMenu(1);
        model.addAttribute("authorities", SysAuthorities);
        //return dsAuthorization.findAll(page);
        return "/admin/v1/authorization/index";
    }

    @RequestMapping(value = "/Authorization/Save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void authorizationSave() {

    }

    @RequestMapping("/Authorization/Edit")
    public void authorizationEdit() {

    }

    @RequestMapping("/Authorization/Delete")
    public void authorizationDelete() {

    }
}
