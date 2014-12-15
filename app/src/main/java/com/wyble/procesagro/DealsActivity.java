package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wyble.procesagro.helpers.CheckAdapter;
import com.wyble.procesagro.models.Oferta;
import com.wyble.procesagro.models.PasoOferta;

import java.io.IOException;
import java.io.Serializable;

import static com.wyble.procesagro.R.drawable;


public class DealsActivity extends ActionBarActivity {

    private Oferta oferta;
    private TextView tituloOferta;
    private TextView descripcionOferta;
    private ListView pasosOfertaListView;
    private Button linkBtn;


    static MediaPlayer mPlayer;
    Button btnPlay;
    Button buttonStop;
    Boolean repro = false;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);

        Serializable dataFromMain = getIntent().getSerializableExtra("OFERTA");
        final Oferta oferta = (Oferta) dataFromMain;
        final String url = oferta.getUrl_audio();
        tituloOferta = (TextView) findViewById(R.id.deal_title);
        descripcionOferta = (TextView) findViewById(R.id.deal_descripcion);

        tituloOferta.setText(oferta.getTitulo());
        descripcionOferta.setText(oferta.getDescripcion());
        descripcionOferta.setMovementMethod(new ScrollingMovementMethod());
        linkBtn = (Button) findViewById(R.id.ofertaLinkUrl2);
        btnPlay = (Button) findViewById(R.id.btnPlay);

        repro = false;

        btnPlay.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(!repro){
                            mPlayer = new MediaPlayer();
                            repro = true;
                            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    try {
                                        mPlayer.setDataSource(url);
                                    } catch (IllegalArgumentException e) {
                                        Toast.makeText(getApplicationContext(), "No hay audio!", Toast.LENGTH_LONG).show();
                                    } catch (SecurityException e) {
                                        Toast.makeText(getApplicationContext(), "No hay audio!", Toast.LENGTH_LONG).show();
                                    } catch (IllegalStateException e) {
                                        Toast.makeText(getApplicationContext(), "No hay audio!", Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        mPlayer.prepare();
                                    } catch (IllegalStateException e) {
                                        Toast.makeText(getApplicationContext(), "No hay audio!", Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        Toast.makeText(getApplicationContext(), "No hay audio!", Toast.LENGTH_LONG).show();
                                    }
                            mPlayer.start();
                            Toast.makeText(getApplicationContext(), "Cargando audio...", Toast.LENGTH_LONG).show();
                            btnPlay.setBackgroundDrawable(getResources().getDrawable(drawable.pause));
                }else{
                    repro = false;

                    if(mPlayer!=null && mPlayer.isPlaying()){
                        mPlayer.pause();
                        btnPlay.setBackgroundDrawable(getResources().getDrawable(drawable.play_icon));
                    }
                }
            }});



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

        linkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DealsActivity.this, WebViewActivity.class);
                intent.putExtra("URL_PARAMETER", oferta.getUrl());
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

    protected void onDestroy() {
        super.onDestroy();
        // TODO Auto-generated method stub
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
