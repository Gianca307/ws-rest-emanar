package ar.com.emanar.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.emanar.domain.Role;
import ar.com.emanar.domain.Usuario;
import ar.com.emanar.dto.AuthRequest;
import ar.com.emanar.dto.AuthResponse;
import ar.com.emanar.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UsuarioRepository usuarioRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthResponse login(AuthRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails usuario = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
		String token = jwtService.getToken(usuario);
		return AuthResponse.builder()
				.token(token)
				.build();
	}

	public AuthResponse register(AuthRequest request) {
		Role role = new Role();
		role.setId(1L);
		role.setRole("ROL_ADMIN");		
		
		if (usuarioRepository.existsByUsernameAllIgnoreCase(request.getUsername())) {
			throw new DataIntegrityViolationException("El usuario ya se encuentra registrado!");
		}
		
		Usuario usuario = Usuario.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(role)
				.build();
		
		usuarioRepository.save(usuario);
		
		return AuthResponse.builder()
				.token(jwtService.getToken(usuario))
				.build();
	}
	
}
