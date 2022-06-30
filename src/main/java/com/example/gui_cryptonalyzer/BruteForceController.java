package com.example.gui_cryptonalyzer;

import com.example.logics.BruteForce;
import com.example.logics.IOTextFile;
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

import java.io.File;
import java.io.IOException;

public class BruteForceController {

    FileChooser chooser = new FileChooser();

    IOTextFile ioTextFile;

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
            if (ioTextFile == null) {
                infoLabel.setTextFill(Color.GREEN);
                infoLabel.setText("Не выбран текстовый файл.");
                return;
            }
            try {
                ioTextFile.pushTextToFile(new BruteForce(ioTextFile.getText()).decrypt());

                infoLabel.setTextFill(Color.GREEN);
                infoLabel.setText("Зашифрованный файл сохранен по адресу: " + ioTextFile.getOutputPathFile());
                ioTextFile = null;
                choiceFileLabel.setTextFill(Color.RED);
                choiceFileLabel.setText("Файл не выбран!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        choiceFileButton.setOnAction(event -> {
            chooser.setTitle("Выбери файл для расшифровки.");
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Выбери текстовый файл", "*.txt");
            chooser.getExtensionFilters().add(filter);
            try {
                File file = chooser.showOpenDialog(new Stage());
                ioTextFile = new IOTextFile(file.getAbsolutePath());

                choiceFileLabel.setTextFill(Color.GREEN);
                choiceFileLabel.setText("Файл выбран: " + ioTextFile.getInputPathFile());
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
