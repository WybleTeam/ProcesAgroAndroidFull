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


public class Call_Form3Activity extends ActionBarActivity {

    private Button form3_next;
    private EditText nombre_solic, cedula_solic, telefono_solic, celular_solic;
    Context context= this;
    private static final String TRAMITE_TABLE = "tramites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form3);

        Serializable dataFromPaso2 = getIntent().getSerializableExtra("TRAMITE_PASO2");
        final Tramite tramite = (Tramite) dataFromPaso2;

        nombre_solic = (EditText) findViewById(R.id.nombre_solic);
        cedula_solic = (EditText) findViewById(R.id.cedula_solic);
        telefono_solic = (EditText) findViewById(R.id.telefono_solic);
        celular_solic = (EditText) findViewById(R.id.celular_solic);

        nombre_solic.setText(tramite.getNombreSolicitante());
        cedula_solic.setText(tramite.getCedulaSolicitante());
        telefono_solic.setText(tramite.getFijoSolicitante());
        celular_solic.setText(tramite.getCelularSolicitante());

        form3_next = (Button) findViewById(R.id.form3_next);
        form3_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String nombre_solic_v= nombre_solic.getText().toString().trim();
                final String cedula_solic_v= cedula_solic.getText().toString().trim();
                final String telefono_solic_v= telefono_solic.getText().toString().trim();
                final String celular_solic_v= celular_solic.getText().toString().trim();
                if(nombre_solic_v.equals("") || cedula_solic_v.equals("")
                        || telefono_solic_v.length()==0 || celular_solic_v.length()==0){

                    Toast.makeText(context, "Todos los campos son requeridos.", Toast.LENGTH_SHORT).show();
                }else{

                    if (nombre_solic_v.length() < 7 || nombre_solic_v.length() > 50) {
                        Toast.makeText(context, "El nombre solicitante debe tener al menos 7 letras y máximo 50 letras", Toast.LENGTH_SHORT).show();
                        nombre_solic.requestFocus();
                    } else if (telefono_solic_v.length() < 6 || telefono_solic_v.length() > 10) {
                        Toast.makeText(context, "El teléfono solicitante debe tener entre 6 y 10 números", Toast.LENGTH_SHORT).show();
                        telefono_solic.requestFocus();
                    } else if (celular_solic_v.length() < 10 || celular_solic_v.length() > 10) {
                        Toast.makeText(context, "El celular solicitante debe tener 10 números", Toast.LENGTH_SHORT).show();
                        celular_solic.requestFocus();
                    } else {

                        tramite.paso3(nombre_solic_v, cedula_solic_v, telefono_solic_v, celular_solic_v);

                        HashMap hmTramite = new HashMap();
                        hmTramite.put(TRAMITE_TABLE, tramite.toJSONArray());

                        ArrayList<HashMap> tables = new ArrayList<HashMap>();
                        tables.add(hmTramite);
                        DB db = new DB(context, tables);
                        db.updateData(TRAMITE_TABLE, tramite.toJSONArray(), tramite.getId());

                        Intent intent = new Intent(Call_Form3Activity.this, Call_Form4Activity.class);
                        intent.putExtra("TRAMITE_PASO3", tramite);
                        startActivity(intent);

                    }

                }
            }
        });
    }
}
