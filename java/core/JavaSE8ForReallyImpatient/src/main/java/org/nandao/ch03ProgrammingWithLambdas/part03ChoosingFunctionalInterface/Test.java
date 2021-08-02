package org.nandao.ch03ProgrammingWithLambdas.part03ChoosingFunctionalInterface;

import java.util.function.UnaryOperator;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// Of course, there are many situations where you want to accept “any function”
// without particular semantics. There are a number of generic function types for
// that purpose (see Table 3–1), and it’s a very good idea to use one of them when
// you can.
//
// Runnable
// Supplier<T>
// Consumer<T>
// BiConsumer<T, U>
// Function<T, R>
// BiFunction<T, U, R>
// UnaryOperator<T>
// BinaryOperator<T>
// Predicate<T>
// BiPredicate<T, U>
//
public class Test {

    public static void main(String[] args) {

        final Image image = new Image("eiffel-tower.jpg");

        final Image brightenedImage = transform(image, Color::brighter);
    }

    public static Image transform(Image in, UnaryOperator<Color> functionUnaryOperator) {
        final int width = (int) in.getWidth();
        final int height = (int) in.getHeight();
        final WritableImage out = new WritableImage(width, height);

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                final PixelWriter pixelWriter = out.getPixelWriter();

                pixelWriter.setColor(x, y,
                                     // note de call to the apply (UnaryOperator)
                                     functionUnaryOperator.apply(in.getPixelReader().getColor(x, y)));
            }
        }
        return out;
    }

    // Sometimes, you need to supply your own functional interface because there is
    // nothing in the standard library that works for you. Suppose you want to modify
    // colors in an image, allowing users to specify a function (int, int, Color) -> Color
    // that computes a new color depending on the (x, y) location in the image. In that
    // case, you can define your own interface:

    @FunctionalInterface
    public interface ColorTransformer {
        Color apply(int x, int y, Color colorAtXY);
    }

}
