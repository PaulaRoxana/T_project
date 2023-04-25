package ro.itschool.controller.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {
    //private String email;
    private String username;
    private String password;
}
