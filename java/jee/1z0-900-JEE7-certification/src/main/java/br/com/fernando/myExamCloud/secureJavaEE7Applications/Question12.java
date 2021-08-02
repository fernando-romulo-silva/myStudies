package br.com.fernando.myExamCloud.secureJavaEE7Applications;

public class Question12 {
    //
    // EPractize Labs Skill Evaluation Lab uses a session bean which acts as a session facade for an application.
    // This means that clients will only see this session bean's interface which offers the application interface.
    // There are three distinct roles known at development time: "admin", "examinee", and "trainee".
    // The majority of the methods will be used by role "admin".
    // All methods must have role permissions active and roles may be added or changed in the future.
    //
    // Which two scenarios are correct? [ Choose two ] 
    //
    // Choice A
    // The developer annotates the bean class with @PermitAll and annotates the methods used by role "trainee" or "examinee" individually.
    //
    // Choice B
    // The developer annotates the bean class with DenyAll and annotates the methods used by role "admin", "examinee", or "trainee" individually.
    //
    // Choice C
    // The developer defines individual method permissions for the methods used by roles "admin", "examinee", and "trainee" in the deployment descriptor.
    //
    // Choice D
    // The developer annotates the bean class with @RolesAllowed("admin") and annotates the methods used by role "examinee" or "trainee" individually.
    //
    // Choice E
    // The developer defines a method permission with method name and role "admin" and adds individual method permissions for the methods used by roles "examinee" and "trainee" in the deployment descriptor.
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
    // Choice C and D are correct answers.
    //
    // Requirement given:  There are three distinct roles known at development time: "admin", "examinee", and "trainee". 
    // The majority of the methods will be used by role "admin". All methods must have role permissions active and roles may be added or changed in the future.
    //
    // Hence the C and D statements are valid.    
}
