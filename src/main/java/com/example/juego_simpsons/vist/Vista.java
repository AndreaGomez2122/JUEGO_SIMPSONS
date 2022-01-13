package com.example.juego_simpsons.vist;

import com.example.juego_simpsons.controller.JuegoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.io.FileNotFoundException;

public class Vista extends BorderPane {

    //DECLARAR
    private StackPane panelPrincipal;
    private Image hom;
    private ImageView hommer;
    private Image ros;
    private ImageView rosquilla;
    private Label puntuacion;
    private Rectangle paredIzquierda;
    private Rectangle paredDerecha;
    private Rectangle paredSuperior;
    private Rectangle paredInferior;
    JuegoController controlador;

    public Vista() throws FileNotFoundException {
        iniciarElementoss();

    }

    private void iniciarElementoss() throws FileNotFoundException {
        panelPrincipal = new StackPane();
        Image im = new Image(getClass().getResource("/imagenes/fondo.jpg").toString());
        panelPrincipal.setBackground(new Background(new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        hom = new Image(getClass().getResource("/imagenes/hommer.png").toString());
        hommer = new ImageView(hom);
        hommer.setFitHeight(150);
        hommer.setFitWidth(100);
        ros= new Image(getClass().getResource("/imagenes/rosquilla.png").toString());
        rosquilla= new ImageView(ros);
        rosquilla.setFitHeight(40);
        rosquilla.setFitWidth(40);
        puntuacion = new Label();


        paredIzquierda = new Rectangle();
        paredDerecha = new Rectangle();
        paredSuperior = new Rectangle();
        paredInferior = new Rectangle();


        panelPrincipal.setMinSize(0, 0);
        paredIzquierda.setFill(Color.rgb(255,195,0));
        paredIzquierda.heightProperty().bind(panelPrincipal.heightProperty());
        paredIzquierda.widthProperty().bind(panelPrincipal.widthProperty().divide(50));

        paredDerecha.setFill(Color.rgb(255,195,0));
        paredDerecha.heightProperty().bind(panelPrincipal.heightProperty());
        paredDerecha.widthProperty().bind(panelPrincipal.widthProperty().divide(50));

        paredSuperior.setFill(Color.rgb(255,195,0));
        paredSuperior.heightProperty().bind(panelPrincipal.heightProperty().divide(40));
        paredSuperior.widthProperty().bind(panelPrincipal.widthProperty());

        paredInferior.setFill(Color.rgb(255,195,0));
        paredInferior.heightProperty().bind(panelPrincipal.heightProperty().divide(40));
        paredInferior.widthProperty().bind(panelPrincipal.widthProperty());


        puntuacion.setMinWidth(50);
        puntuacion.setMinHeight(30);

        puntuacion.setBackground(
                new Background(new BackgroundFill(Color.rgb(231,109,188), new CornerRadii(3), new Insets(0))));
        ((Label) puntuacion).setStyle("-fx-border-color: #7ffde9;");
        ((Label) puntuacion).setMinSize(400, 35);
        ((Label) puntuacion).setTextAlignment(TextAlignment.CENTER);
        ((Label) puntuacion).setFont(Font.font(20));


        panelPrincipal.getChildren().addAll(paredIzquierda, paredDerecha, paredInferior, paredSuperior,rosquilla,  hommer, puntuacion);


        panelPrincipal.setAlignment(paredIzquierda, Pos.CENTER_LEFT);
        panelPrincipal.setAlignment(paredDerecha, Pos.CENTER_RIGHT);
        panelPrincipal.setAlignment(paredSuperior, Pos.TOP_CENTER);
        panelPrincipal.setAlignment(paredInferior, Pos.BOTTOM_CENTER);
        panelPrincipal.setAlignment(rosquilla, Pos.CENTER);
        panelPrincipal.setAlignment(puntuacion, Pos.BOTTOM_RIGHT);

        this.setCenter(panelPrincipal);

        controlador = new JuegoController(paredIzquierda, paredDerecha, paredSuperior, paredInferior, hommer,rosquilla, panelPrincipal, puntuacion);
    }

}
