package com.andrelopes.transaction_api.controllers;

import com.andrelopes.transaction_api.dtos.AuthenticationDTO;
import com.andrelopes.transaction_api.dtos.LoginResponseDTO;
import com.andrelopes.transaction_api.models.UserModel;
import com.andrelopes.transaction_api.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/login",consumes = "application/json")
    public ResponseEntity login (@RequestBody AuthenticationDTO authenticationDTO){

        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(),authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}
