package ari.com.hr.application.services;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ari.com.hr.application.dao.SysUserRolesDao;
import ari.com.hr.application.model.SysUserRoles;
import ari.com.hr.application.util.Logs;

@Repository
public class UserRolesService implements UserRoles {
	
	private static Logger logger = LoggerFactory.getLogger(UserRolesService.class);
	
	@Autowired
    private SysUserRolesDao sysUserRolesDao;
	
	@Autowired
	private EntityManager em;

	@Override
	public SysUserRoles save(SysUserRoles sysUserRoles) {
		return sysUserRolesDao.save(sysUserRoles);
	}

	@Override
	public int deleteByUserId(long userId) {
		int countDelete = em.createQuery("delete from SysUserRoles where sysUser.id = :userId ")
				.setParameter("userId", userId).executeUpdate();
		Logs.logDebug(logger, "delete  SysUserRoles : {} for update", countDelete);
		return countDelete;
	}
}
