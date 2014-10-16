package com.wyble.procesagro.models;

import java.io.Serializable;

/**
 * Created by david on 10/15/14.
 */
public class CursoVirtual implements Serializable {

    private int id;

    private String nombreCurso;

    private String descripcionCurso;

    private String urlAudio;

    private String urlCurso;

    public CursoVirtual(int id, String nombreCurso, String descripcionCurso, String urlAudio, String urlCurso) {
        this.id = id;
        this.nombreCurso = nombreCurso;
        this.descripcionCurso = descripcionCurso;
        this.urlAudio = urlAudio;
        this.urlCurso = urlCurso;
    }

    @Override
    public String toString() {
        return  nombreCurso + "\n" + descripcionCurso;
    }

    public int getId() {
        return id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public String getUrlCurso() {
        return urlCurso;
    }
}
