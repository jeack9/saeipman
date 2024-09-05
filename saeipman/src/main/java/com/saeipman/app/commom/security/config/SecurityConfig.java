package com.saeipman.app.commom.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	@Bean // 비밀번호 암호화
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 접근권한 설정
        http
        	.csrf(csrf -> csrf.disable())
        	.cors(cors -> cors.disable())
            .authorizeHttpRequests(request -> request // http 요청에 대한 인가
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                //정적 리소스 허용
                .requestMatchers("/css/**", "/img/**", "/js/**", "/lib/**", "/scss/**", "/dashmin-1.0.0/**").permitAll()
                .requestMatchers("/member/join").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
	            .loginPage("/member/login")
	            .loginProcessingUrl("/loginProcess") // 로그인 submit url 설정
	            .usernameParameter("loginId") // 파라미터 name 설정
	            .passwordParameter("pw")
	            //.successHandler(null)
	            .defaultSuccessUrl("/test", true)
	            .permitAll() // 로그인 성공시 이동하는 페이지 허용
            )
            .logout(logout -> logout
        		.logoutUrl("logoutProcess")
            	.logoutSuccessUrl("/member/login")
        	);
		return http.build();
	}
}
