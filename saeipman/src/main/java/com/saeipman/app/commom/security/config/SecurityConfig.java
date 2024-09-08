//package com.saeipman.app.commom.security.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.saeipman.app.commom.security.JwtAuthFilter;
//import com.saeipman.app.commom.security.JwtUtil;
//import com.saeipman.app.commom.security.service.impl.MemberDetailsService;
//
//import jakarta.servlet.DispatcherType;
//import lombok.AllArgsConstructor;
//
//@Configuration
//@EnableMethodSecurity
//@EnableWebSecurity
//@AllArgsConstructor
//public class SecurityConfig {
//	
//	private final JwtUtil jwtUtil;
//	private final MemberDetailsService memberDetailsService;
//	private final AuthenticationEntryPoint authenticationEntryPoint;
//	private final AccessDeniedHandler accessDenied;
//	@Bean // 비밀번호 암호화
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	RoleHierarchy roleHierarchy() {
//		RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//	    hierarchy.setHierarchy("""
//	    		ROLE_0 > ROLE_1
//	    		ROLE_1 > ROLE_2
//	    		""");
//	    return hierarchy;
//	}
//	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        // 접근권한 설정
//        http
//        	.csrf(csrf -> csrf.disable()) //csrf 
//        	.cors(cors -> cors.disable())
//        	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .authorizeHttpRequests(request -> request // http 요청에 대한 인가
//                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                //정적 리소스 허용
//                .requestMatchers("/css/**", "/img/**", "/js/**", "/lib/**", "/scss/**", "/dashmin-1.0.0/**").permitAll()
//                .requestMatchers("/member/join").permitAll()
//                .requestMatchers("/").hasAnyRole("")
//                .anyRequest().authenticated()
//            )
//            .formLogin(login -> login
//	            .loginPage("/member/login")
//	            .loginProcessingUrl("/loginProc") // 로그인 submit url 설정
//	            .usernameParameter("loginId") // 파라미터 name 설정
//	            .passwordParameter("pw")
//	            //.successHandler(null)
//	            .defaultSuccessUrl("/test", true)
//	            .permitAll() // 로그인 성공시 이동하는 페이지 허용
//	            .disable()
//            )
//            .logout(logout -> logout
//            		.logoutUrl("logoutProcess")
//            		.logoutSuccessUrl("/member/login")
//    		)
//            .httpBasic(AbstractHttpConfigurer::disable)
//            .addFilterBefore(new JwtAuthFilter(memberDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class)
//        	.exceptionHandling(exceptionHandling -> exceptionHandling
//        			.authenticationEntryPoint(authenticationEntryPoint)
//        			.accessDeniedHandler(accessDenied)
//			)
//        	.authorizeHttpRequests(authorize -> authorize
//        			.requestMatchers("").permitAll()
//        			.anyRequest().permitAll()
//			);
//		return http.build();
//	}
//}
//
