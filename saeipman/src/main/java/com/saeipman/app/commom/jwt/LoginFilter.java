
package com.saeipman.app.commom.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saeipman.app.commom.security.service.CustomUserDetails;
import com.saeipman.app.member.service.LoginInfoVO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//시큐리티 커스텀 로그인 검증 필터

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("로그인필터 시작");
		LoginInfoVO loginDTO = new LoginInfoVO();
		try {
		    ObjectMapper objectMapper = new ObjectMapper();
		    ServletInputStream inputStream = request.getInputStream();
		    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		    loginDTO = objectMapper.readValue(messageBody, LoginInfoVO.class);

		} catch (IOException e) {
		    throw new RuntimeException(e);
		}

		System.out.println(loginDTO.getLoginId());
		
		// 클라이언트 요청에서 username, password 추출
//		String username = obtainUsername(request);
//		String password = obtainPassword(request); // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
		
		// json으로 받기
		String username = loginDTO.getLoginId();
		String password = loginDTO.getPw(); // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
				null);

		// token에 담은 검증을 위한 AuthenticationManager로 전달
		return authenticationManager.authenticate(authToken);
	}

	// 로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) {
		UserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		String username = customUserDetails.getUsername();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();

		// 어드민 권한이 아니면 토큰발급 x
		String role = auth.getAuthority();
		if(!role.equals("ROLE_0")) {
			return;
		}
		String token = jwtUtil.createJwt(username, role, 60 * 60 * 1000000L);
		response.addHeader("Authorization", "Bearer " + token);
	}

	// 로그인 실패시 실행하는 메소드

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) {
		response.setStatus(401);
	}
}
