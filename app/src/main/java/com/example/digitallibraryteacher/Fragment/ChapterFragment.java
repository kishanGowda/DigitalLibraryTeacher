package com.example.digitallibraryteacher.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitallibraryteacher.Adapter.ChapterDetailsAdapter;
import com.example.digitallibraryteacher.Api.ApiClient;
import com.example.digitallibraryteacher.Api.ChapterListResponse;
import com.example.digitallibraryteacher.Api.LoginService;
import com.example.digitallibraryteacher.Model.ChapterDetails;
import com.example.digitallibraryteacher.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChapterFragment extends Fragment {
    View view;
    LoginService loginService;
    Retrofit retrofit;
    RecyclerView recyclerView;
    ChapterDetailsAdapter chapterDetailsAdapter;
    ArrayList<ChapterDetails> chapterdetails;
    RecyclerView.LayoutManager layoutManager;
    int subjectId;
    LinearLayout linearLayout;
    String standardName2,section2;
    Button addTeacher,later;




    //accessing imageview for adding te teacher
    ImageView setting;
    //back
    ImageView back;
    String standardid;

    String section, standard,standardId,subjectName;

    public ChapterFragment(String section, String standard, String standardId, int subjectId, String subjectName) {
        // Required empty public constructor
        this.section=section;
        this.standard=standard;
        this.standardId=standardId;
        this.subjectId=subjectId;
        this.subjectName=subjectName;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chapter, container, false);
        TransitionInflater inflate = TransitionInflater.from(requireContext());
        setExitTransition(inflate.inflateTransition(R.transition.fade));
        Log.i("standardId", standardId);
        Log.i("subjectId", String.valueOf(subjectId));
       //back
        back=view.findViewById(R.id.back_chapter_detials);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


// Load and use views afterwards
        TextView tv1 =view.findViewById(R.id.subject_name);
        tv1.setText(subjectName);

        apiInit();
        createCard();
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayoutChapter);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        createCard();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );





        return view;
    }

    public void apiInit() {
        retrofit = ApiClient.getRetrofit();
        loginService = ApiClient.getApiService();
    }

    public void createCard() {
        Log.i("subjectId", String.valueOf(subjectId));
        Log.i("standardID", String.valueOf(standardId));
        chapterdetails = new ArrayList<>();
        Call<ChapterListResponse> call = loginService.chapterListCall(subjectId, Integer.valueOf(standardId));

        call.enqueue(new Callback<ChapterListResponse>() {
            @Override
            public void onResponse(Call<ChapterListResponse> call, Response<ChapterListResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                ChapterListResponse chapterListResponse = response.body();
                int length = chapterListResponse.chapters.size();
                Log.i("TAG", String.valueOf(chapterListResponse.getChapterCount()));
                if(length==0){
                    linearLayout=view.findViewById(R.id.no_chapter_avialbale_);
                    linearLayout.setVisibility(View.VISIBLE);

                }
                else {
                    for (int i = 0; i <= chapterListResponse.chapters.size()-1; i++) {
                        chapterdetails.add(new ChapterDetails(String.valueOf(chapterListResponse.chapters.get(i).getChapterNo()),
                                String.valueOf(chapterListResponse.chapters.get(i).getTopicCount()),
                                String.valueOf(chapterListResponse.chapters.get(i).getChapterName()),
                                Integer.valueOf(chapterListResponse.chapters.get(i).getNotesCount()),
                                Integer.valueOf(chapterListResponse.chapters.get(i).getVideoCount()),
                                Integer.valueOf(chapterListResponse.chapters.get(i).getQuesBankCount()),
                                Integer.valueOf(chapterListResponse.chapters.get(i).getChapterId())));
                    }
                }
                buildRecyclerView();
            }
            @Override
            public void onFailure(Call<ChapterListResponse> call, Throwable t) {

            }
        });
    }
    public void buildRecyclerView() {
        recyclerView = view.findViewById(R.id.subject_rvv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        chapterDetailsAdapter = new ChapterDetailsAdapter(chapterdetails, getContext(),subjectId,standardId,section2,standardName2,subjectName);
        recyclerView.setAdapter(chapterDetailsAdapter);
    }


}