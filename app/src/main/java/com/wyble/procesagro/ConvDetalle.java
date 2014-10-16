package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

                String urlToShare = convocatoria.getUrl();
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
                    String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                }
                startActivity(intent);
            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("->","Twitter");
                String urlToShare = convocatoria.getUrl();
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
                    Toast.makeText(ConvDetalle.this, "Twitter app not Insatlled in your mobile", Toast.LENGTH_SHORT).show();
                    //String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
                    //intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                }
                startActivity(intent);
            }
        });

    }


}
