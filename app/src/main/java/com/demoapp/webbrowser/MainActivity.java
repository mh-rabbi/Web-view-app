package com.demoapp.webbrowser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    WebView myWeb;
    EditText edtUrl;
    ImageView ivSearch;
    ProgressBar pbLoader;
    WebViewClient myWebClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myWeb = findViewById(R.id.my_web);
        edtUrl = findViewById(R.id.edt_url);
        ivSearch = findViewById(R.id.iv_search);
        pbLoader = findViewById(R.id.pb_loader);
        myWeb.loadUrl("https://youtube.com/");
        myWeb.getSettings().setJavaScriptEnabled(true);


        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = edtUrl.getText().toString();

                if (url.isEmpty()){
                    Toast.makeText(MainActivity.this, "You need to write something to search!", Toast.LENGTH_SHORT).show();

                }else{

                    String regex = "/^(?:(?:(?:https?|ftp):)?\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z0-9\\u00a1-\\uffff][a-z0-9\\u00a1-\\uffff_-]{0,62})?[a-z0-9\\u00a1-\\uffff]\\.)+(?:[a-z\\u00a1-\\uffff]{2,}\\.?))(?::\\d{2,5})?(?:[/?#]\\S*)?$/i";

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(url);

                    if (matcher.matches()){
                        myWeb.loadUrl(url);
                    }else{
                        myWeb.loadUrl("https://www.google.com/search?q="+ url);
                    }
                }
            }
        });

        myWebClient = new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                pbLoader.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                pbLoader.setVisibility(View.VISIBLE);
            }
        };
        myWeb.setWebViewClient(myWebClient);
    }

    @Override
    public void onBackPressed() {
        if (myWeb.canGoBack()){
            myWeb.goBack();
        }else{
            super.onBackPressed();
        }


    }
}