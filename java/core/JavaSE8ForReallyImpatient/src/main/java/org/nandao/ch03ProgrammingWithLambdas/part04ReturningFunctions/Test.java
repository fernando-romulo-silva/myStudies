package org.nandao.ch03ProgrammingWithLambdas.part04ReturningFunctions;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// Java is not quite a functional language because it uses functional interfaces,
// but the principle is the same. You have seen many methods that accept functional interfaces. In this section, we consider methods
// whose return type is a functional interface

public class Test {

    public static void main(String[] args) {

        final Image image = new Image("images//eiffel-tower.jpg");

        final Image brightenedImage1 = transform(image, Color::brighter);

        final Image brightenedImage2 = transform(image, //
                                                 //  R apply(T t, U u); -> color.deriveColor
                                                 (c, factor) -> c.deriveColor(0, 1, factor, 1), // Brighten c by factor, deriveColor is a Color method
                                                 1.2);

        final Image brightenedImage3 = transform(image, brighten(1.2));
        
        System.out.println("Image : "+ image);
        
        System.out.println("brightenedImage 1 : "+ brightenedImage1);
        
        System.out.println("brightenedImage 2 : "+ brightenedImage2);
        
        System.out.println("brightenedImage 3 : "+ brightenedImage3);

    }
    
    // In general, don’t be shy to write methods that produce functions. This is useful
    // to customize the functions that you pass to methods with functional interfaces.
    // For example, consider the Arrays.sort method with a Comparator argument. There
    // are many ways of comparing values, and you can write a method that yields a
    // comparator for your needs

    public static UnaryOperator<Color> brighten(double factor) {
        return c -> c.deriveColor(0, 1, factor, 1);
    }

    public static <T> Image transform(Image in, BiFunction<Color, T, Color> function, T arg) {
        final int width = (int) in.getWidth();
        final int height = (int) in.getHeight();
        final WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y, function.apply(in.getPixelReader().getColor(x, y), arg));
            }
        }
        return out;
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
