package com.example.digitallibraryteacher.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.digitallibraryteacher.Adapter.VideosAdapterTwo;
import com.example.digitallibraryteacher.Api.ApiClient;
import com.example.digitallibraryteacher.Api.Content;
import com.example.digitallibraryteacher.Api.GetLibraryResponse;
import com.example.digitallibraryteacher.Api.LoginService;
import com.example.digitallibraryteacher.Model.VideoModelTwo;
import com.example.digitallibraryteacher.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class VideosFragment extends Fragment {

    View view;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Retrofit retrofit;
    LoginService loginService;
    ArrayList<VideoModelTwo> videoModel;
    VideosAdapterTwo adapter1;
    LinearLayout novideo;
    String subjectName, standardName, topicName, chapterName, sectionName;
    int chapterId, topicId, standardId, subjectId;



    public VideosFragment(int topicId, int chapterId, int standardId) {
            this.topicId=topicId;
            this.chapterId=chapterId;
            this.standardId=standardId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_videos, container, false);

        novideo=view.findViewById(R.id.no_video_avialbale);
        apiInit();
        getLibrary();
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayoutvideos);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        apiInit();
                        getLibrary();
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

    public void getLibrary() {
        Call<GetLibraryResponse> call = loginService.getLibraryCall_notes(topicId,standardId,chapterId,"video");
        call.enqueue(new Callback<GetLibraryResponse>() {
            @Override
            public void onResponse(Call<GetLibraryResponse> call, Response<GetLibraryResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                GetLibraryResponse getLibraryResponse = response.body();
                ArrayList<Content> contents = getLibraryResponse.contents;
                int length=contents.size();
                Log.i("videoSize",String.valueOf(length));

                int size=getLibraryResponse.contents.size();
                Log.i("size", String.valueOf(size));
                if(size==0){
                    novideo.setVisibility(View.VISIBLE);
                }
                else
                {
                    videoModel=new ArrayList<>();
                    for(int i=0;i<=size-1;i++){
                        videoModel.add(new VideoModelTwo(R.drawable.videos,R.drawable.ic_baseline_more_vert_24,getLibraryResponse.contents.get(i).title,"12:00",getLibraryResponse.contents.get(i).link,getLibraryResponse.contents.get(i).file,getLibraryResponse.contents.get(i).status));

                    }
                    buildR();
                }


            }
            @Override
            public void onFailure(Call<GetLibraryResponse> call, Throwable t) {
                Toast.makeText(getContext(),"error", Toast.LENGTH_LONG).show();

            }
        });
    }

    public  void buildR(){
        recyclerView=view.findViewById(R.id.video_rvvv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new VideosAdapterTwo(videoModel,getContext());
        recyclerView.setAdapter(adapter1);}

}