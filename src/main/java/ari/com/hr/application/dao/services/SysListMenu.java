/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao.services;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.dto.SysScreenMenuDto;
import java.util.List;

/**
 *
 * @author ari-prasetiyo
 */
public class SysListMenu {

    public static StringBuilder getScreenMenu(long sysRolesId, long parentId, SysAuthorizationDao sysAuthorizationDao, StringBuilder tmpScript) {
        List<SysScreenMenuDto> listMenu = sysAuthorizationDao.listScreenMenu(sysRolesId, parentId);

        for (SysScreenMenuDto menu : listMenu) {
            if (menu.getParentId() == 0) {
                tmpScript.append("<li class=\"treeview\">\n"
                        + "<a href=\"" + menu.getPatternDispatcherUrl() + "\">\n"
                        + "<i class=\"fa fa-share\"></i> <span>" + menu.getMenuName() + "</span>\n"
                        + "<span class=\"pull-right-container\">\n"
                        + "<i class=\"fa fa-angle-left pull-right\"></i>\n"
                        + "</span>\n"
                        + "</a>\n");
            }
            if (menu.getCounts() > 0 && menu.getParentId() == 0) {
                tmpScript.append(" <ul class=\"treeview-menu\">\n");
                getScreenMenu(sysRolesId, menu.getId(), sysAuthorizationDao, tmpScript);
                tmpScript.append("</ul></li>\n");
            } else if (menu.getCounts() > 0 && menu.getParentId() != 0) {
                tmpScript.append(" <li>\n"
                        + "<a href=\"" + menu.getPatternDispatcherUrl() + "\"><i class=\"fa fa-circle-o\"></i>" + menu.getMenuName() + "\n"
                        + "<span class=\"pull-right-container\">\n"
                        + "<i class=\"fa fa-angle-left pull-right\"></i>\n"
                        + "</span>\n"
                        + "</a>\n"
                        + "<ul class=\"treeview-menu\">\n");
                getScreenMenu(sysRolesId, menu.getId(), sysAuthorizationDao, tmpScript);
                tmpScript.append("</ul></li>\n");
            } else if (menu.getCounts() == 0 && menu.getParentId() != 0) {
                tmpScript.append("<li><a href=\"" + menu.getPatternDispatcherUrl() + "\"><i class=\"fa fa-circle-o\"></i>" + menu.getMenuName() + "</a></li>\n");
            }
        }
        return tmpScript;
    }
}
