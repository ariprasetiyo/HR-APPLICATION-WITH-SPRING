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
import ari.com.hr.application.model.SysAuthorization;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class MenuChainFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Autowired
    SysAuthorizationDao sysAuthorizationDao;

    @Override
    @Transactional
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        List<SysAuthorization> listMenu = sysAuthorizationDao.getForScreenMenu(1);
        if (listMenu.size() > 0) {
            System.out.println("----------------------------" + listMenu.size());
        }

        for (SysAuthorization menu : listMenu) {
            if (menu.getParent() != null) {
                System.out.println("----------------------------" + menu.getParent().getId() + " " + menu.getNameMenu());

            }
        }
        servletRequest.setAttribute("ListMenus", listMenu);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
