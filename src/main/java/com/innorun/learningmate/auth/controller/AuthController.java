package com.innorun.learningmate.auth.controller;

import com.innorun.learningmate.auth.dto.request.LoginRequest;
import com.innorun.learningmate.auth.dto.request.ReissueRequest;
import com.innorun.learningmate.auth.dto.request.SignupRequest;
import com.innorun.learningmate.auth.dto.response.TokenResponse;
import com.innorun.learningmate.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<Void> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<TokenResponse> reissue(
            @Valid @RequestBody ReissueRequest request
    ) {
        return ResponseEntity.ok(authService.reissue(request));
    }
}
