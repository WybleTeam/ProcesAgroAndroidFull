package com.wyble.procesagro;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wyble.procesagro.models.CursoVirtual;

import java.io.Serializable;
import java.util.List;


public class CursoDetalle extends ActionBarActivity {

    private TextView tituloCurso;
    private TextView descCurso;
    private Button linkUrlCurso;
    private Button facebookBtn;
    private Button twitterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso_detalle);

        Serializable dataFromCallActivity = getIntent().getSerializableExtra("CURSO_VIRTUAL_ITEM");
        final CursoVirtual cursoVirtual = (CursoVirtual) dataFromCallActivity;

        tituloCurso = (TextView) findViewById(R.id.tituloCurso);
        descCurso = (TextView) findViewById(R.id.descCurso);
        linkUrlCurso = (Button) findViewById(R.id.linkUrlCurso);
        facebookBtn = (Button) findViewById(R.id.facebookBtnCurso);
        twitterBtn = (Button) findViewById(R.id.twitterBtnCurso);

        tituloCurso.setText(cursoVirtual.getNombreCurso());
        descCurso.setText(cursoVirtual.getDescripcionCurso());

        linkUrlCurso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CursoDetalle.this, WebViewActivity.class);
                intent.putExtra("URL_PARAMETER", cursoVirtual.getUrlCurso());
                startActivity(intent);
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("->", "Facebook");

                String urlToShare = cursoVirtual.getUrlCurso();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
                intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

                // See if official Facebook app is found
                boolean facebookAppFound = false;
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }

                // As fallback, launch sharer.php in a browser
                if (!facebookAppFound) {
                    Toast.makeText(CursoDetalle.this, "No tiene instalada la aplicación de Facebook.", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("->","Twitter");
                String urlToShare = cursoVirtual.getUrlCurso();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
                intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

                // See if official Facebook app is found
                boolean twitterAppFound = false;
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                        intent.setPackage(info.activityInfo.packageName);
                        twitterAppFound = true;
                        break;
                    }
                }

                // As fallback, launch sharer.php in a browser
                if (!twitterAppFound) {
                    Toast.makeText(CursoDetalle.this, "No tiene instalada la aplicación de Twitter.", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

    }
}
