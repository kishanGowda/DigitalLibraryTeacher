package com.example.digitallibraryteacher.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.digitallibraryteacher.R;

public class YoutubeFragment extends Fragment {

    String link,title;
    View view;
    ImageView back;


    public YoutubeFragment(String link, String title) {
        this.link=link;
        this.title=title;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_youtube, container, false);


        back=view.findViewById(R.id.back_youtube_pdf);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        TextView textView=view.findViewById(R.id.youtube_title_name);
        textView.setText(title);

        WebView webView = view.findViewById(R.id.web_youtube);
        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new Browser());
        webView.setWebChromeClient(new MyWebClient());
        webView.loadUrl(link);



        return  view;
    }
    class Browser
            extends WebViewClient
    {
        Browser() {}

        public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
        {
            paramWebView.loadUrl(paramString);
            return true;
        }
    }

    public class MyWebClient
            extends WebChromeClient
    {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        public MyWebClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (YoutubeFragment.this == null) {
                return null;
            }
            return BitmapFactory.decodeResource(YoutubeFragment.this.getActivity().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)YoutubeFragment.this.getView()).removeView(this.mCustomView);
            this.mCustomView = null;
            YoutubeFragment.this.getActivity().getWindow().setContentView(this.mOriginalSystemUiVisibility);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }


    }
}