package org.nandao.ch04JavaFx.part02HelloJavaFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

// In JavaFX, you put everything you want to show onto a scene. There, you can
// decorate and animate your “actors”—that is, your controls and shapes. In our
// program, we won’t do any decorating or animating, but we still need the scene.
// And the scene must reside in a stage. That is a top-level window if the program
// runs on a desktop, or a rectangular area if it runs as an applet. The stage is passed
// as a parameter to the start method that you must override in a subclass of the
// Application classA

public class Test extends Application {

    @Override
    public void start(final Stage stage) {
        final Label message = new Label("Hello, JavaFX!");
        message.setFont(new Font(100));
        stage.setScene(new Scene(message));
        stage.setTitle("Hello");
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
