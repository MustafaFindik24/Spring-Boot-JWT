package com.mustafafindik.security.TokenApp.Controller;

import com.mustafafindik.security.TokenApp.Dto.UserRequest;
import com.mustafafindik.security.TokenApp.Entity.User;
import com.mustafafindik.security.TokenApp.Security.JwtTokenProvider;
import com.mustafafindik.security.TokenApp.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authenticationToken); // authenticationToken ile jwt token oluşturulacak
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken= jwtTokenProvider.generateJwtToken(auth);
        return jwtToken;
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest registerRequest){
        if(userService.getOneUserByUsername(registerRequest.getUsername())!= null){
            return new ResponseEntity<>("Username already defined", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity<>("User successfully registered", HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> allUsers(){
        return userService.getUsers();
    }
}
