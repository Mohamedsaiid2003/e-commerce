package com.E_Commerce.Backend.DTO.response;

import com.E_Commerce.Backend.Entities.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String token;

    public LoginResponse(Users user, String token) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.token = token;
    }
}
