package com.E_Commerce.Backend.DTO.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
}
