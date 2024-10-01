package com.saeipman.app.commom.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 사용자 권한 가져오기
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // 권한에 따른 페이지 이동
        if (roles.contains("ROLE_1")) {
            response.sendRedirect(request.getContextPath() + "/member/home");
        } else if (roles.contains("ROLE_2")) {
            response.sendRedirect(request.getContextPath() + "/paymentInfo");
        }
    }
}
