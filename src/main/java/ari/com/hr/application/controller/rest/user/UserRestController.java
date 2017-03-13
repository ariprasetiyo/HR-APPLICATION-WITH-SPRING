/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.rest.user;

import ari.com.hr.application.dao.SysUserDao;
import ari.com.hr.application.dto.SysUserDto;
import ari.com.hr.application.dto.SysUserHeader;
import ari.com.hr.application.model.SysUser;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
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

    @Autowired
    private EntityManager em;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<SysUser> getListUser(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam(value = "search", required = false) String keySearch) {

        log.debug("offset : " + offset + " limit : " + limit + ", search : " + keySearch);

        return new ResponseEntity(functionSysUserDto(offset, limit, keySearch), HttpStatus.OK);
    }

    private SysUserHeader functionSysUserDto(int offset, int limit, String keySearch) {
        List<SysUser> listSysUser = em.createQuery("from SysUser where username like :searchUserName order by username asc")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("searchUserName", "%" + keySearch + "%")
                .getResultList();

        SysUserHeader sysUserHeader = new SysUserHeader();

        List<SysUserDto> listUserDto = new ArrayList<SysUserDto>();
        for (SysUser sysUser : listSysUser) {

            SysUserDto sysUserDto = new SysUserDto();
            sysUserDto.setCreatedTime(sysUser.getCreatedTime());
            sysUserDto.setModifiedTime(sysUser.getModifiedTime());
            sysUserDto.setId(sysUser.getId());
            sysUserDto.setUsername(sysUser.getUsername());
            sysUserDto.setName(sysUser.getName());
            sysUserDto.setEmail(sysUser.getEmail());
            sysUserDto.setNoHp(sysUser.getNoHp());

            if (sysUser.getSysUserRoles() != null) {
                sysUserDto.setRoleName(sysUser.getSysUserRoles().getRoleName());
                sysUserDto.setRoleId(sysUser.getSysUserRoles().getId());
                log.debug(sysUser.getUsername() + ", role : " + sysUser.getSysUserRoles().getRoleName());
            }
            listUserDto.add(sysUserDto);
        }
        sysUserHeader.setListSysUserDto(listUserDto);
        sysUserHeader.setTotalRecord(sysUserDao.count());
        return sysUserHeader;
    }
}
