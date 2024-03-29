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
import java.util.HashMap;
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
        if(user == null){
            return ResponseEntity.status(400).body("USER_NOT_FOUND");
        }
        try {
            eventPublisher.publishEvent(new VerifyTokenEmmitter(user, applicationUrl(servletRequest)));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        }





    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticateRequest request) {
        try{
        return ResponseEntity.ok(services.authenticate(request));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/tokenUser")
    public ResponseEntity<?> tokenUser(@RequestParam("token") String token) {
        try {
            var user = tokenService.getEmail(token);
            log.info("User Details: {}",user);
            HashMap<String,String> response = new HashMap<>();
            response.put("email",user.getEmail());
            response.put("firstName",user.getFirstName());
            response.put("lastName",user.getLastName());
            response.put("profileIcon",user.getImageId() == null ? null : user.getImageId().toString());

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            log.info("Error: {}",e.getMessage());
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
        log.info(request.getHeader("x-host-url"));
        var user = services.getUser(email);
        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USER_NOT_FOUND");
        }
        try {
            eventPublisher.publishEvent(new ResetPasswordEmmitter(email,applicationUrl(request)));
            return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR_IN_SENDING_EMAIL");
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
        var result = services.resetPassword(request.getEmail(),request.getPassword(),request.getToken());
        if(result.equals("SUCCESS")){
            return ResponseEntity.status(HttpStatus.OK).body("Password Updated");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in updating password");

    }

    @GetMapping("/validateOldPassword")
    public ResponseEntity<?> validateOldPassword(@RequestParam("oldPassword") String password) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try{

            return ResponseEntity.status(HttpStatus.OK).body( services.validateOldPassword(userName,password));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        var result = services.updatePassword(userName,request.getNewPassword(),request.getOldPassword());
        if(result.equals("SUCCESS")){
            return ResponseEntity.status(HttpStatus.OK).body("Password Updated");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in updating password");

    }

    private String applicationUrl(HttpServletRequest servletRequest) {
        log.info("URL: {}",servletRequest.getHeader("x-host-url"));
        return servletRequest.getHeader("x-host-url");



    }


}



