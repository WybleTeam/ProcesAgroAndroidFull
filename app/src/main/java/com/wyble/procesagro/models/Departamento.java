package com.wyble.procesagro.models;

import java.io.Serializable;

/**
 * Created by flia on 23/11/14.
 */
public class Departamento implements Serializable {

    private int id;

    private String nombreDepartamento;



    public Departamento(int id, String nombreDepartamento) {
        this.id = id;
        this.nombreDepartamento = nombreDepartamento;
    }

    public String toString() {
        return this.nombreDepartamento;
    }

    public int getId() { return this.id; }

    public String getNombreDepartamento() { return this.nombreDepartamento; }


}
