/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application;

/**
 *
 * @author ari-prasetiyo
 */
import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.dao.SysRolesDao;
import ari.com.hr.application.dao.services.SysListMenu;
import ari.com.hr.application.model.SysRoles;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class MenuChainFilter implements Filter {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //injector filter chain to spring
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Autowired
    SysAuthorizationDao sysAuthorizationDao;

    @Autowired
    SysRolesDao sysRolesDao;

    @Override
    @Transactional
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRquest = (HttpServletRequest) servletRequest;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        //Convert Object to List
        List listAuthorities = new ArrayList(auth.getAuthorities());
        List<String> listAuthoritiesString = new ArrayList<>(listAuthorities.size());
        for (Object xx : listAuthorities) {
            listAuthoritiesString.add(xx.toString());
        }
      
        //Looking for id from sys_roles
        List<SysRoles> listId = sysRolesDao.getListIdByName(listAuthoritiesString);
        //convert SysRoles.getId to List Long
        List<Long> listLongId = getListAuthorities(listId);
        log.debug("User login : " + name);
        log.debug("Access page : " + httpServletRquest.getRequestURI());
        log.debug("Authorities : ");
        for (int a = 0; a < listAuthorities.size(); a++) {
            log.debug("- id  "+ listAuthorities.get(a));
        }

        StringBuilder tmpScript = new StringBuilder();
        String listMenu = SysListMenu.getScreenMenu(listLongId, 0, sysAuthorizationDao, tmpScript).toString();
        servletRequest.setAttribute("scriptMenu", listMenu);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private List<Long> getListAuthorities(List<SysRoles> listRoles) {
        List<Long> listId = new ArrayList<>();
        for (SysRoles sysRole : listRoles) {
            listId.add(sysRole.getId());
        }
        return listId;
    }
}
