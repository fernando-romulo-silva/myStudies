package org.nandao.ch07TheNashornJavaScriptEngine.part02RunningNashornFromJava;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.application.Application;
import javafx.stage.Stage;

// Running a Nashorn script from Java uses the script engine mechanism that has
// been introduced in Java 6. You can use that mechanism to execute scripts in any
// JVM language with a script engine, such as Groovy, JRuby, or Jython. There are
// also script engines for languages that run outside the JVM, such as PHP or Scheme.

public class Test extends Application {

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    // To run a script, you need to get a ScriptEngine object. If the engine is registered,
    // you can simply get it by name. Java 8 includes an engine with name "nashorn".
    public static void test1() throws ScriptException {

        final Object result = engine.eval("'Hello, World!'.length");
        System.out.println(result);
    }

    // You can also read a script from a Reader:
    public static void test2() throws ScriptException, IOException {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part02RunningNashornFromJava1.js");

        final Object result = engine.eval(Files.newBufferedReader(path));
        System.out.println(result);
    }
    
    // To make a Java object available to your scripts, use the put method of the
    // ScriptEngine interface. For example, you can make a JavaFX stage visible, so that
    // you can populate it using JavaScript code: 
    @Override
    public void start(Stage stage) {
        
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());
        
        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part02RunningNashornFromJava2.js");
        
        final Bindings scope = engine.createBindings();
        scope.put("stage", stage);
        try {
           engine.eval(Files.newBufferedReader(path), scope);
           // Script code can access the object as stage
        } catch (IOException | ScriptException ex) {
           ex.printStackTrace();
        }
     }

    public static void main(final String[] args) throws Exception {
        // test2();
        launch(args);
    }
}
