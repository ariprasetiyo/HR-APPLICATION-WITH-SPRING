package ari.com.hr.application.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.dao.SysMenusDao;
import ari.com.hr.application.dao.SysRolesDao;
import ari.com.hr.application.dto.SysAuthorizationDto;
import ari.com.hr.application.model.SysAuthorization;
import ari.com.hr.application.model.SysMenus;
import ari.com.hr.application.model.SysRoles;

@Repository
public class AuthorizationService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SysAuthorizationDao dsSysAuthorization;

    @Autowired
    SysAuthorizationDao dsAuthorization;

    @Autowired
    SysRolesDao dsSysRoles;

    @Autowired
    SysMenusDao dsSysMenuDao;
    
    public void viewSysRoles(Model model, Long idRoles) {
        model.addAttribute("selectRoleValue", idRoles);
        List<SysRoles> listAllSysRole = (List<SysRoles>) dsSysRoles.findAll();
        model.addAttribute("listRoles", listAllSysRole);
    }

    public void existingMenuInSysMenu(Model model) {
        List<SysMenus> listAllMenu = (List<SysMenus>) dsSysMenuDao.findAll();
        model.addAttribute("listAllMenu", listAllMenu);
    }

    public void existingMenuInAuthorization(Model model, Long idRole) {
        List<SysAuthorization> listParentMenuAuthorization = dsAuthorization.getForScreenMenu(idRole);
        model.addAttribute("listAllMenuAuthorization", listParentMenuAuthorization);
    }
	
	public SysAuthorizationDto getAuthorizationList(int offset, int limit, String keySearch) {
		dsSysAuthorization.findAll();
		return null;
	}
	
	public List<SysAuthorizationDto> getAuthorizationList(Long idRole) {
		if (idRole == null) {
			return null;
		}
		List<SysAuthorization> sysAuthorizationList = getDataMenu(idRole);
		if (sysAuthorizationList == null) {
			return null;
		}
		List<SysAuthorizationDto> sysAuthorizationDtoList = new ArrayList<SysAuthorizationDto>();
		for (SysAuthorization sysAuthorization : sysAuthorizationList) {
			SysAuthorizationDto sysAuthorizationDto = new SysAuthorizationDto();
			sysAuthorizationDto.setId(sysAuthorization.getId());
			sysAuthorizationDto.setMenuName(sysAuthorization.getSysMenu().getMenusName());
			sysAuthorizationDto.setCreatedTime(sysAuthorization.getCreatedTime());
			sysAuthorizationDto.setModifiedTime(sysAuthorization.getModifiedTime());
			sysAuthorizationDto.setIsInsert(sysAuthorization.isIsInsert());
			sysAuthorizationDto.setIsUpdate(sysAuthorization.isIsUpdate());
			sysAuthorizationDto.setIsRead(sysAuthorization.isIsRead());
			sysAuthorizationDto.setIsDelete(sysAuthorization.isIsDelete());
			sysAuthorizationDto.setDisabled(sysAuthorization.isDisabled());
			sysAuthorizationDtoList.add(sysAuthorizationDto);
		}
		return sysAuthorizationDtoList;
	}

	public void viewDataMenu(Model model, Long idRole) {
		model.addAttribute("authorities", getDataMenu(idRole));
	}

	private List<SysAuthorization> getDataMenu(long idRole) {
		List<SysAuthorization> SysAuthorities = (List<SysAuthorization>) dsAuthorization.getForScreenMenu(idRole);
		List<SysAuthorization> SysAuthoritiesNew = new ArrayList<>();

		StringBuilder parentSign = new StringBuilder();
		long idParent = 0;
		int levelMenu = 0;
		for (SysAuthorization sysAuthority : SysAuthorities) {

			idParent = (sysAuthority.getParent() == null) ? 0 : sysAuthority.getParent().getId();
			levelMenu = recursifMethodCountParentId(sysAuthority.getId());
			logger.debug("result count parent id : " + sysAuthority.getId() + ". Level menu :" + levelMenu + ". Id : "
					+ idParent);

			parentSign.delete(0, parentSign.length());
			for (int a = 0; a < levelMenu; a++) {
				parentSign.append("--- ");
			}

			SysMenus sysMenu = new SysMenus();
			sysMenu.setMenusName(parentSign.toString() + sysAuthority.getSysMenu().getMenusName());
			sysAuthority.setSysMenu(sysMenu);
			SysAuthoritiesNew.add(sysAuthority);
		}
		return SysAuthoritiesNew;
	}
	
	private int recursifMethodCountParentId(long id) {
        Long a = dsAuthorization.getParentId(id);
        if (a == null) {
            return 0;
        }
        return recursifMethodCountParentId(a) + 1;
    }

    public SysAuthorizationDto saveDataMenu(Long idRole, boolean vInsert,
            boolean vUpdate, boolean vDelete,
            boolean vDisable, Long MenuId,
            Long parentMenuId) {

        SysAuthorization dataAuthorization = new SysAuthorization();
        logger.debug("-add new menu on id " + idRole
                + ", menuId : " + MenuId
                + ", parentId " + parentMenuId
                + " " + vInsert + " " + vUpdate + " " + vDelete + " " + vDisable);
        dataAuthorization.setSysMenu(MenuId);

        if (parentMenuId == null) {
            dataAuthorization.setParent(null);
        } else {
            dataAuthorization.setParent(parentMenuId);
        }

        dataAuthorization.setSysRoles(idRole);
        dataAuthorization.setIsDelete(vDelete);
        dataAuthorization.setIsInsert(vInsert);
        dataAuthorization.setIsUpdate(vUpdate);
        dataAuthorization.setDisabled(vDisable);
        dataAuthorization.setIsRead(true);

        dataAuthorization = dsSysAuthorization.save(dataAuthorization);
        logger.debug("new id menu after add : " + dataAuthorization.getId() + "--");

        SysAuthorizationDto dataAuthorizations = dsSysAuthorization.getDataAuthorizationById(dataAuthorization.getId());
        logger.debug("new id menu after get :" + dataAuthorizations.getId() + "--");
        logger.debug("menu name after add : " + dataAuthorizations.getMenuName());

        return dataAuthorizations;
    }
}
