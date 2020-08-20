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
    // Correct Answers is A and B
    //
    // A - This option is a bit vague because, actually, since almost of the configuration can be done using annotations, web.xml is optional.
    // However, if it exists, it must existing in the WEB-INF folder.
    //
    // B - war is just the name of the extention. The file format for war as well as ear files is same as a jar file. Both are created using the jar tool.
    //
    // C - JCA Resource adapters existing in JEE application container but are not managed by it. The are definitely not a part of a war.
    //
    // D - EJB components reside in a jar file. Web components (servlets/jsp/filters/html/etc) reside in a war file.
    // Together, the jar and war files are they are packaged in an ear file.
}
