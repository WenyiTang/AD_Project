package com.example.adproject.api;

import com.example.adproject.helper.UserHelper;
import com.example.adproject.model.Role;
import com.example.adproject.model.User;
import com.example.adproject.repo.RoleRepo;
import com.example.adproject.security.Utility;
import com.example.adproject.service.UserNotFoundException;
import com.example.adproject.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginAPI {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService uService;

    @Autowired
    private RoleRepo rRepo;

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

    @PostMapping("/reset_email")
    public Map<String, Integer> sendResetEmail(@RequestParam String email, HttpServletRequest request) {
        Map<String, Integer> response = new HashMap<>();

        User user = uService.findUserByEmail(email);
        String token = RandomString.make(30);

        if (user == null) {
            response.put("status", 1);
        } else {
            try {
                uService.updateResetPasswordToken(token, email);
                String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
                sendEmail(email, resetPasswordLink);
                response.put("status", 0);
            } catch (UserNotFoundException e) {
                response.put("status", 1);
            } catch (UnsupportedEncodingException | MessagingException e) {
                response.put("status", 1);
            }
        }
        return response;
    }

    @PostMapping("/create01")
    public Map<String, String> validateAndSetCredentials(@RequestParam MultipartFile multipartFile,
                                                         @RequestParam String fileName,
                                                         @RequestParam String imageURL,
                                                         @RequestParam String username,
                                                         @RequestParam String password,
                                                         @RequestParam String email) {

        Map<String, String> response = new HashMap<>();

        if (multipartFile == null || multipartFile.isEmpty()) {
            response.put("error", "MultipartFileFailure");
            return response;
        }

        User u1 = uService.findUserByUsername(username);
        if (u1 != null) {
            response.put("status", "Username error");
            response.put("username", "");
            return response;
        }

        User u2 = uService.findUserByEmail(email);

        if (u2 != null) {
            response.put("status", "Email error");
            response.put("username", "");
            return response;
        }

        if (u1 == null && u2 == null) {

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setProfilePic(fileName);
            newUser.setImageURL(imageURL);
            newUser.setEnabled(true);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encoderPassword = encoder.encode(newUser.getPassword());
            newUser.setPassword(encoderPassword);

            Role role = rRepo.findByType("USER");
            newUser.getRoles().add(role);

            uService.save(newUser);

            String uploadDir = "./images/" + newUser.getId();

            Path uploadPath = Paths.get(uploadDir);

            // Saving user's profile pic into directory
            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                File profileImg = new File("images/" + newUser.getId() + "/" + fileName);
                profileImg.createNewFile();
                FileOutputStream fout = new FileOutputStream(profileImg);
                fout.write(multipartFile.getBytes());
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            response.put("status", "OK");
            response.put("username", username);
        }
        return response;
    }

    @PostMapping("/create02")
    public Map<String, String> validateAndSetParticulars(@RequestParam String username,
                                                         @RequestParam String name,
                                                         @RequestParam String height,
                                                         @RequestParam String weight,
                                                         @RequestParam String gender,
                                                         @RequestParam String dob) {

        Map<String, String> response = new HashMap<>();

        User user = uService.findUserByUsername(username);
        Double weight_ = Double.parseDouble(weight);
        Double height_ = Double.parseDouble(height) * 100;

        if (weight_<= 0 && height_ <= 0) {
            response.put("status", "Invalid W & H");
            return response;
        }
        if (weight_ <= 0) {
            response.put("status", "Invalid W");
            return response;
        }
        if (height_ <= 0) {
            response.put("status", "Invalid H");
            return response;
        }

        if (user == null) {
            response.put("status", "Error");
        }
         else {
             user.setName(name);


             user.setHeight(height_);


             user.setWeight(weight_);

             user.setGender(gender);

             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
             LocalDate dob_ = LocalDate.parse(dob, formatter);

             user.setDateOfBirth(dob_);

             uService.save(user);

             response.put("status", "OK");
        }
         return response;
    }

    private void sendEmail(String user_email, String resetPasswordLink)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@fooddiary.com", "Food Diary App Support");
        helper.setTo(user_email);

        String subject = "Reset your password";

        String content = "<p>Hi there,</p> "
                +"<p>We've received your request to reset your password.</p>"
                + "<p>Please click on the link below to reset your password:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Reset Password here</a></p>" + "<br>"
                + "<p>Please ignore this email if you did not make this request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);

    }
}
