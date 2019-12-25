package br.com.fernando.ch03_lambda_expressions.part03_Putting_lambdas_into_practice_the_execute_around_pattern;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Putting lambdas into practice: the execute around pattern
public class Test {

    // Remember behavior parameterization
    public static void test1() throws IOException {
        // Passing behavior is exactly what lambdas are for. So what should the new processFile method
        // look like if you wanted to read two lines at once? You basically need a lambda that takes a
        // BufferedReader and returns a String. For example, here’s how to print two lines of a
        // BufferedReader:

        final String result = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        
        System.out.println(result);
    }

    public static String processFileLimited() throws IOException {

        try (final BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {

            return br.readLine();
        }
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {

        try (final BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {

            return p.process(br);
        }

    }

    @FunctionalInterface
    public interface BufferedReaderProcessor {

        public String process(BufferedReader b) throws IOException;

    }

    public static void main(String[] args) {

    }
}
