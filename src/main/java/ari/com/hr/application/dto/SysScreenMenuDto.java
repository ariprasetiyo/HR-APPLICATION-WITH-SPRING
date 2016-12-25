/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.dto;

/**
 *
 * @author ari-prasetiyo
 */
public class SysScreenMenuDto {

    private long id;
    private String menuName, patternDispatcherUrl;
    private Long parentId;
    private Integer counts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPatternDispatcherUrl() {
        return patternDispatcherUrl;
    }

    public void setPatternDispatcherUrl(String patternDispatcherUrl) {
        this.patternDispatcherUrl = patternDispatcherUrl;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public SysScreenMenuDto(long id,
            String menuName, String patternDispatcherUrl, Long parentId, Integer counts
    ) {
        this.id = id;
        this.menuName = menuName;
        this.patternDispatcherUrl = patternDispatcherUrl;
        this.parentId = parentId;
        this.counts = counts;

    }
}
