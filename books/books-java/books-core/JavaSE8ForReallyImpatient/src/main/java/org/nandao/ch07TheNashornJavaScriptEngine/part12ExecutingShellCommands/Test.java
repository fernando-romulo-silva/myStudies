package org.nandao.ch07TheNashornJavaScriptEngine.part12ExecutingShellCommands;

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

// If you need to automate a repetitive task on your computer, chances are that you
// have put the commands in a shell script, a script that replays a set of OS-level
// commands. I have a directory ~/bin filled with dozens of shell scripts: to upload
// files to my web site, my blog, my photo storage, and to my publisher’s FTP site; 
// to convert images to blog size; to bulk-email my students; 
// to back up my computer at two o’clock in the morning.

public class Test {
    
    private static ScriptEngineManager manager = new ScriptEngineManager();
    private static ScriptEngine engine = manager.getEngineByName("nashorn");

    // Executing Shell Commands
    public static String test1() {
        return "/part12ExecutingShellCommands1.js";
    }

    // String Interpolation
    public static String test2() {
        return "/part12ExecutingShellCommands2.js";
    }
    
    // Script Inputs
    public static String test3() {
        return "/part12ExecutingShellCommands3.js";
    }
    
    public static void main(String[] args) {
        
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathScripts = Paths.get(new File(classLoader.getResource("scripts").getFile()).getAbsolutePath());

        final Path path = FileSystems.getDefault().getPath(pathScripts + test1());

        final Bindings scope = engine.createBindings();

        try {

            final Object result = engine.eval(Files.newBufferedReader(path), scope);
            System.out.println(result);

        } catch (IOException | ScriptException ex) {
            ex.printStackTrace();
        }
    }
}
