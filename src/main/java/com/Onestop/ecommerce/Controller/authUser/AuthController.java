package com.Onestop.ecommerce.Controller.authUser;




import com.Onestop.ecommerce.Service.authService.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateService services;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(services.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request) {
        String token = services.authenticate(request).getToken();
        return ResponseEntity.ok(services.authenticate(request));

    }


}



