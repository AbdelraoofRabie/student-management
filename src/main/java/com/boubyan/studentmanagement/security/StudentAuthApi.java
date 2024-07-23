package com.boubyan.studentmanagement.security;


import com.boubyan.studentmanagement.student.Student;
import com.boubyan.studentmanagement.student.StudentRepository;
import com.boubyan.studentmanagement.security.dto.AuthenticationRequest;
import com.boubyan.studentmanagement.security.dto.JwtResponse;
import com.boubyan.studentmanagement.security.dto.MessageResponse;
import com.boubyan.studentmanagement.security.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class StudentAuthApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (studentRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (studentRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Student user = new Student(signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        studentRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));

    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(),  authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body("Token cannot be refreshed.");
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
