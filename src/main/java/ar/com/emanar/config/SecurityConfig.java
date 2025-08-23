package ar.com.emanar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtRequestFilter jwtRequestFilter;
	private final AuthenticationProvider authProvider;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/productos").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/producto/**").permitAll()
						.requestMatchers("/auth/**",
								"/h2-console/**",
								"/swagger-ui/**",
								"/v3/api-docs/**",
								"/swagger-ui.html").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authProvider)
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) //encargado de auntenticar las credenciales
				.build();
	}	
	
	@Bean
	public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");//.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
}
