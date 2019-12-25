package org.nandao.ch07TheNashornJavaScriptEngine.part03InvokingMethods;

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

// Nashorn supports a convenient property syntax for getters and setters. If the
// expression stage.title occurs to the left of the = operator, it is translated to an
// invocation of the setTitle method. Otherwise it turns into a call stage.getTitle().
// You can even use the JavaScript bracket notation to access properties:
//
// stage['title'] = 'Hello'
//
// Note that the argument of the [] operator is a string. In this context, that isn’t
// useful, but you can call stage[str] with a string variable and thereby access
// arbitrary properties.

public class Test extends Application {
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    @Override
    public void start(final Stage stage) {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + "/part03InvokingMethods1.js");

        final Bindings scope = engine.createBindings();
        scope.put("stage", stage);
        try {
            engine.eval(Files.newBufferedReader(path), scope);
            // Script code can access the object as stage
        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
