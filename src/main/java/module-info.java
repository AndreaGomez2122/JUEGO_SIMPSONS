module com.example.juego_simpsons {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.juego_simpsons to javafx.fxml;
    exports com.example.juego_simpsons;
}