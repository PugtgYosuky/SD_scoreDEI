package com.example.scoredei.data;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="users")
@XmlRootElement
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean isAdmin;

    // A constructor. It is used to create a new user.
    public User() {
    }

    // This is a constructor. It is used to create a new user.
    public User(String name, String username, String password, String email, String phone, boolean isAdmin) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.name = name;
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.email = email;
        this.phone = phone;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }
    
    public void setId(int id) {
        this.id = id;
    }
        
    public void setName(String name) {
        this.name = name;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    public void encrypt() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(this.password);
    }

    /**
     * If the password is the same as the one in the database, return true, otherwise return false.
     * 
     * @param password The password to be checked
     * @return The method returns a boolean value.
     */
    public boolean samePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(password, this.password);
    }

    /**
     * The toString() method returns the string representation of the object
     * 
     * @return The username of the user.
     */
    @Override
    public String toString() {
        return this.username;
    }

    /**
     * If the name, username, password, email, and phone fields are not empty, then return true,
     * otherwise return false.
     */
    public boolean isCompleted(){
        return !this.name.isEmpty() && 
                !this.username.isEmpty() && 
                !this.password.isEmpty()&& 
                !this.email.isEmpty() && 
                !this.phone.isEmpty();
    }

}
