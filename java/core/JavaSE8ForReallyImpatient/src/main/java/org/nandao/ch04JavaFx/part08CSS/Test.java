package org.nandao.ch04JavaFx.part08CSS;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// JavaFX lets you change the visual appearance of the user interface with CSS,
// which is usually more convenient than supplying FXML attributes or calling Java methods.

public class Test extends Application {

    @Override
    public void start(final Stage stage) {
        try {
            final Parent root = FXMLLoader.load(getClass().getResource("/fxml/part08CSS.fxml"));

            final Scene scene = new Scene(root);

            // You can load a CSS style sheet programmatically and have it applied to a scene graph:            

            scene.getStylesheets().add("/fxml/scene.css");

            stage.setScene(scene);
            stage.show();

            // In the style sheet, you can reference any controls that have an ID. For example,
            // here is how you can control the appearance of a GridPane. In the code, set the ID:
            // GridPane pane = new GridPane();
            // pane.setId("pane");
            // Don’t set any padding or spacing in the code. Instead, use CSS.
            // #pane {
            //  -fx-padding: 0.5em;
            //  -fx-hgap: 0.5em;
            //  -fx-vgap: 0.5em;
            //  -fx-background-image: url("metal.jpg")
            // }            
            //
            // You can also use CSS with FXML layouts. Attach the stylesheet to the root pane:
            //    <GridPane id="pane" stylesheets="scene.css">
            // Supply id or styleClass attributes in the FXML code. For example,
            //    <HBox styleClass="buttonrow">            

        } catch (final IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
