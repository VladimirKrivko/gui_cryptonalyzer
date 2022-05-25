package com.example.gui_cryptonalyzer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.logics.IOTextFile;
import com.example.logics.StaticAnalysis;
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

public class StaticAnalysisController {

    FileChooser chooser = new FileChooser();
    FileChooser chooserAuthor = new FileChooser();

    IOTextFile ioTextFile;

    IOTextFile ioTextFileAuthor;

    @FXML
    private Button backButton;

    @FXML
    private Button choiceFileAuthorButton;

    @FXML
    private Label choiceFileAuthorLabel;

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
            } else if (ioTextFileAuthor == null) {
                try {
                    ioTextFile.pushTextToFile(new StaticAnalysis(ioTextFile.getText()).decrypt());

                    infoLabel.setTextFill(Color.GREEN);
                    infoLabel.setText("Зашифрованный файл сохранен по адресу: " + ioTextFile.getOutputPathFile());
                    ioTextFile = null;
                    choiceFileLabel.setTextFill(Color.RED);
                    choiceFileLabel.setText("Файл не выбран!");
                    choiceFileAuthorLabel.setTextFill(Color.RED);
                    choiceFileAuthorLabel.setText("Используется общая частотная статистика русского алфавита.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            try {
                ioTextFile.pushTextToFile(new StaticAnalysis(ioTextFile.getText(), ioTextFileAuthor.getText()).decrypt());

                infoLabel.setTextFill(Color.GREEN);
                infoLabel.setText("Зашифрованный файл сохранен по адресу: " + ioTextFile.getOutputPathFile());
                ioTextFile = null;
                ioTextFileAuthor = null;
                choiceFileLabel.setTextFill(Color.RED);
                choiceFileLabel.setText("Файл не выбран!");
                choiceFileAuthorLabel.setTextFill(Color.RED);
                choiceFileAuthorLabel.setText("Используется общая частотная статистика русского алфавита.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        choiceFileAuthorButton.setOnAction(event -> {
            chooserAuthor.setTitle("Выбери текстовый файл того же автора.");
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Выбери текстовый файл", "*.txt");
            chooserAuthor.getExtensionFilters().add(filter);
            try {
                File file = chooserAuthor.showOpenDialog(new Stage());
                ioTextFileAuthor = new IOTextFile(file.getAbsolutePath());

                choiceFileAuthorLabel.setTextFill(Color.GREEN);
                choiceFileAuthorLabel.setText("Используется частотная статистика русского алфавита автора текста.");
            } catch (Exception e) {
                choiceFileAuthorLabel.setText("Используется частотная статистика русского алфавита автора текста.");
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
