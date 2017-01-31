/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application;

import ari.com.hr.application.dao.SysAuthorizationDao;
import ari.com.hr.application.dto.SysAuthorizationDto;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter {

    private static final String SQL_LOGIN
            = "select username, password, is_active as enabled from sys_user where username = ? and is_active = 1  ;";

    private static final String SQL_PERMISSION 
            = "select u.username as username, r.role_name as authority "
            + "from sys_user u join sys_user_roles ur on u.id = ur.sys_user_id "
            + "join sys_roles r on ur.sys_roles_id = r.id where u.username = ? ;";

    //Logger log = Logger.getLogger(ConfigurationSecurity.class);
    @Autowired
    private DataSource ds;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("ari").password("ari").roles("admin")
//                .and()
//                .withUser("a").password("a").roles("user").
//                and().withUser("b").password("b").roles("public")
//                .and().withUser("c").password("c").roles("c");
        auth
                .jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery(SQL_LOGIN)
                .authoritiesByUsernameQuery(SQL_PERMISSION);

    }
    
    @Autowired
    SysAuthorizationDao sysAuthorizationDao;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        List<SysAuthorizationDto> sysAuthorizations = sysAuthorizationDao.listRolenameAndDispatcherUrl();

        MultiValueMap map = new MultiValueMap();
        for (SysAuthorizationDto sysAuthorization : sysAuthorizations) {
            map.put(sysAuthorization.getPatternDispatcherUrl(), sysAuthorization.getRoleName());
        }
        
        for (Object key : map.keySet()){
            String[] roleName = map.get(key).toString().replaceAll("\\[|\\]|\\s", "").split(",");
            httpSecurity
                    .authorizeRequests()
                    .antMatchers(key.toString())
                    .hasAnyAuthority(roleName);
        }
        
        httpSecurity.authorizeRequests()
                //                .antMatchers("/dashboard/**").hasAnyAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                //defaultSuccessUrl is user success login then foward to hallo and boolean true is user login direct access hallo, if false call back user before login
                //.defaultSuccessUrl("/dashboard", true)
                .and()
                .logout() .and()
                .addFilterAfter(new configurationCrsfFilter(), CsrfFilter.class)
                .csrf().csrfTokenRepository(csrfTokenRepository());
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        // webSecurity.ignoring().antMatchers("/without_restrict/**", "/login", "/");
        webSecurity.ignoring().antMatchers("/resources/**");
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
