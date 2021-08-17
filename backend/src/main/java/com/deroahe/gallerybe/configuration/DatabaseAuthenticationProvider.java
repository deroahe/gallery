//package com.deroahe.gallerybe.configuration;
//
//import com.deroahe.gallerybe.service.impl.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class DatabaseAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
////        Userr userr = userService.findByUsernameAndPassword(username, password);
////
////        if (userr != null) {
////            List<GrantedAuthority> authorities = new ArrayList<>();
////            authorities.add(new SimpleGrantedAuthority(userr.getRole()));
////            return new UsernamePasswordAuthenticationToken(username, password, authorities);
////        }
//        throw new BadCredentialsException("Failed to authenticate");
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
