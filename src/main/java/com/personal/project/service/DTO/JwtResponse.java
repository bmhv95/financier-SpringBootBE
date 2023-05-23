package com.personal.project.service.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JwtResponse implements Serializable {
    private String jwttoken;
    private String type = "Bearer";
    private String email;
    private List<String> roles;

    public JwtResponse(String jwttoken, String email, List<String> roles) {
        this.jwttoken = jwttoken;
        this.email = email;
        this.roles = roles;
    }
}
