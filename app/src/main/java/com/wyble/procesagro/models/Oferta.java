package com.wyble.procesagro.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by david on 9/11/14.
 */
public class Oferta implements Serializable {

    private int id;

    private String usuario;

    private String titulo;

    private String descripcion;

    private String url_audio;

    private String url;

    private ArrayList<PasoOferta> pasos_oferta;

    public Oferta(int id, String usuario, String titulo, String descripcion, String url_audio, String url, ArrayList<PasoOferta> pasos_oferta) {
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url_audio = url_audio;
        this.url = url;
        this.pasos_oferta = pasos_oferta;
    }

    @Override
    public String toString() {
        return titulo;
    }

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrl_audio() {
        return url_audio;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<PasoOferta> getPasosOferta() {
        return pasos_oferta;
    }
}
