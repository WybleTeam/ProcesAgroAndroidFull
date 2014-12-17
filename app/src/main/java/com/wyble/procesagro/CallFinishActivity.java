package com.wyble.procesagro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.models.Tramite;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class CallFinishActivity extends ActionBarActivity implements View.OnClickListener{

    private EditText justificacion;
    private CheckBox terminos;
    private Button finish;
    private Tramite tramite;
    private Context context= this;
    ProgressDialog mProgressDialog;
    private String conAcentos = "áéíóúñ";
    private String sinAcentos = "aeioun";

    String TRAMITE_URL = "http://154.70.153.108/proyectos/procesAgroWeb/web/app.php/crearFormulario/";
    private static final String TRAMITE_TABLE = "tramites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_finish);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Serializable dataFromPaso6 = getIntent().getSerializableExtra("TRAMITE_PASO6");
        tramite = (Tramite) dataFromPaso6;

        justificacion = (EditText) findViewById(R.id.justificacion);
        if (tramite.getPerdidaDIN() <= 0) {
            justificacion.setText("____________________________");
            justificacion.setTextColor(Color.parseColor("#FFFFFF"));
            justificacion.setEnabled(false);
            justificacion.setFocusable(false);
            justificacion.setKeyListener(null);
        } else {
            justificacion.setText(tramite.getJustificacion());
        }
        //terminos = (CheckBox) findViewById(R.id.terminos);
        //terminos.setChecked(tramite.getTerminos());

        finish= (Button) findViewById(R.id.endButt);
        finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String justificacionString = justificacion.getText().toString().trim();
        if (tramite.getPerdidaDIN() > 0) {
            tramite.paso7(justificacionString);
        } else {
            tramite.paso7("sin_justificacion.");
        }

        Boolean conx = this.checkConx(context);
        Log.d("xxxxxxx", "conx : "+ conx );

        if(conx == true){
            if(tramite.getPerdidaDIN() > 0 && justificacionString.isEmpty()){
                Toast.makeText(CallFinishActivity.this, "Ingrese una justificación para continuar.", Toast.LENGTH_SHORT).show();
            } else {
                if (justificacionString.length() < 20 || justificacionString.length() > 200) {
                    Toast.makeText(CallFinishActivity.this, "La justificación debe tener al menos 20 letras y máximo 200.", Toast.LENGTH_SHORT).show();
                    justificacion.requestFocus();
                } else {
                    new AsyncSaveData().execute();
                }
            }
        }else{
            Toast.makeText(context, "Conexión a internet no encontrada. Intente nuevamente..." , Toast.LENGTH_SHORT).show();
        }
    }

    private String join(ArrayList<String> r, String delimiter) {
        if(r == null || r.size() == 0 ){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int i, len = r.size() - 1;
        for (i = 0; i < len; i++){
            sb.append(r.get(i).toString().replaceAll(" ", "_") + delimiter);
        }
        return sb.toString() + r.get(i).toString().replaceAll(" ", "_");
    }

    public Boolean checkConx(Context ctx){
        ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }

    private class AsyncSaveData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(CallFinishActivity.this);
            mProgressDialog.setTitle("ProcesAgro");
            mProgressDialog.setMessage("Enviando información. gracias por su espera...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            HashMap hmTramite = new HashMap();
            hmTramite.put(TRAMITE_TABLE, tramite.toJSONArray());

            ArrayList<HashMap> tables = new ArrayList<HashMap>();
            tables.add(hmTramite);
            DB db = new DB(context, tables);
            db.updateData(TRAMITE_TABLE, tramite.toJSONArray(), tramite.getId());

            ArrayList<String> fields = new ArrayList();
            fields.add(tramite.getIca());
            fields.add(tramite.getNombreFinca());
            fields.add(tramite.getNombrePropietario());
            fields.add(tramite.getCedulaPropietario());
            fields.add(tramite.getFijoPropietario());
            fields.add(tramite.getCelularPropietario());
            fields.add(tramite.getMunicipio());
            fields.add(tramite.getDepartamento());
            fields.add(tramite.getNombreSolicitante());
            fields.add(tramite.getCedulaSolicitante());
            fields.add(tramite.getFijoSolicitante());
            fields.add(tramite.getCelularSolicitante());
            fields.add(Integer.toString(tramite.getMenor1Bovinos()));
            fields.add(Integer.toString(tramite.getEntre12Bovinos()));
            fields.add(Integer.toString(tramite.getEntre23Bovinos()));
            fields.add(Integer.toString(tramite.getMayores3Bovinos()));
            fields.add(Integer.toString(tramite.getMenor1Bufalino()));
            fields.add(Integer.toString(tramite.getEntre12Bufalino()));
            fields.add(Integer.toString(tramite.getEntre23Bufalino()));
            fields.add(Integer.toString(tramite.getMayor3Bufalino()));
            fields.add(Integer.toString(tramite.getPrimeraVez()));
            fields.add(Integer.toString(tramite.getNacimiento()));
            fields.add(Integer.toString(tramite.getCompra()));
            fields.add(Integer.toString(tramite.getPerdidaDIN()));
            fields.add(tramite.getJustificacion());
            fields.add(tramite.getVereda());


            String cadena = join(fields,"/");
            String finalCadena = removerAcentos(cadena);
            String complete_string = TRAMITE_URL+finalCadena;

            Log.d("//url long", "//url long : "+ complete_string );



            final HttpClient client = new DefaultHttpClient();

            final HttpPost httpGet = new HttpPost(complete_string);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpResponse response = client.execute(httpGet);
                        StatusLine statusLine = response.getStatusLine();
                        int statusCode = statusLine.getStatusCode();
                        if (statusCode == 200) {
                            Toast.makeText(context, "Información enviada con éxito", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Formulario guardado, intente enviarlo más tarde", Toast.LENGTH_LONG).show();
                        }
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Intent goToHome= new Intent(CallFinishActivity.this, MainActivity.class);
            startActivity(goToHome);
            return null;
        }
        protected void onPostExecute(Void result) {
            mProgressDialog.hide();
        }
    }


    private String removerAcentos(String input) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Serializable dataFromPaso6 = getIntent().getSerializableExtra("TRAMITE_PASO6");
        //tramite.paso7(justificacionString);
        justificacion.setText(tramite.getJustificacion());
        final Tramite tramite = (Tramite) dataFromPaso6;
        Intent v = new Intent(this, Call_Form6Activity.class);
        v.putExtra("TRAMITE_PASO5", tramite);
        startActivity(v);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // ID del boton
                Serializable dataFromPaso6 = getIntent().getSerializableExtra("TRAMITE_PASO6");
                //tramite.paso7(justificacionString);
                justificacion.setText(tramite.getJustificacion());
                final Tramite tramite = (Tramite) dataFromPaso6;
                Intent v = new Intent(this, Call_Form6Activity.class);
                v.putExtra("TRAMITE_PASO5", tramite);
                startActivity(v);
                finish(); // con finish terminamos el activity actual, con lo que volvemos
                // al activity anterior (si el anterior no ha sido cerrado)
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
