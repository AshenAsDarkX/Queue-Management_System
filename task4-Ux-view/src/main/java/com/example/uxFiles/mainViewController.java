package com.example.uxFiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

//
public class mainViewController {
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<Customer, String> firstNameCol;

    @FXML
    private TableColumn<Customer, String> lastNameCol;

    @FXML
    private TableColumn<Customer, Integer> burgerCol;

    @FXML
    private TableColumn<Customer, String> queueCol;

    @FXML
    private TableView<Customer> tableView;


    @FXML
    public void initialize() {

        // Set up table columns and their cell values
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
        burgerCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("reqBurgers"));
        queueCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("queueName"));

        // Create an observable list to hold the data
        ObservableList<Customer> data = FXCollections.observableArrayList();

        // Read data from "cashiers.txt"
        String fileName = "cashiers.txt";
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<FoodQueue> cashiers = (ArrayList<FoodQueue>) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            // Iterate through the cashiers for table
            for (int i = 0; i < cashiers.size(); i++) {
                for (int j = 0; j < cashiers.get(i).getQueueLength(); j++) {
                    Customer cus = cashiers.get(i).getCus(j);
                    cus.setQueueName("Queue " + (i + 1));
                    data.add(cus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Read data from "waiting.txt"
        fileName = "waiting.txt";
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ArrayList<Customer> waitingList = (ArrayList<Customer>) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            // Add customers from the waiting list to the TableView
            for (Customer cus :
                    waitingList) {
                cus.setQueueName("Waiting List");
                data.add(cus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a filtered list for searching
        FilteredList<Customer> filteredData = new FilteredList<>(data, b -> true);

        // Set up the search functionality using the text field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (Customer.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (Customer.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            });
        });

        // Create a sorted list to display the filtered data
        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        // Bind the comparator of the sorted data to the TableView's comparator
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Set the sorted and filtered data to the TableView
        tableView.setItems(sortedData);
    }

}
