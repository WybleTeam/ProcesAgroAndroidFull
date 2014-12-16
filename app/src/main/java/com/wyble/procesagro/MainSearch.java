package com.wyble.procesagro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wyble.procesagro.helpers.CustomAdapter;
import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.models.Convocatoria;
import com.wyble.procesagro.models.Oferta;
import com.wyble.procesagro.models.PasoOferta;
import com.wyble.procesagro.models.Servicio;

import java.util.ArrayList;
import java.util.HashMap;

public class MainSearch extends ActionBarActivity implements ListView.OnItemClickListener {

    private String textToSearch;

    private ListView mainSearchListView;

    private CustomAdapter mAdapter;

    private DB db;

    private static final String CONVOCATORIAS_TABLE = "convocatorias";

    private static final String OFERTAS_TABLE = "ofertas";

    private static final String PASOS_OFERTAS_TABLE = "pasos_ofertas";

    private static final String SERVICIOS_TABLE = "servicios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

        Bundle dataFromMain = getIntent().getExtras();
        textToSearch = dataFromMain.getString("TEXTO_BUSCAR");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList tables = new ArrayList();
        db = new DB(this, tables);

        ArrayList<Convocatoria> convocatorias = this.getConvocatoriasByName(textToSearch);
        ArrayList<Oferta> ofertas = this.getOfertasByName(textToSearch);
        ArrayList<Servicio> servicios = this.getServiciosByName(textToSearch);

        mainSearchListView = (ListView) findViewById(R.id.mainSearchListView);
        mAdapter = new CustomAdapter(this);
        if (convocatorias.size() > 0) {
            mAdapter.addSectionHeaderItem("Convocatorias");
            for (Convocatoria c : convocatorias) {
                mAdapter.addItem(c);
            }
        }
        if (ofertas.size() > 0) {
            mAdapter.addSectionHeaderItem("Ofertas");
            for (Oferta o : ofertas) {
                mAdapter.addItem(o);
            }
        }
        if (servicios.size() > 0) {
            mAdapter.addSectionHeaderItem("Servicios");
            for (Servicio s : servicios) {
                mAdapter.addItem(s);
            }
        }
        if (convocatorias.size() == 0 && ofertas.size() == 0 && servicios.size() == 0) {
            mAdapter.addSectionHeaderItem("No hay resultados");
        }
        mainSearchListView.setAdapter(mAdapter);
        mainSearchListView.setOnItemClickListener(this);
    }

    private ArrayList<Convocatoria> getConvocatoriasByName(String name) {
        ArrayList convocatorias = new ArrayList();
        ArrayList<HashMap> data = db.getDataByName(CONVOCATORIAS_TABLE, "tituloConvocatoria", name);
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

    private ArrayList<Oferta> getOfertasByName(String name) {
        ArrayList ofertas = new ArrayList();
        ArrayList<HashMap> data = db.getDataByName(OFERTAS_TABLE, "tituloOferta", name);
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

    private ArrayList<Servicio> getServiciosByName(String name) {
        ArrayList servicios = new ArrayList();
        ArrayList<HashMap> data = db.getDataByName(SERVICIOS_TABLE, "tituloServicio", name);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        CustomAdapter adapter = (CustomAdapter) parent.getAdapter();
        Object obj = adapter.getItem(position);
        if (obj.getClass().getName().equals("com.wyble.procesagro.models.Convocatoria")) {
            intent = new Intent(MainSearch.this, ConvDetalle.class);
            Convocatoria item = (Convocatoria) obj;
            intent.putExtra("CONVOCATORIA_ITEM", item);
        } else if (obj.getClass().getName().equals("com.wyble.procesagro.models.Oferta")) {
            intent = new Intent(MainSearch.this, DealsActivity.class);
            Oferta item = (Oferta) obj;
            intent.putExtra("OFERTA", item);
        } else if (obj.getClass().getName().equals("com.wyble.procesagro.models.Servicio")) {
            intent = new Intent(MainSearch.this, WebViewActivity.class);
            Servicio item = (Servicio) obj;
            intent.putExtra("URL_PARAMETER", item.getUrl_servicio());
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // ID del boton
                finish(); // con finish terminamos el activity actual, con lo que volvemos
                // al activity anterior (si el anterior no ha sido cerrado)
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
