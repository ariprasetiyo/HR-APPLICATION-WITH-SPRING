/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.model;

import ari.com.hr.application.dto.SysScreenMenuDto;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author ari-prasetiyo
 */
@Entity
@Table(name = "sys_authorization")
@NamedNativeQuery(name = "SysAuthorization.listScreenMenu",
        query = "select  a.id as id , sm.menus_name as name_menu, sm.url as pattern_dispatcher_url, coalesce(a.parent_id, 0) as parent_id, coalesce(b.con, 0) as con  from "
        + "sys_authorization as a "
        + "LEFT join ( select parent_id, count(1) as con from sys_authorization GROUP by parent_id ) as b \n"
        + "        on a.id = b.parent_id  left join sys_menu sm on a.sys_menu_id = sm.id where sys_roles_id in :nsysRolesId and coalesce(a.parent_id, 0) = :nparentId",
        //resultClass = SysScreenMenuDto.class,
        resultSetMapping = "SysAuthorization.listScreenMenu"
)
@SqlResultSetMapping(name = "SysAuthorization.listScreenMenu",
        classes = {
            @ConstructorResult(
                    targetClass = SysScreenMenuDto.class,
                    columns = {
                        @ColumnResult(name = "id", type = long.class),
                        @ColumnResult(name = "name_menu", type = String.class),
                        @ColumnResult(name = "pattern_dispatcher_url", type = String.class),
                        @ColumnResult(name = "parent_id", type = Long.class),
                        @ColumnResult(name = "con", type = Integer.class)

                    })
        })
//@SqlResultSetMapping(name = "SysAuthorization.listScreenMenu",
//        entities = @EntityResult(entityClass = SysAuthorization.class,
//                fields = {
//                    @FieldResult(name = "id", column = "id"),
//                    @FieldResult(name = "nameMenu", column = "name_menu"),
//                    @FieldResult(name = "patternDispatcherUrl", column = "pattern_dispatcher_url"),
//                    @FieldResult(name = "parent.id", column = "parent_id"),
//                    @FieldResult(name = "counts", column = "con")
//                }))
public class SysAuthorization extends ModelSerializable {

//    @Column(name = "pattern_dispatcher_url", length = 100, nullable = true)
//    private String patternDispatcherUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private SysRoles sysRoles;

//    @Column(name = "name_menu")
//    public String nameMenu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private SysMenus sysMenu;

    private boolean isUpdate;

    private boolean isDelete;

    private boolean isInsert;

    private boolean isRead;

    @Transient
    private Integer counts;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    public SysAuthorization parent;

    public boolean isIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isIsInsert() {
        return isInsert;
    }

    public void setIsInsert(boolean isInsert) {
        this.isInsert = isInsert;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public SysAuthorization getParent() {
        return parent;
    }

    public void setParent(SysAuthorization parent) {
        this.parent = parent;
    }

    public SysRoles getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(SysRoles sysRoles) {
        this.sysRoles = sysRoles;
    }

    public void setSysRoles(long id) {
        SysRoles byId = new SysRoles();
        byId.setId(id);
        this.sysRoles = byId;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public SysMenus getSysMenu() {
        return sysMenu;
    }

    public void setSysMenu(SysMenus sysMenu) {
        this.sysMenu = sysMenu;
    }

    public void setSysMenu(long id) {
        SysMenus sysMenus = new SysMenus();
        sysMenus.setId(id);
        this.sysMenu = sysMenus;
    }

}
