package com.pwr.quizzer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserTmp implements Serializable {

    private static final long serialVersionUID = 6429316483965915578L;

    @Id
    private String username;

    private String password;

    private boolean enabled = true;

    private String able = "";

    
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

    
    public boolean isEnabled() {
        return enabled;
    }

    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAble() {
        return able;
    }
    
    public void setAble(String courseGroup) {
        this.able += courseGroup;
        this.able += ",";
    }

    

}