package br.com.fernando.chapter18_security;

import javax.annotation.security.RunAs;

public class Test03 {

    // Security Identity Propagation
    //
    // 1* Initiating Client ( Application Client, Web Client)
    // (Java EE security Identity)
    //
    // 2* EJB or Web Container (Intermediate)
    // (propagateed Security Identity - Java EE)
    //
    // 3 EJB Container (target)

    @RunAs("Admin")
    public class Calculator {
	// ....
    }

}
