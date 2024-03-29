package com.apress.springbootrecipes.library.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AccessChecker {

    public boolean hasLocalAccess(Authentication authentication) {
	boolean access = false;

	if (authentication.getDetails() instanceof WebAuthenticationDetails) {
	    
	    final var details = (WebAuthenticationDetails) authentication.getDetails();
	    
	    var address = details.getRemoteAddress();
	    
	    access = address.equals("127.0.0.1") || address.equals("0:0:0:0:0:0:0:1");
	}
	
	return access;
    }
}
