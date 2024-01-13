package com.Onestop.ecommerce.Service.UserService;

import com.Onestop.ecommerce.Dto.PasswordResetResponse;
import com.Onestop.ecommerce.Entity.user.PasswordResetToken;
import com.Onestop.ecommerce.Entity.user.VerifyEmailToken;
import com.Onestop.ecommerce.Entity.user.userEntity;
import com.Onestop.ecommerce.Exceptions.ExpiredTokenException;
import com.Onestop.ecommerce.Exceptions.InvalidTokenException;
import com.Onestop.ecommerce.Exceptions.UserAlreadyVerifiedException;
import com.Onestop.ecommerce.Exceptions.UserNotfoundException;
import com.Onestop.ecommerce.Repository.userRepo.ResetPasswordToken;
import com.Onestop.ecommerce.Repository.userRepo.UserRepository;
import com.Onestop.ecommerce.Repository.userRepo.VerifyTokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final UserRepository userRepository;
    private final VerifyTokenRepo verifyTokenRepo;

    private final ResetPasswordToken resetPasswordToken;

    public String saveToken(userEntity user, String token) {
       var verifyEmailToken =  new VerifyEmailToken(token,user);
         verifyTokenRepo.save(verifyEmailToken);
        return "SUCCESS";
    }

    public String verifyToken(String token) {
        var verifyEmailToken = verifyTokenRepo.findByToken(token).orElse(null);
        if(verifyEmailToken == null){
            throw new InvalidTokenException("Invalid Token");
        }
        if(verifyEmailToken.getUser().isEnabled()){
            throw new UserAlreadyVerifiedException("User Already Verified");
        }

        var user = verifyEmailToken.getUser();
        var cal = Calendar.getInstance();
        if((verifyEmailToken.getExpiryDate().getTime() - cal.getTime().getTime())<=0){
            throw new ExpiredTokenException("Token Expired");
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    public VerifyEmailToken generateVerificationToken(String oldToken){
        var verifyEmailToken = verifyTokenRepo.findByToken(oldToken).orElse(null);
        if(verifyEmailToken == null){
            return null;
        }
        var user = verifyEmailToken.getUser();
        verifyTokenRepo.deleteById(verifyEmailToken.getId());
        var newToken = UUID.randomUUID().toString();
        return VerifyEmailToken.builder()
                .user(user)
                .token(newToken)
                .build();
    }


    //reset password token
    public String saveResetPasswordToken(userEntity user, String token) throws Exception {
        var passwordResetToken = resetPasswordToken.findByUserEmail(user.getEmail()).orElse(null);
        if(passwordResetToken != null){
            if(passwordResetToken.getExpiryDate().before(Calendar.getInstance().getTime())){
                resetPasswordToken.deleteById(passwordResetToken.getId());
            }
            else{
                throw new Exception("LINK_ALREADY_SENT");
            }
        }
        var resetToken =  new PasswordResetToken(token,user);
        resetPasswordToken.save(resetToken);
        return "SUCCESS";
    }

    public String verifyResetPasswordToken(String token) {
        var verifyToken = resetPasswordToken.findByToken(token).orElse(null);

        if(verifyToken == null){
            return "INVALID";
        }

        var user = verifyToken.getUser();
        var cal = Calendar.getInstance();
        if((verifyToken.getExpiryDate().getTime() - cal.getTime().getTime())<=0){
            return "EXPIRED";
        }


        return "valid" ;
    }

    public VerifyEmailToken generateResetPasswordToken(String oldToken){
        var verifyEmailToken = verifyTokenRepo.findByToken(oldToken).orElse(null);
        if(verifyEmailToken == null){
            return null;
        }
        var user = verifyEmailToken.getUser();
        verifyTokenRepo.deleteById(verifyEmailToken.getId());
        var newToken = UUID.randomUUID().toString();
        return VerifyEmailToken.builder()
                .user(user)
                .token(newToken)
                .build();
    }
    public PasswordResetResponse getUserDetails(String token){
        var verifyToken = resetPasswordToken.findByToken(token).orElse(null);
        if(verifyToken == null){
            return null;
        }
        var user = verifyToken.getUser();
        var cal = Calendar.getInstance();
        if((verifyToken.getExpiryDate().getTime() - cal.getTime().getTime())<=0){
            return null;
        }
        return PasswordResetResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .ImageId(user.getImageId())
                .build();
    }
    public userEntity getEmail(String token){
        var verifyToken = verifyTokenRepo.findByToken(token).orElse(null);
        if(verifyToken == null){
            throw new UserNotfoundException("User Not Found");
        }
        if(verifyToken.getUser().isEnabled()){
            throw new UserAlreadyVerifiedException("User Already Verified");
        }
        var cal = Calendar.getInstance();
        if((verifyToken.getExpiryDate().getTime() - cal.getTime().getTime())<=0){
            throw new ExpiredTokenException("Token Expired");
        }
        return verifyToken.getUser();
    }
}
