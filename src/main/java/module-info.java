module com.example.trabajofinalprogramacion2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trabajofinalprogramacion2 to javafx.fxml;
    exports com.example.trabajofinalprogramacion2;
    exports com.example.trabajofinalprogramacion2.entidades.usuarios;
    opens com.example.trabajofinalprogramacion2.entidades.usuarios to javafx.fxml;
    exports com.example.trabajofinalprogramacion2.entidades;
    opens com.example.trabajofinalprogramacion2.entidades to javafx.fxml;
    exports com.example.trabajofinalprogramacion2.entidades.membresias;
    opens com.example.trabajofinalprogramacion2.entidades.membresias to javafx.fxml;
    exports com.example.trabajofinalprogramacion2.entidades.gastos;
    opens com.example.trabajofinalprogramacion2.entidades.gastos to javafx.fxml;
    exports com.example.trabajofinalprogramacion2.entidades.gimnasio;
    opens com.example.trabajofinalprogramacion2.entidades.gimnasio to javafx.fxml;
}