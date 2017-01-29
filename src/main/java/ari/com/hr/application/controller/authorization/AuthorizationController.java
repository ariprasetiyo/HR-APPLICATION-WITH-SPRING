/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.authorization;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.model.SysAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/V11")
public class AuthorizationController {
    
    
    @Autowired
    SysAuthorizationDao dsAuthorization;
    
    @RequestMapping(value = "/Authorization", method=RequestMethod.GET)
    public Page<SysAuthorization> index(Pageable page){
       page.getPageNumber();
       return dsAuthorization.findAll(page);
        //return "/admin/v1/authorization/index";
    }
    
    @RequestMapping(value = "/Authorization/Save", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void authorizationSave(){
        
    }
    
    @RequestMapping("/Authorization/Edit")
    public void authorizationEdit(){
        
    }
    
    @RequestMapping("/Authorization/Delete")
    public void authorizationDelete(){
        
    }    
}
