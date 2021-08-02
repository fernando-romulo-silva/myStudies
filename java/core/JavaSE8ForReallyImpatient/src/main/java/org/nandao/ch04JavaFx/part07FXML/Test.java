package org.nandao.ch04JavaFx.part07FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// The markup language that JavaFX uses to describe layouts is called FXML. I
// discuss it in some detail because the concepts are interesting beyond the needs
// of JavaFX, and the implementation is fairly general.
public class Test extends Application implements Initializable {

    // Now look at the structure of the document. First off, the nesting of the GridPane,
    // the labels and text fields, the HBox and its button children reflects the nesting that
    // we built up with Java code in the preceding section.
    //
    // Most of the attributes correspond to property setters. For example,
    // <GridPane hgap="10" vgap="10">
    // means “construct a GridPane and then set the hgap and vgap properties.”
    // When an attribute starts with a class name and a static method, that method is invoked.
    // For example,
    // <TextField GridPane.columnIndex="1" GridPane.rowIndex="0"/>
    // means that the static methods GridPane.setColumnIndex(thisTextField, 1) and GridPane.setRowIndex(thisTextField, 0) will be called.

    @Override
    public void start(final Stage stage) {
        try {
            final Parent root = FXMLLoader.load(getClass().getResource("/fxml/part07FXML.fxml"));
            stage.setScene(new Scene(root));

            // Of course, this is not yet useful by itself. The user interface is displayed, but the
            // program cannot access the values that the user provides. One way of establishing
            // a connection between the controls and the program is to use id attributes, as you
            // would in JavaScript. Provide the id attributes in the FXML file:
            // <TextField id="username" GridPane.columnIndex="1" GridPane.rowIndex="0"/>
            // In the program, look up the control:
            final TextField username = (TextField) root.lookup("#username");
            username.setText("Test");

            stage.show();
        } catch (final IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    // But there is a better way. You can use the @FXML annotation to “inject” the control
    // objects into a controller class. The controller class must implement the Initializable
    // interface. In the controller’s initialize method, you wire up the binders and event
    // handlers. Any class can be the controller, even the FX application itself.    

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button okButton;

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        okButton.disableProperty().bind(Bindings.createBooleanBinding(() -> username.getText().length() == 0 || password.getText().length() == 0, username.textProperty(), password.textProperty()));
        okButton.setOnAction(event -> System.out.println("Verifying " + username.getText() + ":" + password.getText()));
    }
    
    // In the FXML file, provide the names of the controller’s instance variables to the corresponding control elements in the FXML file, 
    // using the fx:id (not id) attribute:
    // <TextField fx:id="username" GridPane.columnIndex="1" GridPane.rowIndex="0"/>
    // <PasswordField fx:id="password" GridPane.columnIndex="1" GridPane.rowIndex="1" />
    // <Button fx:id="okButton" text="Ok" />
    // In the root element, you also need to declare the controller class, using the
    // fx:controller attribute:
    // <GridPane xmlns:fx="http://javafx.com/fxml" hgap="10" vgap="10" fx:controller="org.nandao.ch04JavaFx.part07FXML.Test">

    public static void main(final String[] args) {
        launch(args);
    }

}
