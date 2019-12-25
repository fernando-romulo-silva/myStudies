package org.nandao.cap09.p05RedirectingInputAndOutputFromOperatingSystemProcesses;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// The java.lang.ProcessBuilder class has several new methods that are useful for
// redirecting the input and output of external processes executed from a Java application. The
// nested ProcessBuilder.Redirect class has been introduced to provide these additional
// redirect capabilities. To demonstrate this process, we are going to send command-line
// arguments from a text file to a DOS prompt and record the output in another text file.
public class Test {

    public static void main(String[] args) throws IOException {
        
        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path pathSource = Paths.get(new File(classLoader.getResource("Home").getFile()).getAbsolutePath());

        final File commands = new File(pathSource + "/ProcessCommands.txt");
        final File output = new File(pathSource + "/ProcessLog.txt");
        final File errors = new File(pathSource + "/ErrorLog.txt");

        final ProcessBuilder pb = new ProcessBuilder("cmd");
        System.out.println(pb.redirectInput());
        System.out.println(pb.redirectOutput());
        System.out.println(pb.redirectError());
        
        pb.redirectInput(commands);
        pb.redirectError(errors);
        pb.redirectOutput(output);
        
        System.out.println(pb.redirectInput());
        System.out.println(pb.redirectOutput());
        System.out.println(pb.redirectError());
        
        pb.start();
    }
}
