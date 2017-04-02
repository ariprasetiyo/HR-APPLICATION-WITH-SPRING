/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dto;

/**
 *
 * @author ari-prasetiyo
 */
public class SysRolesDto {

    private Long id;

    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SysRolesDto(Long id) {
        this.id = id;
    }

    public SysRolesDto(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
