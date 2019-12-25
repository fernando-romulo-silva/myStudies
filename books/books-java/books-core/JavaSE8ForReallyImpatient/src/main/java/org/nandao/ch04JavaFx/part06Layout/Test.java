package org.nandao.ch04JavaFx.part06Layout;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// When a graphical user interface contains multiple controls, they need to be arranged
// on the screen in a functional and attractive way. One way to obtain a
// layout is with a design tool. The tool’s user, often a graphics designer, drags images
// of the controls onto a design view and arranges, resizes, and configures
// them. However, this approach can be problematic when the sizes of the elements
// change, for example, because labels have different lengths in international
// versions of a program.
// Alternatively, the layout can be achieved programmatically, by writing code in
// a setup method that adds the user interface controls to specific positions. That is
// what was done in Swing, using layout manager objects.
// Another approach is to specify the layout in a declarative language. For example,
// web pages are laid out with HTML and CSS. Similarly, Android has an XML
// language for specifying layouts.
// JavaFX supports all three approaches. 

public class Test extends Application {

    @Override
    public void start(Stage stage) {
        
        // In JavaFX, dimensions are specified in pixels. In our example, we
        // use ten pixels for the box spacing and padding
        // (A rem or “root em” is the height of the default font of the document root.)
        final double rem = new Text("").getLayoutBounds().getHeight();

        final GridPane pane = new GridPane();
        
        // You will also want to provide some spacing around the rows and columns and
        // some padding around the table:        
        pane.setHgap(0.8 * rem);
        pane.setVgap(0.8 * rem);
        pane.setPadding(new Insets(0.8 * rem));

        final Label usernameLabel = new Label("User name:");
        final Label passwordLabel = new Label("Password:");
        final TextField username = new TextField();
        final PasswordField password = new PasswordField();

        final Button okButton = new Button("Ok");
        final Button cancelButton = new Button("Cancel");

        final HBox buttons = new HBox(0.8 * rem);
        buttons.getChildren().addAll(okButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);
        
        // When you add a child to a GridPane, specify its column and row index (in that
        // order; think x- and y-coordinates).
        pane.add(usernameLabel, 0, 0);
        pane.add(username, 1, 0);
        pane.add(passwordLabel, 0, 1);
        pane.add(password, 1, 1);
        pane.add(buttons, 0, 2, 2, 1); // Note multi-column span

        // TIP: For debugging, it can be useful to see the cell boundaries 
        pane.setGridLinesVisible(true);
        
        // If you want to see the borders of an individual child (for example, to see
        // whether it has grown to fill the entire cell), set its border. This is most easily done with CSS:
        buttons.setStyle("-fx-border-color: red;");
        
        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        stage.setScene(new Scene(pane));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
