package edu.cornell.PPMFullStack.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.cornell.PPMFullStack.domain.User;
import edu.cornell.PPMFullStack.payload.JWTLoginSuccessResponse;
import edu.cornell.PPMFullStack.payload.LoginRequest;
import edu.cornell.PPMFullStack.security.JwtTokenProvider;
import edu.cornell.PPMFullStack.security.SecurityConstants;
import edu.cornell.PPMFullStack.services.MapValidationErrorService;
import edu.cornell.PPMFullStack.services.UserService;
import edu.cornell.PPMFullStack.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        userValidator.validate(user, result);

        ResponseEntity<?> errorMap= mapValidationErrorService.MapValidationServce(result);

        if (errorMap != null) return errorMap;

        User newUser= userService.saveUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
        BindingResult result) {
        ResponseEntity<?> errorMap= mapValidationErrorService.MapValidationServce(result);

        if (errorMap != null) return errorMap;

        Authentication authentication= authenticationManager.authenticate(

            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt= SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }
}
