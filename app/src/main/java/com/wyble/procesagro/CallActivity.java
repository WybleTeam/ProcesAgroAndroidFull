package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyble.procesagro.models.Convocatoria;

import java.io.Serializable;
import java.util.ArrayList;

public class CallActivity extends ActionBarActivity {

    private ListView convListView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        Serializable dataFromMainActivity = getIntent().getSerializableExtra("CONVOCATORIAS");
        final ArrayList<Convocatoria> convocatorias = (ArrayList<Convocatoria>) dataFromMainActivity;

        convListView = (ListView) findViewById(R.id.convListView);
        ArrayAdapter<Convocatoria> arrayAdapter = new ArrayAdapter<Convocatoria>(this, R.layout.convocatoria_text_view, convocatorias);
        convListView.setAdapter(arrayAdapter);

        convListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CallActivity.this, ConvDetalle.class);
                Convocatoria item = convocatorias.get(position);
                intent.putExtra("CONVOCATORIA_ITEM", item);
                startActivity(intent);
            }
        });
    }
}
