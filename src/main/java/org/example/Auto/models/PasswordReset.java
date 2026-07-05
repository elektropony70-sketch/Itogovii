package org.example.Auto.models;

public class PasswordReset {
    private String password;
    private String token;

    public PasswordReset(String password, String token) {
        this.password = password;
        this.token = token;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
