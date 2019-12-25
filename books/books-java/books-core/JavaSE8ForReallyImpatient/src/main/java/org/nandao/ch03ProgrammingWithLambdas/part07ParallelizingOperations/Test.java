package org.nandao.ch03ProgrammingWithLambdas.part07ParallelizingOperations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

// When expressing operations as functional interfaces, the caller gives up control
// over the processing details. As long as the operations are applied so that the
// correct result is achieved, the caller has nothing to complain about. In particular,
// the library can make use of concurrency. For example, in image processing we
// can split the image into multiple strips and process each strip separately

public class Test {

    public static void main(String[] args) {

        final Image image = new Image("images/eiffel-tower.jpg");

        final int width = (int) image.getWidth();
        final int height = (int) image.getHeight();

        Color[][] pixels = new Color[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[y][x] = image.getPixelReader().getColor(x, y);
            }
        }

        pixels = parallelTransform(pixels, Color::grayscale);

        // In real life, implementing lazy operations is quite a bit harder.
        // Usually you have a mixture of operations, and not all of them can be applied lazily.
    }

    public static Color[][] parallelTransform(Color[][] in, UnaryOperator<Color> f) {
        final int n = Runtime.getRuntime().availableProcessors();

        final int height = in.length;
        final int width = in[0].length;

        final Color[][] out = new Color[height][width];

        try {

            final ExecutorService pool = Executors.newCachedThreadPool();

            for (int i = 0; i < n; i++) {

                final int fromY = i * height / n;

                final int toY = (i + 1) * height / n;

                pool.submit(() -> {

                    for (int x = 0; x < width; x++) {

                        for (int y = fromY; y < toY; y++) {
                            out[y][x] = f.apply(in[y][x]);
                        }
                    }
                });
            }

            pool.shutdown();
            pool.awaitTermination(1, TimeUnit.HOURS);
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
        }
        return out;
    }

}
