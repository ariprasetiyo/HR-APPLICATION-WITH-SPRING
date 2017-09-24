package ari.com.hr.application.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ari.com.hr.application.dao.SysUserDao;
import ari.com.hr.application.dao.SysUserRolesDao;
import ari.com.hr.application.dto.SysRolesDto;
import ari.com.hr.application.dto.SysUserDto;
import ari.com.hr.application.dto.SysUserHeader;
import ari.com.hr.application.model.SysUser;
import ari.com.hr.application.util.Logs;

@Repository
public class UserService implements User {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysUserDao sysUserDao;
	
	@Autowired
    private SysUserRolesDao sysUserRolesDao;
	
	@Autowired
    private EntityManager em;
	
	public void delete(long idUser) {
		sysUserDao.delete(idUser);
	}
	
	public SysUser save(SysUser sysUser) {
		return sysUserDao.save(sysUser);
	}
	
	@SuppressWarnings("unchecked")
	public SysUserHeader functionSysUserDto(int offset, int limit, String keySearch) {
        List<SysUser> listSysUser = new ArrayList<SysUser>();
        StringBuilder queryListUser = new StringBuilder();
        if (keySearch == null || keySearch.isEmpty()) {
            queryListUser.append("from SysUser order by username asc");
            listSysUser = em.createQuery(queryListUser.toString())
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } else {
            queryListUser.append("from SysUser where username like :searchUserName order by username asc");
            listSysUser = em.createQuery(queryListUser.toString())
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .setParameter("searchUserName", "%" + keySearch + "%")
                    .getResultList();
        }
        
        SysUserHeader sysUserHeader = new SysUserHeader();
        
        List<SysUserDto> listUserDto = new ArrayList<SysUserDto>();
        for (SysUser sysUser : listSysUser) {
            
            SysUserDto sysUserDto = new SysUserDto();
            sysUserDto.setCreatedTime(sysUser.getCreatedTime());
            sysUserDto.setModifiedTime(sysUser.getModifiedTime());
            sysUserDto.setId(sysUser.getId());
            sysUserDto.setUsername(sysUser.getUsername());
            sysUserDto.setName(sysUser.getName());
            sysUserDto.setEmail(sysUser.getEmail());
            sysUserDto.setNoHp(sysUser.getNoHp());
            sysUserDto.setIsActive(sysUser.isIsActive());
            
            List<SysRolesDto> listSysRoles = sysUserRolesDao.listRolesByNameUser(sysUser.getId());
            if (listSysRoles.size() != 0) {
                StringBuilder builderRoleName = new StringBuilder();
                long[] roloIdArrayLong = new long[listSysRoles.size()];
                int tmpPlusPlus = 0;
                for (SysRolesDto sysRoleDto : listSysRoles) {
                    builderRoleName.append(sysRoleDto.getRoleName());
                    roloIdArrayLong[tmpPlusPlus] = sysRoleDto.getId();
					Logs.logDebug(logger, "{} -> role : {} ", sysUser.getUsername(), sysRoleDto.getRoleName());
					if (tmpPlusPlus == (listSysRoles.size() - 1)) {
						continue;
					}
                    builderRoleName.append(", ");
                    tmpPlusPlus++;
                }
                sysUserDto.setRoleName(builderRoleName.toString());
                sysUserDto.setRoleId(roloIdArrayLong);
            }
            
            listUserDto.add(sysUserDto);
        }
        sysUserHeader.setListSysUserDto(listUserDto);
        sysUserHeader.setTotalRecord(sysUserDao.count());
        return sysUserHeader;
    }
}
