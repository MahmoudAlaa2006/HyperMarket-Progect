package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import models.UserRole;
import services.authServices;
public class LoginMenu extends Application {
    private authServices authServices = new authServices();
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management System");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);
        Label userNameLabel = new Label("Email:");
        grid.add(userNameLabel, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        Label pwLabel = new Label("Password:");
        grid.add(pwLabel, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        Button btn = new Button("Sign in");
        GridPane.setConstraints(btn, 1, 3);
        grid.getChildren().add(btn);
        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 4);
        btn.setOnAction(e -> {
            String email = userTextField.getText();
            String password = pwBox.getText();
            User user = authServices.login(email, password);
            if (user != null) {
                actionTarget.setFill(Color.GREEN);
                actionTarget.setText("Login Successful!");
                showDashboard(primaryStage, user);
            } else {
                actionTarget.setFill(Color.FIREBRICK);
                actionTarget.setText("Invalid credentials.");
            }
        });
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void showDashboard(Stage stage, User user) {
        VBox dashboardLayout = new VBox(20);
        dashboardLayout.setAlignment(Pos.CENTER);
        dashboardLayout.setPadding(new Insets(20));
        String roleTitle = "";
        if (user.getRole() == UserRole.ADMIN) roleTitle = "ADMINISTRATOR DASHBOARD";
        else if (user.getRole() == UserRole.INVENTORY_EMPLOYEE) roleTitle = "INVENTORY MANAGEMENT";
        else if (user.getRole() == UserRole.MARKETING_EMPLOYEE) roleTitle = "MARKETING REPORTS";
        else roleTitle="SALES MANAGEMENT";
        Label titleLabel = new Label(roleTitle);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        Label welcomeLabel = new Label("Welcome, " + user.getName());
        Label idLabel = new Label("ID: " + user.getId());
        Label lastLoginLabel = new Label("Last Login: " + user.getLastLogin());
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> {
            authServices.logout(user);
            start(stage);
        });
        dashboardLayout.getChildren().addAll(titleLabel, welcomeLabel, idLabel, lastLoginLabel, logoutBtn);
        Scene dashboardScene = new Scene(dashboardLayout, 500, 400);
        stage.setScene(dashboardScene);
    }
    public static void launchApp(String[] args) {
        launch(args);
    }
}
