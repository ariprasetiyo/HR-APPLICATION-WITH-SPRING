/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ari-prasetiyo
 */
public class SysUserHeader {

    private float totalRecord;
    private List<SysUserDto> listSysUserDto = new ArrayList<SysUserDto>();

    public float getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(float totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<SysUserDto> getListSysUserDto() {
        return listSysUserDto;
    }

    public void setListSysUserDto(List<SysUserDto> listSysUserDto) {
        this.listSysUserDto = listSysUserDto;
    }
}
