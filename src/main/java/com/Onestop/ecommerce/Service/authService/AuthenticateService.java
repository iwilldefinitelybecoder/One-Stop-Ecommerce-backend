package com.Onestop.ecommerce.Service.authService;


import com.Onestop.ecommerce.Controller.authUser.AuthenticateRequest;
import com.Onestop.ecommerce.Controller.authUser.AuthenticationResponse;
import com.Onestop.ecommerce.Controller.authUser.RegisterRequest;
import com.Onestop.ecommerce.Entity.Role;
import com.Onestop.ecommerce.Entity.user.userEntity;
import com.Onestop.ecommerce.Exceptions.UserAlreadyExistsException;
import com.Onestop.ecommerce.Repository.userRepo.UserRepository;
import com.Onestop.ecommerce.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateService {

    private final UserRepository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest)throws UserAlreadyExistsException {
        var userExists = userRespository.findByEmail(registerRequest.getEmail());
        if (userExists.isPresent()) {
        throw new UserAlreadyExistsException("User already exists");
        }
        var user = userEntity.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRespository.save(user);
        var jwtToken = jwtTokenProvider.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        var user1 = userRespository.findByEmail(request.getEmail());
        if (user1.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user =
                userRespository.findByEmail(request.getEmail())
                        .orElseThrow();
        var jwtToken = jwtTokenProvider.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .imageId(user.getImageId())
                .build();
    }

    public userEntity getUser(String email) {
        return userRespository.findByEmail(email).orElseThrow();
    }

    public String resetPassword(String email, String password) {
        var user = userRespository.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(password));
        try {
            userRespository.save(user);
            return "SUCCESS";
        }catch (Exception e){
            return "FAILED";
        }




        }
    public String updatePassword(String email, String password, String oldPassword) {
        var user = userRespository.findByEmail(email).orElseThrow();
        if(passwordEncoder.matches(oldPassword,user.getPassword())){
            user.setPassword(passwordEncoder.encode(password));
            try {
                userRespository.save(user);
                return "SUCCESS";
            }catch (Exception e){
                return "FAILED";
            }
        }
        return "PASSWORD_MISMATCH";

    }
    }


