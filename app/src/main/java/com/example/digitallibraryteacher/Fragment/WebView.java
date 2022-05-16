package com.example.digitallibraryteacher.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digitallibraryteacher.Model.FullScreenClient;
import com.example.digitallibraryteacher.R;

public class WebView extends AppCompatActivity {
    String link,title;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_youtube);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            link= extras.getString("key");
            title=extras.getString("title");
            //The key argument here must match that used in the other activity
        }
        back=findViewById(R.id.back_youtube_pdf);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





        webView();

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.videos);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
        TextView textView=findViewById(R.id.youtube_title_name);
        textView.setText(title);


    }
    public  void webView(){
        android.webkit.WebView webView=findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new FullScreenClient(WebView.this){
            @Override
            public void onHideCustomView()
            {
                hideFullScreen(webView);
            }
            @Override
            public Bitmap getDefaultVideoPoster()
            {
                return videoBitmap();
            }
            @Override
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
            {
                showFullScreen(view,callback);
            }

        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }

}