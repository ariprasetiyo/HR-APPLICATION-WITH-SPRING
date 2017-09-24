package ari.com.hr.application.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.dto.SysScreenMenuDto;

@Repository
public class MenuService {
	public static StringBuilder getScreenMenu(List<Long> sysRolesId, long parentId,
			SysAuthorizationDao sysAuthorizationDao, StringBuilder tmpScript) {

		// looking for parent menus with sign parentid have values null/zero
		List<SysScreenMenuDto> listMenu = sysAuthorizationDao.listScreenMenu(sysRolesId, parentId);

		for (SysScreenMenuDto menu : listMenu) {
			if (menu.getParentId() == 0) {
				tmpScript.append("<li class=\"treeview\">\n" + "<a href=\"" + menu.getPatternDispatcherUrl() + "\">\n"
						+ "<i class=\"fa fa-share\"></i> <span>" + menu.getMenuName() + "</span>\n"
						+ "<span class=\"pull-right-container\">\n" + "<i class=\"fa fa-angle-left pull-right\"></i>\n"
						+ "</span>\n" + "</a>\n");
			}
			if (menu.getCounts() > 0 && menu.getParentId() == 0) {
				tmpScript.append(" <ul class=\"treeview-menu\">\n");
				getScreenMenu(sysRolesId, menu.getId(), sysAuthorizationDao, tmpScript);
				tmpScript.append("</ul></li>\n");
			} else if (menu.getCounts() > 0 && menu.getParentId() != 0) {
				tmpScript.append(" <li>\n" + "<a href=\"" + menu.getPatternDispatcherUrl()
						+ "\"><i class=\"fa fa-circle-o\"></i>" + menu.getMenuName() + "\n"
						+ "<span class=\"pull-right-container\">\n" + "<i class=\"fa fa-angle-left pull-right\"></i>\n"
						+ "</span>\n" + "</a>\n" + "<ul class=\"treeview-menu\">\n");
				getScreenMenu(sysRolesId, menu.getId(), sysAuthorizationDao, tmpScript);
				tmpScript.append("</ul></li>\n");
			} else if (menu.getCounts() == 0 && menu.getParentId() != 0) {
				tmpScript.append("<li><a href=\"" + menu.getPatternDispatcherUrl()
						+ "\"><i class=\"fa fa-circle-o\"></i>" + menu.getMenuName() + "</a></li>\n");
			}
		}
		return tmpScript;
	}
}
