package org.nandao.ch07TheNashornJavaScriptEngine.part09Lambdas;

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

// JavaScript has anonymous functions, such as
// var square = function(x) { return x * x }
//// The right-hand side is an anonymous function
// var result = square(2)
//// The () operator invokes the function
// Syntactically, such an anonymous function is very similar to a Java lambda expression.
// Instead of an arrow after the parameter list, you have the keyword function.
// You can use an anonymous function as a functional interface argument of a Java
// method, just like you could use a lambda expression in Java

public class Test {

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    public static void main(String[] args) {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part09Lambdas1.js");

        final Bindings scope = engine.createBindings();

        try {

            final Object result = engine.eval(Files.newBufferedReader(path), scope);
            System.out.println(result);

        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
        }
    }
    
}
