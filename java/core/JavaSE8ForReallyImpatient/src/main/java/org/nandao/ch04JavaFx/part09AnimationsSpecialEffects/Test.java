package org.nandao.ch04JavaFx.part09AnimationsSpecialEffects;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

// When JavaFX was born, special effects were all the rage, and JavaFX makes it
// easy to produce shadows, blurs, and movement. You will find dozens of pretty
// demos on the Web with swirling bubbles moving aimlessly, text jumping
// nervously, and so on. I thought you’d enjoy some useful tips on how to bring
// these animations to the world of business applications. Figure 4–12 shows an
// application where the Yes button increases in size while the No button fades into
// the background and the Maybe button rotates.
public class Test extends Application {

    @Override
    public void start(final Stage stage) {
        test2(stage);
    }


    public void test1(final Stage stage) {
        final Button yesButton = new Button("Yes");
        final Button noButton = new Button("No");
        final Button maybeButton = new Button("Maybe");

        // JavaFX defines a number of transitions that, over a period of time, vary a property
        // of a node. Here is how you grow a node by 50% in both x and y directions over three seconds:

        final ScaleTransition st = new ScaleTransition(Duration.millis(3000));
        st.setByX(1.5);
        st.setByY(1.5);
        st.setCycleCount(Animation.INDEFINITE);
        st.setAutoReverse(true);
        st.setNode(yesButton);
        st.play();

        final FadeTransition ft = new FadeTransition(Duration.millis(3000));
        ft.setFromValue(1.0);
        ft.setToValue(0);
        ft.setCycleCount(Animation.INDEFINITE);
        ft.setAutoReverse(true);
        ft.setNode(noButton);
        ft.play();

        final RotateTransition rt = new RotateTransition(Duration.millis(3000));
        rt.setByAngle(180);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setAutoReverse(true);
        rt.setNode(maybeButton);
        rt.play();

        final HBox buttons = new HBox(10);
        buttons.getChildren().addAll(yesButton, noButton, maybeButton);
        final VBox pane = new VBox(10);
        final Label question = new Label("Will you attend?");
        pane.setPadding(new Insets(10));
        pane.getChildren().addAll(question, buttons);

        stage.setScene(new Scene(pane));
        stage.show();
    }

    public void test2(final Stage stage) {

        // Special effects are also very easy to do. If you need a drop shadow for a spiffy
        // caption, make a DropShadow effect and set it as the effect property of a node.

        final DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.GRAY);

        final Text text = new Text();
        text.setY(50.0);
        text.setFill(Color.RED);
        text.setText("Drop shadow");
        text.setFont(Font.font("sans", FontWeight.BOLD, 40));
        text.setEffect(dropShadow);

        final Text text2 = new Text();
        text2.setY(150.0);
        text2.setFill(Color.BLUE);
        text2.setText("Glow");
        text2.setFont(Font.font("sans", FontWeight.BOLD, 40));
        text2.setEffect(new Glow(0.8));

        final Text text3 = new Text();
        text3.setY(250.0);
        text3.setFill(Color.GREEN);
        text3.setText("GaussianBlur");
        text3.setFont(Font.font("sans", FontWeight.BOLD, 40));
        text3.setEffect(new GaussianBlur());

        final Group group = new Group(text, text2, text3);

        stage.setScene(new Scene(group));
        stage.show();

        // Admittedly, the glow effect looks a bit cheesy and the blur effect doesn’t seem
        // to have many applications in the world of business, but it is impressive how easy
        // it is to produce these effects.
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
