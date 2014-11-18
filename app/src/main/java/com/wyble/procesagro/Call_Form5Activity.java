package com.wyble.procesagro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.models.Tramite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Call_Form5Activity extends ActionBarActivity {

    private int suma_bovinos;
    private Button form5_next;
    private EditText bufalino1, bufalino2, bufalino3, bufalino4;
    Context context= this;
    private static final String TRAMITE_TABLE = "tramites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form5);

        Serializable dataFromPaso4 = getIntent().getSerializableExtra("TRAMITE_PASO4");
        final Tramite tramite = (Tramite) dataFromPaso4;
        Intent intent = getIntent();
        suma_bovinos = intent.getIntExtra("total_bovino", 0);

        bufalino1 = (EditText) findViewById(R.id.bufalino1);
        bufalino2 = (EditText) findViewById(R.id.bufalino2);
        bufalino3 = (EditText) findViewById(R.id.bufalino3);
        bufalino4 = (EditText) findViewById(R.id.bufalino4);

        bufalino1.setText(String.valueOf(tramite.getMenor1Bufalino()));
        bufalino2.setText(String.valueOf(tramite.getEntre12Bufalino()));
        bufalino3.setText(String.valueOf(tramite.getEntre23Bufalino()));
        bufalino4.setText(String.valueOf(tramite.getMayor3Bufalino()));

        form5_next = (Button) findViewById(R.id.form5_next);
        form5_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String bufalino1_v= bufalino1.getText().toString().trim();
                final String bufalino2_v= bufalino2.getText().toString().trim();
                final String bufalino3_v= bufalino3.getText().toString().trim();
                final String bufalino4_v= bufalino4.getText().toString().trim();
                if(bufalino1_v.equals("") || bufalino2_v.equals("") || bufalino3_v.equals("") || bufalino4_v.equals("")){
                    Toast.makeText(context, "Todos los campos son requeridos.", Toast.LENGTH_SHORT).show();
                }else {
                    tramite.paso5(Integer.parseInt(bufalino1_v),
                            Integer.parseInt(bufalino2_v),
                            Integer.parseInt(bufalino3_v),
                            Integer.parseInt(bufalino4_v));

                    int sumaBufis = sumaBufalinos(bufalino1_v, bufalino2_v, bufalino3_v, bufalino4_v);
                    Log.d("->", "->" + sumaBufis);

                    HashMap hmTramite = new HashMap();
                    hmTramite.put(TRAMITE_TABLE, tramite.toJSONArray());

                    ArrayList<HashMap> tables = new ArrayList<HashMap>();
                    tables.add(hmTramite);
                    DB db = new DB(context, tables);
                    db.updateData(TRAMITE_TABLE, tramite.toJSONArray(), tramite.getId());

                    int suma_total = suma_bovinos + sumaBufis;

                    if(suma_total > 0){
                        Intent intent = new Intent(Call_Form5Activity.this, Call_Form6Activity.class);
                        intent.putExtra("TRAMITE_PASO5", tramite);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Call_Form5Activity.this, "Ingrese al menos un Bovino o Bufalino antes de continuar." , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public Integer sumaBufalinos(String b1, String b2, String b3, String b4){
        int sumaBuf = 0;
        int bufalino1_v = Integer.parseInt(b1);
        int bufalino2_v = Integer.parseInt(b2);
        int bufalino3_v = Integer.parseInt(b3);
        int bufalino4_v = Integer.parseInt(b4);
        sumaBuf = bufalino1_v + bufalino2_v + bufalino3_v + bufalino4_v;
        return sumaBuf;
    }
}
