/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dao;

import ari.com.hr.application.model.SysUser;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author ari-prasetiyo
 */
public interface SysUserDao extends PagingAndSortingRepository<SysUser, Long > {
    
}
