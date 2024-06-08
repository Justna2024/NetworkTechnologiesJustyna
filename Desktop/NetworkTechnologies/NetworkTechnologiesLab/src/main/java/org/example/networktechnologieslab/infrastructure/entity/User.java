package org.example.networktechnologieslab.infrastructure.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;
// jakarta maps your class to a db
// jakarta uses @ symbols to define db

@Entity
@Table(name="users", schema = "library")
public class User {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long userId;

    @Column(name="firstName")
    @Basic
    private String firstName;

    @Column(name="lastName")
    @Basic
    private String lastName;

    @Column(name="dateOfBirth")
    @Basic
    private Date dateOfBirth;

    @Column(name="email")
    @Basic
    private String email;

    @Column(name="isStudent")
    @Basic
    private boolean isStudent;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Auth auth;

    // relationship as defined in project proposal


    // getters and setters generated
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}
