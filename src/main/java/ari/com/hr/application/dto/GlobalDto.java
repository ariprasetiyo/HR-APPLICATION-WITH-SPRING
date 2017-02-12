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
public class GlobalDto {
    private Long id;
    private String feedBackData;
    private int count;
    
    public String getFeedBackData() {
        return feedBackData;
    }

    public void setFeedBackData(String feedBackData) {
        this.feedBackData = feedBackData;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
