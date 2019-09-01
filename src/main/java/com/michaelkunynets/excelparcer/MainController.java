package com.michaelkunynets.excelparcer;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.lang.String;

public class MainController {
    private static Logger LOGGER = null;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger(MainController.class.getName());
    }

    private ReadXls readXls;

    @FXML
    public TableView work_table;


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
            work_table.setTableMenuButtonVisible(true);
            SetTableViewRooms(readXls.getClassName(), readXls.Read());
            work_table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
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

    private void SetTableViewRooms(String[] rooms, String[][] inputData) {
        LOGGER.info("try set Class name");
        work_table.setEditable(true);
//        for (int i = 0; i < rooms.length; ++i) {
//            TableColumn column = new TableColumn(rooms[i]);
//            work_table.getColumns().add(column);
//        }
        for (int i = 0; i < rooms.length; i++) {

            TableColumn<List<String>, String> column = new TableColumn<>(rooms[i]);

            final int colIndex = i;
            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(colIndex)));

            work_table.getColumns().add(column);

        }

//        work_table.getColumns().addAll(ReadXls.getClassName());
        List<List<String>> data = new ArrayList<List<String>>();

        for (int i = 0; i < inputData.length; i++) {
            List<String> row = new ArrayList<String>();
            for (int j = 0; j < inputData[0].length; j++) {

                row.add(inputData[i][j]);
            }
            data.add(row);
        }
        ObservableList<List<String>> inpData = FXCollections.observableArrayList(data);
        work_table.setItems(inpData);
        work_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        work_table.refresh();
//        TablePosition pos = (TablePosition) work_table.getSelectionModel().getSelectedCells().get(0);
//        int index = pos.getRow();
        LOGGER.info("all data added to table");
    }
}
