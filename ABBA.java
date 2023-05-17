package com.example.demoproyecto2;


public class ABBA {
    private NodoArbol raiz;

    public NodoArbol getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoArbol raiz) {
        this.raiz = raiz;
    }

    public class NodoArbol {
        private Administrador administrador;

        public NodoArbol getIzquierdo() {
            return izquierdo;
        }

        public void setIzquierdo(NodoArbol izquierdo) {
            this.izquierdo = izquierdo;
        }

        public NodoArbol getDerecho() {
            return derecho;
        }

        public void setDerecho(NodoArbol derecho) {
            this.derecho = derecho;
        }

        public Administrador getAdministrador() {
            return administrador;
        }

        public void setAdministrador(Administrador administrador) {
            this.administrador = administrador;
        }

        private NodoArbol izquierdo;
        private NodoArbol derecho;

        public NodoArbol(Administrador administrador) {
            this.administrador = administrador;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    public ABBA() {
        this.raiz = null;
    }

    public void insertar(Administrador administrador) {
        raiz = insertarNodo(raiz, administrador);
    }

    private NodoArbol insertarNodo(NodoArbol nodo, Administrador administrador) {
        if (nodo == null) {
            return new NodoArbol(administrador);
        }

        if (administrador.getId() < nodo.administrador.getId()) {
            nodo.izquierdo = insertarNodo(nodo.izquierdo, administrador);
        } else if (administrador.getId() > nodo.administrador.getId()) {
            nodo.derecho = insertarNodo(nodo.derecho, administrador);
        }

        return nodo;
    }

    public boolean buscar(int id) {
        return buscarNodo(raiz, id);
    }

    private boolean buscarNodo(NodoArbol nodo, int id) {
        if (nodo == null) {
            return false;
        }

        if (nodo.administrador.getId() == id) {
            return true;
        } else if (id < nodo.administrador.getId()) {
            return buscarNodo(nodo.izquierdo, id);
        } else {
            return buscarNodo(nodo.derecho, id);
        }
    }









    public Administrador get(String nombreAdmi) {
        return getAdmiPorNombre(raiz, nombreAdmi);
    }

    private Administrador getAdmiPorNombre(NodoArbol nodo, String nombreAdmi) {
        if (nodo == null) {
            return null;
        }

        int comparacion = nombreAdmi.compareTo(nodo.administrador.getUsuario());
        if (comparacion == 0) {
            return nodo.administrador;
        } else if (comparacion < 0) {
            return getAdmiPorNombre(nodo.izquierdo, nombreAdmi);
        } else {
            return getAdmiPorNombre(nodo.derecho, nombreAdmi);
        }
    }



    public boolean delete(int id) {
        if (buscar(id)) {
            raiz = deleteNodo(raiz, id);
            return true;
        } else {
            return false;
        }
    }

    private NodoArbol deleteNodo(NodoArbol nodo, int id) {
        if (nodo == null) {
            return null;
        }

        if (id < nodo.administrador.getId()) {
            nodo.izquierdo = deleteNodo(nodo.izquierdo, id);
        } else if (id > nodo.administrador.getId()) {
            nodo.derecho = deleteNodo(nodo.derecho, id);
        } else {
            if (nodo.izquierdo == null && nodo.derecho == null) {
                // Caso 1: Nodo sin hijos
                return null;
            } else if (nodo.izquierdo == null) {
                // Caso 2: Nodo con un solo hijo (hijo derecho)
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                // Caso 2: Nodo con un solo hijo (hijo izquierdo)
                return nodo.izquierdo;
            } else {
                // Caso 3: Nodo con dos hijos
                NodoArbol sucesor = encontrarSucesor(nodo.derecho);
                nodo.administrador = sucesor.administrador;
                nodo.derecho = deleteNodo(nodo.derecho, sucesor.administrador.getId());
            }
        }

        return nodo;
    }

    private NodoArbol encontrarSucesor(NodoArbol nodo) {
        if (nodo.izquierdo == null) {
            return nodo;
        }

        return encontrarSucesor(nodo.izquierdo);
    }

}



