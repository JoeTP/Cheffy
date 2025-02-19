package com.example.cheffy.features.auth.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User implements Serializable {
    String id;
    String name;
    String email;
    String password;
    public User(){}
    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        return user;
    }
    public User fromMap(Map<String, Object> map){
        User user = new User();
        user.setId(map.get("id").toString());
        user.setName(map.get("name").toString());
        user.setEmail(map.get("email").toString());
        return  user;
    }
}
