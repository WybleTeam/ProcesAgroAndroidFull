package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wyble.procesagro.helpers.CursoAdapter;
import com.wyble.procesagro.models.CursoVirtual;

import java.io.Serializable;
import java.util.ArrayList;


public class CursosVirtuales extends ActionBarActivity {

    private ListView cursosListView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos_virtuales);

        Serializable dataFromMainActivity = getIntent().getSerializableExtra("CURSOS_VIRTUALES");
        final ArrayList<CursoVirtual> cursosVirt = (ArrayList<CursoVirtual>) dataFromMainActivity;

        cursosListView = (ListView) findViewById(R.id.cursosListView);
        //ArrayAdapter<CursoVirtual> arrayAdapter = new ArrayAdapter<CursoVirtual>(this, R.layout.convocatoria_text_view, cursosVirt);
        CursoAdapter arrayAdapter = new CursoAdapter(this, R.layout.curso_virtual_text_view, cursosVirt);
        cursosListView.setAdapter(arrayAdapter);

        cursosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CursosVirtuales.this, CursoDetalle.class);
                CursoVirtual item = cursosVirt.get(position);
                intent.putExtra("CURSO_VIRTUAL_ITEM", item);
                startActivity(intent);
            }
        });
    }
}
