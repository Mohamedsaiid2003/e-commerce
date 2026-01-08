package com.E_Commerce.Backend.Security;


import com.E_Commerce.Backend.Entities.Users;
import com.E_Commerce.Backend.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(email);
        if (user != null) {
            System.out.println("User found: " + user.getUsername());
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>()
            );
        }

        System.out.println("User not found");
        throw new UsernameNotFoundException("User not found");

    }
}
