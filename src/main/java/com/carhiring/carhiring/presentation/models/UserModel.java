package com.carhiring.carhiring.presentation.models;

import com.carhiring.carhiring.data.entities.User;

public class UserModel implements EntityModel<User> {
    private int id;
    private RoleModel role;
    private String username;
    private String password;

    public UserModel() {}

    public UserModel(int id, RoleModel role, String username, String password) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel(User user) {
        this.id = user.getId();
        this.role = new RoleModel(user.getRole());
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public UserModel(UserModel user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Override
    public String toString() {
        return String.format("%s | %s", this.username, this.role.getName());
    }

    @Override
    public User toEntity() {
        User userTemp = new User();
        userTemp.setId(this.id);
        userTemp.setUsername(this.username);
        userTemp.setRole(this.role.toEntity());
        userTemp.setPassword(this.password);
        return userTemp;
    }
}
