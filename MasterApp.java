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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MasterApp extends Application{

    Button Ingresar2 = new Button(" Ingresar ");
    Button AgregarA = new Button(" Agregar Amdinistrador ");
    Button AgregarP = new Button(" Agregar Plato");
    Button EditarA = new Button(" Editar Amdinistrador");
    Button BorrarA = new Button(" Borrar Amdinistrador");
    Button EditarP = new Button(" Editar Plato");
    Button BorrarP = new Button(" Borrar Plato");
    Button VerPedidos = new Button(" Ver cola de pedidos ");
    TextField NOMBREadministrador = new TextField();
    TextField NombreP = new TextField();
    TextField CaloriasP = new TextField();
    TextField PrecioP = new TextField();
    TextField TiempoP = new TextField();

    TextField IDadministrador = new TextField();
    PasswordField PWadministrador = new PasswordField();
    Pane root2 = new Pane();
    // Crear la escena para la ventana 2
    Scene scene2 = new Scene(root2, 700, 720);
    // Crear una nueva instancia de Stage
    Stage ventana2 = new Stage();
    //////////////////////////////////////////
    public void start(Stage primarystage) throws IOException, InterruptedException {
        IDadministrador.setPromptText(" Ingrese su ID: ");
        IDadministrador.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");
        PWadministrador.setPromptText(" Ingrese su contraseña: ");
        PWadministrador.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");
        NombreP.setPromptText(" Ingrese nombre del plato: ");
        NombreP.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");
        PrecioP.setPromptText(" Ingrese precio del plato: ");
        PrecioP.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");
        CaloriasP.setPromptText(" Ingrese calorias del plato: ");
        CaloriasP.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");
        TiempoP.setPromptText(" Ingrese tiempo  del plato: ");
        TiempoP.setStyle("-fx-prompt-text-fill: gray; -fx-prompt-text-opacity: 0.5;");
        NOMBREadministrador.setLayoutX(0);
        NOMBREadministrador.setLayoutY(0);
        IDadministrador.setLayoutX(200);
        IDadministrador.setLayoutY(170);
        PWadministrador.setLayoutX(200);
        PWadministrador.setLayoutY(200);
        Ingresar2.setLayoutX(200);
        Ingresar2.setLayoutY(230);
        root2.getChildren().addAll(IDadministrador, PWadministrador, Ingresar2,NOMBREadministrador);
        // Establecer la escena en la ventana 2
        ventana2.setScene(scene2);
        // Mostrar la ventana 2
        ventana2.show();
        ventana2.setResizable(false);


        Ingresar2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(IDadministrador.getText()+"");
                System.out.println(PWadministrador.getText()+"");
//              System.out.println(IDusuario.getText() + "");
//                System.out.println(PWusuario.getText() + "");
                System.out.println("entra con exito");
                enviar_Texto();
            }});}

    private void enviar_Texto(){

        try {
            System.out.println("enviado con exito");
            PaqueteAdmi datos = new PaqueteAdmi();

            Socket misocket = new Socket("localhost", 9999);
            datos.setId(Integer.parseInt(IDadministrador.getText()));
            datos.setPassword(PWadministrador.getText());
            datos.setUsuario(NOMBREadministrador.getText());
            System.out.println(IDadministrador.getText());
            System.out.println(PWadministrador.getText());
            datos.setAccion("login");
//            datos.setMensaje(campo1.getText());
            ObjectOutputStream salida = new ObjectOutputStream(misocket.getOutputStream());
            salida.writeObject(datos);
            ObjectInputStream entrada = new ObjectInputStream(misocket.getInputStream());
            String ans = (String) entrada.readObject();
            if (ans.equals("OK")){
                System.out.println("ingreso correctamente");
                Pane root = new Pane();
                // Crear la escena para la ventana 3
                Scene scene3 = new Scene(root, 700, 720);
                // Crear una nueva instancia de Stage
                Stage ventana3 = new Stage();
                // Establecer la escena en la ventana 3
                ventana3.setScene(scene3);
                // Mostrar la ventana 3
                ventana3.show();
                ventana3.setResizable(false);
                ventana2.close();
                    /*
                        Se comienza la interfaz más específica
                     */
                /////////////////////////
                AgregarA.setLayoutX(100);
                AgregarA.setLayoutY(130);
                /////////////////////////
                EditarA.setLayoutX(100);
                EditarA.setLayoutY(160);
                /////////////////////////
                BorrarA.setLayoutX(100);
                BorrarA.setLayoutY(190);
                /////////////////////////
                AgregarP.setLayoutX(250);
                AgregarP.setLayoutY(130);
                /////////////////////////
                BorrarP.setLayoutX(250);
                BorrarP.setLayoutY(160);
                /////////////////////////
                EditarP.setLayoutX(250);
                EditarP.setLayoutY(190);
                /////////////////////////
                VerPedidos.setLayoutX(250);
                VerPedidos.setLayoutY(220);
                /////////////////////////
                PrecioP.setLayoutX(40);
                PrecioP.setLayoutY(250);
                /////////////////////////
                NombreP.setLayoutX(200);
                NombreP.setLayoutY(250);
                /////////////////////////
                TiempoP.setLayoutX(360);
                TiempoP.setLayoutY(250);
                /////////////////////////
                CaloriasP.setLayoutX(520);
                CaloriasP.setLayoutY(250);
                /////////////////////////
                root.getChildren().addAll(AgregarA,AgregarP,EditarA,BorrarA,EditarP,BorrarP,NombreP,CaloriasP,TiempoP,PrecioP,VerPedidos);
            }
            misocket.close();
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main (String[]args){
        launch();
    }

}
