package com.apress.cems.beans.config.scopes3;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(//
		value = "session", // WebApplicationContext.SCOPE_SESSION
		proxyMode = ScopedProxyMode.INTERFACES //
) //
public class UserSettings {

    public UserSettings() {
	// TODO Auto-generated constructor stub
    }

}
