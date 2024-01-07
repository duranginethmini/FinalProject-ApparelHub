package lk.ijse.apparelHub.Controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.awt.*;
import java.util.Optional;


public class NotificationController {
    public static void ErrorMasseage(String messeage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(messeage);

        alert.getDialogPane().setPrefSize(300, 150);
        alert.getDialogPane().setStyle("-fx-background-color: #dfa47e;");
        alert.getDialogPane().setHeaderText(null);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/assets/error.png"));
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(cancelButton);

        alert.showAndWait();

    }

    public static boolean confirmationMasseage(String messeage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirmation");
        alert.setHeaderText(null);
        alert.setContentText(messeage);

        alert.getDialogPane().setPrefSize(300, 150);
        alert.getDialogPane().setHeaderText(null);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        ButtonType okButton = new ButtonType("ok", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(okButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(cancelButton) == okButton;
    }
    public static void notificationBar(String title, String massage) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image, "Notification Example");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Click me to see the message");
        tray.add(trayIcon);
        trayIcon.displayMessage(title, massage, TrayIcon.MessageType.INFO);
    }
}