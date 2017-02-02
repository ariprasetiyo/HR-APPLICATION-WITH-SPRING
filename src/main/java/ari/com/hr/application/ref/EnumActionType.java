/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ari.com.hr.application.ref;


public enum EnumActionType {
    UPLOAD_FILE("Upload file"),
    UPADATE("Update"),
    APPROVAL("Approval"),
    VIEW("View"),
    CREATE("Create"),
    DELETE("Delete");    

    private String name;

    EnumActionType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

