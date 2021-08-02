package org.nandao.ch07TheNashornJavaScriptEngine.part11Exceptions;

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

// When a Java method throws an exception, you can catch it in JavaScript in the usual way:
//
// try {
// var first = list.get(0)
// ...
// } catch (e) {
// if (e instanceof java.lang.IndexOutOfBoundsException)
// print('list is empty')
// }
//
// Note that there is only one catch clause, unlike in Java where you can catch expressions
// by type. That, too, is in the spirit of dynamic languages where all type inquiry happens at runtime.
public class Test {

    
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    public static void main(String[] args) {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part11Exceptions1.js");

        final Bindings scope = engine.createBindings();

        try {

            final Object result = engine.eval(Files.newBufferedReader(path), scope);
            System.out.println(result);

        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
        }
    }
    
}
