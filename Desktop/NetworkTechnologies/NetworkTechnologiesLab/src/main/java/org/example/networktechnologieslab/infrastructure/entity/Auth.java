package org.example.networktechnologieslab.infrastructure.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.example.networktechnologieslab.commonTypes.UserRole;

@Entity
@Table(name= "auth", schema= "library")
public class Auth {

    @Id
    @Column(name="id")
    private Long userId;

    @Column(name="username", unique = true, nullable = false)
    @Basic
    private String username;

    @Column(name="password", nullable = false)
    @Basic
    private String password;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
