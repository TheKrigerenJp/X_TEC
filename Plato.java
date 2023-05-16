package com.example.demoproyecto2;

/**
 * Clase plato
 * @author JJD
 * @version 15/05/2023
 */
public class Plato {
    private String nombre;
    private int calorias;
    private int tiempoPreparacion;
    private int precio;
    private int id;
    private boolean activo;

    public Plato(String nombre, int calorias, int tiempoPreparacion, int precio, int ID) {
        this.nombre = nombre;
        this.calorias = calorias;
        this.tiempoPreparacion = tiempoPreparacion;
        this.precio = precio;
        this.id = ID;
        activo = false;
    }

    // Métodos getter y setter para cada atributo
    /**
     * Metodo para obtener la variable/atributo nombre
     * @return El atributo nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Metodo que se encarga de darle un valor al atributo usuario
     * @param nombre String que al ser recibido se le da ese valor al atributo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Metodo para obtener la variable/atributo calorías
     * @return El atributo calorías
     */
    public int getCalorias() {
        return calorias;
    }
    /**
     * Metodo que se encarga de darle un valor al calorias
     * @param calorias entero que al ser recibido se le da ese valor al atributo
     */
    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }
    /**
     * Metodo para obtener la variable/atributo tiempoPreparacion
     * @return El atributo tiempoPreparacion
     */
    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }
    /**
     * Metodo que se encarga de darle un valor al atributo tiempoPreparacion
     * @param tiempoPreparacion entero que al ser recibido se le da ese valor al atributo
     */
    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }
    /**
     * Metodo para obtener la variable/atributo precio
     * @return El atributo precio
     */
    public int getPrecio() {
        return precio;
    }
    /**
     * Metodo que se encarga de darle un valor al atributo precio
     * @param precio entero que al ser recibido se le da ese valor al atributo
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    /**
     * Metodo para obtener la variable/atributo id
     * @return El atributo id
     */
    public int getId() {
        return id;
    }
    /**
     * Metodo que se encarga de darle un valor al atributo id
     * @param id entero que al ser recibido se le da ese valor al atributo
     */
    public void setId(int id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }
    /**
     * Metodo que se encarga de darle un valor al atributo activo
     * @param activo boolean que al ser recibido se le da ese valor al atributo
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
