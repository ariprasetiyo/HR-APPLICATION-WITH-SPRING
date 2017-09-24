/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author ari-prasetiyo
 */
@Controller
@RequestMapping("/admin/v1/basicweb")
public class BasicwebController {
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(){
        System.out.println("test");
        return "/admin/v1/basicweb/index";
    }
    
}

