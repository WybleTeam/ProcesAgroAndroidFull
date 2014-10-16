package com.wyble.procesagro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.models.Tramite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Call_Form1Activity extends ActionBarActivity {


    private Button form1_next;
    private EditText ica, finca, propietario, cedula_prop, telefono_prop, celular_prop;
    final Context context= this;
    private static final String TRAMITE_TABLE = "tramites";
    private Tramite tramite;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form1);

        Serializable dataFromMenu = getIntent().getSerializableExtra("TRAMITE");
        tramite = (Tramite) dataFromMenu;

        ica = (EditText) findViewById(R.id.ica);
        finca = (EditText) findViewById(R.id.finca);
        propietario = (EditText) findViewById(R.id.propietario);
        cedula_prop = (EditText) findViewById(R.id.cedula_prop);
        telefono_prop = (EditText) findViewById(R.id.telefono_prop);
        celular_prop = (EditText) findViewById(R.id.celular_prop);

        ica.setText(tramite.getIca());
        finca.setText(tramite.getNombreFinca());
        propietario.setText(tramite.getNombrePropietario());
        cedula_prop.setText(tramite.getCedulaPropietario());
        telefono_prop.setText(tramite.getFijoPropietario());
        celular_prop.setText(tramite.getCelularPropietario());

        form1_next = (Button) findViewById(R.id.form1_next);
        form1_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String ica_v = ica.getText().toString();
                final String finca_v = finca.getText().toString();
                final String propietario_v = propietario.getText().toString();
                final String cedula_prop_v = cedula_prop.getText().toString();
                final String telefono_prop_v = telefono_prop.getText().toString();
                final String celular_prop_v = celular_prop.getText().toString();

                //si todos los campos vacios
                if(ica_v.equals("") || finca_v.equals("") || propietario_v.equals("")
                        || cedula_prop_v.equals("") || telefono_prop_v.equals("") || celular_prop_v.equals("")) {

                    Toast.makeText(context, "Todos los campos son requeridos.", Toast.LENGTH_SHORT).show();
                }else{
                            tramite.paso1(ica_v,
                                    finca_v,
                                    propietario_v,
                                    cedula_prop_v,
                                    telefono_prop_v,
                                    celular_prop_v);

                    HashMap hmTramite = new HashMap();
                    hmTramite.put(TRAMITE_TABLE, tramite.toJSONArray());

                    ArrayList<HashMap> tables = new ArrayList<HashMap>();
                    tables.add(hmTramite);
                    db = new DB(context, tables);
                    if (tramite.getId() == 0) {
                        db.insertData(TRAMITE_TABLE, tramite.toJSONArray());
                        ArrayList<Tramite> tramites = getTramites();
                        tramite = tramites.get(0);
                    } else {
                        db.updateData(TRAMITE_TABLE, tramite.toJSONArray(), tramite.getId());
                    }

                    Intent intent = new Intent(Call_Form1Activity.this, Call_Form2Activity.class);
                    intent.putExtra("TRAMITE_PASO1", tramite);
                    startActivity(intent);
                }
            }
        });
    }

    private ArrayList<Tramite> getTramites() {
        ArrayList tramites = new ArrayList();
        ArrayList<HashMap> data = db.getAllData(TRAMITE_TABLE);

        for (HashMap d : data) {
            Tramite tramite = new Tramite();
            tramite.setId(Integer.parseInt(d.get("autoId").toString()));
            tramite.setIca(d.get("ica3101").toString());
            tramite.setNombreFinca(d.get("nombreFinca").toString());
            tramite.setNombrePropietario(d.get("nombrePropietarioFinca").toString());
            tramite.setCedulaPropietario(d.get("cedulaPropietarioFinca").toString());
            tramite.setFijoPropietario(d.get("telefonoFijoPropietario").toString());
            tramite.setCelularPropietario(d.get("telefonoCelularPropietario").toString());
            tramite.setMunicipio(d.get("municipioVereda").toString());
            tramite.setDepartamento(d.get("departamento").toString());
            tramite.setNombreSolicitante(d.get("nombreSolicitante").toString());
            tramite.setCedulaSolicitante(d.get("cedulaSolicitante").toString());
            tramite.setFijoSolicitante(d.get("telefonoFijoSolicitante").toString());
            tramite.setCelularSolicitante(d.get("telefonoCelularSolicitante").toString());
            tramite.setMenor1Bovinos(Integer.parseInt(d.get("menUnoBovino").toString()));
            tramite.setEntre12Bovinos(Integer.parseInt(d.get("unoDosBovino").toString()));
            tramite.setEntre23Bovinos(Integer.parseInt(d.get("dosTresBovino").toString()));
            tramite.setMayores3Bovinos(Integer.parseInt(d.get("tresMayorBovino").toString()));
            tramite.setMenor1Bufalino(Integer.parseInt(d.get("menUnoBufalino").toString()));
            tramite.setEntre12Bufalino(Integer.parseInt(d.get("unoDosBufalino").toString()));
            tramite.setEntre23Bufalino(Integer.parseInt(d.get("dosTresBufalino").toString()));
            tramite.setMayor3Bufalino(Integer.parseInt(d.get("tresMayorBufalino").toString()));
            tramite.setPrimeraVez(Integer.parseInt(d.get("jusPrimera").toString()));
            tramite.setNacimiento(Integer.parseInt(d.get("jusNacimiento").toString()));
            tramite.setCompra(Integer.parseInt(d.get("jusCompraAnimales").toString()));
            tramite.setPerdidaDIN(Integer.parseInt(d.get("jusPerdidaDin").toString()));
            tramite.setJustificacion(d.get("justificacion").toString());
            tramite.setTerminos(Boolean.parseBoolean(d.get("terminos").toString()));
            tramites.add(tramite);
        }
        db.close();
        return tramites;
    }
}
