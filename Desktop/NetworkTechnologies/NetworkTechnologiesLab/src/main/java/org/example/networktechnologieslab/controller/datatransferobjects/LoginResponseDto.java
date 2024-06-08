package org.example.networktechnologieslab.controller.datatransferobjects;

public class LoginResponseDto {
    private String token; // the security token with login response

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public LoginResponseDto(String token){
        this.token=token;
    }
}
