package com.apress.prospring5.ch3;

//The instantiation of the abstract class is supported only when using Lookup Method Injection, 
//in which Spring will use CGLIB to generate a subclass of the AbstractLookupDemoBean class that overrides the method dynamically. 
//The first part of the displayInfo() method creates two local variables of Singer type and assigns them each a value by calling 
//getMySinger() on the bean passed to it. Using these two variables, it writes a message to the console indicating whether the 
//two references point to the same object.
public abstract class AbstractLookupDemoBean implements DemoBean {
    public abstract Singer getMySinger();

    @Override
    public void doSomething() {
        getMySinger().sing();
    }
}
