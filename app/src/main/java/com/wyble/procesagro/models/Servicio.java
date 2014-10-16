package com.wyble.procesagro.models;

/**
 * Created by david on 9/11/14.
 */
public class Servicio {

    private int id;

    private String usuario;

    private String titulo;

    private String descripcion;

    private String url_audio;

    private String url_servicio;

    public Servicio(int id, String usuario, String titulo, String descripcion, String url_audio, String url_servicio) {
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url_audio = url_audio;
        this.url_servicio = url_servicio;
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

    public String getUrl_servicio() {
        return url_servicio;
    }
}
