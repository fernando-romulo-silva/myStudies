package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p02_properties_files;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

// @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class Studying02 {

    // =========================================================================================================================================
    // Resource Bundle
    // Think of a ResourceBundle as a map. You can use property files or Java classes to specify the mappings.
    static void studying01() {

        // It’s critical to understand that when you use Properties files to support ResourceBundle objects,
        // the naming of the files MUST follow two rules:
        //
        // 1. These files must end in “ .properties .“
        //
        // 2. The end of the name before the .properties suffix must be a string that starts with an underscore and
        // then declares the Locale the file represents (e.g., MyApp_en.properties or MyApp_fr.properties or
        // MyApp_fr_CA.properties ). ResourceBundle only knows how to find the appropriate file via the filename.
        // There is no requirement for the data in the file to contain locale information.
        //

        Locale locale = new Locale("fr"); //
        ResourceBundle rb = ResourceBundle.getBundle("Labels", locale);

        System.out.println(rb.getString("hello"));

        // The Java API for java.util.ResourceBundle lists three good reasons to use
        // resource bundles. Using resource bundles “allows you to write programs that can :
        // * Be easily localized, or translated, into different languages.
        // * Handle multiple locales at once
        // * Be easily modified later to support even more locales
    }

    // =========================================================================================================================================
    // Java Resource Bundle
    //
    // When we need to move beyond simple property file key to string value mappings, we
    // can use resource bundles that are Java classes. We write Java classes that extend
    // ListResourceBundle . The class name is similar to the one for property files. Only the
    // extension is different.
    static class Labels_en_CA extends ListResourceBundle {

        @Override
        protected Object[][] getContents() {
            return new Object[][]{ //
                    { "hello", new StringBuilder("from Java") } //
            };
        }
    }

    static void studying02() {
        Locale locale = new Locale("en", "CA");
        ResourceBundle rb = ResourceBundle.getBundle("Labels", locale);
        System.out.println(rb.getObject("hello"));
    }

    // =========================================================================================================================================
    // Default Locale
    // Java will pick the resource bundle that matches the locale the JVM is using.
    // Typically, this matches the locale of the machine running the program, but it doesn’t have to.
    // You can even change the default locale at runtime, which might be useful if you are working with
    // people in different locales so you can get the same behavior on all machines
    static void studying03() {
        // store locale so can put it back at end
        Locale initial = Locale.getDefault();
        System.out.println(initial);

        // set locale to Germany
        Locale.setDefault(Locale.GERMANY);
        System.out.println(Locale.getDefault());

        // put original locale back
        Locale.setDefault(initial);
        System.out.println(Locale.getDefault());

        // You can locale object with builder

        new Locale.Builder().setLanguage("en").setRegion("UK").build(); // L1
    }

    // =========================================================================================================================================
    // Chossing the Right Resource Bundle
    public static void test06() {
        // There are two main ways to get a resource bundle:
        // ResourceBundle.getBundle(baseName)
        // ResourceBundle.getBundle(baseName, locale)
        //
        // Luckily, ResourceBundle.getBundle(baseName) is just shorthand for ResourceBundle.getBundle(baseName, Locale.getDefault()),
        // and you only have to remember one set of rules.

        Locale locale01 = new Locale("fr", "CA");
        ResourceBundle rb01 = ResourceBundle.getBundle("RB", locale01);
        // Java will look for the following files in the classpath in this order
        // RB_fr_CA.java // exactly what we asked for
        // RB_fr_CA.properties

        // RB_fr.java // couldn't find exactly what we asked for now trying just requested language
        // RB_fr.properties

        // RB_en_US.java // couldn't find French
        // RB_en_US.properties // now trying defualt locale

        // If none of these files exist, Java gives up and throws a MissingResourceException .
        // Although this is a lot of things for Java to try, it is pretty easy to remember. Start with
        // the full Locale requested. Then fall back to just language. Then fall back to the default
        // Locale . Then fall back to the default bundle. Then cry.

        Locale locale02 = new Locale("en", "UK");
        ResourceBundle rb02 = ResourceBundle.getBundle("RB", locale02);
        System.out.println(rb02.getString("ride.in") + " " + rb02.getString("elevator"));
        // The common "ride.in" property comes from the parent noncountry-specific bundle
        // “ RB_en.properties .” The "elevator" property is different by country and comes
        // from the UK version that we specifically requested.
        //
        // A bundle’s parent always has a shorter name than the child bundle. If a parent is missing, Java just skips
        // along that hierarchy. ListResourceBundle s and PropertyResourcesBundle s do not share a hierarchy
        //
        //
        // Resource's Bundle Name *| Hierarchy
        // ----------------------------------------------------
        // RB_fr_CA.java **********| RB.java
        // ************************| RB_fr.java
        // ************************| RB_fr_CA.java
        //
        // RB_fr_CA.properties ****| RB.properties
        // ************************| RB_fr.properties
        // ************************| RB_fr_CA.properties
        //
        // RB_en_US.java **********| RB.java
        // ************************| RB_en.java
        // ************************| RB_en_US.java
        //
        // RB_en_US.properties ****| RB.properties
        // ************************| RB_en.properties
        // ************************| RB_en_US.properties

        // Remember that searching for a property file uses a linear list. However, once a
        // matching resource bundle is found, keys can only come from that resource bundle’s hierarchy.
        //
        // Think about which resource bundles will be used from the previous code if we use the following
        // code to request a resource bundle:
        Locale locale03 = new Locale("fr", "FR");
        ResourceBundle rb03 = ResourceBundle.getBundle("RB", locale03);

        // First, Java looks for RB_fr_FR.java and RB_fr_FR.properties . Because neither is
        // found, Java falls back to using RB_fr.java . Then as we request keys from rb , Java
        // starts looking in RB_fr.java and additionally looks in RB.java . Java started out
        // looking for a matching file and then switched to searching the hierarchy of that file.
    }

    // =========================================================================================================================================
    public static void main(String[] args) throws Exception {
        studying01();
    }
}
