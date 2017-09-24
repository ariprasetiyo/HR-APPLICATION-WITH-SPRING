/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.rest;

import java.util.HashMap;
import java.util.Map;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ari.com.hr.application.dto.GlobalDto;
import ari.com.hr.application.model.SysUser;
import ari.com.hr.application.model.SysUserRoles;
import ari.com.hr.application.services.UserService;
import ari.com.hr.application.services.UserRolesService;
import ari.com.hr.application.util.Logs;

@RestController
@RequestMapping(value = "/admin/v1/api/user")
public class UserRestController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRolesService sysUserRolesService;
    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/delete{idUser}", method = RequestMethod.DELETE)
    public ResponseEntity<GlobalDto> deleteUser(@PathVariable("idUser") Long idUser) {
    	userService.delete(idUser);
        GlobalDto globalDto = new GlobalDto();
        globalDto.setId(idUser);
        return new ResponseEntity<GlobalDto>(globalDto, HttpStatus.OK);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<SysUser> getListUser(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam(value = "search", required = false) String keySearch) { 
		Logs.logDebug(logger, "offset : {}, limit : {}, search : {}", offset, limit, keySearch);
        return new ResponseEntity(userService.functionSysUserDto(offset, limit, keySearch), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    @Transactional
    public Map<String, Object> saveUser(
            @RequestParam("textUserName") String textUserName,
            @RequestParam("textName") String textName,
            @RequestParam("textEmail") String textEmail,
            @RequestParam("textNoHp") String noHp,
            @RequestParam("selectRole[]") Long[] selectRole,
            @RequestParam("checkBoxIsActive") boolean isActiveUser,
            @RequestParam("textPassword") String textPassword,
            @RequestParam(value = "idUSerNya", required = false) String idUser
    ) {
		Logs.logDebug(logger,
				" textUserName : {}, textPassword : {},textName : {}, textEmail : {},"
						+ " noHp : {}, selectRole : {}, isActiveUser : {}, idUSerNya : {}",
				textUserName, textPassword, textName, textEmail, noHp, selectRole[0], isActiveUser, idUser);

		SysUser sysUser = new SysUser();     
        boolean isUpdate = false;
        if (idUser != null && !idUser.isEmpty()) {
            sysUser.setId(Long.valueOf(idUser));
            isUpdate = true;
        }
        
        sysUser.setUsername(textUserName);
        sysUser.setPassword(textPassword);
        sysUser.setName(textName);
        sysUser.setEmail(textEmail);
        sysUser.setNoHp(noHp);
        sysUser.setIsActive(isActiveUser);
        sysUser = userService.save(sysUser);
        
		if (isUpdate) {
			// int countDelete = em.createQuery("delete from SysUserRoles where
			// sysUser.id = :userId ")
			// .setParameter("userId", sysUser.getId())
			// .executeUpdate();
			sysUserRolesService.deleteByUserId(sysUser.getId());
		}
        
        for (int a = 0; a < selectRole.length; a++) {
            SysUserRoles sysUserRoles = new SysUserRoles();
            sysUserRoles.setSysUser(sysUser);
            sysUserRoles.setSysRoles(selectRole[a]);
            sysUserRolesService.save(sysUserRoles);
        }
        
        Map<String, Object> mapJson = new HashMap<String, Object>();
        boolean isSuccessSave = false;
        if (sysUser.getId() != null) {
            isSuccessSave = true;
        }
        mapJson.put("isSuccessSave", isSuccessSave);
        return mapJson;
    }
    
}
