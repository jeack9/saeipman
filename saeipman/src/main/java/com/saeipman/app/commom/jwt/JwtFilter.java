package com.saeipman.app.commom.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.saeipman.app.commom.security.service.CustomUserDetails;
import com.saeipman.app.member.service.LoginInfoVO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// request에서 Authorization 헤더를 찾음
		String authorization = request.getHeader("Authorization");
		// Authorization 헤더 검증
		if (authorization == null || !authorization.startsWith("Bearer ")) {

			System.out.println("token null, JwtFilter");
			filterChain.doFilter(request, response);

			// 조건이 해당되면 메소드 종료 (필수)
			return;
		}

		// Bearer 부분 제거 후 순수 토큰만 획득
		String token = authorization.split(" ")[1];
		// 토큰 소멸 시간 검증
		if (jwtUtil.isExpired(token)) {
			System.out.println("token expired, JwtFilter");
			filterChain.doFilter(request, response);
			// 조건이 해당되면 메소드 종료 (필수)
			return;
		}
		
		//토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        int role = Integer.parseInt(jwtUtil.getRole(token).split("_")[1]);
				
		//Login 정보를 생성하여 값 set
        LoginInfoVO loginVO = new LoginInfoVO();
        loginVO.setLoginId(username);
        loginVO.setPw("testJWT");
        loginVO.setAuth(role);
				
		//UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(loginVO);

		//스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
	}
}
