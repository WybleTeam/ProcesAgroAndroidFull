package com.wyble.procesagro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.helpers.Webservice;
import com.wyble.procesagro.models.Convocatoria;
import com.wyble.procesagro.models.CursoVirtual;
import com.wyble.procesagro.models.Oferta;
import com.wyble.procesagro.models.PasoOferta;
import com.wyble.procesagro.models.Servicio;
import com.wyble.procesagro.models.Tramite;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity{

    //private static final String SERVICIO1_URL = "http://google.com/";

    private static final String SERVICIO2_URL = "http://www.agronet.gov.co/agronetweb1/Agromapas.aspx";

    private static final String SERVICIO3_URL = "https://www.dane.gov.co/index.php/agropecuario-alias/sistema-de-informacion-de-precios-sipsa";

    private static final String SERVICIO4_URL = "http://www.siembra.gov.co/";

    private static final String CONVOCATORIAS_URL = "http://tucompualdia.com/aplicaciones/procesAgroWebService/convocatorias.php";

    private static final String OFERTAS_URL = "http://tucompualdia.com/aplicaciones/procesAgroWebService/ofertasinstitucionales.php";

    private static final String PASOS_OFERTAS_URL = "http://tucompualdia.com/aplicaciones/procesAgroWebService/pasosofertas.php";

    private static final String SERVICIOS_URL = "http://tucompualdia.com/aplicaciones/procesAgroWebService/servicios.php";

    private static final String CURSOS_VIRTUALES_URL = "http://tucompualdia.com/aplicaciones/procesAgroWebService/cursosvirtuales.php";

    private static final String CONVOCATORIAS_TABLE = "convocatorias";

    private static final String OFERTAS_TABLE = "ofertas";

    private static final String PASOS_OFERTAS_TABLE = "pasos_ofertas";

    private static final String SERVICIOS_TABLE = "servicios";

    private static final String TRAMITE_TABLE = "tramites";

    private static final String CURSOS_VIRTUALES_TABLE = "cursos_virtuales";

    private ArrayList<HashMap> tables;

    public ArrayList<Convocatoria> convocatorias;

    private DB db;

    private Oferta oferta1;

    private Oferta oferta2;

    private Oferta oferta3;

    private Oferta oferta4;

    private Tramite tramite;

    private EditText searchText;

    private Button searchBtn;

    Button callView1;
    Button callView2;
    Button callView3;
    Button callView4;
    public Button callView5; //public for access using other Class
    Button callView6;
    Button callView7;
    Button callView8;
    Button callView9;
    Button callView10;
    Button AboutCall;
    Button UpdateCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Just for testing, allow network access in the main thread
        // NEVER use this is productive code
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        tables = new ArrayList<HashMap>();

        Webservice wsConvocatorias = new Webservice(CONVOCATORIAS_URL);
        Webservice wsOfertas = new Webservice(OFERTAS_URL);
        Webservice wsPasosOfertas = new Webservice(PASOS_OFERTAS_URL);
        Webservice wsServicios = new Webservice(SERVICIOS_URL);
        Webservice wsCursosVirt = new Webservice(CURSOS_VIRTUALES_URL);

        final HashMap<String, JSONArray> hmConvocatorias = new HashMap();
        final HashMap<String, JSONArray> hmOfertas = new HashMap();
        final HashMap<String, JSONArray> hmPasosOfertas = new HashMap();
        final HashMap<String, JSONArray> hmServicios = new HashMap();
        final HashMap<String, JSONArray> hmCursosVirt = new HashMap();
        HashMap<String, JSONArray> hmTramite = new HashMap();

        hmConvocatorias.put(CONVOCATORIAS_TABLE, wsConvocatorias.parseJsonText(wsConvocatorias.getJsonText()));
        hmOfertas.put(OFERTAS_TABLE, wsOfertas.parseJsonText(wsOfertas.getJsonText()));
        hmPasosOfertas.put(PASOS_OFERTAS_TABLE, parsePasosOfertasJsonText(wsPasosOfertas.getJsonText()));
        hmServicios.put(SERVICIOS_TABLE, wsServicios.parseJsonText(wsServicios.getJsonText()));
        hmCursosVirt.put(CURSOS_VIRTUALES_TABLE, wsCursosVirt.parseJsonText(wsCursosVirt.getJsonText()));
        Tramite initTramite = new Tramite();
        hmTramite.put(TRAMITE_TABLE, initTramite.toJSONArray());

        tables.add(hmConvocatorias);
        tables.add(hmOfertas);
        tables.add(hmPasosOfertas);
        tables.add(hmServicios);
        tables.add(hmCursosVirt);
        tables.add(hmTramite);

        db = new DB(this, tables);


        convocatorias = this.getConvocatorias();
        ArrayList<Oferta> ofertas = this.getOfertas();
        ArrayList<Tramite> tramites = this.getTramites();

        if (tramites.size() > 0) {
            tramite = tramites.get(tramites.size() - 1);
        } else {
            tramite = initTramite;
        }

        oferta1 = ofertas.get(0);
        oferta2 = ofertas.get(1);
        oferta3 = ofertas.get(2);
        oferta4 = ofertas.get(3);

        searchText = (EditText) findViewById(R.id.editText);
        searchBtn = (Button) findViewById(R.id.buscar_button);

        callView1= (Button) findViewById(R.id.row1_button1);//row1
        callView2= (Button) findViewById(R.id.row1_button2);//row1
        callView3= (Button) findViewById(R.id.row2_button1);//row2
        callView4= (Button) findViewById(R.id.row2_button2);//row2

        callView5= (Button) findViewById(R.id.row3_button1);//row3


        Integer ANDROID_VERSION = android.os.Build.VERSION.SDK_INT;
        Log.d("ANDROID-VERSION", "=====>" + ANDROID_VERSION);

        if(ANDROID_VERSION >= 11){
            Log.d("Version-validator", "Version superior a 10");
            Timer myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            new AsyncWS().execute();
                        }
                    });
                }
            }, 0, 5000);
        }else{
            Log.d("Version-validator", "Version igual o menor a 10");
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(convocatorias.size());
            callView5.setText(convocatorias.get(index).getTitulo() + "\n" + convocatorias.get(index).getDescripcionCorta());
            Log.d(MainActivity.class.getName(), convocatorias.get(index).getTitulo() + "\n" + convocatorias.get(index).getDescripcionCorta());

            Toast toast = Toast.makeText(MainActivity.this,"Su versión de Android no soporta algunas funciones avanzadas.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        callView6= (Button) findViewById(R.id.row4_button1);//row4
        callView7= (Button) findViewById(R.id.row5_button1);//row5
        callView8= (Button) findViewById(R.id.row5_button2);//row5
        callView9= (Button) findViewById(R.id.row6_button1);//row6
        callView10= (Button) findViewById(R.id.row6_button2);//row6
        AboutCall= (Button) findViewById(R.id.acercade_btn);//About button
        UpdateCall= (Button) findViewById(R.id.actualizar_btn);//Update button

        callView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<CursoVirtual> cursosVirtuales = getCursosVirtuales();
                Intent intent = new Intent(MainActivity.this, CursosVirtuales.class);
                intent.putExtra("CURSOS_VIRTUALES", cursosVirtuales);
                startActivity(intent);
            }
        });

        callView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("URL_PARAMETER", SERVICIO2_URL);
                startActivity(intent);
            }
        });

        callView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("URL_PARAMETER", SERVICIO3_URL);
                startActivity(intent);
            }
        });

        callView4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("URL_PARAMETER", SERVICIO4_URL);
                startActivity(intent);
            }
        });

        callView5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToCall = new Intent(MainActivity.this, CallActivity.class);
                intentToCall.putExtra("CONVOCATORIAS", convocatorias);
                startActivity(intentToCall);
            }
        });

        callView6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToForm = new Intent(MainActivity.this, Call_Form1Activity.class);
                intentToForm.putExtra("TRAMITE", tramite);
                startActivity(intentToForm);
            }
        });

        callView7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToDeals1 = new Intent(MainActivity.this, DealsActivity.class);
                intentToDeals1.putExtra("OFERTA", oferta1);
                startActivity(intentToDeals1);
            }
        });

        callView8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToDeals2 = new Intent(MainActivity.this, DealsActivity.class);
                intentToDeals2.putExtra("OFERTA", oferta2);
                startActivity(intentToDeals2);
            }
        });

        callView9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToDeals3 = new Intent(MainActivity.this, DealsActivity.class);
                intentToDeals3.putExtra("OFERTA", oferta3);
                startActivity(intentToDeals3);
            }
        });

        callView10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToDeals4 = new Intent(MainActivity.this, DealsActivity.class);
                intentToDeals4.putExtra("OFERTA", oferta4);
                startActivity(intentToDeals4);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String textToSearch = String.valueOf(searchText.getText());
                Intent intentToMainSearch = new Intent(MainActivity.this, MainSearch.class);
                intentToMainSearch.putExtra("TEXTO_BUSCAR", textToSearch);
                startActivity(intentToMainSearch);
            }
        });

        AboutCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentToAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentToAbout);
            }
        });

        UpdateCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "La información se está actualizando.", Toast.LENGTH_LONG).show();
                db.initDataTable(hmConvocatorias);
                db.initDataTable(hmOfertas);
                db.initDataTable(hmPasosOfertas);
                db.initDataTable(hmServicios);
                db.initDataTable(hmCursosVirt);
            }
        });
    }

    private JSONArray parsePasosOfertasJsonText(String jsonText) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonText);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    jsonObject.put("isChecked", "false");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private ArrayList<Convocatoria> getConvocatorias() {
        ArrayList convocatorias = new ArrayList();
        ArrayList<HashMap> data = db.getAllData(CONVOCATORIAS_TABLE);
        for (HashMap d : data) {
            convocatorias.add(new Convocatoria(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("tituloConvocatoria").toString(),
                    d.get("descripcion").toString(),
                    d.get("descripcionLarga").toString(),
                    d.get("urlConvocatoria").toString(),
                    d.get("usuario_id").toString()
            ));
        }

        db.close();
        return convocatorias;
    }

    private ArrayList<CursoVirtual> getCursosVirtuales() {
        ArrayList cursosVirtuales = new ArrayList();
        ArrayList<HashMap> data = db.getAllData(CURSOS_VIRTUALES_TABLE);
        for (HashMap d : data) {
            cursosVirtuales.add(new CursoVirtual(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("nombreCurso").toString(),
                    d.get("descripcionCurso").toString(),
                    d.get("urlAudio").toString(),
                    d.get("urlCurso").toString()
            ));
        }

        db.close();
        return cursosVirtuales;
    }

    private ArrayList<Oferta> getOfertas() {
        ArrayList ofertas = new ArrayList();
        ArrayList<HashMap> data = db.getAllData(OFERTAS_TABLE);
        for (HashMap d : data) {
            ofertas.add(new Oferta(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("usuario_id").toString(),
                    d.get("tituloOferta").toString(),
                    d.get("descripcionOferta").toString(),
                    d.get("urlAudioOferta").toString(),
                    d.get("urlOferta").toString(),
                    getPasosOfertaByOfertaId(d.get("id").toString())
            ));
        }

        /*{"id":"1","usuario_id":"2","tituloOferta":"COMPRA DE ARTICULOS","descripcionOferta":"sadasdas","urlAudioOferta":"http:\/\/gos.com","urlOferta":"http:\/\/gos.com"},*/

        db.close();
        return ofertas;
    }

    private ArrayList<PasoOferta> getPasosOfertaByOfertaId(String id) {
        ArrayList<PasoOferta> pasosOfertas = new ArrayList<PasoOferta>();
        ArrayList<HashMap> data = db.getDataByValue(PASOS_OFERTAS_TABLE, "ofertaInstitucional_id", id);
        for (HashMap d : data) {
            pasosOfertas.add(new PasoOferta(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("tituloPasos").toString(),
                    d.get("descripcionPaso").toString(),
                    d.get("urlPaso").toString(),
                    Boolean.parseBoolean(d.get("isChecked").toString())
            ));
        }
        db.close();
        return pasosOfertas;
    }

    private ArrayList<PasoOferta> getPasosOferta() {
        ArrayList pasosOfertas = new ArrayList();
        ArrayList<HashMap> data = db.getAllData(PASOS_OFERTAS_TABLE);
        for (HashMap d : data) {
            pasosOfertas.add(new PasoOferta(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("tituloPasos").toString(),
                    d.get("descripcionPaso").toString(),
                    d.get("urlPaso").toString(),
                    Boolean.parseBoolean(d.get("isChecked").toString())
            ));
        }
        db.close();
        return pasosOfertas;
    }

    private ArrayList<Servicio> getServicios() {
        ArrayList servicios = new ArrayList();
        ArrayList<HashMap> data = db.getAllData(SERVICIOS_TABLE);
        for (HashMap d : data) {
            servicios.add(new Servicio(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("usuario_id").toString(),
                    d.get("tituloServicio").toString(),
                    d.get("descripcionServicio").toString(),
                    d.get("urlAudioServicio").toString(),
                    d.get("urlServicio").toString()
            ));
        }

        /*{"id":"2","usuario_id":"2","tituloServicio":"Servicio 1","descripcionServicio":"DEscripcion del servicio","urlAudioServicio":"https:\/\/www.google.com\/glass\/start\/","urlServicio":"http:\/\/audio.com"}*/

        db.close();
        return servicios;
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

    private class AsyncWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("Iniciando Thread convocatoria", "1.->Iniciando Thread convocatoria");
        }

        protected Void doInBackground(Void... params) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Random randomGenerator = new Random();
                    int index = randomGenerator.nextInt(convocatorias.size());
                    callView5.setText(convocatorias.get(index).getTitulo() + "\n" + convocatorias.get(index).getDescripcionCorta());
                    //Log.d(MainActivity.class.getName(), convocatorias.get(index).getTitulo() + "\n" + convocatorias.get(index).getDescripcionCorta());
                    Log.d("Procesando Thread Convocatoria", "2.->Procesando Thread Convocatoria");
                }
            });
            return null;
        }

        protected void onPostExecute(Void result) {
            Log.d("Terminando Thread Convocatoria", "3.->Terminando Thread Convocatoria");
        }
    }

}
