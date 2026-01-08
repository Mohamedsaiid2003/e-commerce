package com.E_Commerce.Backend.Entities;

import com.E_Commerce.Backend.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "users_email_unique",columnNames = "email")
        }

)
public class Users implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = IDENTITY
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name = "name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String name;
    @Column(
            name = "email",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true
    )
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(
            name = "password",
            columnDefinition = "TEXT",
            nullable = false
    )
    @JsonIgnore
    private String password;
    @Column(
            name = "phone",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String phone;
    @Column(
            name = "address",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String address;
    private boolean enabled = true; // لحالة الحساب Active/Inactive
    private LocalDateTime createdAt;



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));

    }
    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
