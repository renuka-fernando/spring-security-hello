package com.renuka.controllers;

import com.renuka.dto.AuthToken;
import com.renuka.dto.User;
import com.renuka.entity.Role;
import com.renuka.entity.RoleType;
import com.renuka.exception.HelloAppException;
import com.renuka.repository.RoleRepository;
import com.renuka.repository.UserRepository;
import com.renuka.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken(jwtTokenProvider.generateToken(authentication));
        authToken.setTokenType("bearer");
        return ResponseEntity.ok(authToken);
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@RequestBody User user) {
        if (userRepository.existsByName(user.getUsername())) {
            return ResponseEntity.badRequest().build();
        }

        com.renuka.entity.User userEntity = new com.renuka.entity.User();
        userEntity.setName(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.getRoleByRoleType(RoleType.ROLE_USER).orElseThrow(() -> new HelloAppException("Role Not Found"));
        userEntity.setRoles(Collections.singleton(role));
        userRepository.save(userEntity);

        return ResponseEntity.accepted().build();
    }
}
