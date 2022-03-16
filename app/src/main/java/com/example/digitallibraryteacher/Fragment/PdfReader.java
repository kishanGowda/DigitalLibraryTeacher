package com.example.digitallibraryteacher.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.digitallibraryteacher.R;


public class PdfReader extends Fragment {
View view;
    String file,name;
    ImageView back;
    public PdfReader(String file, String name) {
        // Required empty public constructor
        this.file=file;
        this.name=name;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_pdf_reader, container, false);
        back=view.findViewById(R.id.back_question_pdf);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        TextView tv1 =view.findViewById(R.id.question_pdf_tool_bar);
        tv1.setText(name);
        web();
        return  view;
    }
    public  void  web(){
        WebView webView = view.findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://docs.google.com/viewer?url="+"https://test-digital-library.s3.ap-south-1.amazonaws.com/"+file);
        //   webView.loadUrl("https://youtu.be/mAtkPQO1FcA");
    }
}