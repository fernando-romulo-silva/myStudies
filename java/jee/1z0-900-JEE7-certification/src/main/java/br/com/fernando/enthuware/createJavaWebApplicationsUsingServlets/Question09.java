package br.com.fernando.enthuware.createJavaWebApplicationsUsingServlets;

public class Question09 {

    // You created two filters for your web application by using the @WebFilter annotation,
    // one for authorization and the other for narrowing results by the provided search criteria.
    // The authorization filter must be invoked first.
    //
    // How can you specify this?
    //
    // A - setting the priority attribute of the @WebFilter annotation for each of the filters
    //
    // B - placing the filter mapping elements in the required order in the web.xml deployment descriptor
    //
    // C - placing @WebFilterMapping annotations in the required order
    //
    // D - specifying the filter precedence order by using the @Priority annotation
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
    // The correct answer is B
    //
    // @WebFilter has no element to define the order of filter of execution. 
    //
    // If there are filters matched by servlet name and the Web resource has a <servlet-name>, 
    // the container builds the chain of filters matching in the order declared in the deployment descriptor.
    //
    // The last filter in this chain corresponds to the last <servlet-name> matching filter and is the filter that invokes the target Web resource.
    // 
    // If there are filters using <url-pattern> matching and the <url-pattern> matches the request URI according to the rules of Section 12.2, 
    // "Specification of Mappings", the container builds the chain of <url-pattern> matched filters in the same order as declared in the deployment descriptor. 
    //
    // The last filter in this chain is the last <url-pattern> matching filter in the deployment descriptor for this request URI. 
    // The last filter in this chain is the filter that invokes the first filter in the <servlet-name> matching chain, or invokes the target Web resource if there are none.

}
