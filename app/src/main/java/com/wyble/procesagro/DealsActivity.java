package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wyble.procesagro.helpers.CheckAdapter;
import com.wyble.procesagro.models.Oferta;
import com.wyble.procesagro.models.PasoOferta;

import java.io.Serializable;


public class DealsActivity extends ActionBarActivity {

    private Oferta oferta;
    private TextView tituloOferta;
    private TextView descripcionOferta;
    private ListView pasosOfertaListView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        Serializable dataFromMain = getIntent().getSerializableExtra("OFERTA");
        final Oferta oferta = (Oferta) dataFromMain;

        tituloOferta = (TextView) findViewById(R.id.deal_title);
        descripcionOferta = (TextView) findViewById(R.id.deal_descripcion);

        tituloOferta.setText(oferta.getTitulo());
        descripcionOferta.setText(oferta.getDescripcion());
        descripcionOferta.setMovementMethod(new ScrollingMovementMethod());

        CheckAdapter mAdapter = new CheckAdapter(this, R.layout.check_list_item, oferta.getPasosOferta());
        pasosOfertaListView = (ListView) findViewById(R.id.pasoslistView);
        pasosOfertaListView.setAdapter(mAdapter);

        pasosOfertaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckAdapter adapter = (CheckAdapter) parent.getAdapter();
                PasoOferta pasoOferta = adapter.getItem(position);

                Intent intent = new Intent(DealsActivity.this, StepsDetailsActivity.class);
                intent.putExtra("PASO_OFERTA", pasoOferta);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent v = new Intent(this, MainActivity.class);
        startActivity(v);
    }
}
