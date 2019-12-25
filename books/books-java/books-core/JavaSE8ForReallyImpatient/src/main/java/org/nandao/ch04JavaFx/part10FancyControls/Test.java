package org.nandao.ch04JavaFx.part10FancyControls;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

// Of course, JavaFX has combo boxes, tab panes, trees, and tables, just like Swing
// does, as well as a few user interface controls that Swing never got, such as a date
// picker and an accordion. It would take an entire book to describe these in detail.
// In this section, I want to dispel any remaining Swing nostalgia by showing you
// three fancy controls that are far beyond what Swing had to offer.

public class Test extends Application {

    @Override
    public void start(final Stage stage) {
        test2(stage);
    }

    // Shows one of many charts that you can make with JavaFX, out of the
    // box, without having to install any third-party libraries.
    public void test1(final Stage stage) {

        final ObservableList<PieChart.Data> pieChartData = // 
                FXCollections.observableArrayList( //
                        new PieChart.Data("Asia", 4298723000.0), //
                        new PieChart.Data("North America", 355361000.0), //
                        new PieChart.Data("South America", 616644000.0), //
                        new PieChart.Data("Europe", 742452000.0), //
                        new PieChart.Data("Africa", 1110635000.0), //
                        new PieChart.Data("Oceania", 38304000.0)); //

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Population of the Continents");

        final HBox box = new HBox(chart);
        box.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }

    // In Swing, you could show HTML in a JEditorPane, but the rendering was poor for
    // most real-world HTML. That’s understandable—implementing a browser is hard work. 
    // In fact, it is so hard that most browsers are built on top of the open source WebKit engine. 
    // JavaFX does the same. A WebView displays an embedded native WebKit window
    public void test2(final Stage stage) {
        final String location = "http://www.google.com";
        final WebView browser = new WebView();
        final WebEngine engine = browser.getEngine();
        engine.load(location);

        final Scene scene = new Scene(browser);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }

    // Prior to JavaFX, media playback was pitiful in Java. A Java Media Framework
    // was available as an optional download, but it did not get much love from the
    // developers. Of course, implementing audio and video playback is even harder
    // than writing a browser. Therefore, JavaFX leverages an existing toolkit, the open
    // source GStreamer framework.
    public void test3(final Stage stage) {

        final ClassLoader classLoader = Test.class.getClassLoader();
        final Path path = Paths.get(new File(classLoader.getResource("videos").getFile()).getAbsolutePath());

        final Path pathFile = Paths.get(path.toString() + "/moonlanding.mp4");
        final String location = pathFile.toUri().toString();
        final Media media = new Media(location);
        final MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);

        final MediaView view = new MediaView(player);
        view.setOnError(e -> System.out.println(e));
        final HBox box = new HBox(view);
        box.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
