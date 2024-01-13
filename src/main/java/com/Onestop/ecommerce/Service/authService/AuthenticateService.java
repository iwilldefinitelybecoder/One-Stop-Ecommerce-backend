package com.Onestop.ecommerce.Service.authService;


import com.Onestop.ecommerce.Controller.authUser.AuthenticateRequest;
import com.Onestop.ecommerce.Controller.authUser.AuthenticationResponse;
import com.Onestop.ecommerce.Controller.authUser.RegisterRequest;
import com.Onestop.ecommerce.Entity.user.RoleEntity;
import com.Onestop.ecommerce.Entity.user.userEntity;
import com.Onestop.ecommerce.Exceptions.UserAlreadyExistsException;
import com.Onestop.ecommerce.Exceptions.UserEmailNotVerifiedException;
import com.Onestop.ecommerce.Exceptions.UserNotfoundException;
import com.Onestop.ecommerce.Repository.userRepo.ResetPasswordToken;
import com.Onestop.ecommerce.Repository.userRepo.RoleRepository;
import com.Onestop.ecommerce.Repository.userRepo.UserRepository;
import com.Onestop.ecommerce.Service.Customer.CustomerServices;
import com.Onestop.ecommerce.Service.Customer.WishListServices;
import com.Onestop.ecommerce.configuration.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateService {

    private final UserRepository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtTokenProvider;

    private final RoleRepository roleRepository;
    private final ResetPasswordToken resetPasswordToken;

    private final AuthenticationManager authenticationManager;
    private final CustomerServices customerServices;

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
                .roles(new ArrayList<RoleEntity>())
                .build();
        userRespository.save(user);
        addRoleToUser(user, "USER");
        String value = customerServices.saveCustomer(user);
        var user1 = userRespository.findByEmail(registerRequest.getEmail()).orElseThrow();
        var jwtToken = jwtTokenProvider.generateToken(user);
        Collection<String> roles = new ArrayList<>();
        for (RoleEntity role : user1.getRoles()) {
            roles.add(role.getName());
        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user1.getEmail())
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .roles(roles)
                .build();

    }

    private void addRoleToUser(userEntity user, String role) {
        var user1 = userRespository.findByEmail(user.getEmail()).orElseThrow();
        var role1 = roleRepository.findByName(role);
        var addNewRole = user1.getRoles();
        addNewRole.add(role1);
        user.setRoles(addNewRole);
        userRespository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        var user1 = userRespository.findByEmail(request.getEmail());

        if (user1.isEmpty()) {
            throw new UserNotfoundException("User not found");
        }
        if(!user1.get().isEnabled()){
            throw new UserEmailNotVerifiedException("NOT_VERIFIED");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        log.info("Authenticating user {}", user1.get().getEmail());

        var user =
                userRespository.findByEmail(request.getEmail())
                        .orElseThrow();
        var jwtToken = jwtTokenProvider.generateToken(user);
        Collection<String> roles = new ArrayList<>();
        for (RoleEntity role : user.getRoles()) {
            roles.add(role.getName());
        }
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(roles)
                .imageId(user.getImageId())
                .build();
    }

    public userEntity getUser(String email) {
        return userRespository.findByEmail(email).orElseThrow();
    }

    public String resetPassword(String email, String password,String token) {
    log.info("Resetting password for user {}",token);

        var user = userRespository.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(password));
        var res = resetPasswordToken.findByToken(token).orElseThrow();
        try {
            userRespository.save(user);
            resetPasswordToken.delete(res);
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

    public void addRoles(String role) {
        var role1 = RoleEntity.builder()
                .name(role)
                .build();
        roleRepository.save(role1);
    }

    }


