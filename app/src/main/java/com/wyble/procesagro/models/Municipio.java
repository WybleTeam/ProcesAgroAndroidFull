package com.wyble.procesagro.models;

import java.io.Serializable;

/**
 * Created by flia on 24/11/14.
 */
public class Municipio implements Serializable {

    private int id;

    private String nombreMunicipio;

    private String departamento;



    public Municipio(int id, String nombreMunicipio, String departamento) {
        this.id = id;
        this.nombreMunicipio = nombreMunicipio;
        this.departamento = departamento;
    }

    public String toString() {
        return this.nombreMunicipio;
    }

    public int getId() { return this.id; }

    public String getNombreMunicipio() { return this.nombreMunicipio; }

    public String getDepartamento() { return this.departamento; }
}
