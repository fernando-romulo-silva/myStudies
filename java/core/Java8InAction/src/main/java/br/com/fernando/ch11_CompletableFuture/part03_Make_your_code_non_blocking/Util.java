package br.com.fernando.ch11_CompletableFuture.part03_Make_your_code_non_blocking;

class Util {

    public static void delay() {
	final int delay = 2000;
	// int delay = 500 + RANDOM.nextInt(2000);
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
}
