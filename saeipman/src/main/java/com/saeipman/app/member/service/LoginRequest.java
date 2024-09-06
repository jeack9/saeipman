package com.saeipman.app.member.service;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "아이디: 필수입니다.")
	private String loginId;
	@NotBlank(message = "비밀번호: 필수입니다.")
	private String pw;
}
