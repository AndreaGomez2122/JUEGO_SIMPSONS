package com.example.juego_simpsons.controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;



import java.util.ArrayList;
import java.util.List;

public class JuegoController {
    private StackPane principal;

    private Rectangle paredIzquierda;
    private Rectangle paredDerecha;
    private Rectangle paredSuperior;
    private Rectangle paredInferior;

    private double desplazamientoX;
    private double desplazamientoY;
    private double bartY;

    private double velocidad;
    private ImageView hommer;
    private ImageView rosquilla;
    private Timeline animacion;
    List<ImageView> listaBarts;
    private Image b;
    private ImageView bart;

    private int vidas = 5;
    private int puntos = 0;
    private final int BARTS = 10;
    private boolean lanzado = false;
    private Label puntuacion;
    AudioClip yuju = new AudioClip(getClass().getResource("/sonidos/yuju.mp3").toString());
    AudioClip calla = new AudioClip(getClass().getResource("/sonidos/calla.mp3").toString());


    public JuegoController(Rectangle paredIzquierda, Rectangle paredDerecha, Rectangle paredSuperior, Rectangle paredInferior, ImageView hommer, ImageView rosquilla, StackPane principal, Label puntuacion) {
        this.principal = principal;
        this.paredIzquierda = paredIzquierda;
        this.paredDerecha = paredDerecha;
        this.paredSuperior = paredSuperior;
        this.paredInferior = paredInferior;
        this.hommer = hommer;
        this.rosquilla = rosquilla;
        this.puntuacion = puntuacion;
        this.velocidad = 2.5;
        this.bartY = 1;
        puntuacion.setText("PUNTOS: 0 " + " VIDAS: 3 ");
        crearBarts();
        inicializarController();
        inicializarJuego();
    }

    private void inicializarController() {
        principal.setOnKeyPressed(e -> {
            animacion.play();

            switch (e.getCode()) {
                case A:  //x-1 izquierda
                    desplazamientoX = -1 * velocidad;
                    break;
                case W: //Y-1 arriba
                    desplazamientoY = -1 * velocidad;
                    break;
                case S: //Y+1 abajo
                    desplazamientoY = 1 * velocidad;
                    break;
                case D: //x+1 derecha
                    desplazamientoX = 1 * velocidad;
                    break;
                case RIGHT: //x+1 derecha
                    desplazamientoX = 1 * velocidad;
                    break;
                case LEFT: //x+1 derecha
                    desplazamientoX = -1 * velocidad;
                    break;
                case UP: //Y-1 arriba
                    desplazamientoY = -1 * velocidad;
                    break;
                case DOWN: //Y+1 abajo
                    desplazamientoY = 1 * velocidad;
                    break;
                case SPACE: //x+1 derecha
                    lanzarDonut();
                    break;
            }
        });
        principal.setOnKeyReleased(e -> {

            switch (e.getCode()) {
                case A:  //x-1 izquierda
                    desplazamientoX = 0;
                    break;
                case W: //Y-1 arriba
                    desplazamientoY = 0;
                    break;
                case S: //Y+1 abajo
                    desplazamientoY = 0;
                    break;
                case D: //x+1 derecha
                    desplazamientoX = 0;
                    break;
                case LEFT:  //x-1 izquierda
                    desplazamientoX = 0;
                    break;
                case RIGHT: //x+1 derecha
                    desplazamientoX = 0;
                    break;
                case UP: //Y-1 arriba
                    desplazamientoY = 0;
                    break;
                case DOWN: //Y+1 abajo
                    desplazamientoY = 0;
                    break;

            }
        });

        principal.setFocusTraversable(true);
    }

    private void moverPersonaje() {
        if (desplazamientoX != 0 || desplazamientoY != 0) {
            hommer.setTranslateX(hommer.getTranslateX() + desplazamientoX * velocidad);
            hommer.setTranslateY(hommer.getTranslateY() + desplazamientoY * velocidad);
        }
    }

    private void inicializarJuego() {
        this.animacion = new Timeline(new KeyFrame(Duration.millis(17), t -> {

            detectarColision();
            moverPersonaje();
            if (lanzado) {
                moverDonut();
            }
            moverBart();
        }));
        animacion.setCycleCount(Animation.INDEFINITE);

    }

    private void detectarColision() {
        //HOMMER CONTRA PAREDES

        if (hommer.getBoundsInParent().intersects(paredDerecha.getBoundsInParent())) {
            desplazamientoX = -1;
        }
        if (hommer.getBoundsInParent().intersects(paredIzquierda.getBoundsInParent())) {
            desplazamientoX = 1;
        }
        if (hommer.getBoundsInParent().intersects(paredInferior.getBoundsInParent())) {
            desplazamientoY = -1;
        }
        if (hommer.getBoundsInParent().intersects(paredSuperior.getBoundsInParent())) {
            desplazamientoY = 1;
        }

        //COLISIÓN ENTRE DONNUT Y BART

        listaBarts.forEach(bart -> {
            if (rosquilla.getBoundsInParent().intersects(bart.getBoundsInParent())) {
                if (puntos >= BARTS * 100) {
                    yuju.play();
                    principal.getChildren().removeAll(bart, hommer, rosquilla);
                    Image im = new Image(getClass().getResource("/imagenes/homer-yuju.jpg").toString());
                    principal.setBackground(new Background(new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                    animacion.stop();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("HAS GANADO!");
                    alert.setHeaderText("FELICIDADES!");
                    alert.setContentText("HAS PODIDO CON BART!!");

                    alert.show();
                } else {

                    bart.setTranslateY((int) Math.floor(Math.random() * 210 - 210));
                    bart.setTranslateX((int) Math.floor(Math.random() * 210 - 210));
                    morir(bart);
                    listaBarts.remove(bart);
                    puntos = puntos + 100;
                    puntuacion.setText("PUNTOS:  " + puntos + " VIDAS: " + vidas);
                    principal.getChildren().remove(bart);
                }
            }
        });
        listaBarts.forEach(bart -> {
            if (hommer.getBoundsInParent().intersects(bart.getBoundsInParent())) {
                if (vidas < 1) {
                    principal.getChildren().removeAll(bart, hommer, rosquilla);
                    calla.play();
                    Image im = new Image(getClass().getResource("/imagenes/homer-ouch.jpg").toString());
                    principal.setBackground(new Background(new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                    animacion.stop();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("HAS PERDIDO!");
                    alert.setHeaderText("OOOH!");
                    alert.setContentText("BART HA PODIDO CONTIGO!!");
                    alert.show();
                }else {
                    bart.setTranslateY((int) Math.floor(Math.random() * 210 - 210));
                    bart.setTranslateX((int) Math.floor(Math.random() * 210 - 210));
                    vidas = vidas - 1;
                    puntuacion.setText("PUNTOS:  " + puntos + " VIDAS: " + vidas);
                    herir(hommer);
                }

            }
        });
    }
    //DONUTS

    private void lanzarDonut() {

        rosquilla.setTranslateY(hommer.getTranslateY() - 10);
        rosquilla.setTranslateX(hommer.getTranslateX());
        lanzado = true;

    }
    private void moverDonut() {
        rosquilla.setTranslateX(rosquilla.getTranslateX());
        rosquilla.setTranslateY(rosquilla.getTranslateY() - 30);
    }
    //BARTS

    private List<ImageView> crearBarts() {
        listaBarts = new ArrayList<>();
        int contador = 0;
        while (contador <= BARTS) {
            b = new Image(getClass().getResource("/imagenes/bart.png").toString());
            bart = new ImageView(b);
            bart.setFitHeight(80);
            bart.setFitWidth(60);
            bart.setTranslateY((int) Math.floor(Math.random() * (-500 - -900)) + -900);
            bart.setTranslateX((int) Math.floor(Math.random() * (500 - -500)) + -500);
            principal.getChildren().add(bart);
            listaBarts.add(bart);
            contador++;
        }
        return listaBarts;
    }

    private void moverBart() {
        listaBarts.forEach(bart -> {
            double velo = (int) Math.floor(Math.random() * (2.5 - 0.5)) + 0.5;
            bart.setTranslateY(bart.getTranslateY() + bartY * velo);
        });

    }

    private void morir(ImageView personaje) {
        FadeTransition parpadeo = new FadeTransition(Duration.millis(100), personaje);
        parpadeo.setFromValue(0.0);
        parpadeo.setToValue(1.0);
        parpadeo.setAutoReverse(true);
        parpadeo.setCycleCount(3);
        parpadeo.play();
        //principal.getChildren().remove(personaje);
    }

    private void herir(ImageView personaje) {
        FadeTransition parpadeo = new FadeTransition(Duration.millis(100), personaje);
        parpadeo.setFromValue(0.0);
        parpadeo.setToValue(1.0);
        parpadeo.setAutoReverse(true);
        parpadeo.setCycleCount(3);
        parpadeo.play();
    }


}



