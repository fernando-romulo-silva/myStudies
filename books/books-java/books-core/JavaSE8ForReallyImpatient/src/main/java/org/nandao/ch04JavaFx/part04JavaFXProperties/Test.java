package org.nandao.ch04JavaFx.part04JavaFXProperties;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Test extends Application {

    // A property is an attribute of a class that you can read or write. Commonly, the
    // property is backed by a field, and the property getter and setter simply read and
    // write that field. But the getter and setter can also  take other actions, such as
    // reading values from a database or sending out change notifications. In many
    // programming languages, there is convenient syntax for invoking property getters
    // and setters. 
    //
    // The StringProperty class wraps a string. It has methods for getting and setting the
    // wrapped value and for managing listeners. As you can see, implementing a
    // JavaFX property requires some boilerplate code, and there is unfortunately no
    // way in Java to generate the code automatically. But at least you won’t have
    // to worry about managing listeners.
    // It is not a requirement to decAlare property getters and setters as final, but the
    // JavaFX designers recommend it.
    //
    //
    // In the preceding example, we defined a StringProperty. For a primitive type
    // property, use one of IntegerProperty, LongProperty, DoubleProperty, FloatProperty, or
    // BooleanProperty. There are also ListProperty, MapProperty, and SetProperty classes. For
    // everything else, use an ObjectProperty<T>. All these are abstract classes with concrete
    // subclasses SimpleIntegerProperty, SimpleObjectProperty<T>, and so on.

    public class Greeting {
        private final StringProperty text = new SimpleStringProperty("");

        public final StringProperty textProperty() {
            return text;
        }

        public final void setText(final String newValue) {
            text.set(newValue);
        }

        public final String getText() {
            return text.get();
        }
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        final Label message = new Label("Hello, JavaFX!");
        message.setFont(new Font(100));

        final Slider slider = new Slider();
        slider.setValue(100);
        slider.valueProperty().addListener( //
                (property, oldValue, newValue) -> message.setFont(new Font((double) newValue))//
        );

        // Grouping components
        final VBox root = new VBox();
        root.getChildren().addAll(slider, message);
        final Scene scene = new Scene(root);

        primaryStage.setTitle("Hello");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(final String[] args) {
        launch(args);
    }
}
