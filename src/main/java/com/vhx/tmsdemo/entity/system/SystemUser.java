package com.vhx.tmsdemo.entity.system;

import java.util.List;

public class SystemUser {

    /** 用户编号 */
    private Integer id;
    /** 用户姓名 */
    private String userName;
    /** 用户口令 */
    private String password;

    private List<SystemRole> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SystemRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SystemRole> roles) {
        this.roles = roles;
    }
}
