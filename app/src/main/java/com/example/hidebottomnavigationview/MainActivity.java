package com.example.hidebottomnavigationview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MainActivity extends AppCompatActivity {

    NestedScrollWebView webView;
    BottomNavigationView navView;
    ImageView refresh,stop;
    ProgressBar progressBar;
    EditText et;
    boolean canExit;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                     = new BottomNavigationView.OnNavigationItemSelectedListener() {

                 @Override
                 public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                     switch (item.getItemId()) {
                         case R.id.forward_arrow:
                                if(webView != null){
                                    webView.goForward();
                                }
                             return true;
                         case R.id.back_arrow:
                             if(webView != null){
                                 webView.goBack();
                             }

                             return true;
                         case R.id.menu:

                             return true;

                         case R.id.refresh:
                             if(webView != null){
                                 webView.reload();
                             }

                             return true;

                         case R.id.home:

                             return true;
                     }
                     return false;
                 }
             };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);

        et = findViewById(R.id.et);
        et.setSelectAllOnFocus(true);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    webView.loadUrl(et.getText().toString());
                }

                return false;
            }
        });
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setMax(100);

        refresh = findViewById(R.id.refresh);
        stop =  findViewById(R.id.stop);

        refresh.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.GONE);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView != null){
                    webView.reload();
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView != null){
                    webView.stopLoading();
                }

            }
        });

        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setNestedScrollingEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress < 100 && progressBar.getVisibility() == progressBar.GONE) {
                    progressBar.setVisibility(progressBar.VISIBLE);
                    stop.setVisibility(View.VISIBLE);
                    refresh.setVisibility(View.GONE);

                }
                if (newProgress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                    stop.setVisibility(View.GONE);
                    refresh.setVisibility(View.VISIBLE);

                }else{
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                    stop.setVisibility(View.VISIBLE);
                    refresh.setVisibility(View.GONE);

                }


            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
              et.setText(view.getUrl());
            }

        });
        webView.loadUrl("https://www.google.com");
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }

        if(canExit)
            super.onBackPressed();
        else{
            canExit = true;
            Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        mHandler.sendEmptyMessageDelayed(1, 2000/*time interval to next press in milli second*/);
    }

    public Handler mHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 1:
                    canExit = false;
                    break;
                default:
                    break;
            }
        }
    };
}
