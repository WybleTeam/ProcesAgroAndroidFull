package com.wyble.procesagro.models;

import java.io.Serializable;

/**
 * Created by david on 9/7/14.
 */
public class Convocatoria implements Serializable {

    private int id;

    private String titulo;

    private String descripcion_corta;

    private String descripcion_larga;

    private String url;

    private String usuario;

    public Convocatoria(int id, String titulo, String descripcion_corta, String descripcion_larga, String url, String usuario) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion_corta = descripcion_corta;
        this.descripcion_larga = descripcion_larga;
        this.url = url;
        this.usuario = usuario;
    }

    public String toString() {
        return this.titulo + "\n" + this.descripcion_corta;
    }

    public int getId() { return this.id; }

    public String getTitulo() { return this.titulo; }

    public String getDescripcionCorta() {
        return this.descripcion_corta;
    }

    public String getDescripcionLarga() {
        return this.descripcion_larga;
    }

    public String getUrl() {
        return this.url;
    }

    public String getUsuario() {
        return this.usuario;
    }
}
