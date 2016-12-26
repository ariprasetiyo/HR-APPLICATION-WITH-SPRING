/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao;

import ari.com.hr.application.model.SysUserRoles;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

/**
 *
 * @author ari-prasetiyo
 */
public interface SysUserRolesDao extends PagingAndSortingRepository<SysUserRoles, Long> {
    
    @Query("select x from  SysUserRoles x where sysRoles.roleName = :nRoleName order by id desc")
    public Page<SysUserRoles> getByName(@Param("nRoleName") String roleName, @PageableDefault(value = 1) Pageable pageable);

    @Query ("select id from SysUserRoles where sysRoles.roleName = :nRolesName order by id desc")
    public long getIdByName (@Param("nRolesName") String roleName);
    
    @Query("from SysUserRoles where sysRoles.roleName = :nRolesName order by id desc")
    public SysUserRoles getIdByNameFeedbackAll (@Param("nRolesName") String roleName);
    
    @Query("from SysUserRoles where sysUser.username = :nUsername order by id")
    public List<SysUserRoles> listUserRolesByNameUser(@Param("nUsername") String userName); 
}