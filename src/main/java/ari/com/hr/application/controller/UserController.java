
package ari.com.hr.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/Utility/User")
public class UserController {
    
    @RequestMapping(value ="", method = RequestMethod.GET)
    public String index(){
        return "/admin/v1/user/index";
    }
}
