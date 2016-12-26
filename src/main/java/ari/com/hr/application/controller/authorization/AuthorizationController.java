/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.authorization;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/V1")
public class AuthorizationController {
    
    @RequestMapping("/Authorization")
    public String index(){
        return "/admin/v1/authorization/index";
    }
}
