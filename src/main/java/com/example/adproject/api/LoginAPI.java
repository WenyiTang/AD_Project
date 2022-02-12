package com.example.adproject.api;

import com.example.adproject.helper.UserHelper;
import com.example.adproject.model.User;
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
    public UserHelper authenticateLogin(@RequestParam String username, @RequestParam String password) {
        Boolean authenticated = uService.authenticateUser(username, password);

        if (authenticated) {
            User user = uService.findUserByUsername(username);
            UserHelper response = new UserHelper(user.getId().toString(), user.getUsername(), user.getName(), user.getProfilePic());
            return response;
        } else {
            return null;
        }
    }
}
