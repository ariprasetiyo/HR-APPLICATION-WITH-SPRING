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
public class SysAuthorizationDto {

    private String roleName;
    private String patternDispatcherUrl;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String rolename) {
        this.roleName = rolename;
    }

    public String getPatternDispatcherUrl() {
        return patternDispatcherUrl;
    }

    public void setPatternDispatcherUrl(String patternDispatcherUrl) {
        this.patternDispatcherUrl = patternDispatcherUrl;
    }

    public SysAuthorizationDto(String patternDispatcherUrl, String roleName) {
        this.roleName = roleName;
        this.patternDispatcherUrl = patternDispatcherUrl;
    }
}
