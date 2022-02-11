package com.example.adproject.api;

import com.example.adproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginAPI {

    @Autowired
    private UserService uService;

    @PostMapping("/auth")
    public String authenticateLogin(@RequestParam String username, @RequestParam String password) {
        Boolean authenticated = uService.authenticateUser(username, password);
        String response = "";
        if (authenticated) {
            response = "VALID";
        } else {
            response = "INVALID";
        }
        return response;
    }
}
