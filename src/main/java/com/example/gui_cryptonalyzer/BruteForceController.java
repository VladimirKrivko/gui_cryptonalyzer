package com.example.gui_cryptonalyzer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BruteForceController {

    FileChooser chooser = new FileChooser();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button choiceFileButton;

    @FXML
    private Label choiceFileLabel;

    @FXML
    private Button cipherButton;

    @FXML
    private Label infoLabel;

    @FXML
    void initialize(ActionEvent event) {

    }

    @FXML
    void initialize() {

        cipherButton.setOnAction(event -> {
            if (Cipher.fileInputName == null) {
                infoLabel.setTextFill(Color.RED);
                infoLabel.setText("Не выбран текстовый файл.");
                return;
            }
            try {
                Cipher.pushTextToFile(Cipher.bruteForce(Cipher.getTextFromFile(Cipher.fileInputName), ", "));
                infoLabel.setTextFill(Color.GREEN);
                infoLabel.setText("Зашифрованный файл сохранен по адресу: " + Cipher.fileOutputName);

                Cipher.fileInputName = null;
                choiceFileLabel.setTextFill(Color.RED);
                choiceFileLabel.setText("Файл не выбран!");
            } catch (IOException e) {
                e.printStackTrace();
                //throw new RuntimeException(e);
            }
        });

        choiceFileButton.setOnAction(event -> {
            chooser.setTitle("Выбери файл для расшифровки.");
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Выбери текстовый файл", "*.txt");
            chooser.getExtensionFilters().add(filter);
            try {
                File file = chooser.showOpenDialog(new Stage());
                Cipher.fileInputName = file.getAbsolutePath();
                Cipher.setFileOutputName(Cipher.fileInputName);

                choiceFileLabel.setTextFill(Color.GREEN);
                choiceFileLabel.setText("Файл выбран: " + Cipher.fileInputName);
            } catch (Exception e) {
                choiceFileLabel.setText("Файл не выбран!");
            }
        });

        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainCrypto-view.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cryptanalyzer 98");
            stage.getIcons().add(new Image("icon.png"));
            stage.setResizable(false);
            stage.show();
        });

    }

}
