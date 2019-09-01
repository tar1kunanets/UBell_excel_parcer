package com.michaelkunynets.excelparcer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.logging.Logger;

public class MainController {
    private static Logger LOGGER = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(MainController.class.getName());
    }

    private ReadXls readXls;

    @FXML
    private TableView work_table;

    @FXML
    private void SendMouseClicked(ActionEvent event) {
        // FIXME: 01.09.2019 SEND to Server Here
    }

    @FXML
    public void handleOpenAction(ActionEvent actionEvent) {
        LOGGER.info("File chooser");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null || selectedFile.getName().endsWith(".xlsx")) {
            LOGGER.info("File choose correctly");
            readXls = new ReadXls(selectedFile.getPath());
        } else {
            LOGGER.warning("File choose incorrect, send alert");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText("You took not a 'xlsx' file");
            alert.setContentText("please give a correct file to program");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    @FXML
    public void handleExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void SetTableViewRooms() {
//        work_table.getColumns().addAll();
    }
}
