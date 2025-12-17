package ui;

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

import java.util.List;
import java.util.Optional;

public class AdminMenu {
    
    private AdminServices adminServices;
    private Stage stage;
    private TableView<User> employeeTable;
    
    public AdminMenu() {
        this.adminServices = new AdminServices();
        this.stage = new Stage();
    }
    
    public void show() {
        stage.setTitle("Admin Dashboard");
        
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(15));
        
        // Top - Title
        Label titleLabel = new Label("Employee Management System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 15, 0));
        
        // Center - Table
        employeeTable = createEmployeeTable();
        VBox tableBox = new VBox(10);
        tableBox.getChildren().add(employeeTable);
        VBox.setVgrow(employeeTable, Priority.ALWAYS);
        
        // Bottom - Buttons
        HBox buttonBox = createButtonBox();
        
        mainLayout.setTop(titleBox);
        mainLayout.setCenter(tableBox);
        mainLayout.setBottom(buttonBox);
        
        Scene scene = new Scene(mainLayout, 900, 600);
        stage.setScene(scene);
        
        // Load initial data
        refreshTable();
        
        stage.show();
    }
    
    private TableView<User> createEmployeeTable() {
        TableView<User> table = new TableView<>();
        
        TableColumn<User, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(150);
        
        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        
        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(250);
        
        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        roleCol.setPrefWidth(150);
        
        TableColumn<User, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordCol.setPrefWidth(150);
        
        table.getColumns().addAll(idCol, nameCol, emailCol, roleCol, passwordCol);
        
        return table;
    }
    
    private HBox createButtonBox() {
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addBtn = new Button("Add Employee");
        addBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        addBtn.setPrefWidth(130);
        addBtn.setOnAction(e -> showAddEmployeeDialog());
        
        Button updateBtn = new Button("Update Employee");
        updateBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px;");
        updateBtn.setPrefWidth(130);
        updateBtn.setOnAction(e -> showUpdateEmployeeDialog());
        
        Button deleteBtn = new Button("Delete Employee");
        deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");
        deleteBtn.setPrefWidth(130);
        deleteBtn.setOnAction(e -> deleteSelectedEmployee());
        
        Button searchBtn = new Button("Search Employee");
        searchBtn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-size: 14px;");
        searchBtn.setPrefWidth(130);
        searchBtn.setOnAction(e -> showSearchDialog());
        
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-size: 14px;");
        refreshBtn.setPrefWidth(130);
        refreshBtn.setOnAction(e -> refreshTable());
        
        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, searchBtn, refreshBtn);
        
        return buttonBox;
    }
    
    private void showAddEmployeeDialog() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Add New Employee");
        dialog.setHeaderText("Enter employee details:");
        
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        ComboBox<UserRole> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll(UserRole.values());
        roleCombo.setPromptText("Select Role");
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Role:"), 0, 2);
        grid.add(roleCombo, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                UserRole role = roleCombo.getValue();
                
                if (name.isEmpty() || email.isEmpty() || role == null) {
                    showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
                    return null;
                }
                
                User newUser = adminServices.addEmployee(name, email, role);
                showAlert("Success", "Employee added successfully!\nID: " + newUser.getId() + "\nPassword: " + newUser.getPassword(), Alert.AlertType.INFORMATION);
                refreshTable();
                return newUser;
            }
            return null;
        });
        
        dialog.showAndWait();
    }
    
    private void showUpdateEmployeeDialog() {
        User selectedUser = employeeTable.getSelectionModel().getSelectedItem();
        
        if (selectedUser == null) {
            showAlert("Error", "Please select an employee to update!", Alert.AlertType.ERROR);
            return;
        }
        
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Update Employee");
        dialog.setHeaderText("Update employee details:");
        
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField nameField = new TextField(selectedUser.getName());
        TextField emailField = new TextField(selectedUser.getEmail());
        TextField passwordField = new TextField(selectedUser.getPassword());
        ComboBox<UserRole> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll(UserRole.values());
        roleCombo.setValue(selectedUser.getUserRole());
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(emailField, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(new Label("Role:"), 0, 3);
        grid.add(roleCombo, 1, 3);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password = passwordField.getText().trim();
                UserRole role = roleCombo.getValue();
                
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
                    showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
                    return null;
                }
                
                selectedUser.setName(name);
                selectedUser.setEmail(email);
                selectedUser.setPassword(password);
                selectedUser.setUserRole(role);
                
                adminServices.updateEmployee(selectedUser);
                showAlert("Success", "Employee updated successfully!", Alert.AlertType.INFORMATION);
                refreshTable();
                return selectedUser;
            }
            return null;
        });
        
        dialog.showAndWait();
    }
    
    private void deleteSelectedEmployee() {
        User selectedUser = employeeTable.getSelectionModel().getSelectedItem();
        
        if (selectedUser == null) {
            showAlert("Error", "Please select an employee to delete!", Alert.AlertType.ERROR);
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete Employee");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedUser.getName() + "?");
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            adminServices.deleteEmployee(selectedUser.getId());
            showAlert("Success", "Employee deleted successfully!", Alert.AlertType.INFORMATION);
            refreshTable();
        }
    }
    
    private void showSearchDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Employee");
        dialog.setHeaderText("Search for an employee");
        dialog.setContentText("Enter name, email, or ID:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(searchTerm -> {
            if (!searchTerm.trim().isEmpty()) {
                User found = adminServices.searchEmployee(searchTerm.trim());
                if (found != null) {
                    employeeTable.getItems().clear();
                    employeeTable.getItems().add(found);
                    employeeTable.getSelectionModel().select(found);
                    showAlert("Success", "Employee found!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Not Found", "No employee found with that search term.", Alert.AlertType.WARNING);
                    refreshTable();
                }
            }
        });
    }
    
    private void refreshTable() {
        List<User> employees = adminServices.listAllEmployees();
        employeeTable.getItems().clear();
        employeeTable.getItems().addAll(employees);
    }
    
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}