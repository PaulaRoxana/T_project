package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.controller.model.AuthenticationRequest;
import ro.itschool.controller.model.AuthenticationResponse;
import ro.itschool.controller.model.RegisterRequest;
import ro.itschool.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return service.authenticate(authenticationRequest);
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest registerRequest) {
        return service.register(registerRequest);
    }

}
