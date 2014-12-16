package com.wyble.procesagro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.models.Tramite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Call_Form6Activity extends ActionBarActivity {

    private Button form6_next;
    private EditText primera_vez, nacimiento, compra_animales, perdida_din;
    Context context= this;
    private static final String TRAMITE_TABLE = "tramites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form6);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Serializable dataFromPaso5 = getIntent().getSerializableExtra("TRAMITE_PASO5");
        final Tramite tramite = (Tramite) dataFromPaso5;

        primera_vez = (EditText) findViewById(R.id.primera_vez);
        nacimiento = (EditText) findViewById(R.id.nacimiento);
        compra_animales = (EditText) findViewById(R.id.compra_animales);
        perdida_din = (EditText) findViewById(R.id.perdida_din);

        if(tramite.getPrimeraVez() == 0){

        }else {
            primera_vez.setText(String.valueOf(tramite.getPrimeraVez()));
        }
        if(tramite.getNacimiento() == 0){

        }else {
            nacimiento.setText(String.valueOf(tramite.getNacimiento()));
        }
        if(tramite.getCompra() == 0){

        }else {
            compra_animales.setText(String.valueOf(tramite.getCompra()));
        }
        if(tramite.getPerdidaDIN() == 0){

        }else {
            perdida_din.setText(String.valueOf(tramite.getPerdidaDIN()));
        }

        form6_next = (Button) findViewById(R.id.form6_next);
        form6_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String primera_vez_v= primera_vez.getText().toString().trim();
                if(primera_vez_v.equals("")){
                    primera_vez_v = "0";
                }
                String nacimiento_v= nacimiento.getText().toString().trim();
                if(nacimiento_v.equals("")){
                    nacimiento_v = "0";
                }
                String compra_animales_v= compra_animales.getText().toString().trim();
                if(compra_animales_v.equals("")){
                    compra_animales_v = "0";
                }
                String perdida_din_v= perdida_din.getText().toString().trim();
                if(perdida_din_v.equals("")){
                    perdida_din_v = "0";
                }

                    tramite.paso6(Integer.parseInt(primera_vez_v),
                            Integer.parseInt(nacimiento_v),
                            Integer.parseInt(compra_animales_v),
                            Integer.parseInt(perdida_din_v));


                    if (tramite.validaSumaBovinosBufalinosMotivos()) {
                        HashMap hmTramite = new HashMap();
                        hmTramite.put(TRAMITE_TABLE, tramite.toJSONArray());

                        ArrayList<HashMap> tables = new ArrayList<HashMap>();
                        tables.add(hmTramite);
                        DB db = new DB(context, tables);
                        db.updateData(TRAMITE_TABLE, tramite.toJSONArray(), tramite.getId());

                        Intent intent = new Intent(Call_Form6Activity.this, CallFinishActivity.class);
                        intent.putExtra("TRAMITE_PASO6", tramite);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "La suma de las cantidades debe ser igual a la suma total de bovinos y bufalinos.", Toast.LENGTH_LONG).show();
                    }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Serializable dataFromPaso5 = getIntent().getSerializableExtra("TRAMITE_PASO5");

        final Tramite tramite = (Tramite) dataFromPaso5;
        Intent v = new Intent(this, Call_Form5Activity.class);
        v.putExtra("TRAMITE_PASO4", tramite);
        startActivity(v);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // ID del boton
                Serializable dataFromPaso5 = getIntent().getSerializableExtra("TRAMITE_PASO5");

                final Tramite tramite = (Tramite) dataFromPaso5;
                Intent v = new Intent(this, Call_Form5Activity.class);
                v.putExtra("TRAMITE_PASO4", tramite);
                startActivity(v);
                finish(); // con finish terminamos el activity actual, con lo que volvemos
                // al activity anterior (si el anterior no ha sido cerrado)
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
