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


import com.example.digitallibraryteacher.Adapter.LecturerAdapterTwo;
import com.example.digitallibraryteacher.Api.ApiClient;
import com.example.digitallibraryteacher.Api.GetLibraryResponse;
import com.example.digitallibraryteacher.Api.LoginService;
import com.example.digitallibraryteacher.Model.LecturerModelTwo;
import com.example.digitallibraryteacher.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Question extends Fragment {
    ArrayList<LecturerModelTwo> lecturerModelTwo;
    LecturerAdapterTwo adapter;
    LinearLayoutManager layoutManager;
    View view;
    LinearLayout linearLayout;
    LoginService loginService;
    Retrofit retrofit;
    int topicId,chapterId;
    RecyclerView recyclerView;
    int standardId;


    public Question(int topicId, int chapterId, int standardId) {
        this.topicId=topicId;
        this.chapterId=chapterId;
        this.standardId=standardId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_library_lecture, container, false);
        linearLayout=view.findViewById(R.id.no_lecturer_notes_avialbale);
        apiInIt();
        getLibrary();
        return view;
    }
    public void apiInIt()
    {
        retrofit= ApiClient.getRetrofit();
        loginService=ApiClient.getApiService();
    }
    public void getLibrary(){
        Call<GetLibraryResponse> call=loginService.getLibraryCall_notes(topicId,standardId,chapterId,"question-bank");
        call.enqueue(new Callback<GetLibraryResponse>() {
            @Override
            public void onResponse(Call<GetLibraryResponse> call, Response<GetLibraryResponse> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                }
                GetLibraryResponse getLibraryResponse=response.body();

                int size=getLibraryResponse.contents.size();
                Log.i("size", String.valueOf(size));
                if(size==0){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    lecturerModelTwo=new ArrayList<>();
                    for(int i=0;i<=size-1;i++){
                        lecturerModelTwo.add(new LecturerModelTwo(getLibraryResponse.contents.get(i).title,getLibraryResponse.contents.get(i).id,getLibraryResponse.contents.get(i).file));
                    }
                    buildR();
                }

//                List<ContentGL> contentGL=getLibraryResponse.contents;
//                for(ContentGL chg:
//                        contentGL) {
//                    Toast.makeText(getApplicationContext(), String.valueOf(chg.title), Toast.LENGTH_SHORT).show();
                //}
            }

            @Override
            public void onFailure(Call<GetLibraryResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error in get library", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void buildR() {
        recyclerView = view.findViewById(R.id.lecturernotervv);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LecturerAdapterTwo(lecturerModelTwo, getContext());
        recyclerView.setAdapter(adapter);

    }

}