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

    /**
     * This function returns the id of the current object
     * 
     * @return The id of the object.
     */
    public int getId() {
        return id;
    }

    /**
     * This function returns the name of the person
     * 
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * This function returns the username of the user
     * 
     * @return The username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * This function returns the password of the user
     * 
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * This function returns the email of the user
     * 
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * This function returns the phone number of the user
     * 
     * @return The phone number of the person.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This function returns the value of the isAdmin variable
     * 
     * @return The value of the isAdmin variable.
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }
    
    /**
     * This function sets the id of the object to the id passed in as a parameter
     * 
     * @param id The id of the user.
     */
    public void setId(int id) {
        this.id = id;
    }
        
    /**
     * This function sets the name of the object to the name passed in as a parameter
     * 
     * @param name The name of the parameter.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * This function sets the username of the user
     * 
     * @param username The username of the user you want to send the message to.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This function sets the password of the user
     * 
     * @param password The password to use for the connection.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This function sets the email of the user
     * 
     * @param email The email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
        
    /**
     * This function sets the phone number of the user
     * 
     * @param phone The phone number to send the message to.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This function sets the value of the isAdmin variable to the value of the isAdmin parameter
     * 
     * @param isAdmin This is a boolean value that determines whether the user is an admin or not.
     */
    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    /**
     * The BCryptPasswordEncoder is a class that implements the PasswordEncoder interface. The encode()
     * method takes a String and returns a String
     */
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
