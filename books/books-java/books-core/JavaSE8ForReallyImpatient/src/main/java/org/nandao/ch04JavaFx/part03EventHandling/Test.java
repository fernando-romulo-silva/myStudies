package org.nandao.ch04JavaFx.part03EventHandling;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {

        final Label message = new Label("Hello, JavaFX!");
        message.setFont(new Font(100));

        // As in Swing, you add an event handler to a button so you can be notified when
        // it is clicked. Lambda expressions make this very simple:
        final Button red = new Button("Red");
        red.setOnAction(event -> message.setTextFill(Color.RED));

        // However, with most JavaFX controls, event handling is different. Consider a slider. 
        // When the slider is adjusted, its value changes.
        // However, you shouldn’t listen to the low-level events that the slider emits to indicate
        // those changes. Instead, the slider has a JavaFX property called value, and
        // the property emits events when it changes.
        final Slider slider = new Slider();
        slider.setValue(100);
        slider.valueProperty().addListener( //
                property -> message.setFont(new Font(slider.getValue())) //
        );

        // Grouping components
        final VBox root = new VBox();
        root.getChildren().addAll(slider, message, red);
        final Scene scene = new Scene(root);

        primaryStage.setTitle("Hello");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
