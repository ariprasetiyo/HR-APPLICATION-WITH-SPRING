/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author ari-prasetiyo
 */
@Controller
@RequestMapping("/Dashboard")
public class DashBoardController  {

    @RequestMapping("/V1")
    public String dashboard() {
        return "/admin/index";
    }

    @RequestMapping("/V2")
    public String dashboard2() {
        return "/admin/index_ori.html";
    }
}
