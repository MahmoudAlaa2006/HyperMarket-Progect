package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.User;
import models.UserRole;
import services.AdminServices;

public class AdminMenu extends Application {
    
    private AdminServices adminServices;
    private TableView<User> employeeTable;
    private TextField nameField, emailField, searchField;
    private ComboBox<UserRole> roleComboBox;
    private TextArea outputArea;
    
    @Override
    public void start(Stage primaryStage) {
        adminServices = new AdminServices();
        
        primaryStage.setTitle("Admin Menu - Employee Management");
        
        // Main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        
        // Top - Search section
        HBox searchBox = createSearchSection();
        mainLayout.setTop(searchBox);
        
        // Center - Employee table
        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(10, 0, 10, 0));
        
        Label tableLabel = new Label("Employee List");
        tableLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        employeeTable = createEmployeeTable();
        
        centerBox.getChildren().addAll(tableLabel, employeeTable);
        mainLayout.setCenter(centerBox);
        
        // Right - Add/Update form
        VBox formBox = createFormSection();
        mainLayout.setRight(formBox);
        
        // Bottom - Output area
        VBox bottomBox = new VBox(5);
        bottomBox.setPadding(new Insets(10, 0, 0, 0));
        
        Label outputLabel = new Label("Output:");
        outputLabel.setStyle("-fx-font-weight: bold;");
        
        outputArea = new TextArea();
        outputArea.setPrefHeight(100);
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        
        bottomBox.getChildren().addAll(outputLabel, outputArea);
        mainLayout.setBottom(bottomBox);
        
        // Load initial data
        refreshTable();
        
        Scene scene = new Scene(mainLayout, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private HBox createSearchSection() {
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");
        
        Label searchLabel = new Label("Search:");
        searchField = new TextField();
        searchField.setPromptText("Enter name, email, or ID");
        searchField.setPrefWidth(250);
        
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchEmployee());
        
        Button refreshButton = new Button("Refresh All");
        refreshButton.setOnAction(e -> refreshTable());
        
        searchBox.getChildren().addAll(searchLabel, searchField, searchButton, refreshButton);
        
        return searchBox;
    }
    
    private TableView<User> createEmployeeTable() {
        TableView<User> table = new TableView<>();
        table.setPrefHeight(400);
        
        TableColumn<User, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(150);
        
        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);
        
        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(200);
        
        TableColumn<User, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordCol.setPrefWidth(120);
        
        TableColumn<User, UserRole> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        roleCol.setPrefWidth(100);
        
        table.getColumns().addAll(idCol, nameCol, emailCol, passwordCol, roleCol);
        
        // Click to load employee into form
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                User selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    loadEmployeeToForm(selected);
                }
            }
        });
        
        return table;
    }
    
    private VBox createFormSection() {
        VBox formBox = new VBox(10);
        formBox.setPadding(new Insets(10));
        formBox.setPrefWidth(300);
        formBox.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 5;");
        
        Label formLabel = new Label("Add / Update Employee");
        formLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Name field
        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        nameField.setPromptText("Enter full name");
        
        // Email field
        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("Enter email");
        
        // Role combo box
        Label roleLabel = new Label("Role:");
        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll(UserRole.values());
        roleComboBox.setValue(UserRole.SELLER);
        roleComboBox.setPrefWidth(Double.MAX_VALUE);
        
        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addButton = new Button("Add Employee");
        addButton.setPrefWidth(130);
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addButton.setOnAction(e -> addEmployee());
        
        Button updateButton = new Button("Update");
        updateButton.setPrefWidth(130);
        updateButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        updateButton.setOnAction(e -> updateEmployee());
        
        buttonBox.getChildren().addAll(addButton, updateButton);
        
        Button deleteButton = new Button("Delete Selected");
        deleteButton.setPrefWidth(270);
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> deleteEmployee());
        
        Button clearButton = new Button("Clear Form");
        clearButton.setPrefWidth(270);
        clearButton.setOnAction(e -> clearForm());
        
        Separator separator = new Separator();
        
        formBox.getChildren().addAll(
            formLabel,
            new Separator(),
            nameLabel, nameField,
            emailLabel, emailField,
            roleLabel, roleComboBox,
            separator,
            buttonBox,
            deleteButton,
            clearButton
        );
        
        return formBox;
    }
    
    private void addEmployee() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        UserRole role = roleComboBox.getValue();
        
        if (name.isEmpty() || email.isEmpty()) {
            showOutput("Error: Name and Email are required!", true);
            return;
        }
        
        try {
            User newEmployee = adminServices.addEmployee(name, email, role);
            showOutput("Employee added successfully!\nID: " + newEmployee.getId() + 
                      "\nPassword: " + newEmployee.getPassword(), false);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            showOutput("Error adding employee: " + e.getMessage(), true);
        }
    }
    
    private void updateEmployee() {
        User selectedUser = employeeTable.getSelectionModel().getSelectedItem();
        
        if (selectedUser == null) {
            showOutput("Error: Please select an employee from the table to update!", true);
            return;
        }
        
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        UserRole role = roleComboBox.getValue();
        
        if (name.isEmpty() || email.isEmpty()) {
            showOutput("Error: Name and Email are required!", true);
            return;
        }
        
        try {
            selectedUser.setName(name);
            selectedUser.setEmail(email);
            selectedUser.setUserRole(role);
            
            adminServices.updateEmployee(selectedUser);
            showOutput("Employee updated successfully!\nID: " + selectedUser.getId(), false);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            showOutput("Error updating employee: " + e.getMessage(), true);
        }
    }
    
    private void deleteEmployee() {
        User selectedUser = employeeTable.getSelectionModel().getSelectedItem();
        
        if (selectedUser == null) {
            showOutput("Error: Please select an employee from the table to delete!", true);
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Employee");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedUser.getName() + "?");
        
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    adminServices.deleteEmployee(selectedUser.getId());
                    showOutput("Employee deleted successfully: " + selectedUser.getName(), false);
                    refreshTable();
                    clearForm();
                } catch (Exception e) {
                    showOutput("Error deleting employee: " + e.getMessage(), true);
                }
            }
        });
    }
    
    private void searchEmployee() {
        String searchTerm = searchField.getText().trim();
        
        if (searchTerm.isEmpty()) {
            showOutput("Error: Please enter a search term!", true);
            return;
        }
        
        try {
            User found = adminServices.searchEmployee(searchTerm);
            
            if (found != null) {
                employeeTable.getItems().clear();
                employeeTable.getItems().add(found);
                employeeTable.getSelectionModel().select(found);
                showOutput("Employee found: " + found.getName() + " (" + found.getEmail() + ")", false);
            } else {
                showOutput("No employee found with search term: " + searchTerm, true);
                refreshTable();
            }
        } catch (Exception e) {
            showOutput("Error searching: " + e.getMessage(), true);
        }
    }
    
    private void refreshTable() {
        try {
            employeeTable.getItems().clear();
            employeeTable.getItems().addAll(adminServices.listAllEmployees());
            showOutput("Table refreshed. Total employees: " + employeeTable.getItems().size(), false);
        } catch (Exception e) {
            showOutput("Error refreshing table: " + e.getMessage(), true);
        }
    }
    
    private void loadEmployeeToForm(User user) {
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        roleComboBox.setValue(user.getUserRole());
        showOutput("Loaded employee to form: " + user.getName() + "\nDouble-click to edit.", false);
    }
    
    private void clearForm() {
        nameField.clear();
        emailField.clear();
        roleComboBox.setValue(UserRole.SELLER);
        employeeTable.getSelectionModel().clearSelection();
    }
    
    private void showOutput(String message, boolean isError) {
        String prefix = isError ? "[ERROR] " : "[INFO] ";
        outputArea.setText(prefix + message);
        outputArea.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}