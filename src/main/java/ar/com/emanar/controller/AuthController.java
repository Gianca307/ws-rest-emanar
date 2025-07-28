package ar.com.emanar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.emanar.dto.AuthRequest;
import ar.com.emanar.dto.AuthResponse;
import ar.com.emanar.service.AuthService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
		return ResponseEntity.ok(this.authService.login(request));
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registro(@RequestBody AuthRequest request){
		return ResponseEntity.ok(this.authService.register(request));
	}	
}
