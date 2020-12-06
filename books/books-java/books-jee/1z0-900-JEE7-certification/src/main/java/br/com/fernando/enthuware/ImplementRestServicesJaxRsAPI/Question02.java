package br.com.fernando.enthuware.ImplementRestServicesJaxRsAPI;

public class Question02 {

    // Which URL represents an invalid approach to pass a parameter to a REST resource?
    //
    // Choice A 	
    // <base-url>/Employees?value=1
    //
    // Choice B 	
    // <base-url>/Employees&value=1
    //
    // Choice C 	
    // <base-url>/Employees/1
    //
    // Choice D 	
    // <base-url>/Employees;value=1
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // Choice B is correct.
    //
    // A is wrong because QUERY parameters. 
    // Query parameters appear in the URL after the question mark (?) after the resource name:
    // https://myserver.com/resource-name?param1=value1&param2=value2
    // 
    // C is wrong because TEMPLATE Parameters
    // These parameters appear in resource paths. 
    // They give API developers a flexible way of parameterizing resources:
    // http://myserver.com/some-path/parameter/path-continued/parameter2
    //
    // D is wrong because of MATRIX Parameters
    // These parameters also go in the request URL. 
    // They reside between the resource path and QUERY parameters, and are separated from the resource path by a semicolon (;).
}
