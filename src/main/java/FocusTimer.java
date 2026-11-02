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

public class FocusTimer extends Application {

    private long endMillis; 
    private int focusMinutes = 25;
    private double dragX, dragY;

    @Override
    public void start(Stage stage) {
        Label time = new Label("25:00");
        StackPane root = new StackPane(time);

        root.setOnMousePressed(e -> {
            dragX = e.getSceneX();
            dragY = e.getSceneY();
        });
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - dragX);
            stage.setY(e.getScreenY() - dragY);
        });

        endMillis = System.currentTimeMillis() + focusMinutes * 60 * 1000L;

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            long remainingMs = Math.max(0, endMillis - System.currentTimeMillis());
            long secs = (remainingMs + 999) / 1000;
            time.setText(String.format("%02d:%02d", secs / 60, secs % 60));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        root.setStyle("-fx-background-color: #FFE5EC;");
        time.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #6B2D45;");

        stage.setScene(new Scene(root, 220, 260));
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
