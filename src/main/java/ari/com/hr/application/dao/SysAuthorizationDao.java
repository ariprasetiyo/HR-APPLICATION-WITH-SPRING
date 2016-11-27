/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao;

import ari.com.hr.application.dto.SysAuthorizationDto;
import ari.com.hr.application.model.SysAuthorization;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ari-prasetiyo
 */
public interface SysAuthorizationDao extends PagingAndSortingRepository<SysAuthorization, Long> {

    @Query("select id from SysAuthorization where sysRoles.roleName = :nRoleName order by id desc ")
    public long getIdByNameAuthorization(@Param("nRoleName") String nameAuthority);

    @Query("select a.patternDispatcherUrl as patternDispatcherUrl, b.roleName as roleName from SysAuthorization a left join a.sysRoles b ")
    public List<SysAuthorizationDto> listAll2();

    @Query("SELECT new ari.com.hr.application.dto.SysAuthorizationDto(a.patternDispatcherUrl as patternDispatcherUrl, b.roleName as roleName) from SysAuthorization a left join a.sysRoles b order by a.patternDispatcherUrl ")
    public List<SysAuthorizationDto> listRolenameAndDispatcherUrl();
}
