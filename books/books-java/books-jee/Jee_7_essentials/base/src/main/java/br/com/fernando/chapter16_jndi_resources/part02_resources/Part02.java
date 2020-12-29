package br.com.fernando.chapter16_jndi_resources.part02_resources;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Part02 {

    // Naming Context
    //
    // java:comp/env is the node in the JNDI tree where you can find properties for the current Java EE component (a webapp, or an EJB).
    //
    // At the root context of the namespace is a binding with the name "comp", which is bound to a subtree reserved for component-related bindings. 
    // The name "comp" is short for component.
    //
    // There are no other bindings at the root context.
    // However, the root context is reserved for the future expansion of the policy, specifically for naming resources that are tied not
    // to the component itself but to other types of entities such as users or departments.
    //
    // For example, future policies might allow you to name users and organizations/departments by using names such
    // as "java:user/alice" and "java:org/engineering".
    //
    // In the "comp" context, there are two bindings: "env" and "UserTransaction".
    // The name "env" is bound to a subtree that is reserved for the component's environment-related bindings, as defined by its deployment descriptor.
    //
    // The "env" is short for environment. 
    // The JEE recommends (but does not require) the following structure for the "env" namespace.
    //
    // For example, if your configuration is (Spring framework):
    //
    /**
     * <pre>
     *     <bean id="someId" class="org.springframework.jndi.JndiObjectFactoryBean">
     *       <property name="jndiName" value="foo" />
     *     </bean>
     * </pre>
     */
    // Then you can access it directly using:
    public static void test01() throws Exception {
	Context ctx = new InitialContext();
	DataSource ds01 = (DataSource) ctx.lookup("java:comp/env/foo");
	// or you could make an intermediate step so you don't have to specify "java:comp/env" for every resource you retrieve:
	Context envCtx = (Context) ctx.lookup("java:comp/env");
	DataSource ds02 = (DataSource) envCtx.lookup("foo");
    }
    
    // please look at br.com.fernando.chapter08_enterpriseJavaBeans.part06_portableGlobalJndiNames.PortableGlobalJndiNames01 and PortableGlobalJndiNames01Export

    // http://itdoc.hitachi.co.jp/manuals/3020/30203Y0610e/EY060029.HTM
}
