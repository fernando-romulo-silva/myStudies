package com.apress.cems.beans.config.scopes3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThemeManager {
 
    private UserSettings userSettings;

    @Autowired
    public void setUserSettings(UserSettings userSettings) {
	this.userSettings = userSettings;
    }
}