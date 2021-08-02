package org.agoncal.fascicle.quarkus.reactive.mutiny;

import io.smallrye.mutiny.Multi;

import java.util.concurrent.Executor;

public class BackPressure {

    public static void main(String[] args) {
	System.out.println("#### fromItems()");
	fromItems();
    }

    private static void fromItems() {
	Multi<String> multi = Multi.createFrom().items("Carla Bley", "John Coltrane", "Juliette Gréco");
	// Infrastructure.setDefaultExecutor();
	Executor executor = new MyExecutor();

	String res1 = multi //
		.emitOn(executor) //
		.onOverflow().buffer(10) //
		.collectItems().first() //
		.await().indefinitely();
    }

    static class MyExecutor implements Executor {

	@Override
	public void execute(Runnable command) {

	}
    }
}
