package org.example.Auto.models;

public class LogoutRequest {

    private String refreshToken;

    // Пустой конструктор для стабильности библиотек сериализации
    public LogoutRequest() {
    }

    public LogoutRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}