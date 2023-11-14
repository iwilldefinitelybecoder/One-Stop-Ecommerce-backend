package com.Onestop.ecommerce.Controller.authUser;




import com.Onestop.ecommerce.Dto.PasswordResetRequest;
import com.Onestop.ecommerce.Dto.UpdatePasswordRequest;
import com.Onestop.ecommerce.Entity.user.VerifyEmailToken;
import com.Onestop.ecommerce.Events.Emmitter.ResendVerifyToken;
import com.Onestop.ecommerce.Events.Emmitter.ResetPasswordEmmitter;
import com.Onestop.ecommerce.Events.Emmitter.VerifyTokenEmmitter;
import com.Onestop.ecommerce.Exceptions.UserAlreadyExistsException;
import com.Onestop.ecommerce.Service.UserService.TokenService;
import com.Onestop.ecommerce.Service.authService.AuthenticateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.concurrent.Flow;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticateService services;
    private final TokenService tokenService;
    private final ApplicationEventPublisher eventPublisher;


    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request,HttpServletRequest servletRequest) {

        var response = services.register(request);
         var user = services.getUser(request.getEmail());
        eventPublisher.publishEvent(new VerifyTokenEmmitter(user, applicationUrl(servletRequest)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request) {
        return ResponseEntity.ok(services.authenticate(request));

    }

    @GetMapping("/tokenUser")
    public ResponseEntity<?> tokenUser(@RequestParam("token") String token) {
        try {
            var user = tokenService.getEmail(token);
            log.info("User Details: {}",user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/verifyemail")
    public ResponseEntity<?> verifyRegistration(@RequestParam("token") String token) {
        String result = tokenService.verifyToken(token);
        if(result.equalsIgnoreCase("valid")) {
            return ResponseEntity.status(HttpStatus.OK).body("User Verified");
        } else if (result.equalsIgnoreCase("expired")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token Expired");

        }else if(result.equalsIgnoreCase("ALREADY_VERIFIED")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Already Verified");

        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");

        }
    }


    @GetMapping("/resendVerifyToken")
    public ResponseEntity<?> resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request) {
        eventPublisher.publishEvent(new ResendVerifyToken(oldToken,applicationUrl(request)));
        return ResponseEntity.status(HttpStatus.OK).body("Verification Token Resent");
    }

    @PostMapping("/requestResetPassword")
    public ResponseEntity<?> requestResetPassword(@RequestParam("email") String email,
                                           HttpServletRequest request) {
        var user = services.getUser(email);
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Does Not Exist");
        }
        try {
            eventPublisher.publishEvent(new ResetPasswordEmmitter(email,applicationUrl(request)));
            return ResponseEntity.status(HttpStatus.OK).body("Reset Password Token Sent");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in sending token");
        }
    }

    @GetMapping("/verifyResetPasswordToken")
    public ResponseEntity<?> verifyResetPasswordToken(@RequestParam("token") String token) {
        String result = tokenService.verifyResetPasswordToken(token);
        if(result.equalsIgnoreCase("valid")) {
           var response =  tokenService.getUserDetails(token);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else if (result.equalsIgnoreCase("expired")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token Expired");
        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Token");

        }

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        var result = services.resetPassword(request.getEmail(),request.getPassword());
        if(result.equals("SUCCESS")){
            return ResponseEntity.status(HttpStatus.OK).body("Password Updated");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in updating password");

    }
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) {
        var result = services.updatePassword(request.getEmail(),request.getPassword(),request.getOldPassword());
        if(result.equals("SUCCESS")){
            return ResponseEntity.status(HttpStatus.OK).body("Password Updated");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in updating password");

    }

    private String applicationUrl(HttpServletRequest servletRequest) {
        return servletRequest.getHeader("x-host-url");



    }


}



