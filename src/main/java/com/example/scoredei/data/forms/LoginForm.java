package com.example.scoredei.data.forms;

public class LoginForm {

    private String username;
    private String password;

    public LoginForm() {
        
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

}
