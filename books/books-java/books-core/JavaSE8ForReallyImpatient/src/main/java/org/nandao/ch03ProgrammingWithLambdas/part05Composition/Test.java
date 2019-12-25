package org.nandao.ch03ProgrammingWithLambdas.part05Composition;

import java.util.function.UnaryOperator;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// A single-argument function transforms one value into another. If you have two
// such transformations, then doing one after the other is also a transformation.
public class Test {

    public static void main(String[] args) {
        final Image image = new Image("eiffel-tower.jpg");

        // firts grayscle, before, brighter
        final Image finalImage = transform(image, compose(Color::brighter, Color::grayscale));
    }

    // That is much better. Now the composed transformation is directly applied to
    // each pixel, and there is no need for an intermediate image.
    public static <T> UnaryOperator<T> compose(UnaryOperator<T> op1, UnaryOperator<T> op2) {
        return t -> op2.apply(op1.apply(t));
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

}
