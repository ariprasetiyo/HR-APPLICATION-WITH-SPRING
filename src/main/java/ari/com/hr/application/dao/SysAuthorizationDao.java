/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao;

import ari.com.hr.application.dto.SysAuthorizationDto;
import ari.com.hr.application.dto.SysScreenMenuDto;
import ari.com.hr.application.model.SysAuthorization;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("select a.sysMenu.url as patternDispatcherUrl, b.roleName as roleName from SysAuthorization a left join a.sysRoles b ")
    public List<SysAuthorizationDto> listAll2();

    @Query("SELECT new ari.com.hr.application.dto.SysAuthorizationDto(a.sysMenu.url as patternDispatcherUrl, b.roleName as roleName) from SysAuthorization a left join a.sysRoles b where a.sysMenu.url is not null order by a.sysMenu.url ")
    public List<SysAuthorizationDto> listRolenameAndDispatcherUrl();

    @Modifying(clearAutomatically = true)
    //@Transactional
    @Query("update SysAuthorization a set a.parent.id = :nparentId where a.sysRoles.id = :nId")
    public void updateSysRoleId(@Param("nId") long id, @Param("nparentId") Long parentId);

    /*Query untuk ambil data SysAuthorization*/
    @Query("from SysAuthorization  where sysRoles.id = :nsysRolesId and sysMenu.menusName is not null")
    public List<SysAuthorization> getForScreenMenu(@Param("nsysRolesId") long idSysRole);

    /*Query count parentId data SysAuthorization. NamedNativeQuery is "SysAuthorization.countParentId"
    Could using like this method from controller without pass interface
    Query q = em.createNamedQuery("SysAuthorization.countParentId");
    log.debug(q.toString());
    q.setParameter("nparentId", 72);
     */
    @Query(nativeQuery = true)
    public Integer countParentId(@Param("nparentId") long nparentId);
    
    /*
    Query for rekursif view menu base on parent id
    */
    @Query("select parent.id  from SysAuthorization where id = :nId ")
    public Long getParentId(@Param("nId") long ntId);

    //Using @NamedNativeSQL and sqlResultsetMapping
    //resultSetMapping = "SysAuthorization.listScreenMenu" in Model
    //parentId by default setting must be set 0
    public List<SysScreenMenuDto> listScreenMenu(@Param("nsysRolesId") List<Long> id, @Param("nparentId") Long parentId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update SysAuthorization a set a.isInsert = :nisInsert, a.isUpdate = :nisUpdate, a.isDelete = :nisDelete, a.disabled = :ndisabled where a.id = :nId")
    public int updateAuthorization(@Param("nId") long id,
            @Param("nisInsert") boolean isInsert,
            @Param("nisUpdate") boolean isUpdate,
            @Param("nisDelete") boolean isDelete, @Param("ndisabled") boolean disabled);

}
