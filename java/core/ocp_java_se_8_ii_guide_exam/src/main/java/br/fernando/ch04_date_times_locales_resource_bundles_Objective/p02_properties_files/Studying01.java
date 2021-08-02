package br.fernando.ch04_date_times_locales_resource_bundles_Objective.p02_properties_files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Studying01 {

    // =========================================================================================================================================
    // Properties Files
    // Property files are typically used to externally store configuration settings and operating parameters for your applications.
    static void studying01() {
        // ! comment
        // # coment

        // Property files can define key/value pairs in any of the following formats:
        // key=value
        // key:value
        // key value

        // As we mentioned earlier, java.lang.System provides access to a property file.
        // Although this file isn’t on the exam, the following code
        Properties p = System.getProperties(); // Open system properties file
        p.setProperty("myProp", "myValue"); // add an entry
        p.list(System.out); // list the file's contents
    }

    static void studying02() {
        Properties p01 = new Properties();
        p01.setProperty("k1", "v1");
        p01.setProperty("k2", "v2");
        p01.list(System.out);

        // creates or replace file
        try (FileOutputStream out = new FileOutputStream("test01.properties")) {

            p01.store(out, "test-comment");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Properties p02 = new Properties();

        try (FileInputStream in = new FileInputStream("test01.properties"); //
                FileOutputStream out = new FileOutputStream("test01.properties")) {

            p02.load(in);
            p02.list(System.out);

            p02.store(out, "myUpdate");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // =========================================================================================================================================
    public static void main(String[] args) {
        studying01();
    }
}
