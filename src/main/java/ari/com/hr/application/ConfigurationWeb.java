/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author ari-prasetiyo
 */
@Configuration
public class ConfigurationWeb extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/without_restrict").setViewName("index");
        //registry.addViewController("/login").setViewName("/login/login");
        // registry.addViewController("/logout");
        //registry.addViewController("/without_restrict").setViewName("index");
    }

//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//
//        //   bean.setViewClass(JstlView.class);
//        bean.setPrefix("../resources/templates/");
//        bean.setSuffix(".html");
//
//        return bean;
//    }
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
//    }
}
