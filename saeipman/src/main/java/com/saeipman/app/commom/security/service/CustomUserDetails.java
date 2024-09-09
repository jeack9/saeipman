/*
 * package com.saeipman.app.commom.security.service;
 * 
 * import java.util.ArrayList; import java.util.Collection; import
 * java.util.List;
 * 
 * import org.springframework.security.core.GrantedAuthority; import
 * org.springframework.security.core.authority.SimpleGrantedAuthority; import
 * org.springframework.security.core.userdetails.UserDetails;
 * 
 * import com.saeipman.app.member.service.LoginInfoVO;
 * 
 * import lombok.RequiredArgsConstructor;
 * 
 * @RequiredArgsConstructor public class CustomUserDetails implements
 * UserDetails { private final LoginInfoVO member;
 * 
 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
 * List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
 * auths.add(new SimpleGrantedAuthority("ROLE_" + member.getAuth())); return
 * auths; }
 * 
 * @Override public String getPassword() { return member.getPw(); }
 * 
 * @Override public String getUsername() { return member.getLoginId(); }
 * 
 * @Override public boolean isAccountNonExpired() { return true; }
 * 
 * @Override public boolean isAccountNonLocked() { return true; }
 * 
 * @Override public boolean isCredentialsNonExpired() { return true; }
 * 
 * @Override public boolean isEnabled() { return true; }
 * 
 * }
 */