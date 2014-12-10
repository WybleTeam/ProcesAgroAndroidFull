package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wyble.procesagro.models.Convocatoria;

import java.io.Serializable;
import java.util.List;


public class ConvDetalle extends ActionBarActivity {

    private TextView tituloConv;
    private TextView descConv;
    private Button linkUrl;
    private Button facebookBtn;
    private Button twitterBtn;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conv_detalle);

        Serializable dataFromCallActivity = getIntent().getSerializableExtra("CONVOCATORIA_ITEM");
        final Convocatoria convocatoria = (Convocatoria) dataFromCallActivity;

        tituloConv = (TextView) findViewById(R.id.tituloConv);
        descConv = (TextView) findViewById(R.id.descConv);
        linkUrl = (Button) findViewById(R.id.linkUrl);
        facebookBtn = (Button) findViewById(R.id.facebookBtn);
        twitterBtn = (Button) findViewById(R.id.twitterBtn);

        tituloConv.setText(convocatoria.getTitulo());
        descConv.setText(convocatoria.getDescripcionLarga());
        descConv.setMovementMethod(new ScrollingMovementMethod());

        linkUrl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ConvDetalle.this, WebViewActivity.class);
                intent.putExtra("URL_PARAMETER", convocatoria.getUrl());
                startActivity(intent);
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("->","Facebook");
               // String convo_string = convocatoria.getTitulo().toString();
                //String convo = convo_string.substring(0,118);
                //String urlToShare = convocatoria.getUrl();
                String urlToShare = "#ProcesAgro , "+ convocatoria.getTitulo() +", mas información "+convocatoria.getUrl();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
                intent.putExtra(Intent.EXTRA_TEXT, urlToShare);

                // See if official Facebook app is found
                boolean facebookAppFound = false;
                List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
                for (ResolveInfo info : matches) {
                    Log.d("->","xxxxxx");
                    if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                        intent.setPackage(info.activityInfo.packageName);
                        facebookAppFound = true;
                        break;
                    }
                }

                // As fallback, launch sharer.php in a browser
                if (!facebookAppFound) {
                    Toast.makeText(ConvDetalle.this, "No tiene instalada la aplicación de Facebook.", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("->","Twitter");
                //String urlToShare = convocatoria.getUrl();
                String urlToShare = "#ProcesAgro , "+convocatoria.getTitulo() +", mas información "+convocatoria.getUrl();
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
                    Toast.makeText(ConvDetalle.this, "No tiene instalada la aplicación de Twitter.", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });
    }
}
