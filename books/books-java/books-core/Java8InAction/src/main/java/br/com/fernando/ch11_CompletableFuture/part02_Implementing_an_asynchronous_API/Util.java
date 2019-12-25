package br.com.fernando.ch11_CompletableFuture.part02_Implementing_an_asynchronous_API;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class Util {

    public static final DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

    public static void delay() {
	final int delay = 2000;
	try {
	    Thread.sleep(delay);
	} catch (final InterruptedException e) {
	    throw new RuntimeException(e);
	}
    }

    public static void delay(int value) {
	try {
	    Thread.sleep(value);
	} catch (final InterruptedException e) {
	    throw new RuntimeException(e);
	}
    }

    public static double format(double number) {
	synchronized (formatter) {
	    return new Double(formatter.format(number));
	}
    }
}
