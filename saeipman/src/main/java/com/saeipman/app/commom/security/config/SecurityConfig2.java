package com.saeipman.app.commom.security.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.saeipman.app.commom.jwt.JwtFilter;
import com.saeipman.app.commom.jwt.JwtUtil;
import com.saeipman.app.commom.jwt.LoginFilter;
import com.saeipman.app.commom.security.CustomLoginSuccessHandler;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig2 {
	// AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtill;
	 private final CustomLoginSuccessHandler successHandler;

	@Bean // 비밀번호 암호화
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// AuthenticationManager Bean 등록
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

//	@Bean
//	RoleHierarchy roleHierarchy() {
//		RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//		hierarchy.setHierarchy("""
//				ROLE_0 > ROLE_1
//				ROLE_1 > ROLE_2
//				""");
//		return hierarchy;
//	}

	// 관리자 페이지 - JWT 기반
	@Bean
	SecurityFilterChain fiterChainAdmin(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.disable());
		
		http
		.cors((cors) -> cors
				.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

	                    CorsConfiguration configuration = new CorsConfiguration();

	                    configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
	                    configuration.setAllowedMethods(Collections.singletonList("*"));
	                    configuration.setAllowCredentials(true);
	                    configuration.setAllowedHeaders(Collections.singletonList("*"));
	                    configuration.setMaxAge(3600L);
						configuration.setExposedHeaders(Collections.singletonList("Authorization"));

	                    return configuration;	
					}
				}));
		// csrf disable
		http
			.csrf(auth -> auth.disable());
//		 Form 로그인 방식 disable
		http.formLogin((auth) -> auth.disable());
        http
        	.securityMatcher("/api/admin/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/admin/**").hasRole("0")
                .anyRequest().authenticated()
    		);

        // 필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
		http
	    	.addFilterBefore(new JwtFilter(jwtUtill), LoginFilter.class);
		LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtill);
		loginFilter.setFilterProcessesUrl("/api/admin/login");
		http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
//		http
//		    .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtill), UsernamePasswordAuthenticationFilter.class);
		//세션 설정
		http
			.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login.loginPage("/all/login").loginProcessingUrl("/all/loginProc") // 로그인 submit url 설정
				.usernameParameter("loginId") // 파라미터 name 설정
				.passwordParameter("pw")
				.successHandler(successHandler) // 권한별 로그인 성공시 이동
		).logout(logout -> logout.logoutUrl("/all/logoutProc").logoutSuccessUrl("/all/login"));

		// 세션설정
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

		// 자동로그인
		http.rememberMe(remember -> remember.key("securityRemember~~~~").rememberMeParameter("remember-me")
				.tokenValiditySeconds(60 * 60 * 24 * 14)
		// .authenticationSuccessHandler(new CustomAuthenticationSuccessHandler())
		);

		// 경로별 인가 작업
		http.authorizeHttpRequests((auth) -> auth.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				// 정적 리소스 허용
				.requestMatchers("/css/**", "/img/**", "/js/**", "/lib/**", "/scss/**", "/dashmin-1.0.0/**", "/images/**").permitAll()
				.requestMatchers("/all/**").permitAll()
				.requestMatchers("/member/**").hasAnyAuthority("ROLE_1", "ROLE_2")
				.requestMatchers("/room/**", "/support/**").hasAnyAuthority("ROLE_1", "ROLE_0")
				.anyRequest().authenticated());
		return http.build();
	}
}