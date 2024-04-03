package com.carhiring.carhiring.presentation.models;

import com.carhiring.carhiring.data.entities.Role;

public class RoleModel implements EntityModel<Role> {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleModel() {}

    public RoleModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleModel(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public RoleModel(RoleModel role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    @Override
    public String toString() {
        return String.format("%s", this.name);
    }

    @Override
    public Role toEntity() {
        Role roleTemp = new Role();
        roleTemp.setId(this.id);
        roleTemp.setName(this.name);
        return roleTemp;
    }
}
