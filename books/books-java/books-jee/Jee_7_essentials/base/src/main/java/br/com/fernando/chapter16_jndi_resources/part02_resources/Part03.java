package br.com.fernando.chapter16_jndi_resources.part02_resources;

public class Part03 {

    // The external-jndi-resource specifies a resource that exists in an external JNDI repository.
    
    /**
     * <pre>
     * 
     * <external-jndi-resource 
     *          res-type="javax.naming.reference" description="" 
     *          jndi-name="ejb/documentServiceInvocation" 
     *          factory-class="com.sun.jndi.cosnaming.cnctxfactory" 
     *          jndi-lookup-name="ejb/documentServiceInvocation">
     *             
     *    <property name="java.naming.provider.url" value="corbaname::server01:9812,:server01:9813/nameserviceserverroot"></property> 
     * </external-jndi-resource>
     * 
     * </pre>
     */

    /**
     * <pre>
     * 
     * <servers>     
     *   <server name="server" config-ref="server-config">       ...       
     *     <resource-ref ref="ejb/documentServiceInvocation"></resource-ref>     
     *   </server>   
     * </servers>
     * 
     * </pre>
     */

//my web.xml:
    /**
     * <pre>
     * 
     *  <ejb-ref>    
     *     <ejb-ref-name>ejb/documentServiceInvocation</ejb-ref-name>    
     *     <ejb-ref-type>session</ejb-ref-type>    
     *     <home>mypackage.documentServiceInvocationHome</home>    
     *     <remote>mypackage.documentServiceInvocationRemote</remote> 
     *  </ejb-ref>
     * 
     * </pre>
     */

}
