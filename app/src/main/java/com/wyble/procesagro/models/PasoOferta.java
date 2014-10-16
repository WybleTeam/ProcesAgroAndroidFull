package com.wyble.procesagro.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by david on 9/11/14.
 */
public class PasoOferta implements Serializable {

    private int id;

    private String titulo;

    private String descripcion;

    private String url;

    private Boolean is_checked;

    public PasoOferta(int id, String titulo, String descripcion, String url, Boolean is_checked) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.url = url;
        this.is_checked = is_checked;
    }

    @Override
    public String toString() {
        return titulo;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getIsChecked() {
        return is_checked;
    }

    public void setIsChecked(Boolean is_checked) {
        this.is_checked = is_checked;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            //obj.put("id", this.getId());
            obj.put("tituloPasos", this.getTitulo());
            obj.put("descripcionPaso", this.getDescripcion());
            obj.put("urlPaso", this.getUrl());
            obj.put("isChecked", Boolean.toString(this.getIsChecked()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public JSONArray toJSONArray() {
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(this.toJSON());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
