package com.wyble.procesagro.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

/**
 * Created by david on 9/7/14.
 */
public class Tramite implements Serializable {

    private int id;
    private String ica;
    private String nombreFinca;
    private String nombrePropietario;
    private String cedulaPropietario;
    private String fijoPropietario;
    private String celularPropietario;
    private String municipio;
    private String departamento;
    private String nombreSolicitante;
    private String cedulaSolicitante;
    private String fijoSolicitante;
    private String celularSolicitante;
    private int menor1Bovinos;
    private int entre12Bovinos;
    private int entre23Bovinos;
    private int mayores3Bovinos;
    private int menor1Bufalino;
    private int entre12Bufalino;
    private int entre23Bufalino;
    private int mayor3Bufalino;
    private int primeraVez;
    private int nacimiento;
    private int compra;
    private int perdidaDIN;
    private String justificacion;
    private Boolean terminos;

    public Tramite() {
        this.id = 0;
        this.ica = "";
        this.nombreFinca = "";
        this.nombrePropietario = "";
        this.cedulaPropietario = "";
        this.fijoPropietario = "";
        this.celularPropietario = "";
        this.municipio = "";
        this.departamento = "";
        this.nombreSolicitante = "";
        this.cedulaSolicitante = "";
        this.fijoSolicitante = "";
        this.celularSolicitante = "";
        this.menor1Bovinos = 0;
        this.entre12Bovinos = 0;
        this.entre23Bovinos = 0;
        this.mayores3Bovinos = 0;
        this.menor1Bufalino = 0;
        this.entre12Bufalino = 0;
        this.entre23Bufalino = 0;
        this.mayor3Bufalino = 0;
        this.primeraVez = 0;
        this.nacimiento = 0;
        this.compra = 0;
        this.perdidaDIN = 0;
        this.justificacion = "";
        this.terminos = Boolean.FALSE;
    }

    public void paso1(String ica, String nombreFinca, String nombrePropietario, String cedulaPropietario, String fijoPropietario, String celularPropietario) {
        this.ica = ica;
        this.nombreFinca = nombreFinca;
        this.nombrePropietario = nombrePropietario;
        this.cedulaPropietario = cedulaPropietario;
        this.fijoPropietario = fijoPropietario;
        this.celularPropietario = celularPropietario;
    }

    public void paso2(String municipio, String departamento) {
        this.municipio = municipio;
        this.departamento = departamento;
    }

    public void paso3(String nombreSolicitante, String cedulaSolicitante, String fijoSolicitante, String celularSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
        this.cedulaSolicitante = cedulaSolicitante;
        this.fijoSolicitante = fijoSolicitante;
        this.celularSolicitante = celularSolicitante;
    }

    public void paso4(int menor1Bovinos, int entre12Bovinos, int entre23Bovinos, int mayores3Bovinos) {
        this.menor1Bovinos = menor1Bovinos;
        this.entre12Bovinos = entre12Bovinos;
        this.entre23Bovinos = entre23Bovinos;
        this.mayores3Bovinos = mayores3Bovinos;
    }

    public void paso5(int menor1Bufalino, int entre12Bufalino, int entre23Bufalino, int mayor3Bufalino) {
        this.menor1Bufalino = menor1Bufalino;
        this.entre12Bufalino = entre12Bufalino;
        this.entre23Bufalino = entre23Bufalino;
        this.mayor3Bufalino = mayor3Bufalino;
    }

    public void paso6(int perdidaDIN, int compra, int nacimiento, int primeraVez) {
        this.perdidaDIN = perdidaDIN;
        this.compra = compra;
        this.nacimiento = nacimiento;
        this.primeraVez = primeraVez;
    }

    public void paso7(String justificacion, Boolean terminos) {
        this.justificacion = justificacion;
        this.terminos = terminos;
    }

    @Override
    public String toString() {
        return "Tramite{" +
                "nombrePropietario='" + nombrePropietario + '\'' +
                ", cedulaPropietario='" + cedulaPropietario + '\'' +
                '}';
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        try {
            //obj.put("id", this.getId());
            obj.put("ica3101", this.getIca());
            obj.put("nombreFinca", this.getNombreFinca());
            obj.put("nombrePropietarioFinca", this.getNombrePropietario());
            obj.put("cedulaPropietarioFinca", this.getCedulaPropietario());
            obj.put("telefonoFijoPropietario", this.getFijoPropietario());
            obj.put("telefonoCelularPropietario", this.getCelularPropietario());
            obj.put("municipioVereda", this.getMunicipio());
            obj.put("departamento", this.getDepartamento());
            obj.put("nombreSolicitante", this.getNombreSolicitante());
            obj.put("cedulaSolicitante", this.getCedulaSolicitante());
            obj.put("telefonoFijoSolicitante", this.getFijoSolicitante());
            obj.put("telefonoCelularSolicitante", this.getCelularSolicitante());
            obj.put("menUnoBovino", this.getMenor1Bovinos());
            obj.put("unoDosBovino", this.getEntre12Bovinos());
            obj.put("dosTresBovino", this.getEntre23Bovinos());
            obj.put("tresMayorBovino", this.getMayores3Bovinos());
            obj.put("menUnoBufalino", this.getMenor1Bufalino());
            obj.put("unoDosBufalino", this.getEntre12Bufalino());
            obj.put("dosTresBufalino", this.getEntre23Bufalino());
            obj.put("tresMayorBufalino", this.getMayor3Bufalino());
            obj.put("jusPrimera", this.getPrimeraVez());
            obj.put("jusNacimiento", this.getNacimiento());
            obj.put("jusCompraAnimales", this.getCompra());
            obj.put("jusPerdidaDin", this.getPerdidaDIN());
            obj.put("justificacion", this.getJustificacion());
            obj.put("terminos", this.getTerminos());
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

    public int getId() {
        return id;
    }

    public String getIca() {
        return ica;
    }

    public String getNombreFinca() {
        return nombreFinca;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public String getCedulaPropietario() {
        return cedulaPropietario;
    }

    public String getFijoPropietario() {
        return fijoPropietario;
    }

    public String getCelularPropietario() {
        return celularPropietario;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public String getCedulaSolicitante() {
        return cedulaSolicitante;
    }

    public String getFijoSolicitante() {
        return fijoSolicitante;
    }

    public String getCelularSolicitante() {
        return celularSolicitante;
    }

    public int getMenor1Bovinos() { return menor1Bovinos; }

    public int getEntre12Bovinos() {
        return entre12Bovinos;
    }

    public int getEntre23Bovinos() {
        return entre23Bovinos;
    }

    public int getMayores3Bovinos() {
        return mayores3Bovinos;
    }

    public int getMenor1Bufalino() {
        return menor1Bufalino;
    }

    public int getEntre12Bufalino() {
        return entre12Bufalino;
    }

    public int getEntre23Bufalino() {
        return entre23Bufalino;
    }

    public int getMayor3Bufalino() {
        return mayor3Bufalino;
    }

    public int getPrimeraVez() {
        return primeraVez;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public int getCompra() {
        return compra;
    }

    public int getPerdidaDIN() {
        return perdidaDIN;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public Boolean getTerminos() {
        return terminos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIca(String ica) {
        this.ica = ica;
    }

    public void setNombreFinca(String nombreFinca) {
        this.nombreFinca = nombreFinca;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public void setCedulaPropietario(String cedulaPropietario) {
        this.cedulaPropietario = cedulaPropietario;
    }

    public void setFijoPropietario(String fijoPropietario) {
        this.fijoPropietario = fijoPropietario;
    }

    public void setCelularPropietario(String celularPropietario) {
        this.celularPropietario = celularPropietario;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public void setCedulaSolicitante(String cedulaSolicitante) {
        this.cedulaSolicitante = cedulaSolicitante;
    }

    public void setFijoSolicitante(String fijoSolicitante) {
        this.fijoSolicitante = fijoSolicitante;
    }

    public void setCelularSolicitante(String celularSolicitante) {
        this.celularSolicitante = celularSolicitante;
    }

    public void setMenor1Bovinos(int menor1Bovinos) {
        this.menor1Bovinos = menor1Bovinos;
    }

    public void setEntre12Bovinos(int entre12Bovinos) {
        this.entre12Bovinos = entre12Bovinos;
    }

    public void setEntre23Bovinos(int entre23Bovinos) {
        this.entre23Bovinos = entre23Bovinos;
    }

    public void setMayores3Bovinos(int mayores3Bovinos) {
        this.mayores3Bovinos = mayores3Bovinos;
    }

    public void setMenor1Bufalino(int menor1Bufalino) {
        this.menor1Bufalino = menor1Bufalino;
    }

    public void setEntre12Bufalino(int entre12Bufalino) {
        this.entre12Bufalino = entre12Bufalino;
    }

    public void setEntre23Bufalino(int entre23Bufalino) {
        this.entre23Bufalino = entre23Bufalino;
    }

    public void setMayor3Bufalino(int mayor3Bufalino) {
        this.mayor3Bufalino = mayor3Bufalino;
    }

    public void setPrimeraVez(int primeraVez) {
        this.primeraVez = primeraVez;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setCompra(int compra) {
        this.compra = compra;
    }

    public void setPerdidaDIN(int perdidaDIN) {
        this.perdidaDIN = perdidaDIN;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public void setTerminos(Boolean terminos) {
        this.terminos = terminos;
    }

    public Boolean validaSumaBovinosBufalinosMotivos() {
        Boolean result = false;
        Integer bovinos = this.menor1Bovinos + this.entre12Bovinos + this.entre23Bovinos + this.mayores3Bovinos;
        Integer bufalinos = this.menor1Bufalino + this.entre12Bufalino + this.entre23Bufalino + this.mayor3Bufalino;
        Integer motivos = this.perdidaDIN + this.compra + this.nacimiento + this.primeraVez;

        if ((bovinos + bufalinos) == motivos) {
            result = true;
        }
        return  result;
    }
}
