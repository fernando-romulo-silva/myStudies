package org.nandao.ch04JavaFx.part05Bindings;

import static javafx.beans.binding.Bindings.greaterThanOrEqual;
import static javafx.beans.binding.Bindings.lessThanOrEqual;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(final Stage stage) {
        example3(stage);
    }

    public void example1(final Stage stage) {
        final TextArea shipping = new TextArea();
        final TextArea billing = new TextArea();

        // The raison d’être for JavaFX properties is the notion of binding: automatically
        // updating one property when another one changes.
        final StringProperty textProperty = billing.textProperty();

        // This is achieved by binding one property to the other:
        textProperty.bindBidirectional(shipping.textProperty());

        // If either of the properties changes, the other is updated.
        // To undo a binding, call unbind or unbindBidirectional.

        final VBox root = new VBox();
        root.getChildren().addAll(new Label("Shipping"), shipping, new Label("Billing"), billing);

        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void example2(final Stage stage) {

        // Of course, in many situations, one property depends on another, but the relationship is more complex. 
        // That is, its centerX property should be one half of the width property of the scene.
        final Circle circle = new Circle(100, 100, 100);
        circle.setFill(Color.RED);

        final Pane pane = new Pane();
        pane.getChildren().add(circle);

        final Scene scene = new Scene(pane);

        final DoubleProperty centerXProperty = circle.centerXProperty();
        final DoubleProperty centerYProperty = circle.centerYProperty();

        // To achieve this, we need to produce a computed property. The Bindings class has
        // static methods for this purpose. For example, Bindings.divide(scene.widthProperty(), 2) 
        // is a property whose value is one half of the scene width.
        centerXProperty.bind(Bindings.divide(scene.widthProperty(), 2));
        centerYProperty.bind(Bindings.divide(scene.heightProperty(), 2));
        // When the scene width changes, so does that property. All that remains is to bind that computed property
        // to the circle’s centerX property

        stage.setScene(scene);
        stage.show();
    }

    public void example3(final Stage stage) {
        final Button smaller = new Button("Smaller");
        final Button larger = new Button("Larger");

        final Rectangle gauge = new Rectangle(0, 5, 50, 15);

        final Rectangle outline = new Rectangle(0, 5, 100, 15);
        outline.setFill(null);
        outline.setStroke(Color.BLACK);

        final Pane pane = new Pane();
        pane.getChildren().addAll(gauge, outline);

        smaller.setOnAction(event -> gauge.setWidth(gauge.getWidth() - 10));
        larger.setOnAction(event -> gauge.setWidth(gauge.getWidth() + 10));

        smaller.disableProperty().bind(lessThanOrEqual(gauge.widthProperty(), 0));
        larger.disableProperty().bind(greaterThanOrEqual(gauge.widthProperty(), 100));

        final HBox box = new HBox(10);
        box.getChildren().addAll(smaller, pane, larger);

        final Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
