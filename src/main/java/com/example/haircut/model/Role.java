package com.example.haircut.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Role")
public class Role {
    private String roleID;
    private String roleName;

    public Role() {
    }

    public Role(String roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
