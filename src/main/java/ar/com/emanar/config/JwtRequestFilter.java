package ar.com.emanar.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import ar.com.emanar.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) // va a realizar todos los filtros relacionados al token
			throws ServletException, IOException {
		
		final String token = getTokenFromRequest(request);
		final String username;
		
		if (token == null) {
			chain.doFilter(request, response);
			return;
		}
		
		username = jwtService.getUsernameFromToken(token);
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
			
			if (jwtService.isTokenValid(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		} 
		
		chain.doFilter(request, response);
		
	}

	private String getTokenFromRequest(HttpServletRequest request) { //nos devuelve un token String
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) { // si usamos jwt tenemops que evaluar que el authHeader comience con Bearer
			//extraemos el token
			return authHeader.substring(7);
		}		
		return null;
	}
}
