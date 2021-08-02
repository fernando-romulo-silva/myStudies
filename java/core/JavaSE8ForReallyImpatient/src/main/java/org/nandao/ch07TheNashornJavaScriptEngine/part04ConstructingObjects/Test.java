package org.nandao.ch07TheNashornJavaScriptEngine.part04ConstructingObjects;

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

// When you want to construct objects in JavaScript (as opposed to having them
// handed to you from the script engine), you need to know how to access Java
// packages. There are two mechanisms.
// There are global objects java, javax, javafx, com, org, and edu that yield package and
// class objects via the dot notation. See example in part04ConstructingObjects1.js file.

public class Test {
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    public static void main(String[] args) {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part04ConstructingObjects1.js");

        final Bindings scope = engine.createBindings();
        
        
        try {
            
            final Object result = engine.eval(Files.newBufferedReader(path), scope);
            System.out.println(result);
            
        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
        }

    }
}
