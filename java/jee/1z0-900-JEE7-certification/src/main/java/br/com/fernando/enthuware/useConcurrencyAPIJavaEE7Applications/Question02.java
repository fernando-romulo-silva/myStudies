package br.com.fernando.enthuware.useConcurrencyAPIJavaEE7Applications;

public class Question02 {

    // How can you configure, in a portable way, the number of threads used by a ManagedExecutorService?
    // You have to select 1 option.
    //
    // A
    // programmatically, using a ManagedExecutorService.
    //
    // B
    // using the ManagedExecutors utility class to define the execution properties.
    //
    // C
    // This cannot be done because this configuration is dependent on the Java EE Product Provider.
    //
    // D
    // configuring the thread pool info in the deployment descriptor.
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
    // The answer correct is D
    //
    // The maximum thread pool size can be determined when the ManagedExcecutorService is created in the Application Server.
    //
    // On Glassfish:
    // $ asadmin create-managed-executor-service concurrent/myExecutor --maximumpoolsize=20
}
