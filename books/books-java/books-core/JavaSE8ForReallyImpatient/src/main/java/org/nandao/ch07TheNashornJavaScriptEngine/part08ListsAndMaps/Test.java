package org.nandao.ch07TheNashornJavaScriptEngine.part08ListsAndMaps;

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

// Nashorn provides “syntactic sugar” for Java lists and maps. You can use the
// bracket operator with any Java List to invoke the get and set methods
public class Test {

    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    public static void main(String[] args) {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part08ListsAndMaps1.js");

        final Bindings scope = engine.createBindings();

        try {

            final Object result = engine.eval(Files.newBufferedReader(path), scope);
            System.out.println(result);

        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
        }
    }
}
