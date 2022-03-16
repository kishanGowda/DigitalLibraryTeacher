package com.example.digitallibraryteacher.Fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitallibraryteacher.Adapter.CountAdapter;
import com.example.digitallibraryteacher.Adapter.LecturerAdapter;
import com.example.digitallibraryteacher.Adapter.ParentRecyclerViewAdapter;
import com.example.digitallibraryteacher.Adapter.QusetionAdapter;
import com.example.digitallibraryteacher.Adapter.VideoAdapter;
import com.example.digitallibraryteacher.Api.ApiClient;
import com.example.digitallibraryteacher.Api.HomePageResponse;
import com.example.digitallibraryteacher.Api.LoginService;
import com.example.digitallibraryteacher.Model.CountModel;
import com.example.digitallibraryteacher.Model.LecturerModel;
import com.example.digitallibraryteacher.Model.ParentModel;
import com.example.digitallibraryteacher.Model.QuestionModel;
import com.example.digitallibraryteacher.Model.VideoModel;
import com.example.digitallibraryteacher.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomePage extends Fragment {
View view;

    ArrayList<CountModel> cardHori;
    HomePageResponse homePageGetAllStdResponse,overAllStateResponse;
    RecyclerView recyclerView1;
    Retrofit retrofit;
    LoginService loginService;
    CountAdapter cardHoriAdapter;
    LinearLayout foeLecturer,forVideo,forQuestion;
    RecyclerView recyclerView, recyclerViewVideo,recyclerViewQuestion,recyclerViewSubject;
    LinearLayoutManager layoutManager, layoutManagerVideo,layoutManagerQuestion,layoutManagerSubject;
    ArrayList<LecturerModel> lecturerModels;
    ArrayList<VideoModel> videoModels;
    ArrayList<QuestionModel> questionModels;
    VideoAdapter videoAdapter;
    LecturerAdapter lecturerAdapter;
    QusetionAdapter qusetionAdapter;
    ArrayList<ParentModel> parentModelArrayList;
    private RecyclerView.LayoutManager parentLayoutManager;
    private RecyclerView parentRecyclerView;
    private RecyclerView.Adapter ParentAdapter;
    ConstraintLayout cons;


    RecyclerView.LayoutManager layoutManager1;


    public HomePage() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_page, container, false);
        apiInit();
        getSubmitAns();
        getStudHomeP();
        standard();
        foeLecturer=view.findViewById(R.id.lect_linear);
        forVideo=view.findViewById(R.id.video_linear);
        forQuestion=view.findViewById(R.id.question_linear);
        return  view;
    }

    public void apiInit() {

        retrofit = ApiClient.getRetrofit();
        loginService = ApiClient.getApiService();

    }

    public void getSubmitAns() {
        Call<HomePageResponse> call = loginService.getHomepageCall();
        call.enqueue(new Callback<HomePageResponse>() {
            @Override
            public void onResponse(Call<HomePageResponse> call, Response<HomePageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                HomePageResponse homePageGetAllStdResponse = response.body();
                cardHori = new ArrayList<>();
                cardHori.add(new CountModel(R.drawable.notes,homePageGetAllStdResponse.getCount().notesCount,"Lecture",String.valueOf(homePageGetAllStdResponse.getCount().lastWeekLectureNotesCount+"  From last week")));
                cardHori.add(new CountModel(R.drawable.videos,homePageGetAllStdResponse.getCount().videoCount,"videos",String.valueOf(homePageGetAllStdResponse.getCount().lastWeekVideoCount+"  From last week")));
                cardHori.add(new CountModel(R.drawable.quebank,homePageGetAllStdResponse.getCount().quesBankCount,"Question",String.valueOf(homePageGetAllStdResponse.getCount().lastWeekQuestionBankCount+"  From last week")));
                buildRecyclerView();

            }

            @Override
            public void onFailure(Call<HomePageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error submit :(", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void buildRecyclerView() {

        recyclerView1 = view.findViewById(R.id.recyler1);
        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);
        cardHoriAdapter = new CountAdapter(cardHori);
        recyclerView1.setAdapter(cardHoriAdapter);
        recyclerView1.setNestedScrollingEnabled(false);
    }

    public void getStudHomeP()
    {
        Call<HomePageResponse> call=loginService.getHomepageCall();
        call.enqueue(new Callback<HomePageResponse>() {
            @Override
            public void onResponse(Call<HomePageResponse> call, Response<HomePageResponse> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                overAllStateResponse=response.body();

                int lecSize=overAllStateResponse.lectureNotes.size();
             int   length=overAllStateResponse.lectureNotes.size();
                Log.i("length", String.valueOf(length));
                if(length==0){
                }
                else{

                }
                Log.i("size", String.valueOf(lecSize));
                if(lecSize==0){
                    foeLecturer.setVisibility(View.GONE);
                }
                else {
                    lecturerModels=new ArrayList<>();
                    for (int i = 0; i <= overAllStateResponse.lectureNotes.size() - 1; i++) {
                        lecturerModels.add(new LecturerModel(R.drawable.sbj_chemistry,overAllStateResponse.lectureNotes.get(i).topic.name,overAllStateResponse.lectureNotes.get(i).chapter.name,overAllStateResponse.lectureNotes.get(i).subject.name,overAllStateResponse.lectureNotes.get(i).file));
                    }
                    build();


                }
                if(overAllStateResponse.video.size()==0) {
                    forVideo.setVisibility(View.GONE);
                }else{
                    videoModels=new ArrayList<>();
                    for (int j=0;j<=overAllStateResponse.video.size()-1;j++){

                        videoModels.add(new VideoModel(R.drawable.mapchem,overAllStateResponse.video.get(j).topic.name,overAllStateResponse.video.get(j).chapter.name,overAllStateResponse.video.get(j).subject.name,overAllStateResponse.video.get(j).link,overAllStateResponse.video.get(j).title,overAllStateResponse.video.get(j).status,overAllStateResponse.video.get(j).file));
                    }
                    buildVideo();
                }
                if(overAllStateResponse.questionBank.size()==0) {
                    forQuestion.setVisibility(View.GONE);
                }else{
                    questionModels=new ArrayList<>();
                    for (int j=0;j<=overAllStateResponse.questionBank.size()-1;j++){
                        questionModels.add(new QuestionModel(R.drawable.question_banks,overAllStateResponse.questionBank.get(j).topic.name,overAllStateResponse.questionBank.get(j).chapter.name,overAllStateResponse.questionBank.get(j).subject.name,overAllStateResponse.questionBank.get(j).file));
                    }
                    buildQuestion();
                }

            }
            @Override
            public void onFailure(Call<HomePageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error fail in home page", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void build(){
        recyclerView = view.findViewById(R.id.for_ln_rv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        lecturerAdapter = new LecturerAdapter(lecturerModels,getActivity());
        recyclerView.setAdapter(lecturerAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }
    public void buildVideo(){
        recyclerViewVideo=view.findViewById(R.id.for_v_rv);
        recyclerViewVideo.setHasFixedSize(true);
        layoutManagerVideo = new GridLayoutManager(getContext(),2);
        recyclerViewVideo.setLayoutManager(layoutManagerVideo);
        videoAdapter = new VideoAdapter(videoModels,getContext());
        recyclerViewVideo.setAdapter(videoAdapter);
        recyclerViewVideo.setNestedScrollingEnabled(false);
    }


    public void buildQuestion(){
        recyclerViewQuestion=view.findViewById(R.id.for_q_rv);
        recyclerViewQuestion.setHasFixedSize(true);
        layoutManagerQuestion = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewQuestion.setLayoutManager(layoutManagerQuestion);
        qusetionAdapter= new QusetionAdapter(questionModels,getContext());
        recyclerViewQuestion.setAdapter(qusetionAdapter);
        recyclerViewQuestion.setNestedScrollingEnabled(false);
    }

    public void standard() {
        Call<HomePageResponse> call = loginService.getHomepageCall();
        call.enqueue(new Callback<HomePageResponse>() {
            @Override
            public void onResponse(Call<HomePageResponse> call, Response<HomePageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                homePageGetAllStdResponse = response.body();
                ArrayList<String> standard=new ArrayList();
                for(int i=0;i<=homePageGetAllStdResponse.data.size()-1;i++) {
                    standard.add(homePageGetAllStdResponse.data.get(i).std_std);
                }
                Set values=new HashSet(standard);
                Log.i("tag",values.toString());
                ArrayList<String> uni=new ArrayList<>(values);
                parentModelArrayList = new ArrayList<>();
                for (int i = 0; i <= values.size()-1; i++) {
                    parentModelArrayList.add(new ParentModel(uni.get(i)));
                }
                buildRecyclerView2();

            }

            @Override
            public void onFailure(Call<HomePageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error submit :(", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void buildRecyclerView2() {
        parentRecyclerView = view.findViewById(R.id.rv_parent);
        parentRecyclerView.setHasFixedSize(true);
        parentLayoutManager = new LinearLayoutManager(getContext());
        ParentAdapter = new ParentRecyclerViewAdapter(parentModelArrayList, getContext(), homePageGetAllStdResponse, cons);
        parentRecyclerView.setLayoutManager(parentLayoutManager);
        parentRecyclerView.setAdapter(ParentAdapter);
        ParentAdapter.notifyDataSetChanged();
    }

}