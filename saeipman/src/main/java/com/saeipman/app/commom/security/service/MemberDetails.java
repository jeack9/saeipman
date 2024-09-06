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
 * import lombok.AllArgsConstructor; import lombok.Getter;
 * 
 * @AllArgsConstructor
 * 
 * @Getter public class MemberDetails implements UserDetails{ private
 * LoginInfoVO member;
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
 * @Override public boolean isAccountNonExpired() { // TODO Auto-generated
 * method stub return true; }
 * 
 * @Override public boolean isAccountNonLocked() { // TODO Auto-generated method
 * stub return true; }
 * 
 * @Override public boolean isCredentialsNonExpired() { // TODO Auto-generated
 * method stub return true; }
 * 
 * @Override public boolean isEnabled() { // TODO Auto-generated method stub
 * return true; }
 * 
 * }
 */