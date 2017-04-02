/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user")
public class SysUser extends ModelSerializable {

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50, nullable = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(length = 30)
    private String email;

    @Column(name = "no_hp", length = 13)
    private String noHp;

    @Column(name = "is_active")
    private boolean isActive;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(nullable = true)
//    private SysRoles SysUserRoles;
//
//    public SysRoles getSysUserRoles() {
//        return SysUserRoles;
//    }
//
//    public void setSysUserRoles(SysRoles SysUserRoles) {
//        this.SysUserRoles = SysUserRoles;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
