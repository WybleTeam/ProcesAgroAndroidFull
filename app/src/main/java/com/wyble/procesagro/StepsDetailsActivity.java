package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyble.procesagro.models.PasoOferta;

import java.io.Serializable;


public class StepsDetailsActivity extends ActionBarActivity {

    private TextView title;

    private TextView description;

    private Button linkBtn;
    static MediaPlayer mPlayer;
    Button btnPlay;
    Button buttonStop;
    Boolean repro = false;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Serializable dataFromOferta = getIntent().getSerializableExtra("PASO_OFERTA");
        final PasoOferta pasoOferta = (PasoOferta) dataFromOferta;

        title = (TextView) findViewById(R.id.step_detail_title);
        description = (TextView) findViewById(R.id.step_detail_description);
        linkBtn = (Button) findViewById(R.id.ofertaLinkUrl);

        title.setText(pasoOferta.getTitulo());
        description.setText(pasoOferta.getDescripcion());

        linkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StepsDetailsActivity.this, WebViewActivity.class);
                intent.putExtra("URL_PARAMETER", pasoOferta.getUrl());
                startActivity(intent);
            }
        });


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
