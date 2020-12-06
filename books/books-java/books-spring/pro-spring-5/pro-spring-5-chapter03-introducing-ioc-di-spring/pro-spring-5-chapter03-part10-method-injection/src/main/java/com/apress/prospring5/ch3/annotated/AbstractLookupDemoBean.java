package com.apress.prospring5.ch3.annotated;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

// The AbstractLookupDemoBean class is no longer an abstract class, and the method getMySinger() has an empty body and is annotated 
// with @Lookup that receives as an argument the name of the Singer bean. 
// The method body will be overridden, in the dynamically generated subclass.
@Component("abstractLookupBean")
public class AbstractLookupDemoBean implements DemoBean {

    @Lookup("singer")
    public Singer getMySinger() {
	return null; // This implementation will be overridden by dynamically generated subclass
    }

    @Override
    public void doSomething() {
	getMySinger().sing();
    }
}