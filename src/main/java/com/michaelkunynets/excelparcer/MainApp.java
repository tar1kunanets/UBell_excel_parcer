package com.michaelkunynets.excelparcer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
//        long startTime = System.nanoTime();
//        ReadXls readXls = new ReadXls();
//        readXls.Read();
//        long endTime = System.nanoTime();
//        System.out.println("Took "+(endTime - startTime) + " ns");
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("/fxml/main.fxml"));
        stage.setTitle("Excel parser");
        stage.setScene(new Scene(root));
        stage.show();
    }
}