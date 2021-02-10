package br.com.fernando.enthuware.managePersistenceJpaAndBeanValidation;

public class Question08 {

    // Given the persistence.xml fragment:
    /**
     * <pre>
     *     <persistence-unit name="OrderPU" transaction-type="RESOURCE_LOCAL">
     *     <!-- remaining xml -->
     *     </persistence-unit>
     * </pre>
     */
    // What does this configuration signify about transactions management in the application?
    //
    // A - The transactions must be managed by the application server for local EJBs or CDI beans.
    //
    // B - The transactions must be managed by EJB Lite container.
    //
    // C - The transactions must be managed only for the local (embedded) database.
    //
    // D - The transactions must be managed programmatically.
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
    // The correct answer is D
    //
    // A transaction-type of RESOURCE_LOCAL assumes that a non-JTA datasource will be provided.
    // This means, the container won't be managing transactions for this resource and that you, the programmer, has to manage transactions for it programmatically.
    //
    // This is done using EntityManagerFactory to get an EntityManager and then using EntityTransaction API to begin/commit around every call to your EntityManger.

}
