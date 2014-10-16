package com.wyble.procesagro;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewActivity extends ActionBarActivity {
    WebView webView1_aux;
    String url_setter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Bundle dataFromHomeActivity= getIntent().getExtras();
        url_setter= dataFromHomeActivity.getString("URL_PARAMETER");

        webView1_aux= (WebView) findViewById(R.id.webView1);
        WebSettings webSettings = webView1_aux.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView1_aux.loadUrl(url_setter);
        webView1_aux.setWebViewClient(new WebViewClient());
    }
}
