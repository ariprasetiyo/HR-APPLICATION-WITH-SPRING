/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.controller.rest.user;

import ari.com.hr.application.dao.SysUserDao;
import ari.com.hr.application.dao.SysUserRolesDao;
import ari.com.hr.application.dto.GlobalDto;
import ari.com.hr.application.dto.SysRolesDto;
import ari.com.hr.application.dto.SysUserDto;
import ari.com.hr.application.dto.SysUserHeader;
import ari.com.hr.application.model.SysUser;
import ari.com.hr.application.model.SysUserRoles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
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

@RestController
@RequestMapping(value = "/admin/v1/api/user")
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRolesDao sysUserRolesDao;

    @Autowired
    private EntityManager em;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/delete{idUser}", method = RequestMethod.DELETE)
    public ResponseEntity<GlobalDto> deleteUser(@PathVariable("idUser") Long idUser) {
        sysUserDao.delete(idUser);
        GlobalDto globalDto = new GlobalDto();
        globalDto.setId(idUser);
        return new ResponseEntity(globalDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<SysUser> getListUser(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam(value = "search", required = false) String keySearch) {

        log.debug("offset : " + offset + " limit : " + limit + ", search : " + keySearch);

        return new ResponseEntity(functionSysUserDto(offset, limit, keySearch), HttpStatus.OK);
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
        log.debug(" textUserName : " + textUserName
                + "\n textPassword : " + textPassword
                + "\n textName : " + textName
                + "\n textEmail : " + textEmail
                + "\n noHp : " + noHp
                + "\n selectRole : " + selectRole[0]
                + "\n isActiveUser : " + isActiveUser
                + "\n idUSerNya : " + idUser
        );
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
        sysUser = sysUserDao.save(sysUser);

        if (isUpdate) {
            int countDelete = em.createQuery("delete from SysUserRoles where sysUser.id = :userId ")
                    .setParameter("userId", sysUser.getId())
                    .executeUpdate();
            log.debug("delete  SysUserRoles : " + countDelete);
        }

        for (int a = 0; a < selectRole.length; a++) {
            SysUserRoles sysUserRoles = new SysUserRoles();
            sysUserRoles.setSysUser(sysUser);
            sysUserRoles.setSysRoles(selectRole[a]);
            sysUserRolesDao.save(sysUserRoles);
        }

        Map<String, Object> mapJson = new HashMap();
        boolean isSuccessSave = false;
        if (sysUser.getId() != null) {
            isSuccessSave = true;
        }
        mapJson.put("isSuccessSave", isSuccessSave);
        return mapJson;
    }

    private SysUserHeader functionSysUserDto(int offset, int limit, String keySearch) {
        List<SysUser> listSysUser = new ArrayList();
        StringBuilder queryListUser = new StringBuilder();
        if (keySearch == null || keySearch.isEmpty()) {
            queryListUser.append("from SysUser order by username asc");
            listSysUser = em.createQuery(queryListUser.toString())
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } else {
            queryListUser.append("from SysUser where username like :searchUserName order by username asc");
            listSysUser = em.createQuery(queryListUser.toString())
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .setParameter("searchUserName", "%" + keySearch + "%")
                    .getResultList();
        }

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
            sysUserDto.setIsActive(sysUser.isIsActive());

            List<SysRolesDto> listSysRoles = sysUserRolesDao.listRolesByNameUser(sysUser.getId());
            if (listSysRoles.size() != 0) {
                StringBuilder builderRoleName = new StringBuilder();
                long[] roloIdArrayLong = new long[listSysRoles.size()];
                int tmpPlusPlus = 0;
                for (SysRolesDto sysRoleDto : listSysRoles) {
                    builderRoleName.append(sysRoleDto.getRoleName());
                    roloIdArrayLong[tmpPlusPlus] = sysRoleDto.getId();
                    log.debug(sysUser.getUsername() + ", role : " + sysRoleDto.getRoleName());
                    if (tmpPlusPlus == (listSysRoles.size() - 1)) {
                        continue;
                    }
                    builderRoleName.append(", ");
                    tmpPlusPlus++;
                }
                sysUserDto.setRoleName(builderRoleName.toString());
                sysUserDto.setRoleId(roloIdArrayLong);
            }

            listUserDto.add(sysUserDto);
        }
        sysUserHeader.setListSysUserDto(listUserDto);
        sysUserHeader.setTotalRecord(sysUserDao.count());
        return sysUserHeader;
    }
}
