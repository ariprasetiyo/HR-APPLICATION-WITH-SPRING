/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dto;

import java.util.Date;

/**
 *
 * @author ari-prasetiyo
 */
public class SysAuthorizationDto extends Dto {

	private String roleName;
	private String url;

	private String menuName;

	private Long parentId;

	private Long id;

	private boolean isUpdate;

	private boolean isDelete;

	private boolean isInsert;

	private boolean isRead;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String rolename) {
        this.roleName = rolename;
    }

    public Long getId() {
        return id;
    }

    public void setRoleName(Long id) {
        this.id = id;
    }

    public String getPatternDispatcherUrl() {
        return url;
    }

    public void setPatternDispatcherUrl(String patternDispatcherUrl) {
        this.url = patternDispatcherUrl;
    }

    public SysAuthorizationDto(String patternDispatcherUrl, String roleName) {
        this.roleName = roleName;
        this.url = patternDispatcherUrl;
    }
    
    public SysAuthorizationDto(){};

    public SysAuthorizationDto(Date createTime, Date modifyTime, Long id, String menuName, Long parentId, boolean isUpdate, boolean isDelete, boolean isInsert, boolean isRead) {
        this.id = id;
        this.menuName = menuName;
        this.parentId = parentId;
        this.isUpdate = isUpdate;
        this.isDelete = isDelete;
        this.isInsert = isInsert;
        this.isRead = isRead;
    }

	@Override
	public String toString() {
		return "SysAuthorizationDto [roleName=" + roleName + ", url=" + url + ", menuName=" + menuName + ", parentId="
				+ parentId + ", id=" + id + ", isUpdate=" + isUpdate + ", isDelete=" + isDelete + ", isInsert="
				+ isInsert + ", isRead=" + isRead + "]";
	}
   
}
