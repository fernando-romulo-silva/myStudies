package org.nandao.ch03ProgrammingWithLambdas.part06Laziness;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// In the preceding section, you saw how users of an image transformation method
// can precompose operations to avoid intermediate images. But why should they
// have to do that? Another approach is for the library to accumulate all operations
// and then fuse them. This is, of course, what the stream library does.

public class Test {

    public static void main(String[] args) {

        final Image image = new Image("eiffel-tower.jpg");

        final Image finalImage = LatentImage.from(image) //
            .transform(Color::brighter) //
            .transform(Color::grayscale) // any transformation you need
            .toImage();

        // In real life, implementing lazy operations is quite a bit harder.
        // Usually you have a mixture of operations, and not all of them can be applied lazily.
    }

    public static class LatentImage {

        private final Image in;

        private final List<UnaryOperator<Color>> pendingOperations = new ArrayList<>();

        private LatentImage(final Image in) {
            this.in = in;
        }

        public Image getIn() {
            return in;
        }

        public LatentImage transform(final UnaryOperator<Color> f) {
            pendingOperations.add(f);
            return this;
        }

        public static LatentImage from(Image in) {
            return new LatentImage(in);
        }

        public Image toImage() {
            final int width = (int) in.getWidth();
            final int height = (int) in.getHeight();
            final WritableImage out = new WritableImage(width, height);

            for (int x = 0; x < width; x++)

                for (int y = 0; y < height; y++) {
                    Color c = in.getPixelReader().getColor(x, y);

                    for (final UnaryOperator<Color> f : pendingOperations) {
                        c = f.apply(c);
                    }

                    out.getPixelWriter().setColor(x, y, c);
                }
            return out;
        }

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
