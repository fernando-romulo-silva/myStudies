package org.nandao.ch07TheNashornJavaScriptEngine.part06Numbers;

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

// JavaScript has no explicit support for integers. Its Number type is the same as
// the Java double type. When a number is passed to Java code that expects an int or
// long, any fractional part is silently removed. For example, 'Hello'.slice(-2.99) is
// the same as 'Hello'.slice(-2).
// For efficiency, Nashorn keeps computations as integers when possible, but that
// difference is generally transparent.

public class Test {

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    public static void main(String[] args) {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part06Numbers1.js");

        final Bindings scope = engine.createBindings();

        try {

            final Object result = engine.eval(Files.newBufferedReader(path), scope);
            System.out.println(result);

        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
        }
    }

}
