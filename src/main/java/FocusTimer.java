import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.StageStyle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class FocusTimer extends Application {

    // clock time in ms
    private long endMillis;

    private int focusMinutes = 25;

    //where inside the window the mouse grabbed it 
    private double dragX, dragY;

    @Override
    public void start(Stage stage) {
        Label time = new Label("25:00");
        StackPane root = new StackPane(time);

        //dragging
        root.setOnMousePressed(e -> {
            dragX = e.getSceneX();
            dragY = e.getSceneY();
        });
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - dragX);
            stage.setY(e.getScreenY() - dragY);
        });

        //timer logic
        endMillis = System.currentTimeMillis() + focusMinutes * 60 * 1000L;

        //run this every 200ms on the JavaFX thread
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            long remainingMs = Math.max(0, endMillis - System.currentTimeMillis()); // calculate remaining time
            long secs = (remainingMs + 999) / 1000;
            time.setText(String.format("%02d:%02d", secs / 60, secs % 60)); // set remaining time
        }));
        timeline.setCycleCount(Animation.INDEFINITE); //repeat forever
        timeline.play();

        //looks
        root.setStyle("-fx-background-color: #FFE5EC;");
        time.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #6B2D45;");

        Label title = new Label("focus time");
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #6B2D45;");

        Button close = new Button("✕");
        close.setStyle("-fx-background-color: transparent; -fx-text-fill: #6B2D45; -fx-font-weight: bold;");
        close.setOnAction(e -> stage.close());

        Region spacer = new Region();                  // invisible stretchy gap
        HBox.setHgrow(spacer, Priority.ALWAYS);        // makes the gap eat all the free space

        HBox topBar = new HBox(title, spacer, close);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(6, 8, 0, 12));

        BorderPane layout = new BorderPane();
        layout.setTop(topBar);
        layout.setCenter(root);
        layout.setStyle("-fx-background-color: #FFE5EC;");

        // window settings 
        stage.setScene(new Scene(layout, 220, 260));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.setTitle("focus");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
