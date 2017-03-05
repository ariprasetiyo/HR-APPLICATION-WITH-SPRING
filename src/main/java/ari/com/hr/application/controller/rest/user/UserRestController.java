/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.rest.user;

import ari.com.hr.application.dao.SysUserDao;
import ari.com.hr.application.dto.SysUserDto;
import ari.com.hr.application.model.SysUser;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/v1/api/user")
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserDao sysUserDao;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<SysUser> getListUser(@RequestParam("start") int start, @RequestParam("length") int lengthPage) {
        log.debug("start : " + start + "length" + lengthPage);
        List<SysUser> listSysUser = (List<SysUser>) sysUserDao.findAll();

        return new ResponseEntity(functionSysUserDto(listSysUser), HttpStatus.OK);
    }

    private List<SysUserDto> functionSysUserDto(List<SysUser> listSysUser) {
        List<SysUserDto> listUserDto = new ArrayList<SysUserDto>();
        for (SysUser sysUser : listSysUser) {
            SysUserDto sysUserDto = new SysUserDto();
            sysUserDto.setUsername(sysUser.getUsername());
            if (sysUser.getSysUserRoles() != null) {
                sysUserDto.setRoleName(sysUser.getSysUserRoles().getRoleName());
                log.debug(sysUser.getUsername() + ", role : " + sysUser.getSysUserRoles().getRoleName());
            }
            listUserDto.add(sysUserDto);
        }
        return listUserDto;
    }
}
