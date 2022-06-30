package com.example.gui_cryptonalyzer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private ImageView bruteForceButton;

    @FXML
    private ImageView cesarDecryptButton;

    @FXML
    private ImageView cesarEncryptButton;

    @FXML
    private ImageView statAnalButton;

    @FXML
    void initialize() {
        cesarEncryptButton.setOnMouseClicked(event -> {
            cesarEncryptButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CesarEncrypt-view.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cryptanalyzer 98        Зашифровка");
            stage.getIcons().add(new Image("icon.png"));
            stage.setResizable(false);
            stage.show();
        });

        cesarDecryptButton.setOnMouseClicked(event -> {
            cesarDecryptButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CesarDecrypt-view.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cryptanalyzer 98        Расшифровка");
            stage.getIcons().add(new Image("icon.png"));
            stage.setResizable(false);
            stage.show();
        });

        bruteForceButton.setOnMouseClicked(event -> {
            bruteForceButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BruteForce-view.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cryptanalyzer 98        BruteForce");
            stage.getIcons().add(new Image("icon.png"));
            stage.setResizable(false);
            stage.show();
        });

        statAnalButton.setOnMouseClicked(event -> {
            statAnalButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("StatAnal-view.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cryptanalyzer 98        Частотный анализ");
            stage.getIcons().add(new Image("icon.png"));
            stage.setResizable(false);
            stage.show();
        });
    }
}
