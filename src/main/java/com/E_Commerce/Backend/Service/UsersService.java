package com.E_Commerce.Backend.Service;


import com.E_Commerce.Backend.DTO.request.LoginRequest;
import com.E_Commerce.Backend.DTO.request.RegisterRequest;
import com.E_Commerce.Backend.DTO.response.LoginResponse;
import com.E_Commerce.Backend.DTO.response.UserResponse;
import com.E_Commerce.Backend.Entities.Users;
import com.E_Commerce.Backend.Enum.Role;
import com.E_Commerce.Backend.Repository.UserRepository;
import com.E_Commerce.Backend.Security.JWTService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class UsersService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final JWTService jwtService;
    @Autowired
    private  final AuthenticationManager authMAnager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();

    }
    @PostConstruct
    public void staticAdmin() {
        String adminEmail = "admin@ecommerce.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            Users admin = new Users();
            admin.setName("Mohamed Saeed");
            admin.setRole(Role.ADMIN);
            admin.setPhone("01557646848");
            admin.setEmail(adminEmail);
            admin.setAddress("Giza");
            admin.setPassword(encoder.encode("Mohamed@2003"));

            userRepository.save(admin);
            System.out.println("Static admin created.");
        }
    }

    public void SignUp(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user.setRole(Role.USER);
        userRepository.save(user);


    }
    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authMAnager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Invalid email or password");
        }

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(user, token);
    }



    public void deleteuser(Long userId) {
        boolean exists =  userRepository.existsById(userId);
        if (!exists){
            throw new IllegalStateException("user with id " + userId + "does not exists");
        }
        userRepository.deleteById(userId);
    }
    @Transactional
    public void updateUser(Long userId , String name , String email){
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId +
                        "does not exists"));
        if (name != null && name.length() > 0 && !Objects.equals(users.getName(),name)){
            users.setName(name);
        }
        if (email != null && email.length() > 0 && !Objects.equals(users.getEmail(),email)){
            Optional<Users> usersOptional = userRepository.findByEmail(email);
            if (usersOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            users.setEmail(email);
        }
    }

}
