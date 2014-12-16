package com.wyble.procesagro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.models.Departamento;
import com.wyble.procesagro.models.Municipio;
import com.wyble.procesagro.models.Tramite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Call_Form2Activity extends ActionBarActivity {

    private Button form2_next;
    //private EditText municipio, departamento;
    private Spinner municipio, departamento;
    private EditText vereda;
    Context context= this;
    private static final String TRAMITE_TABLE = "tramites";
    private String iddepartamento = null;
    private DB db;
    String id_dpto;
    String spinDepa;

    private ArrayList<HashMap> tables;
    private ArrayList<Municipio> MunicipioSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form2);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Serializable dataFromPaso1 = getIntent().getSerializableExtra("TRAMITE_PASO1");

        final Tramite tramite = (Tramite) dataFromPaso1;

        tables = new ArrayList<HashMap>();

        db = new DB(this,tables);

        departamento = (Spinner) findViewById(R.id.departamento);
        municipio = (Spinner) findViewById(R.id.municipio);
        vereda = (EditText) findViewById(R.id.vereda);



        ArrayAdapter<Municipio> adaptadorMun =
                new ArrayAdapter<Municipio>(Call_Form2Activity.this, android.R.layout.simple_list_item_1,getMunicipio());


        municipio.setAdapter(adaptadorMun);

        ArrayAdapter<Departamento> adaptador =
                new ArrayAdapter<Departamento>(this, android.R.layout.simple_list_item_1,getDepartamentos());

        departamento.setAdapter(adaptador);
        departamento.setOnItemSelectedListener(new SpinnerLis());

    if(tramite.getDepartamento().isEmpty()){

    }else {
        String idDepartamento = tramite.getDepartamento();
        String sub_Posicdpto = idDepartamento.substring(0,1);
        Log.d("sub_dtpo", sub_Posicdpto);
        String trim_Posdpto = sub_Posicdpto.trim();
        spinDepa = trim_Posdpto;
        departamento.setSelection(Integer.parseInt(spinDepa) - 1);

        MunicipioSpin = getMunicipio2(spinDepa);

        Integer i = 0;
        for (Municipio municipio1 : MunicipioSpin = getMunicipio2(spinDepa)) {

            Log.d("recorr", "Recorriendo el municipio..." + municipio1.getId() + " " + municipio1.getNombreMunicipio());
            String MunEncontrado = municipio1.getNombreMunicipio();
            String Municipio = tramite.getMunicipio();

            if (MunEncontrado.equals(Municipio)) {
                municipio.setSelection(i);
                Log.d("revision", "lo encontre ..." + Municipio + " Posicion " + i);
            } else {
                Log.d("recor", "No encontrado " + tramite.getMunicipio().toString().trim() + "-" + MunEncontrado.trim());
            }
            i = i + 1;
        }

        vereda.setText(tramite.getVereda());
    }

        form2_next = (Button) findViewById(R.id.form2_next);
        form2_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String municipio_v = municipio.getSelectedItem().toString();
                String departamento_v = departamento.getSelectedItem().toString();
                String vereda_v = vereda.getText().toString().trim();
                //if(vereda_v.equals("")){
                //    vereda_v = "_";
                //}
                Log.d("//log", "//log" + municipio_v + " - " + departamento_v);

                if(vereda_v.equals("")){
                    Toast.makeText(context, "Escriba una Vereda.", Toast.LENGTH_SHORT).show();
                }else{


                    tramite.paso2(municipio_v, departamento_v, vereda_v);

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

    private ArrayList<Departamento> getDepartamentos() {
        ArrayList departamentos = new ArrayList();
        ArrayList<HashMap> data = db.getAllData("departamentos");
        for (HashMap d : data) {
            departamentos.add(new Departamento(
                    Integer.parseInt(d.get("id").toString()),
                    d.get("nombreDepartamento").toString()
            ));
        }

        this.db.close();
        return departamentos;
    }


    private ArrayList<Municipio> getMunicipio() {
        ArrayList municipios = new ArrayList();
        ArrayList<HashMap> data = db.getAllData("municipios");
        for (HashMap d : data) {
            municipios.add(new Municipio(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("nombreMunicipio").toString(),
                    d.get("departamento").toString()
            ));
        }

        this.db.close();
        return municipios;
    }

    private ArrayList<Municipio> getMunicipio2(String iddepartamento) {
        ArrayList municipios = new ArrayList();
        ArrayList<HashMap> data = db.getDataByName("municipios","departamento", iddepartamento);
        for (HashMap d : data) {
            municipios.add(new Municipio(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("nombreMunicipio").toString(),
                    d.get("departamento").toString()
            ));
        }

        this.db.close();
        return municipios;
    }

    public class SpinnerLis implements AdapterView.OnItemSelectedListener {
        private long itemIdAtPosition;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            Serializable dataFromPaso1 = getIntent().getSerializableExtra("TRAMITE_PASO1");

            final Tramite tramite = (Tramite) dataFromPaso1;

            TextView tex = (TextView) findViewById(R.id.tituloUbicacion);

            tex.setText("DEPARTAMENTO :" + tramite.getDepartamento().toString() +"\n"+"MUNICIPIO: "+tramite.getMunicipio().toString()+"\n"+"VEREDA: " + tramite.getVereda().toString().toUpperCase());
            //cargaSpinnerMunicipio(parent.);
            Log.d("mensaje", "spinner data"+departamento.getSelectedItem().toString());
            String dpto = departamento.getSelectedItem().toString();
            Log.d("Depart",dpto);
            String sub_dpto = dpto.substring(0,1);
            Log.d("sub_dtpo",sub_dpto);
            String trim_dpto = sub_dpto.trim();
            id_dpto = trim_dpto;
            cargaSpinnerMunicipio(id_dpto);


            MunicipioSpin = getMunicipio2(id_dpto);

            Integer i=0;
            for (Municipio municipio1 : MunicipioSpin = getMunicipio2(id_dpto)) {

                Log.d("recorr","Recorriendo el municipio..."+municipio1.getId()+" "+municipio1.getNombreMunicipio());
                String MunEncontrado = municipio1.getNombreMunicipio();
                String Municipio = tramite.getMunicipio();

                if(MunEncontrado.equals(Municipio)){
                    municipio.setSelection(i);
                    Log.d("revision","lo encontre ciclo..."+Municipio+" Posicion "+i);
                }else{
                    Log.d("recor","No encontrado "+tramite.getMunicipio().toString().trim()+"-"+MunEncontrado.trim());
                }
                i = i + 1;
            }
        }

        private void cargaSpinnerMunicipio(String selectedItemPosition) {
            //String item = Integer.toString(selectedItemPosition);
            ArrayAdapter<Municipio> adaptadorMun =
                    new ArrayAdapter<Municipio>(Call_Form2Activity.this, android.R.layout.simple_list_item_1,getMunicipio2(selectedItemPosition));

            municipio.setAdapter(adaptadorMun);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }



    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Serializable dataFromPaso1 = getIntent().getSerializableExtra("TRAMITE_PASO1");

        final Tramite tramite = (Tramite) dataFromPaso1;
        Intent intentToForm = new Intent(this, Call_Form1Activity.class);
        intentToForm.putExtra("TRAMITE", tramite);
        startActivity(intentToForm);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // ID del boton
                Serializable dataFromPaso1 = getIntent().getSerializableExtra("TRAMITE_PASO1");

                final Tramite tramite = (Tramite) dataFromPaso1;
                Intent intentToForm = new Intent(this, Call_Form1Activity.class);
                intentToForm.putExtra("TRAMITE", tramite);
                startActivity(intentToForm);
                finish(); // con finish terminamos el activity actual, con lo que volvemos
                // al activity anterior (si el anterior no ha sido cerrado)
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


