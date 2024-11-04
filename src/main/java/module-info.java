module com.example.ejerciciojavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ejerciciojavafx to javafx.fxml;
    exports com.example.ejerciciojavafx;
}