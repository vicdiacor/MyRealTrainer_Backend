package com.MyRealTrainer.SecurityConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory
	implements WithSecurityContextFactory<WithMockCustomUser> {
	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		String principal = customUser.username();
		Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password", mapRolesToAuthorities(customUser.roles()));
		context.setAuthentication(auth);
		return context;
	}
    
    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(String[] nameRoles){
        List<GrantedAuthority> authList= new ArrayList<GrantedAuthority>();

            
        for (String nameRol: nameRoles) {           
            GrantedAuthority auth=new SimpleGrantedAuthority(nameRol);
            authList.add(auth);
        
        }
        
        return authList;
    }

   
}