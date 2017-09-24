package ari.com.hr.application.services;

import ari.com.hr.application.model.SysUserRoles;

public interface UserRoles {
	public SysUserRoles save(SysUserRoles sysUserRoles);
	public int deleteByUserId(long userId);
}
