package br.com.fernando.enthuware.understandJavaEEArchitecture;

public class Question01 {

    // Which of the following are true about .war files?
    // You had to select 2 option(s)
    //
    //
    // A - You must place a web.xml descriptor file into the WEB-INF folder inside the .war file.
    //
    // B - You use the jar command to package a .war file.
    //
    // C - You can place JCA components into a .war file.
    //
    // D - You can place EJB components into a .war file.
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
    //
    //
    //
    // Correct Answers is B and D
    //
    // B - war is just the name of the extention. The file format for war as well as ear files is same as a jar file. Both are created using the jar tool.
    //
    // D - Enterprise beans may be packaged within a WAR module as Java programming language class files or within a JAR file that is bundled within the WAR module.
    // To include enterprise bean class files in a WAR module, the class files should be in the WEB-INF/classes directory.
    // To include a JAR file that contains enterprise beans in a WAR module, add the JAR to the WEB-INF/lib directory of the WAR module.
    // WAR modules that contain enterprise beans do not require an ejb-jar.xml deployment descriptor. If the application uses ejb-jar.xml, it must be located in the WAR module’s WEB-INF directory.
    //
    //
    //
    // A - This option is a bit vague because, actually, since almost of the configuration can be done using annotations, web.xml is optional.
    // However, if it exists, it must existing in the WEB-INF folder.
    //
    // C - JCA Resource adapters existing in JEE application container but are not managed by it. The are definitely not a part of a war.
}
