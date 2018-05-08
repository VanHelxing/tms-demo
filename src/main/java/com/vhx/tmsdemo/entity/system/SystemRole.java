package com.vhx.tmsdemo.entity.system;

public class SystemRole {

    /** 角色编号 */
    private Integer id;
    /** 角色名称 */
    private String name;

    public SystemRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SystemRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
