package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserPanelController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField name;
    @FXML
    private TextField customer;
    @FXML
    private TextField price;
    @FXML
    private TableColumn<Tour, String> colName;
    @FXML
    private TableColumn<Tour, String> colDate_check_in;
    @FXML
    private TableColumn<Tour, String> colDate_check_out;
    @FXML
    private TableColumn<Tour, Integer> colPrice;
    @FXML
    private TableView<Tour> table;
    @FXML
    private DatePicker check_in_date;
    @FXML
    private DatePicker check_out_date;
    @FXML
    private Button logout;
    private ObservableList<Tour> list = FXCollections.observableArrayList();
    private ArrayList<Tour> arrayList=new ArrayList<>();
    private FileWorker worker = new FileWorker();

    private int counter = 0;

    public void initialize()  {
        File fileinputstream1 = new File("vicusa.txt");

        colName.setCellValueFactory(new PropertyValueFactory<Tour, String>("name"));
        colDate_check_in.setCellValueFactory(new PropertyValueFactory<Tour, String>("check_in_date"));
        colDate_check_out.setCellValueFactory(new PropertyValueFactory<Tour, String>("check_out_date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Tour, Integer>("price"));
        table.setEditable(true);
        try {
            arrayList = (ArrayList<Tour>) worker.readObject("vicusa.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.addAll(arrayList);
        table.setItems(list);
    }

}
