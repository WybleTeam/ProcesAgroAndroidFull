package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyble.procesagro.models.PasoOferta;

import java.io.Serializable;


public class StepsDetailsActivity extends ActionBarActivity {

    private TextView title;

    private TextView description;

    private Button linkBtn;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);

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
}
