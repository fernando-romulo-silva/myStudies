package org.nandao.ch07TheNashornJavaScriptEngine.part05Strings;

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

// Strings in Nashorn are, of course, JavaScript objects. For example, consider the call
// 'Hello'.slice(-2) // Yields 'lo'
// Here, we call the JavaScript method slice. There is no such method in Java.
// But the call
// 'Hello'.compareTo('World')
// also works, even though in JavaScript there is no compareTo method. (You just use the < operator.)
// In this case, the JavaScript string is converted to a Java string. In general, a
// JavaScript string is converted to a Java string whenever it is passed to a Java method.

public class Test {

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    public static void main(String[] args) {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part05Strings1.js");

        final Bindings scope = engine.createBindings();

        try {

            final Object result = engine.eval(Files.newBufferedReader(path), scope);
            System.out.println(result);

        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
        }

    }
}
