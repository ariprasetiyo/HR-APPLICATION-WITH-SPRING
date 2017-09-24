/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ari.com.hr.application.services.AuthorizationService;

@Controller
@RequestMapping("/admin/v1/authorization")
public class AuthorizationController {

    Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    AuthorizationService authorizationImpl;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
    	authorizationImpl.viewSysRoles(model, null);
        return "/admin/v1/authorization/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String index(Model model, Pageable page,
            @RequestParam(value = "roles_id", required = false) Long idRole) {
    	authorizationImpl.viewSysRoles(model, idRole);
    	authorizationImpl.viewDataMenu(model, idRole);
    	authorizationImpl.existingMenuInSysMenu(model);
    	authorizationImpl.existingMenuInAuthorization(model, idRole);
        return "/admin/v1/authorization/index";
    }
}
