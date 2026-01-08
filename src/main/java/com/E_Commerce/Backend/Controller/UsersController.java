package com.E_Commerce.Backend.Controller;

import com.E_Commerce.Backend.DTO.request.LoginRequest;
import com.E_Commerce.Backend.DTO.request.RegisterRequest;
import com.E_Commerce.Backend.DTO.response.LoginResponse;
import com.E_Commerce.Backend.DTO.response.UserResponse;
import com.E_Commerce.Backend.Service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/user/")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping(path = "/GetUserData/")
     public List<UserResponse> getUserData(){
       return usersService.getAllUsers();
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(usersService.login(request));
    }

    @PostMapping(path = "/SignUp/")
    public void  registerNewUser(@RequestBody RegisterRequest request){
        usersService.SignUp(request);
    }
    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ){
        usersService.updateUser(userId,name,email);

    }
    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        usersService.deleteuser(userId);
    }


}