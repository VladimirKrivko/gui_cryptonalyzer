package com.example.gui_cryptonalyzer;

import java.io.File;
import java.io.IOException;

import com.example.logics.Encrypt;
import com.example.logics.IOTextFile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CesarEncryptController {

    FileChooser chooser = new FileChooser();

    IOTextFile ioTextFile;

    String password;

    @FXML
    private Button backButton;

    @FXML
    private Button choiceFileButton;

    @FXML
    private Label choiceFileLabel;

    @FXML
    private Button cipherButton;

    @FXML
    private TextField enterCodeButton;

    @FXML
    private Label enterCodeLabel;

    @FXML
    private Label infoLabel;

    @FXML
    void initialize(ActionEvent event) {

    }

    @FXML
    void initialize() {

        cipherButton.setOnAction(event -> {
            if (ioTextFile == null) {
                infoLabel.setTextFill(Color.LIGHTGREEN);
                infoLabel.setText("Не выбран текстовый файл.");
                return;
            }
            if (null == password || password.equals("")) {
                infoLabel.setTextFill(Color.RED);
                infoLabel.setText("Либо данный ключ не приведет к шифрованию, либо он не задан.");
                return;
            }
            try {
                ioTextFile.pushTextToFile(new Encrypt(ioTextFile.getText(), password).encrypt());

                infoLabel.setTextFill(Color.LIGHTGREEN);
                infoLabel.setText("Зашифрованный файл сохранен по адресу: " + ioTextFile.getOutputPathFile());
                ioTextFile = null;
                choiceFileLabel.setTextFill(Color.RED);
                choiceFileLabel.setText("Файл не выбран!");
                password = null;
                enterCodeLabel.setTextFill(Color.RED);
                enterCodeLabel.setText("Ключ не принят!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        enterCodeButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (!enterCodeButton.getText().equals("")) {
                        password = enterCodeButton.getText();
                        enterCodeLabel.setTextFill(Color.LIGHTGREEN);
                        enterCodeLabel.setText("Ключ принят!    " + password);
                    }
                    // можно в конце почистить текст
                    enterCodeButton.setText("");
                }
            }
        });


        choiceFileButton.setOnAction(event -> {
            chooser.setTitle("Выбери файл для шифрования.");
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Выбери текстовый файл", "*.txt");
            chooser.getExtensionFilters().add(filter);
            try {
                File file = chooser.showOpenDialog(new Stage());
                ioTextFile = new IOTextFile(file.getAbsolutePath());

                choiceFileLabel.setTextFill(Color.LIGHTGREEN);
                choiceFileLabel.setText("Файл выбран: " + ioTextFile.getInputPathFile());
            } catch (Exception e) {
                choiceFileLabel.setTextFill(Color.RED);
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
