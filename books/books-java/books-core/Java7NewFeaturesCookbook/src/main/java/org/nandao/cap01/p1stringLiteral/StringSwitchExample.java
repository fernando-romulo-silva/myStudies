package org.nandao.cap01.p1stringLiteral;

public class StringSwitchExample {

    private static boolean verbose = false;

    private static boolean logging = false;

    private static boolean displayHelp = false;

    // The general rules regarding switch statements apply when using string literals. Each statement within the switch block must have a valid non-null
    // label, no two labels may be identical, and only one default label may be associated with each switch block.

    public static void main(final String[] args) {

        for (final String argument : args) {
            switch (argument) {
            case "-verbose":
            case "-v":
                verbose = true;
                break;
            case "-log":
                logging = true;
                break;
            case "-help":
                displayHelp = true;
                break;
            default:
                System.out.println("Illegal command lineargument");
            }
        }
        displayApplicationSettings();
    }

    private static void displayApplicationSettings() {
        System.out.println("Application Settings");
        System.out.println("Verbose: " + verbose);
        System.out.println("Logging: " + logging);
        System.out.println("Help: " + displayHelp);
    }
}
