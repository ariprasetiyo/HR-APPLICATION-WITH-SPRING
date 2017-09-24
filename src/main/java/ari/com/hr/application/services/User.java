package ari.com.hr.application.services;

import ari.com.hr.application.model.SysUser;

public interface User {
	public void delete(long idUser);
	public SysUser save(SysUser sysUser);
}
