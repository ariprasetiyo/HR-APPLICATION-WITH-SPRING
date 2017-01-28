/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao;

import ari.com.hr.application.model.SysRoles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ari-prasetiyo
 */
public interface SysRolesDao extends PagingAndSortingRepository<SysRoles, Long> {

    @Query("select id from SysRoles where roleName = :nRolesName order by id desc")
    public long getIdByName(@Param("nRolesName") String roleName);
}
