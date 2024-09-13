package com.saeipman.app.commom.security.service.impl;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saeipman.app.commom.security.service.CustomUserDetails;
import com.saeipman.app.member.service.LoginInfoVO;
import com.saeipman.app.member.service.MemberService;
import com.saeipman.app.room.service.RoomService;
import com.saeipman.app.room.service.RoomVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberService loginService;
	private final RoomService roomService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginInfoVO loginVO = new LoginInfoVO();
		loginVO.setLoginId(username);
		LoginInfoVO findVO = loginService.loginInfo(loginVO);
		if (findVO == null) {
			throw new UsernameNotFoundException("없는 아이디");
		}else if(findVO.getAuth() == 2) {
			// 임차인인 경우 현재 계약한 방의 아이디와 건물의 아이디 같이 저장
			RoomVO imchainRoom = roomService.imchainRoomInfo(username);
			findVO.setBuildingId(imchainRoom.getBuildingId());
			findVO.setRoomId(imchainRoom.getRoomId());
		}
		return new CustomUserDetails(findVO);
	}

}