/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author ari-prasetiyo
 */
@Entity
@Table(name = "sys_user_roles")
public class SysUserRoles extends ModelSerializable {

    @ManyToOne
    @JoinColumn(nullable = false, name="sys_user_id")
    @Cascade(CascadeType.REMOVE)
    private SysUser sysUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private SysRoles sysRoles;

    public SysRoles getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(SysRoles sysRoles) {
        this.sysRoles = sysRoles;
    }

    public void setSysRoles(Long id) {
        SysRoles sysRolesId = new SysRoles();
        sysRolesId.setId(id);
        this.sysRoles = sysRolesId;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public void setSysUser(Long id) {
        SysUser sysUserId = new SysUser();
        sysUserId.setId(id);
        this.sysUser = sysUserId;
    }

}
