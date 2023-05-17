package com.example.demoproyecto2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;


public class ClienteApp extends Application {
    Button Ingresar1 = new Button(" Ingresar ");
    Button Buscar = new Button(" Buscar ");
    Button Agregar = new Button(" Agregar ");
    Button Pedir = new Button("Pedir");
    Button Menu = new Button(" Menu ");
    Button Historial = new Button("Pedidos");
    CheckBox EnPreparacion = new CheckBox("En preparacion");
    TextField BuscarPlato = new TextField();
    TextField IDusuario = new TextField();
    TextField NOMBREsuario = new TextField();
    PasswordField PWusuario = new PasswordField();
    Label info_historial = new Label();
    Label info_platillo = new Label();
    Label menu = new Label();
    //////////////////////////////////////////
    Pane root1 = new Pane();
    // Crear la escena para la ventana 1
    Scene scene1 = new Scene(root1, 700, 720);
    // Crear una nueva instancia de Stage
    Stage ventana1 = new Stage();
    //////////////////////////////////////////
    Pane root4 = new Pane();
    // Crear la escena para la ventana 4
    Scene scene4 = new Scene(root4, 700, 720);
    // Crear una nueva instancia de Stage
    Stage ventana4 = new Stage();
    //////////////////////////////////////////
    Pane root5 = new Pane();
    // Crear la escena para la ventana 4
    Scene scene5 = new Scene(root5, 700, 720);
    // Crear una nueva instancia de Stage
    Stage ventana5 = new Stage();

    @Override
    public void start(Stage primarystage) throws IOException, InterruptedException {

        Ingresar1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(IDusuario.getText() + "");
                System.out.println(PWusuario.getText() + "");
                System.out.println("entra con exito");
                enviar_Texto();


                }
        });

        Menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Establecer la escena en la ventana 4
                ventana4.setScene(scene4);
                // Mostrar la ventana 4
                ventana4.show();
                ventana4.setResizable(false);
//                EnPreparacion.setLayoutX(250);
//                EnPreparacion.setLayoutY(140);
                menu.setLayoutX(200);
                menu.setLayoutY(150);
                menu.setPrefHeight(300);
                menu.setPrefWidth(300);
                menu.setText("A jose tambien le gustan los negros en la cola");
                root4.getChildren().addAll(menu);
//                ventana2.close();
            }
        });

        Historial.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // Establecer la escena en la ventana 5
                ventana5.setScene(scene5);
                // Mostrar la ventana 5
                ventana5.show();
                ventana5.setResizable(false);
                EnPreparacion.setLayoutX(250);
                EnPreparacion.setLayoutY(140);
                info_historial.setLayoutX(200);
                info_historial.setLayoutY(150);
                info_historial.setPrefHeight(300);
                info_historial.setPrefWidth(300);
                info_historial.setText("A jair le gustan las bellakas con un toto asi { (*) } ");
                root5.getChildren().addAll(info_historial, EnPreparacion);
//                ventana2.close();
            }
        });

        BuscarPlato.setPromptText(" Busque su plato ");
        BuscarPlato.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");
        IDusuario.setPromptText(" Ingrese su ID: ");
        IDusuario.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");
        PWusuario.setPromptText(" Ingrese su contraseña: ");
        PWusuario.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");

        IDusuario.setLayoutX(200);
        IDusuario.setLayoutY(170);
        PWusuario.setLayoutX(200);
        PWusuario.setLayoutY(200);
        Ingresar1.setLayoutX(200);
        Ingresar1.setLayoutY(230);

        root1.getChildren().addAll(IDusuario, PWusuario, NOMBREsuario,Ingresar1);
        // Establecer la escena en la ventana 1
        ventana1.setScene(scene1);
        // Mostrar la ventana 1
        ventana1.show();
        ventana1.setResizable(false);
    }


    private void enviar_Texto(){
    try {
        System.out.println("enviado con exito");
        PaqueteUsuario datos = new PaqueteUsuario();

        Socket misocket = new Socket("localhost", 9999);
        datos.setId(Integer.parseInt(IDusuario.getText()));
        datos.setPassword(PWusuario.getText());
        datos.setUsuario(NOMBREsuario.getText());
        System.out.println(IDusuario.getText());
        System.out.println(PWusuario.getText());
        datos.setAccion("login");
//            datos.setMensaje(campo1.getText());
        ObjectOutputStream salida = new ObjectOutputStream(misocket.getOutputStream());
        salida.writeObject(datos);
        ObjectInputStream entrada = new ObjectInputStream(misocket.getInputStream());
        String ans = (String) entrada.readObject();
        if (ans.equals("OK")){

                /*
                 Se comienza la interfaz más específica
                 */
            System.out.println("ingreso correctamente");
            Pane root = new Pane();
            // Crear la escena para la ventana 3
            Scene scene3 = new Scene(root, 700, 720);
            // Crear una nueva instancia de Stage
            Stage ventana3 = new Stage();
            // Establecer la escena en la ventana 3
            ventana3.setScene(scene3);

                    /*
                        elementos de interfaz del usuario
                    */

            //
            Buscar.setLayoutX(100);
            Buscar.setLayoutY(100);
            //
            BuscarPlato.setLayoutX(180);
            BuscarPlato.setLayoutY(100);
            BuscarPlato.setPrefWidth(250);
            //
            Agregar.setLayoutX(100);
            Agregar.setLayoutY(130);
            //
            Pedir.setLayoutX(100);
            Pedir.setLayoutY(160);
            //
            Menu.setLayoutX(100);
            Menu.setLayoutY(40);
            Menu.setPrefHeight(55);
            Menu.setPrefWidth(175);
            //
            Historial.setLayoutX(276);
            Historial.setLayoutY(40);
            Historial.setPrefHeight(55);
            Historial.setPrefWidth(175);
            //
            info_platillo.setLayoutX(180);
            info_platillo.setLayoutY(130);
            info_platillo.setText("A daniel le gustan los negros, en la cola");


            root.getChildren().addAll(Buscar, BuscarPlato, Agregar, info_platillo, Menu, Historial, Pedir);


            // Mostrar la ventana 3
            ventana3.show();
            ventana3.setResizable(false);
            ventana1.close();

        }
        misocket.close();
    } catch (IOException exc) {
        System.out.println(exc.getMessage());
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    }

        public static void main (String[]args) {
            launch();
        }

}
