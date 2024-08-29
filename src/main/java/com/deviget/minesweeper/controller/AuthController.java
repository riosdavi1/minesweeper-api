package com.deviget.minesweeper.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deviget.minesweeper.model.User;
import com.deviget.minesweeper.payload.request.LoginRequest;
import com.deviget.minesweeper.payload.request.SignupRequest;
import com.deviget.minesweeper.payload.response.LoginResponse;
import com.deviget.minesweeper.payload.response.MessageResponse;
import com.deviget.minesweeper.repository.UserRepository;
import com.deviget.minesweeper.security.service.UserDetailsImpl;
import com.deviget.minesweeper.security.token.TokenUtils;

/**
 * Controller class which handles user registration and token based authentication.
 * 
 * @author david.rios
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenUtils jwtUtils;

    /**
     * Handles user registration.
     * 
     * @param signUpRequest - a {@link SignupRequest} instance with the user details.
     * @return a {@link MessageResponse} message indicating if the registration was successful or not.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username already exists"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email already exists"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered"));
    }

    /**
     * Handles user authentication.
     * 
     * @param loginRequest - a {@link LoginRequest} instance with the login details.
     * @return a {@link LoginResponse} instance with the authentication token and user details.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest
                .getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LoginResponse(token, userDetails.getUsername(), userDetails.getEmail(), roles));
    }
}
