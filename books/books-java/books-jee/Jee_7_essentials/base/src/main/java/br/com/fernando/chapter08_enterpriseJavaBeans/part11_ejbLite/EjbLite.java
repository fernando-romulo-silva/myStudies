package br.com.fernando.chapter08_enterpriseJavaBeans.part11_ejbLite;

public class EjbLite {

    // The full set of EJB functionality may not be required for all enterprise applications.
    //
    // As explained earlier, the Web Profile offers a reasonably complete stack composed of standard APIs,
    // and is capable out-of-the-box of addressing a wide variety of web applications.
    //
    // The applications targeted toward web profiles will want to use transactions, security,
    // and other functionality defined in the EJB specification. EJB Lite was created to meet that need.
    //
    // Session beans ✔ ✔
    // Message-driven beans ✘ ✔
    // Java Persistence 2.0 ✔ ✔
    // Local business interface/No-interface ✔ ✔
    // 3.x Remote ✘ ✔
    // 2.x Remote/Home component ✘ ✔
    // 2.x Local/Home component ✘ ✔
    // JAX-WS web service endpoint ✘ ✔
    // Nonpersistent EJB Timer Service ✔ ✔
    // Persistent EJB Timer Service ✘ ✔
    // Local asynchronous session bean invocations ✔ ✔
    // Remote asynchronous session bean invocations ✘ ✔
    // Interceptors ✔ ✔
    // RMI-IIOP interoperability ✘ ✔
    // Container-managed/bean-managed transactions ✔ ✔
    // Declarative and programmatic security ✔ ✔
    // Embeddable API ✔ ✔

}
