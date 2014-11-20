package com.wyble.procesagro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.models.Tramite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Call_Form2Activity extends ActionBarActivity {

    private Button form2_next;
    //private EditText municipio, departamento;
    private Spinner municipio, departamento;
    Context context= this;
    private static final String TRAMITE_TABLE = "tramites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form2);

        Serializable dataFromPaso1 = getIntent().getSerializableExtra("TRAMITE_PASO1");
        final Tramite tramite = (Tramite) dataFromPaso1;

        municipio = (Spinner) findViewById(R.id.municipio);
        departamento = (Spinner) findViewById(R.id.departamento);

        //municipio.setText(tramite.getMunicipio());
        //departamento.setText(tramite.getDepartamento());
        //municipio.setText(tramite.getMunicipio());
        //departamento.setText(tramite.getDepartamento());

        form2_next = (Button) findViewById(R.id.form2_next);
        form2_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //final String municipio_v = municipio.getText().toString().trim();
                //final String departamento_v = departamento.getText().toString().trim();
                String municipio_v = municipio.getSelectedItem().toString();
                String departamento_v = departamento.getSelectedItem().toString();
                Log.d("//log", "//log" + municipio_v + " - " + departamento_v);

                if(municipio_v.equals("Seleccione un municipio") || departamento_v.equals("Seleccione un departamento")){
                    Toast.makeText(context, "Seleccione un departamento y municipio.", Toast.LENGTH_SHORT).show();
                }else{
                    tramite.paso2(municipio_v, departamento_v);

                    HashMap hmTramite = new HashMap();
                    hmTramite.put(TRAMITE_TABLE, tramite.toJSONArray());

                    ArrayList<HashMap> tables = new ArrayList<HashMap>();
                    tables.add(hmTramite);
                    DB db = new DB(context, tables);
                    db.updateData(TRAMITE_TABLE, tramite.toJSONArray(), tramite.getId());

                    Intent intent = new Intent(Call_Form2Activity.this, Call_Form3Activity.class);
                    intent.putExtra("TRAMITE_PASO2", tramite);
                    startActivity(intent);
                }
            }
        });

    }
}
