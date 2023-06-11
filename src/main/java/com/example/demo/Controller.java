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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
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
    private TableColumn<Tour, String> colCustomer;
    @FXML
    private TableColumn<Tour, String> colDate_check_in;
    @FXML
    private TableColumn<Tour, String> colDate_check_out;
    @FXML
    private TableColumn<Tour, Integer> colPrice;
    @FXML
    private TableView<Tour> table;
    @FXML
    private TextField find;
    @FXML
    private Button back;
    @FXML
    private Button addLine;
    @FXML
    private Button delete;
    @FXML
    private Button search;
    @FXML
    private TextField check_in_date1;
    @FXML
    private TextField check_out_date1;
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
        check_in_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        check_out_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        File fileinputstream1 = new File("vicusa.txt");

        colName.setCellValueFactory(new PropertyValueFactory<Tour, String>("name"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<Tour, String>("customer"));
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
    @FXML //Метод удаления строки
    protected void delete(){
        if (!list.isEmpty())
        {
            int row = table.getSelectionModel().getSelectedIndex();
            if (row >= 0){
                arrayList.remove(row);
                list.remove(row);
                worker.writeObject("vicusa.txt", arrayList);
                sort();
            }
            else {
                wrongInput("Удаление", "Не выбран ряд удаления");
            }
        }
        else {
            wrongInput("Удаление", "Список пуст");
        }
    }
    @FXML
    protected void addLine(){
        try {
            String pattern = "MMMM dd, yyyy";
            if (name.getText().matches("^([а-яА-Я])+(\s)*[а-яА-Я]*(\s)*[а-яА-Я]*$") && !name.getText().equals("")
                    &&  !customer.getText().equals("")
                   && !check_in_date.getValue().toString().equals("")
                    && !check_out_date.getValue().toString().equals("")
                    && Integer.parseInt(price.getText()) > 0 && price.getText().matches("^[0-9]*$")) {
                Tour book = new Tour(name.getText(),customer.getText(), check_in_date.getValue().toString(),
                        check_out_date.getValue().toString(), Integer.parseInt(price.getText()));
                list.add(book);
                arrayList.add(book);
                table.setItems(list);
                sort();
                freeInput();
                worker.writeObject("vicusa.txt", arrayList);
            }
            else {
                wrongInput("Некорректный ввод", "Введите корректные данные\n Текст на русском\n Число положительное\n Год издания 1800-2022");
            }
        }catch (IllegalStateException | NumberFormatException exc){
            wrongInput("Некорректный ввод", "Введите корректные данные\n Текст на русском\n Число положительное\n Год издания 1800-2022");
        }
    }
    protected void wrongInput(String header, String exc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setContentText(exc);
        freeInput();
        alert.showAndWait();
    }

    protected void wrongInputForEdit(String header, String exc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(header);
        alert.setContentText(exc);
        alert.showAndWait();
    }
    protected void freeInput(){
        name.setText("");
        customer.setText("");
        check_in_date.setValue(null);
        check_out_date.setValue(null);
        price.setText("");
    }

    @FXML //Метод сортировки по названию
    protected void sort(){
        if (!list.isEmpty()){
            table.getSortOrder().addAll(colName);
        }
        else {
            wrongInput("Сортировка", "Список пуст");
        }
    }
    @FXML
    protected void edit(){
        int row = table.getSelectionModel().getSelectedIndex();
        if(counter == 0)
        {
            if (row >= 0) {
                Tour chosen = list.get(row);
                name.setText(chosen.getName());
                customer.setText(chosen.getCustomer());
                check_in_date.setValue(LocalDate.parse(chosen.getCheck_in_date()));
                check_out_date.setValue(LocalDate.parse(chosen.getCheck_out_date()));
                price.setText(Integer.toString(chosen.getPrice()));
                addLine.setDisable(true);
                delete.setDisable(true);
                search.setDisable(true);
            }
            else {
                wrongInputForEdit("Редактирование", "Не выбран ряд");
                return;
            }
        }
        if (counter > 0)
        {
            if(row >= 0) {
                if (!list.isEmpty())
                {
                    try{
                        String pattern = "MMMM dd, yyyy";
                        if (name.getText().matches("^([а-яА-Я])+(\s)*[а-яА-Я]*(\s)*[а-яА-Я]*$") && !name.getText().equals("")
                               && !customer.getText().equals("")
                               && !check_in_date.getValue().format(DateTimeFormatter.ofPattern(pattern)).equals("")
                               && !check_out_date.getValue().format(DateTimeFormatter.ofPattern(pattern)).equals("")
                                && Integer.parseInt(price.getText()) > 0 && price.getText().matches("^[0-9]*$")) {
                            Tour book = new Tour(name.getText(), customer.getText(), check_in_date.getValue().toString(),
                                    check_out_date.getValue().toString(), Integer.parseInt(price.getText()));
                            arrayList.remove(row);
                            list.remove(row);
                            arrayList.add(row, book);
                            list.add(row, book);
                            sort();
                            counter = 0;
                            addLine.setDisable(false);
                            delete.setDisable(false);
                            search.setDisable(false);
                            freeInput();
                            worker.writeObject("vicusa.txt", arrayList);
                            return;
                        }
                        else {
                            wrongInputForEdit("Некорректный ввод", "Введите корректные данные\n Текст на русском\n Число положительное\n Год издания 1800-2022");
                        }
                    }catch (IllegalStateException | NumberFormatException exc){
                        wrongInputForEdit("Некорректный ввод", "Введите корректные данные\n Текст на русском\n Число положительное\n Год издания 1800-2022");
                    }
                }
                else {
                    wrongInputForEdit("Редактирование", "Список пуст");
                }
            }
            else{
                wrongInputForEdit("Редактирование", "Не выбран ряд редактирования");
            }
        }
        counter++;
    }
    @FXML //Метод поиска по автору
    protected void search(){
        if (!find.getText().equals("")){
            table.setItems(list);
            ObservableList<Tour> names=FXCollections.observableArrayList();
            if(!list.isEmpty()){
                for(int i=0;i<list.size();i++){
                    if (colCustomer.getCellData(i).equals(find.getText())) {
                        names.add(list.get(i));
                    }}}
            else {
                wrongInput("Поиск", "Список пуст");
                find.setText("");
            }
            if (!names.isEmpty()) {
                table.setItems(names);
                back.setDisable(false);
            }
            else {
                wrongInput("Поиск", "Такого покупателя не найдено");
                find.setText("");
            }
        }
        else {
            wrongInput("Поиск", "Некорректный ввод данных");
            find.setText("");
        }
    }
    @FXML //Метод осуществления возвращения к первоначальной таблице
    protected void back(){
        table.setItems(list);
        find.setText(null);
        back.setDisable(true);
    }


    public void userLogOut(ActionEvent event) throws IOException {
        Application m = new Application();
        m.changeScene("sample.fxml");

    }
}
