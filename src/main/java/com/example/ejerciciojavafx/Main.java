package com.example.ejerciciojavafx;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    private final ObservableList<User> userData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Administrador usuarios");

        TableView<User> table = new TableView<>();
        table.setEditable(true);

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

        TableColumn<User, String> plataformaCol = new TableColumn<>("Plataforma");
        plataformaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlataforma()));

        TableColumn<User, String> administradorCol = new TableColumn<>("Administrador");
        administradorCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isAdministrador() ? "Si" : "No"));

        TableColumn<User, String> versionCol = new TableColumn<>("Versión Software");
        versionCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getVersion())));

        TableColumn<User, String> horaCol = new TableColumn<>("Hora");
        horaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHoraAt()));

        table.getColumns().addAll(emailCol, plataformaCol, administradorCol, versionCol, horaCol);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField plataformaField = new TextField();
        plataformaField.setPromptText("Plataforma");

        CheckBox administradorCheckBox = new CheckBox("Administrador");

        Spinner<Integer> versionSpinner = new Spinner<>(1, 5, 1);

        Button addButton = new Button("Añadir");
        addButton.setOnAction(e -> {
            String email = emailField.getText();
            String plataforma = plataformaField.getText();

            if (email.isEmpty() || plataforma.isEmpty() || !email.contains("@")) {
                showAlert("Faltan datos", "Por favor, complete todos los campos del formulario.");
                return;
            }

            boolean isAdmin = administradorCheckBox.isSelected();
            int version = versionSpinner.getValue();
            String horaAt = java.time.LocalDateTime.now().toString();

            User newUser = new User(email, plataforma, isAdmin, version, horaAt);
            userData.add(newUser);

            emailField.clear();
            plataformaField.clear();
            administradorCheckBox.setSelected(false);
            versionSpinner.getValueFactory().setValue(1);
        });

        Button clearButton = new Button("Limpiar");
        clearButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Limpiar tabla");
            alert.setContentText("¿Está seguro de que desea eliminar todos los usuarios?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                userData.clear();
            }
        });

        table.setItems(userData);

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setVgap(10);
        formPane.setHgap(10);
        formPane.add(new Label("Email:"), 0, 0);
        formPane.add(emailField, 1, 0);
        formPane.add(new Label("Plataforma:"), 0, 1);
        formPane.add(plataformaField, 1, 1);
        formPane.add(new Label("Administrador:"), 0, 2);
        formPane.add(administradorCheckBox, 1, 2);
        formPane.add(new Label("Versión Software:"), 0, 3);
        formPane.add(versionSpinner, 1, 3);
        formPane.add(addButton, 1, 4);
        formPane.add(clearButton, 1, 5);

        HBox root = new HBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(table, formPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class User {
        private final String email;
        private final String plataforma;
        private final boolean isAdministrador;
        private final int version;
        private final String hora;

        public User(String email, String plataforma, boolean isAdministrador, int version, String horaAt) {
            this.email = email;
            this.plataforma = plataforma;
            this.isAdministrador = isAdministrador;
            this.version = version;
            this.hora = horaAt;
        }

        public String getEmail() {
            return email;
        }

        public String getPlataforma() {
            return plataforma;
        }

        public boolean isAdministrador() {
            return isAdministrador;
        }

        public int getVersion() {
            return version;
        }

        public String getHoraAt() {
            return hora;
        }
    }
}
