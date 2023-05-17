package com.example.demoproyecto2;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Server {
    static ArrayList<Plato> platos = new ArrayList<>();
    public static ABBU usuarios = new ABBU();
    public static ABBA administradores = new ABBA();
    public static AVL platillos = new AVL();
    private static final int PUERTO = 9999;

    public static Cola cola = new Cola();

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException, IOException, ClassNotFoundException, TransformerException {
       escribirAdministradoresXML(new Administrador(13, "Jair", "jkjk"));
       escribirUsuariosXML(new Usuario(130, "Juan", "jkjk"));
        cargarUsuariosDesdeXML(usuarios);
        guardarArbolUEnXML("usuarios.xml");
        cargarAdmiDesdeXML(administradores);
        guardarArbolAEnXML("administradores.xml");
        leerUsuariosXML();
        leerAdministradoresXML();
        System.out.println(usuarios.buscar(130));
        System.out.println(administradores.buscar(13));

        addPlatosToJson(platos,"platos.json", new Plato("Olla de Carne", 300,25,1200,1));
        addPlatosToJson(platos,"platos.json", new Plato("Ensalada", 40,10,700,2));
        addPlatosToJson(platos,"platos.json", new Plato("Arroz con Pollo", 220,20,1000,3));
        readPlatosFromJson("platos.json");
        saveAVLToJson(platillos, "platos.json");
        cola.encolar(platos.get(0).getId());
        cola.encolar(platos.get(1).getId());
        cola.encolar(platos.get(2).getId());
        cola.imprimir();
        try {
            // Crear un servidor socket
            ServerSocket servidorSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado");

            while (true) {
                // Esperar a que un cliente se conecte
                Socket clienteSocket = servidorSocket.accept();
                // System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());

                // Crear flujos de entrada/salida para comunicación con el cliente
                ObjectOutputStream salida = new ObjectOutputStream(clienteSocket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(clienteSocket.getInputStream());

                // Leer objeto Mensaje del cliente]
                //Mensaje mensaje = (Mensaje) entrada.readObject();
                // Leer objeto de entrada del cliente
                Object entradaObj = entrada.readObject();

// Verificar si el objeto es del tipo Mensaje
                if (entradaObj instanceof PaqueteUsuario) {
                    System.out.println("conexion  exitos");
                    PaqueteUsuario mensaje = (PaqueteUsuario) entradaObj;
                    // Verificar acción del mensaje
                    if (mensaje.getAccion().equals("login")) {
                        // Verificar credenciales con el árbol de usuarios válidos

                        boolean credencialesCorrectas = usuarios.buscar(mensaje.getId())
                                && usuarios.get(mensaje.getUsuario()).getPassword().equals(mensaje.getPassword());

                        // Enviar respuesta al cliente
                        if (credencialesCorrectas) {
                            salida.writeObject("OK");
                        } else {
                            salida.writeObject("ERROR");
                        }
                    } else if (mensaje.getAccion().equals("loginAdm")) {
                        // Verificar credenciales con el árbol de usuarios válidos
                        boolean credencialesCorrectas = administradores.buscar(mensaje.getId())
                                && administradores.get(mensaje.getUsuario()).getPassword().equals(mensaje.getPassword());

                        // Enviar respuesta al cliente
                        if (credencialesCorrectas) {
                            salida.writeObject("OK");
                        } else {
                            salida.writeObject("ERROR");
                        }
                    } else if (mensaje.getAccion().equals("register")) {

                        // Enviar respuesta al cliente
                        if (usuarios.buscar(mensaje.getId())) {
                            salida.writeObject("Nombre de usuario no disponible");
                        } else {
                            usuarios.insertar(new Usuario(mensaje.getId(), mensaje.getUsuario(), mensaje.getPassword()));
                            salida.writeObject("Se ha registrado con exito");
                        }
                    } else if (mensaje.getAccion().equals("registerAdm")) {

                        // Enviar respuesta al cliente
                        if (administradores.buscar(mensaje.getId())) {
                            salida.writeObject("Usuario no disponible");
                        } else {
                            administradores.insertar(new Administrador(mensaje.getId(), mensaje.getUsuario(), mensaje.getPassword()));
                            salida.writeObject("Usuario registrado con exito");
                        }
                    } else if (mensaje.getAccion().equals("deleteAdm")) {

                        // Enviar respuesta al cliente
                        if (administradores.buscar(mensaje.getId())) {
                            administradores.delete(mensaje.getId());
//                            administradores.toXml();
                            salida.writeObject("Usuario eliminado");
                        } else {

                            salida.writeObject("Usuario no encontrado");
                        }
                    } else if (mensaje.getAccion().equals("editAdm")) {

                    }}
                    if (entradaObj instanceof PaqueteAdmi) {
                        System.out.println("conexion  exitos");
                        PaqueteAdmi mensaj = (PaqueteAdmi) entradaObj;
                        // Verificar acción del mensaje
                        if (mensaj.getAccion().equals("login")) {
                            // Verificar credenciales con el árbol de usuarios válidos

                            boolean credencialesCorrectas = administradores.buscar(mensaj.getId())
                                    && administradores.get(mensaj.getUsuario()).getPassword().equals(mensaj.getPassword());

                            // Enviar respuesta al cliente
                            if (credencialesCorrectas) {
                                salida.writeObject("OK");
                            } else {
                                salida.writeObject("ERROR");
                            }
                        } else if (mensaj.getAccion().equals("loginAdm")) {
                            // Verificar credenciales con el árbol de usuarios válidos
                            boolean credencialesCorrectas = administradores.buscar(mensaj.getId())
                                    && administradores.get(mensaj.getUsuario()).getPassword().equals(mensaj.getPassword());

                            // Enviar respuesta al cliente
                            if (credencialesCorrectas) {
                                salida.writeObject("OK");
                            } else {
                                salida.writeObject("ERROR");
                            }
                        } else if (mensaj.getAccion().equals("register")) {

                            // Enviar respuesta al cliente
                            if (usuarios.buscar(mensaj.getId())) {
                                salida.writeObject("Nombre de usuario no disponible");
                            } else {
                                usuarios.insertar(new Usuario(mensaj.getId(), mensaj.getUsuario(), mensaj.getPassword()));
                                salida.writeObject("Se ha registrado con exito");
                            }
                        } else if (mensaj.getAccion().equals("registerAdm")) {

                            // Enviar respuesta al cliente
                            if (administradores.buscar(mensaj.getId())) {
                                salida.writeObject("Usuario no disponible");
                            } else {
                                administradores.insertar(new Administrador(mensaj.getId(), mensaj.getUsuario(), mensaj.getPassword()));
                                salida.writeObject("Usuario registrado con exito");
                            }
                        } else if (mensaj.getAccion().equals("deleteAdm")) {

                            // Enviar respuesta al cliente
                            if (administradores.buscar(mensaj.getId())) {
                                administradores.delete(mensaj.getId());
//                            administradores.toXml();
                                salida.writeObject("Usuario eliminado");
                            } else {

                                salida.writeObject("Usuario no encontrado");
                            }
                        } else if (mensaj.getAccion().equals("editAdm")) {

                        }}
                        entrada.close();
                        salida.close();
                        clienteSocket.close();

                    }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void escribirUsuariosXML(Usuario usuario) throws TransformerException {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document;

            // Verificar si el archivo XML ya existe
            File file = new File("usuarios.xml");
            if (file.exists()) {
                // Si el archivo existe, cargar el documento existente
                document = builder.parse(file);
            } else {
                // Si el archivo no existe, crear un nuevo documento
                document = builder.newDocument();
            }
            Element root;
            if (document.getDocumentElement() == null) {
                // Si no hay elemento raíz, crear uno nuevo
                root = document.createElement("usuarios");
                document.appendChild(root);
            } else {
                // Si hay elemento raíz, obtener el elemento raíz existente
                root = document.getDocumentElement();
            }

            // Crear el elemento de usuario
            Element usuarioElement = document.createElement("usuario");

            // Crear y agregar elementos de usuario
            Element nombreElement = document.createElement("nombre");
//            nombreElement.appendChild(document.createTextNode(usuario.getNombre()));
            usuarioElement.appendChild(nombreElement);

            Element usuarioUsuario = document.createElement("nombre_de_usuario");
            usuarioUsuario.appendChild(document.createTextNode(usuario.getUsuario() + ""));
            usuarioElement.appendChild(usuarioUsuario);

            Element idElement = document.createElement("id");
            idElement.appendChild(document.createTextNode(String.valueOf(usuario.getId())));
            usuarioElement.appendChild(idElement);

            Element passwordElement = document.createElement("password");
            passwordElement.appendChild(document.createTextNode(usuario.getPassword() + ""));
            usuarioElement.appendChild(passwordElement);

            // Agregar el elemento de usuario al elemento raíz
            root.appendChild(usuarioElement);

            // Transformar y guardar el documento en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static void cargarUsuariosDesdeXML(ABBU arbol) {
        try {
            File file = new File("usuarios.xml");
            if (!file.exists()) {
                System.out.println("El archivo XML no existe.");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = document.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("usuario");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element usuarioElement = (Element) nodeList.item(i);
                String nombre = obtenerValorElemento(usuarioElement, "nombre");
                int id = Integer.parseInt(obtenerValorElemento(usuarioElement, "id"));
                String usuario = obtenerValorElemento(usuarioElement, "nombre_de_usuario");
                String password = obtenerValorElemento(usuarioElement, "password");

                Usuario nuevoUsuario = new Usuario(id, usuario, password);
                arbol.insertar(nuevoUsuario);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }


    public static void cargarAdmiDesdeXML(ABBA arbol) {
        try {
            File file = new File("administradores.xml");
            if (!file.exists()) {
                System.out.println("El archivo XML no existe.");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = document.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("administrador");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element usuarioElement = (Element) nodeList.item(i);
                String nombre = obtenerValorElemento(usuarioElement, "nombre");
                int id = Integer.parseInt(obtenerValorElemento(usuarioElement, "id"));
                String usuario = obtenerValorElemento(usuarioElement, "nombre_de_usuario");
                String password = obtenerValorElemento(usuarioElement, "password");

                Administrador nuevoUsuario = new Administrador(id, usuario, password);
                arbol.insertar(nuevoUsuario);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }


    public static void guardarArbolUEnXML(String nombreArchivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("usuarios");
            document.appendChild(root);

            guardarNodosUEnXML(usuarios.getRaiz(), root, document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(nombreArchivo));
            transformer.transform(source, result);

            System.out.println("Árbol guardado en el archivo XML: " + nombreArchivo);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }





    public static void guardarArbolAEnXML(String nombreArchivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("administradores");
            document.appendChild(root);

            guardarNodosAEnXML(usuarios.getRaiz(), root, document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(nombreArchivo));
            transformer.transform(source, result);

            System.out.println("Árbol guardado en el archivo XML: " + nombreArchivo);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }





    private static void guardarNodosAEnXML(ABBU.NodoArbol nodo, Element elementoPadre, Document document) {
        if (nodo == null) {
            return;
        }

        Usuario usuario = nodo.getUsuario();
        Element usuarioElement = document.createElement("administrador");

//        Element nombreElement = document.createElement("nombre");
//        nombreElement.setTextContent(usuario.getNombre());
//        usuarioElement.appendChild(nombreElement);

        Element idElement = document.createElement("id");
        idElement.setTextContent(String.valueOf(usuario.getId()));
        usuarioElement.appendChild(idElement);

        Element usuarioUsuario = document.createElement("nombre_de_usuario");
        usuarioUsuario.setTextContent(String.valueOf(usuario.getUsuario()));
        usuarioElement.appendChild(usuarioUsuario);

        Element passwordElement = document.createElement("password");
        passwordElement.setTextContent(String.valueOf(usuario.getPassword()));
        usuarioElement.appendChild(passwordElement);

        elementoPadre.appendChild(usuarioElement);

        guardarNodosAEnXML(nodo.getIzquierdo(), usuarioElement, document);
        guardarNodosAEnXML(nodo.getDerecho(), usuarioElement, document);
    }




    private static void guardarNodosUEnXML(ABBU.NodoArbol nodo, Element elementoPadre, Document document) {
        if (nodo == null) {
            return;
        }

        Usuario usuario = nodo.getUsuario();
        Element usuarioElement = document.createElement("usuario");

//        Element nombreElement = document.createElement("nombre");
//        nombreElement.setTextContent(usuario.getNombre());
//        usuarioElement.appendChild(nombreElement);

        Element idElement = document.createElement("id");
        idElement.setTextContent(String.valueOf(usuario.getId()));
        usuarioElement.appendChild(idElement);

        Element usuarioUsuario = document.createElement("nombre_de_usuario");
        usuarioUsuario.setTextContent(String.valueOf(usuario.getUsuario()));
        usuarioElement.appendChild(usuarioUsuario);

        Element passwordElement = document.createElement("password");
        passwordElement.setTextContent(String.valueOf(usuario.getPassword()));
        usuarioElement.appendChild(passwordElement);

        elementoPadre.appendChild(usuarioElement);

        guardarNodosUEnXML(nodo.getIzquierdo(), usuarioElement, document);
        guardarNodosUEnXML(nodo.getDerecho(), usuarioElement, document);
    }



    private static String obtenerValorElemento(Element elemento, String etiqueta) {
        NodeList nodeList = elemento.getElementsByTagName(etiqueta);
        if (nodeList.getLength() > 0) {
            Element elementoEtiqueta = (Element) nodeList.item(0);
            return elementoEtiqueta.getTextContent();
        } else {
            return null; // O cualquier valor predeterminado que desees retornar en caso de que la etiqueta no se encuentre
        }
    }



    public static Plato searchPlatoInAVL(AVL tree, int id) {
        return searchPlato(tree.getRoot(), id);
    }

    private static Plato searchPlato(AVL.Node node, int id) {
        if (node == null || node.getPlato().getId() == id) {
            return (node != null) ? node.getPlato() : null;
        }

        if (id < node.getPlato().getId()) {
            return searchPlato(node.getLeft(), id);
        } else {
            return searchPlato(node.getRight(), id);
        }
    }





    public static void escribirAdministradoresXML(Administrador usuario) throws TransformerException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document;

            // Verificar si el archivo XML ya existe
            File file = new File("administradores.xml");
            if (file.exists()) {
                // Si el archivo existe, cargar el documento existente
                document = builder.parse(file);
            } else {
                // Si el archivo no existe, crear un nuevo documento
                document = builder.newDocument();
            }

            Element root;
            if (document.getDocumentElement() == null) {
                // Si no hay elemento raíz, crear uno nuevo
                root = document.createElement("administradores");
                document.appendChild(root);
            } else {
                // Si hay elemento raíz, obtener el elemento raíz existente
                root = document.getDocumentElement();
            }

            // Crear el elemento de usuario
            Element usuarioElement = document.createElement("administrador");

            // Crear y agregar elementos de usuario
            Element nombreElement = document.createElement("nombre");
//            nombreElement.appendChild(document.createTextNode(usuario.getNombre()));
            usuarioElement.appendChild(nombreElement);

            Element usuarioUsuario = document.createElement("nombre_de_usuario");
            usuarioUsuario.appendChild(document.createTextNode(usuario.getUsuario() + ""));
            usuarioElement.appendChild(usuarioUsuario);

            Element idElement = document.createElement("id");
            idElement.appendChild(document.createTextNode(String.valueOf(usuario.getId())));
            usuarioElement.appendChild(idElement);
            Element passwordElement = document.createElement("password");
            passwordElement.appendChild(document.createTextNode(usuario.getPassword() + ""));
            usuarioElement.appendChild(passwordElement);

            // Agregar el elemento de usuario al elemento raíz
            root.appendChild(usuarioElement);

            // Transformar y guardar el documento en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }



    public static void leerUsuariosXML() {
        File archivoXML = new File("usuarios.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(archivoXML);
            NodeList listaUsuarios = document.getElementsByTagName("usuario");
            for (int i = 0; i < listaUsuarios.getLength(); i++) {
                Element usuario = (Element) listaUsuarios.item(i);
                String nombre = getTextContent(usuario, "nombre");
                String usuarioStr = getTextContent(usuario, "nombre_de_usuario");
                String id = getTextContent(usuario, "id");
                String password = getTextContent(usuario, "password");
                System.out.println("Nombre: " + nombre);
                System.out.println("Usuario: " + usuarioStr);
                System.out.println("ID: " + id);
                System.out.println("Password: " + password);
                System.out.println("---------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTextContent(Element element, String tagName) {
        String textContent = "";
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            textContent = nodeList.item(0).getTextContent();
        }
        return textContent;
    }


    public static void buscarUsuarioXML(String nombreUsuario) {
        File archivoXML = new File("usuarios.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(archivoXML);
            NodeList listaUsuarios = document.getElementsByTagName("usuario");
            boolean encontrado = false;

            for (int i = 0; i < listaUsuarios.getLength(); i++) {
                Element usuario = (Element) listaUsuarios.item(i);
                String nombre = getTextContent(usuario, "nombre");

                if (nombre.equalsIgnoreCase(nombreUsuario)) {
                    String usuarioStr = getTextContent(usuario, "nombre_de_usuario");
                    String id = getTextContent(usuario, "id");
                    String password = getTextContent(usuario, "password");
                    System.out.println("Usuario encontrado:");
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Usuario: " + usuarioStr);
                    System.out.println("ID: " + id);
                    System.out.println("Password: " + password);
                    System.out.println("---------------------");
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("El usuario no existe en el archivo XML.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void buscarAdmiXML(String nombreUsuario) {
        File archivoXML = new File("administradores.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(archivoXML);
            NodeList listaUsuarios = document.getElementsByTagName("administrador");
            boolean encontrado = false;

            for (int i = 0; i < listaUsuarios.getLength(); i++) {
                Element usuario = (Element) listaUsuarios.item(i);
                String nombre = getTextContent(usuario, "nombre");

                if (nombre.equalsIgnoreCase(nombreUsuario)) {
                    String usuarioStr = getTextContent(usuario, "nombre_de_usuario");
                    String id = getTextContent(usuario, "id");
                    String password = getTextContent(usuario, "password");
                    System.out.println("Usuario encontrado:");
                    System.out.println("Nombre: " + nombre);
                    System.out.println("administrador: " + usuarioStr);
                    System.out.println("ID: " + id);
                    System.out.println("Password: " + password);
                    System.out.println("---------------------");
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("El administrador no existe en el archivo XML.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void eliminarUsuarioXML(int idUsuario) throws TransformerException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document;

            // Verificar si el archivo XML existe
            File file = new File("usuarios.xml");
            if (!file.exists()) {
                System.out.println("El archivo XML no existe.");
                return;
            }

            // Cargar el documento existente
            document = builder.parse(file);

            // Obtener el elemento raíz
            Element root = document.getDocumentElement();

            // Obtener la lista de elementos de usuario
            NodeList usuarios = root.getElementsByTagName("usuario");

            // Buscar el usuario por su ID y eliminarlo
            for (int i = 0; i < usuarios.getLength(); i++) {
                Element usuarioElement = (Element) usuarios.item(i);
                Element idElement = (Element) usuarioElement.getElementsByTagName("id").item(0);
                int id = Integer.parseInt(idElement.getTextContent());

                if (id == idUsuario) {
                    // Eliminar el elemento de usuario
                    root.removeChild(usuarioElement);
                    break;
                }
            }

            // Guardar el documento actualizado en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            System.out.println("Usuario eliminado exitosamente del archivo XML.");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }


    public static void leerAdministradoresXML() {
        File archivoXML = new File("administradores.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(archivoXML);
            NodeList listaUsuarios = document.getElementsByTagName("administrador");
            for (int i = 0; i < listaUsuarios.getLength(); i++) {
                Element usuario = (Element) listaUsuarios.item(i);
                String nombre = getTextContent(usuario, "nombre");
                String usuarioStr = getTextContent(usuario, "nombre_de_usuario");
                String id = getTextContent(usuario, "id");
                String password = getTextContent(usuario, "password");
                System.out.println("Nombre: " + nombre);
                System.out.println("administrador: " + usuarioStr);
                System.out.println("ID: " + id);
                System.out.println("Password: " + password);
                System.out.println("---------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/*

    public static void saveAVLToJson(AVL tree, String jsonFilePath) {
        ArrayList<Plato> platos = new ArrayList<>();
        traverseTree(tree.getRoot(), platos);

        JSONArray platosJsonArray = new JSONArray();
        for (Plato platillo : platos) {
            JSONObject platilloJson = new JSONObject();
            platilloJson.put("nombre", platillo.getNombre());
            platilloJson.put("calorias", platillo.getCalorias());
            platilloJson.put("tiempo_preparacion", platillo.getTiempoPreparacion());
            platilloJson.put("precio", platillo.getPrecio());
            platilloJson.put("id", platillo.getId());
            platosJsonArray.add(platilloJson);
        }

        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(platosJsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void traverseTree(AVL.Node node, ArrayList<Plato> platos) {
        if (node == null) {
            return;
        }
        traverseTree(node.getLeft(), platos);
        platos.add(node.getPlato());
        traverseTree(node.getRight(), platos);
    }

 */


    public static void saveAVLToJson(AVL tree, String jsonFilePath) {
        ArrayList<Plato> platos = new ArrayList<>();
        traverseTree(tree.getRoot(), platos);

        JSONArray platosJsonArray = new JSONArray();
        for (Plato platillo : platos) {
            JSONObject platilloJson = new JSONObject();
            platilloJson.put("nombre", platillo.getNombre());
            platilloJson.put("calorias", platillo.getCalorias());
            platilloJson.put("tiempo_preparacion", platillo.getTiempoPreparacion());
            platilloJson.put("precio", platillo.getPrecio());
            platilloJson.put("id", platillo.getId());
            platosJsonArray.add(platilloJson);
        }

        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(platosJsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Actualizar la raíz del árbol AVL después de guardar los platos
        tree.setRoot(null); // Vaciar el árbol
        for (Plato platillo : platos) {
            tree.insert(platillo);
        }
    }

    private static void traverseTree(AVL.Node node, ArrayList<Plato> platos) {
        if (node == null) {
            return;
        }
        traverseTree(node.getLeft(), platos);
        platos.add(node.getPlato());
        traverseTree(node.getRight(), platos);
    }


    public static void addPlatosToJson(ArrayList<Plato> platos, String jsonFilePath, Plato platillo) {
        // Load existing platos from the JSON file
        JSONArray platosJsonArray = loadPlatosFromJson(jsonFilePath);

        // Add the new platillo to the platos list
        platos.add(platillo);

        // Create a JSON object for the new platillo
        JSONObject platilloJson = new JSONObject();
        platilloJson.put("nombre", platillo.getNombre());
        platilloJson.put("calorias", platillo.getCalorias());
        platilloJson.put("tiempo_preparacion", platillo.getTiempoPreparacion());
        platilloJson.put("precio", platillo.getPrecio());
        platilloJson.put("id", platillo.getId());

        // Add the JSON object of the new platillo to the platosJsonArray
        platosJsonArray.add(platilloJson);

        // Write the updated JSON array to the file
        try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
            fileWriter.write(platosJsonArray.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray loadPlatosFromJson(String jsonFilePath) {
        JSONArray platosJsonArray = new JSONArray();
        try (FileReader fileReader = new FileReader(jsonFilePath)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray existingPlatosJsonArray = (JSONArray) jsonParser.parse(fileReader);

            // Add each existing platillo to the platosJsonArray
            for (Object obj : existingPlatosJsonArray) {
                platosJsonArray.add(obj);
            }
        } catch (IOException | ParseException e) {
            // Ignore if the file doesn't exist or has invalid JSON content
        }
        return platosJsonArray;
    }



    public static ArrayList<Plato> readPlatosFromJson(String jsonFilePath) {
        ArrayList<Plato> platos = new ArrayList<>();

        try (FileReader fileReader = new FileReader(jsonFilePath)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray platosJsonArray = (JSONArray) jsonParser.parse(fileReader);

            for (Object platoObj : platosJsonArray) {
                JSONObject platoJson = (JSONObject) platoObj;
                String nombre = (String) platoJson.get("nombre");
                int calorias = ((Long) platoJson.get("calorias")).intValue();
                int tiempoPreparacion = ((Long) platoJson.get("tiempo_preparacion")).intValue();
                int precio = ((Long) platoJson.get("precio")).intValue();
                int id = ((Long) platoJson.get("id")).intValue();

                Plato plato = new Plato(nombre, calorias, tiempoPreparacion, precio, id);
                platos.add(plato);
            }

            for (Plato plato : platos) {
                System.out.println("Nombre: " + plato.getNombre());
                System.out.println("Calorías: " + plato.getCalorias());
                System.out.println("Tiempo de preparación: " + plato.getTiempoPreparacion());
                System.out.println("Precio: " + plato.getPrecio());
                System.out.println("ID: " + plato.getId());
                System.out.println("-----------------------------");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return platos;
    }



    public void removePlatosFromJson(String jsonFilePath, String platoNombre) {
        try {
            // Leer el archivo JSON existente
            JSONParser parser = new JSONParser();
            JSONArray platosJsonArray = (JSONArray) parser.parse(new FileReader(jsonFilePath));

            // Recorrer el JSONArray para buscar el plato a eliminar
            Iterator<Object> iterator = platosJsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject platoJson = (JSONObject) iterator.next();
                String nombre = (String) platoJson.get("nombre");

                // Si se encuentra el plato, eliminarlo del JSONArray
                if (nombre.equals(platoNombre)) {
                    iterator.remove();
                    break;
                }
            }

            // Escribir el JSONArray actualizado en el archivo
            try (FileWriter file = new FileWriter(jsonFilePath)) {
                file.write(platosJsonArray.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }






}