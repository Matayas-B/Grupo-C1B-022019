package backend.controller;

import backend.controller.requests.LoginRequest;
import backend.controller.requests.SignUpRequest;
import backend.controller.responses.ApiResponse;
import backend.controller.responses.AuthResponse;
import backend.model.CustomerUser;
import backend.model.SupplierUser;
import backend.model.User;
import backend.model.enums.AuthProvider;
import backend.model.enums.UserType;
import backend.model.exception.BadRequestException;
import backend.repository.ICustomerRepository;
import backend.repository.ISupplierRepository;
import backend.repository.IUserRepository;
import backend.security.TokenProvider;
import backend.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private ISupplierRepository supplierRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CommunicationService communicationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerCustomerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws MessagingException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User result;
        if (signUpRequest.getUsertype().equals(UserType.customer)) {
            // Creating customer's account
            CustomerUser newCustomer = new CustomerUser(signUpRequest.getName(),
                    signUpRequest.getLastname(),
                    signUpRequest.getEmail(),
                    passwordEncoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getPhone(),
                    signUpRequest.getAddress());
            newCustomer.setProvider(AuthProvider.local);

            result = customerRepository.save(newCustomer);
        } else {
            // Creating supplier's account
            SupplierUser newSupplier = new SupplierUser(signUpRequest.getName(),
                    signUpRequest.getLastname(),
                    signUpRequest.getEmail(),
                    passwordEncoder.encode(signUpRequest.getPassword()),
                    signUpRequest.getPhone(),
                    signUpRequest.getAddress());
            newSupplier.setProvider(AuthProvider.local);

            result = supplierRepository.save(newSupplier);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        communicationService.sendWelcomeEmail(result.getEmail(), String.format("Welcome to our tasty world, %s", result.getName()), result.getName(), signUpRequest.getPassword());

        return ResponseEntity.created(location).body(new ApiResponse(true, result.getId().toString()));
    }
}
