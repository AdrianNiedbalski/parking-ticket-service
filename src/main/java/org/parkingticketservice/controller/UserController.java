package org.parkingticketservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    @PostMapping(value = "/loginWithInjection", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> loginWithInjection(@RequestParam("login") String login) {
        String sql = "SELECT username FROM users WHERE username = '" + login + "' AND password = 'password123'";
        //String sql = "SELECT username FROM users WHERE username = 'admin' OR '1' = '1' AND password = '123'";
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

            if (!rows.isEmpty()) {
                return ResponseEntity.ok("Welcome, attack is successful, " + rows.get(0).get("username"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sql error: " + e.getMessage());
        }
    }

    @PostMapping(value = "/loginSecure", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> loginSecure(@RequestParam("login") String login) {
        String sql = "SELECT username FROM users WHERE username = ? AND password = 'password123'";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, login);
        if (!rows.isEmpty()) {
            return ResponseEntity.ok("Welcome, " + rows.get(0).get("username"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login or password");
        }
    }
}
