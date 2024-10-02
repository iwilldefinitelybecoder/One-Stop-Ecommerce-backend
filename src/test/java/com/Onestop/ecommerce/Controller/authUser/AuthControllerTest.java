//package com.Onestop.ecommerce.Controller.authUser;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.atLeast;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.Onestop.ecommerce.Dto.PasswordResetRequest;
//import com.Onestop.ecommerce.Dto.PasswordResetResponse;
//import com.Onestop.ecommerce.Dto.UpdatePasswordRequest;
//import com.Onestop.ecommerce.Entity.user.PasswordResetToken;
//import com.Onestop.ecommerce.Entity.user.RoleEntity;
//import com.Onestop.ecommerce.Entity.user.VerifyEmailToken;
//import com.Onestop.ecommerce.Entity.user.userEntity;
//import com.Onestop.ecommerce.Exceptions.UserAlreadyExistsException;
//import com.Onestop.ecommerce.Repository.CustomerRepo.CartItemsRepo;
//import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
//import com.Onestop.ecommerce.Repository.CustomerRepo.CustomerRepo;
//import com.Onestop.ecommerce.Repository.CustomerRepo.WishListRepo;
//import com.Onestop.ecommerce.Repository.userRepo.ResetPasswordToken;
//import com.Onestop.ecommerce.Repository.userRepo.RoleRepository;
//import com.Onestop.ecommerce.Repository.userRepo.UserRepository;
//import com.Onestop.ecommerce.Repository.userRepo.VerifyTokenRepo;
//import com.Onestop.ecommerce.Service.Customer.CartServices;
//import com.Onestop.ecommerce.Service.Customer.CustomerServices;
//import com.Onestop.ecommerce.Service.UserService.TokenService;
//import com.Onestop.ecommerce.Service.authService.AuthenticateService;
//import com.Onestop.ecommerce.configuration.JwtService;
//import jakarta.servlet.http.HttpServletRequest;
//
//import java.time.LocalDate;
//import java.time.ZoneOffset;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Map;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//class AuthControllerTest {
//    /**
//     * Method under test: {@link AuthController#register(RegisterRequest, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRegister() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        RegisterRequest request = new RegisterRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou");
//
//        authController.register(request, new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#register(RegisterRequest, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRegister2() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   com.Onestop.ecommerce.Exceptions.UserAlreadyExistsException: User already exists
//        //       at com.Onestop.ecommerce.Service.authService.AuthenticateService.register(AuthenticateService.java:53)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.register(AuthController.java:45)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new userEntity()));
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        RegisterRequest request = new RegisterRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou");
//
//        authController.register(request, new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#register(RegisterRequest, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRegister3() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.util.NoSuchElementException: No value present
//        //       at java.util.Optional.orElseThrow(Optional.java:377)
//        //       at com.Onestop.ecommerce.Service.authService.AuthenticateService.addRoleToUser(AuthenticateService.java:89)
//        //       at com.Onestop.ecommerce.Service.authService.AuthenticateService.register(AuthenticateService.java:64)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.register(AuthController.java:45)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        RegisterRequest request = new RegisterRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou");
//
//        authController.register(request, new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#register(RegisterRequest, HttpServletRequest)}
//     */
//    @Test
//    void testRegister4() throws UserAlreadyExistsException {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.register((RegisterRequest) any())).thenReturn(new AuthenticationResponse());
//        when(authenticateService.getUser((String) any())).thenReturn(new userEntity());
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        RegisterRequest request = new RegisterRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou");
//
//        ResponseEntity<?> actualRegisterResult = authController.register(request, new MockHttpServletRequest());
//        assertTrue(actualRegisterResult.hasBody());
//        assertEquals(201, actualRegisterResult.getStatusCodeValue());
//        assertTrue(actualRegisterResult.getHeaders().isEmpty());
//        verify(authenticateService).register((RegisterRequest) any());
//        verify(authenticateService).getUser((String) any());
//        verify(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#register(RegisterRequest, HttpServletRequest)}
//     */
//    @Test
//    void testRegister5() throws UserAlreadyExistsException {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.register((RegisterRequest) any())).thenReturn(new AuthenticationResponse());
//        when(authenticateService.getUser((String) any())).thenReturn(null);
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        RegisterRequest request = new RegisterRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou");
//
//        ResponseEntity<?> actualRegisterResult = authController.register(request, new MockHttpServletRequest());
//        assertEquals("USER_NOT_FOUND", actualRegisterResult.getBody());
//        assertEquals(400, actualRegisterResult.getStatusCodeValue());
//        assertTrue(actualRegisterResult.getHeaders().isEmpty());
//        verify(authenticateService).register((RegisterRequest) any());
//        verify(authenticateService).getUser((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#register(RegisterRequest, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRegister6() throws UserAlreadyExistsException {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "com.Onestop.ecommerce.Controller.authUser.RegisterRequest.getEmail()" because "request" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.register(AuthController.java:46)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.register((RegisterRequest) any())).thenReturn(new AuthenticationResponse());
//        when(authenticateService.getUser((String) any())).thenReturn(new userEntity());
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        authController.register(null, new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#register(RegisterRequest, HttpServletRequest)}
//     */
//    @Test
//    void testRegister7() throws UserAlreadyExistsException {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.register((RegisterRequest) any())).thenReturn(new AuthenticationResponse());
//        when(authenticateService.getUser((String) any())).thenReturn(new userEntity());
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        ResponseEntity<?> actualRegisterResult = authController
//                .register(new RegisterRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou"), null);
//        assertEquals(
//                "Cannot invoke \"jakarta.servlet.http.HttpServletRequest.getHeader(String)\" because \"servletRequest\""
//                        + " is null",
//                actualRegisterResult.getBody());
//        assertEquals(400, actualRegisterResult.getStatusCodeValue());
//        assertTrue(actualRegisterResult.getHeaders().isEmpty());
//        verify(authenticateService).register((RegisterRequest) any());
//        verify(authenticateService).getUser((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#authenticate(AuthenticateRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testAuthenticate() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.authenticate(new AuthenticateRequest("jane.doe@example.org", "iloveyou"));
//    }
//
//    /**
//     * Method under test: {@link AuthController#authenticate(AuthenticateRequest)}
//     */
//    @Test
//    void testAuthenticate2() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new userEntity()));
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualAuthenticateResult = authController
//                .authenticate(new AuthenticateRequest("jane.doe@example.org", "iloveyou"));
//        assertEquals("NOT_VERIFIED", actualAuthenticateResult.getBody());
//        assertEquals(404, actualAuthenticateResult.getStatusCodeValue());
//        assertTrue(actualAuthenticateResult.getHeaders().isEmpty());
//        verify(userRepository).findByEmail((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#authenticate(AuthenticateRequest)}
//     */
//    @Test
//    void testAuthenticate3() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        UserRepository userRepository = mock(UserRepository.class);
//        Date DateOfBirth = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
//        ArrayList<RoleEntity> roles = new ArrayList<>();
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new userEntity(1L, "Jane", "Doe",
//                "iloveyou", DateOfBirth, 1L, "jane.doe@example.org", roles, new ArrayList<>(), 1L, true)));
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualAuthenticateResult = authController
//                .authenticate(new AuthenticateRequest("jane.doe@example.org", "iloveyou"));
//        assertEquals(
//                "No AuthenticationProvider found for org.springframework.security.authentication.UsernamePasswordAuth"
//                        + "enticationToken",
//                actualAuthenticateResult.getBody());
//        assertEquals(404, actualAuthenticateResult.getStatusCodeValue());
//        assertTrue(actualAuthenticateResult.getHeaders().isEmpty());
//        verify(userRepository).findByEmail((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#authenticate(AuthenticateRequest)}
//     */
//    @Test
//    void testAuthenticate4() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        userEntity userEntity = mock(userEntity.class);
//        when(userEntity.isEnabled()).thenReturn(true);
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(userEntity));
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualAuthenticateResult = authController
//                .authenticate(new AuthenticateRequest("jane.doe@example.org", "iloveyou"));
//        assertEquals(
//                "No AuthenticationProvider found for org.springframework.security.authentication.UsernamePasswordAuth"
//                        + "enticationToken",
//                actualAuthenticateResult.getBody());
//        assertEquals(404, actualAuthenticateResult.getStatusCodeValue());
//        assertTrue(actualAuthenticateResult.getHeaders().isEmpty());
//        verify(userRepository).findByEmail((String) any());
//        verify(userEntity).isEnabled();
//    }
//
//    /**
//     * Method under test: {@link AuthController#authenticate(AuthenticateRequest)}
//     */
//    @Test
//    void testAuthenticate5() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualAuthenticateResult = authController
//                .authenticate(new AuthenticateRequest("jane.doe@example.org", "iloveyou"));
//        assertEquals("User not found", actualAuthenticateResult.getBody());
//        assertEquals(404, actualAuthenticateResult.getStatusCodeValue());
//        assertTrue(actualAuthenticateResult.getHeaders().isEmpty());
//        verify(userRepository).findByEmail((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#authenticate(AuthenticateRequest)}
//     */
//    @Test
//    void testAuthenticate6() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        userEntity userEntity = mock(userEntity.class);
//        when(userEntity.isEnabled()).thenReturn(true);
//        Optional.of(userEntity);
//        AuthController authController = new AuthController(null,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualAuthenticateResult = authController
//                .authenticate(new AuthenticateRequest("jane.doe@example.org", "iloveyou"));
//        assertEquals(
//                "Cannot invoke \"com.Onestop.ecommerce.Service.authService.AuthenticateService.authenticate(com.Onestop"
//                        + ".ecommerce.Controller.authUser.AuthenticateRequest)\" because \"this.services\" is null",
//                actualAuthenticateResult.getBody());
//        assertEquals(404, actualAuthenticateResult.getStatusCodeValue());
//        assertTrue(actualAuthenticateResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link AuthController#authenticate(AuthenticateRequest)}
//     */
//    @Test
//    void testAuthenticate7() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        userEntity userEntity = mock(userEntity.class);
//        when(userEntity.isEnabled()).thenReturn(true);
//        Optional.of(userEntity);
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.authenticate((AuthenticateRequest) any())).thenReturn(new AuthenticationResponse());
//        AuthController authController = new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualAuthenticateResult = authController
//                .authenticate(new AuthenticateRequest("jane.doe@example.org", "iloveyou"));
//        assertTrue(actualAuthenticateResult.hasBody());
//        assertEquals(200, actualAuthenticateResult.getStatusCodeValue());
//        assertTrue(actualAuthenticateResult.getHeaders().isEmpty());
//        verify(authenticateService).authenticate((AuthenticateRequest) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testTokenUser() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testTokenUser2() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).tokenUser("foo");
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser3() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(new VerifyEmailToken("ABC123")));
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services,
//                new TokenService(mock(UserRepository.class), verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals(
//                "Cannot invoke \"com.Onestop.ecommerce.Entity.user.userEntity.isEnabled()\" because the return value of"
//                        + " \"com.Onestop.ecommerce.Entity.user.VerifyEmailToken.getUser()\" is null",
//                actualTokenUserResult.getBody());
//        assertEquals(400, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(verifyTokenRepo).findByToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser4() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(new userEntity());
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services,
//                new TokenService(mock(UserRepository.class), verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals("Token Expired", actualTokenUserResult.getBody());
//        assertEquals(400, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(verifyTokenRepo).findByToken((String) any());
//        verify(verifyEmailToken).getUser();
//        verify(verifyEmailToken).getExpiryDate();
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser5() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate()).thenReturn(null);
//        when(verifyEmailToken.getUser()).thenReturn(new userEntity());
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services,
//                new TokenService(mock(UserRepository.class), verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals(
//                "Cannot invoke \"java.util.Date.getTime()\" because the return value of \"com.Onestop.ecommerce.Entity"
//                        + ".user.VerifyEmailToken.getExpiryDate()\" is null",
//                actualTokenUserResult.getBody());
//        assertEquals(400, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(verifyTokenRepo).findByToken((String) any());
//        verify(verifyEmailToken).getUser();
//        verify(verifyEmailToken).getExpiryDate();
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser6() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        Date DateOfBirth = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
//        ArrayList<RoleEntity> roles = new ArrayList<>();
//        when(verifyEmailToken.getUser()).thenReturn(new userEntity(1L, "Jane", "Doe", "iloveyou", DateOfBirth, 1L,
//                "jane.doe@example.org", roles, new ArrayList<>(), 1L, true));
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services,
//                new TokenService(mock(UserRepository.class), verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals("User Already Verified", actualTokenUserResult.getBody());
//        assertEquals(400, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(verifyTokenRepo).findByToken((String) any());
//        verify(verifyEmailToken).getUser();
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser7() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        userEntity userEntity = mock(userEntity.class);
//        when(userEntity.isEnabled()).thenReturn(true);
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(userEntity);
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services,
//                new TokenService(mock(UserRepository.class), verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals("User Already Verified", actualTokenUserResult.getBody());
//        assertEquals(400, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(verifyTokenRepo).findByToken((String) any());
//        verify(verifyEmailToken).getUser();
//        verify(userEntity).isEnabled();
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser8() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.empty());
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services,
//                new TokenService(mock(UserRepository.class), verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals("User Not Found", actualTokenUserResult.getBody());
//        assertEquals(400, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(verifyTokenRepo).findByToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser9() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        Optional.of(verifyEmailToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(
//                new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider, roleRepository,
//                        resetPasswordToken, authenticationManager,
//                        new CustomerServices(customerRepo, cartRepo,
//                                new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                                mock(WishListRepo.class)),
//                        mock(ApplicationEventPublisher.class)),
//                null, mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals("Cannot invoke \"com.Onestop.ecommerce.Service.UserService.TokenService.getEmail(String)\" because"
//                + " \"this.tokenService\" is null", actualTokenUserResult.getBody());
//        assertEquals(400, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser10() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        Optional.of(verifyEmailToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        TokenService tokenService = mock(TokenService.class);
//        when(tokenService.getEmail((String) any())).thenReturn(new userEntity());
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals(4, ((Map<String, Object>) actualTokenUserResult.getBody()).size());
//        assertTrue(actualTokenUserResult.hasBody());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        assertEquals(200, actualTokenUserResult.getStatusCodeValue());
//        verify(tokenService).getEmail((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser11() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        Optional.of(verifyEmailToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        TokenService tokenService = mock(TokenService.class);
//        when(tokenService.getEmail((String) any())).thenReturn(null);
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals("Cannot invoke \"com.Onestop.ecommerce.Entity.user.userEntity.getEmail()\" because \"user\" is null",
//                actualTokenUserResult.getBody());
//        assertEquals(400, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(tokenService).getEmail((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser12() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        Optional.of(verifyEmailToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        TokenService tokenService = mock(TokenService.class);
//        Date DateOfBirth = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
//        ArrayList<RoleEntity> roles = new ArrayList<>();
//        when(tokenService.getEmail((String) any())).thenReturn(new userEntity(1L, "Jane", "Doe", "iloveyou", DateOfBirth,
//                1L, "jane.doe@example.org", roles, new ArrayList<>(), 1L, true));
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals(4, ((Map<String, String>) actualTokenUserResult.getBody()).size());
//        assertTrue(actualTokenUserResult.hasBody());
//        assertEquals(200, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(tokenService).getEmail((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#tokenUser(String)}
//     */
//    @Test
//    void testTokenUser13() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        Optional.of(verifyEmailToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        userEntity userEntity = mock(userEntity.class);
//        when(userEntity.getImageId()).thenReturn(1L);
//        when(userEntity.getEmail()).thenReturn("jane.doe@example.org");
//        when(userEntity.getFirstName()).thenReturn("Jane");
//        when(userEntity.getLastName()).thenReturn("Doe");
//        TokenService tokenService = mock(TokenService.class);
//        when(tokenService.getEmail((String) any())).thenReturn(userEntity);
//        ResponseEntity<?> actualTokenUserResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).tokenUser("ABC123");
//        assertEquals(4, ((Map<String, String>) actualTokenUserResult.getBody()).size());
//        assertTrue(actualTokenUserResult.hasBody());
//        assertEquals(200, actualTokenUserResult.getStatusCodeValue());
//        assertTrue(actualTokenUserResult.getHeaders().isEmpty());
//        verify(tokenService).getEmail((String) any());
//        verify(userEntity, atLeast(1)).getImageId();
//        verify(userEntity).getEmail();
//        verify(userEntity).getFirstName();
//        verify(userEntity).getLastName();
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyRegistration() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyRegistration2() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("foo");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyRegistration3() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "com.Onestop.ecommerce.Entity.user.userEntity.isEnabled()" because the return value of "com.Onestop.ecommerce.Entity.user.VerifyEmailToken.getUser()" is null
//        //       at com.Onestop.ecommerce.Service.UserService.TokenService.verifyToken(TokenService.java:41)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.verifyRegistration(AuthController.java:91)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(new VerifyEmailToken("ABC123")));
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    void testVerifyRegistration4() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//
//        VerifyEmailToken verifyEmailToken = new VerifyEmailToken("ABC123");
//        verifyEmailToken.setUser(new userEntity());
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        (new AuthController(services, new TokenService(userRepository, verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//        verify(verifyTokenRepo).findByToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyRegistration5() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "java.util.Date.getTime()" because the return value of "com.Onestop.ecommerce.Entity.user.VerifyEmailToken.getExpiryDate()" is null
//        //       at com.Onestop.ecommerce.Service.UserService.TokenService.verifyToken(TokenService.java:47)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.verifyRegistration(AuthController.java:91)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//
//        VerifyEmailToken verifyEmailToken = new VerifyEmailToken();
//        verifyEmailToken.setUser(new userEntity());
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        (new AuthController(services, new TokenService(userRepository, verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyRegistration6() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   com.Onestop.ecommerce.Exceptions.ExpiredTokenException: Token Expired
//        //       at com.Onestop.ecommerce.Service.UserService.TokenService.verifyToken(TokenService.java:48)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.verifyRegistration(AuthController.java:91)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(new userEntity());
//        doNothing().when(verifyEmailToken).setUser((userEntity) any());
//        verifyEmailToken.setUser(new userEntity());
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        (new AuthController(services, new TokenService(userRepository, verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyRegistration7() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   com.Onestop.ecommerce.Exceptions.UserAlreadyVerifiedException: User Already Verified
//        //       at com.Onestop.ecommerce.Service.UserService.TokenService.verifyToken(TokenService.java:42)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.verifyRegistration(AuthController.java:91)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        Date DateOfBirth = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
//        ArrayList<RoleEntity> roles = new ArrayList<>();
//        when(verifyEmailToken.getUser()).thenReturn(new userEntity(1L, "Jane", "Doe", "iloveyou", DateOfBirth, 1L,
//                "jane.doe@example.org", roles, new ArrayList<>(), 1L, true));
//        doNothing().when(verifyEmailToken).setUser((userEntity) any());
//        verifyEmailToken.setUser(new userEntity());
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        (new AuthController(services, new TokenService(userRepository, verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyRegistration8() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   com.Onestop.ecommerce.Exceptions.UserAlreadyVerifiedException: User Already Verified
//        //       at com.Onestop.ecommerce.Service.UserService.TokenService.verifyToken(TokenService.java:42)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.verifyRegistration(AuthController.java:91)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        doNothing().when(verifyEmailToken).setUser((userEntity) any());
//        verifyEmailToken.setUser(new userEntity());
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.of(verifyEmailToken));
//        (new AuthController(services, new TokenService(userRepository, verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyRegistration9() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   com.Onestop.ecommerce.Exceptions.InvalidTokenException: Invalid Token
//        //       at com.Onestop.ecommerce.Service.UserService.TokenService.verifyToken(TokenService.java:39)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.verifyRegistration(AuthController.java:91)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        doNothing().when(verifyEmailToken).setUser((userEntity) any());
//        verifyEmailToken.setUser(new userEntity());
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        VerifyTokenRepo verifyTokenRepo = mock(VerifyTokenRepo.class);
//        when(verifyTokenRepo.findByToken((String) any())).thenReturn(Optional.empty());
//        (new AuthController(services, new TokenService(userRepository, verifyTokenRepo, mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    void testVerifyRegistration10() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        doNothing().when(verifyEmailToken).setUser((userEntity) any());
//        verifyEmailToken.setUser(new userEntity());
//        Optional.of(verifyEmailToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        TokenService tokenService = mock(TokenService.class);
//        when(tokenService.verifyToken((String) any())).thenReturn("ABC123");
//        ResponseEntity<?> actualVerifyRegistrationResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//        assertEquals("Invalid Token", actualVerifyRegistrationResult.getBody());
//        assertEquals(400, actualVerifyRegistrationResult.getStatusCodeValue());
//        assertTrue(actualVerifyRegistrationResult.getHeaders().isEmpty());
//        verify(verifyEmailToken).setUser((userEntity) any());
//        verify(tokenService).verifyToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    void testVerifyRegistration11() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        doNothing().when(verifyEmailToken).setUser((userEntity) any());
//        verifyEmailToken.setUser(new userEntity());
//        Optional.of(verifyEmailToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        TokenService tokenService = mock(TokenService.class);
//        when(tokenService.verifyToken((String) any())).thenReturn("expired");
//        ResponseEntity<?> actualVerifyRegistrationResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//        assertEquals("Token Expired", actualVerifyRegistrationResult.getBody());
//        assertEquals(400, actualVerifyRegistrationResult.getStatusCodeValue());
//        assertTrue(actualVerifyRegistrationResult.getHeaders().isEmpty());
//        verify(verifyEmailToken).setUser((userEntity) any());
//        verify(tokenService).verifyToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyRegistration(String)}
//     */
//    @Test
//    void testVerifyRegistration12() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        VerifyEmailToken verifyEmailToken = mock(VerifyEmailToken.class);
//        when(verifyEmailToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        when(verifyEmailToken.getUser()).thenReturn(mock(userEntity.class));
//        doNothing().when(verifyEmailToken).setUser((userEntity) any());
//        verifyEmailToken.setUser(new userEntity());
//        Optional.of(verifyEmailToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        TokenService tokenService = mock(TokenService.class);
//        when(tokenService.verifyToken((String) any())).thenReturn("ALREADY_VERIFIED");
//        ResponseEntity<?> actualVerifyRegistrationResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).verifyRegistration("ABC123");
//        assertEquals("User Already Verified", actualVerifyRegistrationResult.getBody());
//        assertEquals(400, actualVerifyRegistrationResult.getStatusCodeValue());
//        assertTrue(actualVerifyRegistrationResult.getHeaders().isEmpty());
//        verify(verifyEmailToken).setUser((userEntity) any());
//        verify(tokenService).verifyToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#resendVerificationToken(String, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testResendVerificationToken() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.resendVerificationToken("ABC123", new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#resendVerificationToken(String, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testResendVerificationToken2() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.resendVerificationToken("foo", new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#resendVerificationToken(String, HttpServletRequest)}
//     */
//    @Test
//    void testResendVerificationToken3() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        ResponseEntity<?> actualResendVerificationTokenResult = authController.resendVerificationToken("ABC123",
//                new MockHttpServletRequest());
//        assertEquals("Verification Token Resent", actualResendVerificationTokenResult.getBody());
//        assertEquals(200, actualResendVerificationTokenResult.getStatusCodeValue());
//        assertTrue(actualResendVerificationTokenResult.getHeaders().isEmpty());
//        verify(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#resendVerificationToken(String, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testResendVerificationToken4() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "jakarta.servlet.http.HttpServletRequest.getHeader(String)" because "servletRequest" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.applicationUrl(AuthController.java:185)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.resendVerificationToken(AuthController.java:111)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher)).resendVerificationToken("ABC123", null);
//    }
//
//    /**
//     * Method under test: {@link AuthController#requestResetPassword(String, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRequestResetPassword() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.requestResetPassword("jane.doe@example.org", new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#requestResetPassword(String, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRequestResetPassword2() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.requestResetPassword("foo", new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#requestResetPassword(String, HttpServletRequest)}
//     */
//    @Test
//    void testRequestResetPassword3() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new userEntity()));
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        ResponseEntity<?> actualRequestResetPasswordResult = authController.requestResetPassword("jane.doe@example.org",
//                new MockHttpServletRequest());
//        assertEquals("SUCCESS", actualRequestResetPasswordResult.getBody());
//        assertEquals(200, actualRequestResetPasswordResult.getStatusCodeValue());
//        assertTrue(actualRequestResetPasswordResult.getHeaders().isEmpty());
//        verify(userRepository).findByEmail((String) any());
//        verify(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#requestResetPassword(String, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRequestResetPassword4() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.isEmpty()" because "user" is null
//        //       at com.Onestop.ecommerce.Service.authService.AuthenticateService.getUser(AuthenticateService.java:135)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.requestResetPassword(AuthController.java:119)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail((String) any())).thenReturn(null);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        authController.requestResetPassword("jane.doe@example.org", new MockHttpServletRequest());
//    }
//
//    /**
//     * Method under test: {@link AuthController#requestResetPassword(String, HttpServletRequest)}
//     */
//    @Test
//    void testRequestResetPassword5() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        ResponseEntity<?> actualRequestResetPasswordResult = authController.requestResetPassword("jane.doe@example.org",
//                new MockHttpServletRequest());
//        assertEquals("USER_NOT_FOUND", actualRequestResetPasswordResult.getBody());
//        assertEquals(400, actualRequestResetPasswordResult.getStatusCodeValue());
//        assertTrue(actualRequestResetPasswordResult.getHeaders().isEmpty());
//        verify(userRepository).findByEmail((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#requestResetPassword(String, HttpServletRequest)}
//     */
//    @Test
//    void testRequestResetPassword6() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.getUser((String) any())).thenReturn(new userEntity());
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        ResponseEntity<?> actualRequestResetPasswordResult = authController.requestResetPassword("jane.doe@example.org",
//                new MockHttpServletRequest());
//        assertEquals("SUCCESS", actualRequestResetPasswordResult.getBody());
//        assertEquals(200, actualRequestResetPasswordResult.getStatusCodeValue());
//        assertTrue(actualRequestResetPasswordResult.getHeaders().isEmpty());
//        verify(authenticateService).getUser((String) any());
//        verify(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#requestResetPassword(String, HttpServletRequest)}
//     */
//    @Test
//    void testRequestResetPassword7() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.getUser((String) any())).thenReturn(new userEntity());
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        AuthController authController = new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher);
//        ResponseEntity<?> actualRequestResetPasswordResult = authController.requestResetPassword(null,
//                new MockHttpServletRequest());
//        assertEquals("ERROR_IN_SENDING_EMAIL", actualRequestResetPasswordResult.getBody());
//        assertEquals(400, actualRequestResetPasswordResult.getStatusCodeValue());
//        assertTrue(actualRequestResetPasswordResult.getHeaders().isEmpty());
//        verify(authenticateService).getUser((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#requestResetPassword(String, HttpServletRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testRequestResetPassword8() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "jakarta.servlet.http.HttpServletRequest.getHeader(String)" because "request" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.requestResetPassword(AuthController.java:118)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.getUser((String) any())).thenReturn(new userEntity());
//        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
//        doNothing().when(applicationEventPublisher).publishEvent((ApplicationEvent) any());
//        (new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                applicationEventPublisher)).requestResetPassword("jane.doe@example.org", null);
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyResetPasswordToken(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyResetPasswordToken() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyResetPasswordToken("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyResetPasswordToken(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyResetPasswordToken2() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).verifyResetPasswordToken("foo");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyResetPasswordToken(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyResetPasswordToken3() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "com.Onestop.ecommerce.Entity.user.userEntity.getEmail()" because "user" is null
//        //       at com.Onestop.ecommerce.Service.UserService.TokenService.getUserDetails(TokenService.java:127)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.verifyResetPasswordToken(AuthController.java:136)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        ResetPasswordToken resetPasswordToken1 = mock(ResetPasswordToken.class);
//        when(resetPasswordToken1.findByToken((String) any())).thenReturn(Optional.of(new PasswordResetToken("ABC123")));
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), resetPasswordToken1),
//                mock(ApplicationEventPublisher.class))).verifyResetPasswordToken("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyResetPasswordToken(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testVerifyResetPasswordToken4() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "java.util.Date.getTime()" because the return value of "com.Onestop.ecommerce.Entity.user.PasswordResetToken.getExpiryDate()" is null
//        //       at com.Onestop.ecommerce.Service.UserService.TokenService.verifyResetPasswordToken(TokenService.java:95)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.verifyResetPasswordToken(AuthController.java:134)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        ResetPasswordToken resetPasswordToken1 = mock(ResetPasswordToken.class);
//        when(resetPasswordToken1.findByToken((String) any())).thenReturn(Optional.of(new PasswordResetToken()));
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), resetPasswordToken1),
//                mock(ApplicationEventPublisher.class))).verifyResetPasswordToken("ABC123");
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyResetPasswordToken(String)}
//     */
//    @Test
//    void testVerifyResetPasswordToken5() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        PasswordResetToken passwordResetToken = mock(PasswordResetToken.class);
//        when(passwordResetToken.getUser()).thenReturn(new userEntity());
//        when(passwordResetToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        ResetPasswordToken resetPasswordToken1 = mock(ResetPasswordToken.class);
//        when(resetPasswordToken1.findByToken((String) any())).thenReturn(Optional.of(passwordResetToken));
//        ResponseEntity<?> actualVerifyResetPasswordTokenResult = (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), resetPasswordToken1),
//                mock(ApplicationEventPublisher.class))).verifyResetPasswordToken("ABC123");
//        assertEquals("Token Expired", actualVerifyResetPasswordTokenResult.getBody());
//        assertEquals(400, actualVerifyResetPasswordTokenResult.getStatusCodeValue());
//        assertTrue(actualVerifyResetPasswordTokenResult.getHeaders().isEmpty());
//        verify(resetPasswordToken1).findByToken((String) any());
//        verify(passwordResetToken).getUser();
//        verify(passwordResetToken).getExpiryDate();
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyResetPasswordToken(String)}
//     */
//    @Test
//    void testVerifyResetPasswordToken6() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        ResetPasswordToken resetPasswordToken1 = mock(ResetPasswordToken.class);
//        when(resetPasswordToken1.findByToken((String) any())).thenReturn(Optional.empty());
//        ResponseEntity<?> actualVerifyResetPasswordTokenResult = (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), resetPasswordToken1),
//                mock(ApplicationEventPublisher.class))).verifyResetPasswordToken("ABC123");
//        assertEquals("Invalid Token", actualVerifyResetPasswordTokenResult.getBody());
//        assertEquals(400, actualVerifyResetPasswordTokenResult.getStatusCodeValue());
//        assertTrue(actualVerifyResetPasswordTokenResult.getHeaders().isEmpty());
//        verify(resetPasswordToken1).findByToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyResetPasswordToken(String)}
//     */
//    @Test
//    void testVerifyResetPasswordToken7() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        PasswordResetToken passwordResetToken = mock(PasswordResetToken.class);
//        when(passwordResetToken.getUser()).thenReturn(new userEntity());
//        when(passwordResetToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        Optional.of(passwordResetToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        TokenService tokenService = mock(TokenService.class);
//        when(tokenService.verifyResetPasswordToken((String) any())).thenReturn("ABC123");
//        ResponseEntity<?> actualVerifyResetPasswordTokenResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).verifyResetPasswordToken("ABC123");
//        assertEquals("Invalid Token", actualVerifyResetPasswordTokenResult.getBody());
//        assertEquals(400, actualVerifyResetPasswordTokenResult.getStatusCodeValue());
//        assertTrue(actualVerifyResetPasswordTokenResult.getHeaders().isEmpty());
//        verify(tokenService).verifyResetPasswordToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#verifyResetPasswordToken(String)}
//     */
//    @Test
//    void testVerifyResetPasswordToken8() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        PasswordResetToken passwordResetToken = mock(PasswordResetToken.class);
//        when(passwordResetToken.getUser()).thenReturn(new userEntity());
//        when(passwordResetToken.getExpiryDate())
//                .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
//        Optional.of(passwordResetToken);
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        TokenService tokenService = mock(TokenService.class);
//        when(tokenService.getUserDetails((String) any())).thenReturn(mock(PasswordResetResponse.class));
//        when(tokenService.verifyResetPasswordToken((String) any())).thenReturn("valid");
//        ResponseEntity<?> actualVerifyResetPasswordTokenResult = (new AuthController(services, tokenService,
//                mock(ApplicationEventPublisher.class))).verifyResetPasswordToken("ABC123");
//        assertTrue(actualVerifyResetPasswordTokenResult.hasBody());
//        assertEquals(200, actualVerifyResetPasswordTokenResult.getStatusCodeValue());
//        assertTrue(actualVerifyResetPasswordTokenResult.getHeaders().isEmpty());
//        verify(tokenService).getUserDetails((String) any());
//        verify(tokenService).verifyResetPasswordToken((String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#resetPassword(PasswordResetRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testResetPassword() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.resetPassword(new PasswordResetRequest("jane.doe@example.org", "iloveyou", "ABC123"));
//    }
//
//    /**
//     * Method under test: {@link AuthController#resetPassword(PasswordResetRequest)}
//     */
//    @Test
//    void testResetPassword2() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(new userEntity()));
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        doNothing().when(resetPasswordToken).delete((PasswordResetToken) any());
//        when(resetPasswordToken.findByToken((String) any())).thenReturn(Optional.of(new PasswordResetToken("ABC123")));
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualResetPasswordResult = authController
//                .resetPassword(new PasswordResetRequest("jane.doe@example.org", "iloveyou", "ABC123"));
//        assertEquals("Password Updated", actualResetPasswordResult.getBody());
//        assertEquals(200, actualResetPasswordResult.getStatusCodeValue());
//        assertTrue(actualResetPasswordResult.getHeaders().isEmpty());
//        verify(userRepository).save((userEntity) any());
//        verify(userRepository).findByEmail((String) any());
//        verify(resetPasswordToken).findByToken((String) any());
//        verify(resetPasswordToken).delete((PasswordResetToken) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#resetPassword(PasswordResetRequest)}
//     */
//    @Test
//    void testResetPassword3() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        userEntity userEntity = mock(userEntity.class);
//        doNothing().when(userEntity).setPassword((String) any());
//        Optional<userEntity> ofResult = Optional.of(userEntity);
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        doNothing().when(resetPasswordToken).delete((PasswordResetToken) any());
//        when(resetPasswordToken.findByToken((String) any())).thenReturn(Optional.of(new PasswordResetToken("ABC123")));
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualResetPasswordResult = authController
//                .resetPassword(new PasswordResetRequest("jane.doe@example.org", "iloveyou", "ABC123"));
//        assertEquals("Password Updated", actualResetPasswordResult.getBody());
//        assertEquals(200, actualResetPasswordResult.getStatusCodeValue());
//        assertTrue(actualResetPasswordResult.getHeaders().isEmpty());
//        verify(userRepository).save((userEntity) any());
//        verify(userRepository).findByEmail((String) any());
//        verify(userEntity).setPassword((String) any());
//        verify(resetPasswordToken).findByToken((String) any());
//        verify(resetPasswordToken).delete((PasswordResetToken) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#resetPassword(PasswordResetRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testResetPassword4() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.util.NoSuchElementException: No value present
//        //       at java.util.Optional.orElseThrow(Optional.java:377)
//        //       at com.Onestop.ecommerce.Service.authService.AuthenticateService.resetPassword(AuthenticateService.java:147)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.resetPassword(AuthController.java:149)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        when(userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        doNothing().when(resetPasswordToken).delete((PasswordResetToken) any());
//        when(resetPasswordToken.findByToken((String) any())).thenReturn(Optional.of(new PasswordResetToken("ABC123")));
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.resetPassword(new PasswordResetRequest("jane.doe@example.org", "iloveyou", "ABC123"));
//    }
//
//    /**
//     * Method under test: {@link AuthController#resetPassword(PasswordResetRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testResetPassword5() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.util.NoSuchElementException: No value present
//        //       at java.util.Optional.orElseThrow(Optional.java:377)
//        //       at com.Onestop.ecommerce.Service.authService.AuthenticateService.resetPassword(AuthenticateService.java:149)
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.resetPassword(AuthController.java:149)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        userEntity userEntity = mock(userEntity.class);
//        doNothing().when(userEntity).setPassword((String) any());
//        Optional<userEntity> ofResult = Optional.of(userEntity);
//        UserRepository userRepository = mock(UserRepository.class);
//        when(userRepository.save((userEntity) any())).thenReturn(new userEntity());
//        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        doNothing().when(resetPasswordToken).delete((PasswordResetToken) any());
//        when(resetPasswordToken.findByToken((String) any())).thenReturn(Optional.empty());
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRepository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.resetPassword(new PasswordResetRequest("jane.doe@example.org", "iloveyou", "ABC123"));
//    }
//
//    /**
//     * Method under test: {@link AuthController#resetPassword(PasswordResetRequest)}
//     */
//    @Test
//    void testResetPassword6() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        userEntity userEntity = mock(userEntity.class);
//        doNothing().when(userEntity).setPassword((String) any());
//        Optional.of(userEntity);
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.resetPassword((String) any(), (String) any(), (String) any())).thenReturn("iloveyou");
//        AuthController authController = new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        ResponseEntity<?> actualResetPasswordResult = authController
//                .resetPassword(new PasswordResetRequest("jane.doe@example.org", "iloveyou", "ABC123"));
//        assertEquals("Error in updating password", actualResetPasswordResult.getBody());
//        assertEquals(400, actualResetPasswordResult.getStatusCodeValue());
//        assertTrue(actualResetPasswordResult.getHeaders().isEmpty());
//        verify(authenticateService).resetPassword((String) any(), (String) any(), (String) any());
//    }
//
//    /**
//     * Method under test: {@link AuthController#resetPassword(PasswordResetRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testResetPassword7() {
//        //   Diffblue Cover was unable to write a Spring test,
//        //   so wrote a non-Spring test instead.
//        //   Diffblue AI was unable to find a test
//
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   java.lang.NullPointerException: Cannot invoke "com.Onestop.ecommerce.Dto.PasswordResetRequest.getEmail()" because "request" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.resetPassword(AuthController.java:149)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        userEntity userEntity = mock(userEntity.class);
//        doNothing().when(userEntity).setPassword((String) any());
//        Optional.of(userEntity);
//        AuthenticateService authenticateService = mock(AuthenticateService.class);
//        when(authenticateService.resetPassword((String) any(), (String) any(), (String) any())).thenReturn("iloveyou");
//        (new AuthController(authenticateService,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).resetPassword(null);
//    }
//
//    /**
//     * Method under test: {@link AuthController#validateOldPassword(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testValidateOldPassword() {
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.validateOldPassword(AuthController.java:160)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).validateOldPassword("iloveyou");
//    }
//
//    /**
//     * Method under test: {@link AuthController#validateOldPassword(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testValidateOldPassword2() {
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.validateOldPassword(AuthController.java:160)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).validateOldPassword("foo");
//    }
//
//    /**
//     * Method under test: {@link AuthController#validateOldPassword(String)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testValidateOldPassword3() {
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.validateOldPassword(AuthController.java:160)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        (new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class))).validateOldPassword("iloveyou");
//    }
//
//    /**
//     * Method under test: {@link AuthController#updatePassword(UpdatePasswordRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testUpdatePassword() {
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.updatePassword(AuthController.java:174)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.updatePassword(new UpdatePasswordRequest("jane.doe@example.org", "iloveyou", "iloveyou"));
//    }
//
//    /**
//     * Method under test: {@link AuthController#updatePassword(UpdatePasswordRequest)}
//     */
//    @Test
//    @Disabled("TODO: Complete this test")
//    void testUpdatePassword2() {
//        // TODO: Complete this test.
//        //   Reason: R013 No inputs found that don't throw a trivial exception.
//        //   Diffblue Cover tried to run the arrange/act section, but the method under
//        //   test threw
//        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
//        //       at com.Onestop.ecommerce.Controller.authUser.AuthController.updatePassword(AuthController.java:174)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
//        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
//        //   See https://diff.blue/R013 to resolve this issue.
//
//        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
//        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
//        UserRepository userRespository = mock(UserRepository.class);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        JwtService jwtTokenProvider = new JwtService();
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        ResetPasswordToken resetPasswordToken = mock(ResetPasswordToken.class);
//        CustomerRepo customerRepo = mock(CustomerRepo.class);
//        CartRepo cartRepo = mock(CartRepo.class);
//        AuthenticateService services = new AuthenticateService(userRespository, passwordEncoder, jwtTokenProvider,
//                roleRepository, resetPasswordToken, authenticationManager,
//                new CustomerServices(customerRepo, cartRepo,
//                        new CartServices(mock(CartRepo.class), mock(CartItemsRepo.class), mock(CustomerRepo.class)),
//                        mock(WishListRepo.class)),
//                mock(ApplicationEventPublisher.class));
//
//        AuthController authController = new AuthController(services,
//                new TokenService(mock(UserRepository.class), mock(VerifyTokenRepo.class), mock(ResetPasswordToken.class)),
//                mock(ApplicationEventPublisher.class));
//        authController.updatePassword(new UpdatePasswordRequest("jane.doe@example.org", "iloveyou", "iloveyou"));
//    }
//}
//
