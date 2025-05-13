package com.example.jwtdemo.jwtdemo.controller;

import com.example.jwtdemo.jwtdemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${role.admin}")
    private String roleAdmin;

    @Value("${role.user}")
    private String roleUser;

//    // To access user-protected resources
//    @GetMapping("/protected-data")
//    public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token) {
//
//        if (token != null && token.startsWith("Bearer ")) {
//            String jwttoken = token.substring(7);
//        }
//
//        try {
//            if (jwtUtil.validateToken(token)) {
//                String username = jwtUtil.extractUsername(token); // Extract username from the JWT token
//
//                // Extract roles from the token
//                Set<String> roles = jwtUtil.extractRoles(token);
//
//                if (roles.contains(roleAdmin)) {
//                    return ResponseEntity.ok("Welcome " + username + ", here is your role-specific data: " + roles);
//                } else if (roles.contains(roleUser)) {
//                    return ResponseEntity.ok("Welcome " + username + ", here is your role-specific data: " + roles);
//                } else {
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You don't have the required role");
//                }
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
//        }
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//    }

    @GetMapping("/protected-data")
    public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing or invalid token format");
        }

        String jwtToken = token.substring(7); // Extract the token after "Bearer "

        try {
            if (jwtUtil.validateToken(jwtToken)) { // Use the extracted token for validation
                String username = jwtUtil.extractUsername(jwtToken); // Extract username
                Set<String> roles = jwtUtil.extractRoles(jwtToken); // Extract roles

                if (roles.contains(roleAdmin)) {
                    return ResponseEntity.ok("Welcome " + username + ", here is your role-specific data: " + roles);
                } else if (roles.contains(roleUser)) {
                    return ResponseEntity.ok("Welcome " + username + ", here is your role-specific data: " + roles);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: You don't have the required role");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token: " + e.getMessage());
        }
    }
}