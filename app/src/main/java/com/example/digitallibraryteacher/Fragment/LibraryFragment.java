package com.example.digitallibraryteacher.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.example.digitallibraryteacher.Adapter.ContentAdapter;
import com.example.digitallibraryteacher.Api.ApiClient;
import com.example.digitallibraryteacher.Api.GetLibraryResponse;
import com.example.digitallibraryteacher.Api.LoginService;
import com.example.digitallibraryteacher.Model.ContentModel;
import com.example.digitallibraryteacher.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LibraryFragment extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    LoginService loginService;
    Retrofit retrofit;
    Toolbar toolbar;
    public int notes, video, question;
    RecyclerView recyclerView;
    ImageView back;
    ContentAdapter adapter1;
    GetLibraryResponse getLibraryResponse;
    ArrayList<ContentModel>contentModels;
TextView topic;
    RecyclerView.LayoutManager layoutManager;


    private int chapterId, topicId, standardId,subjectId;
    String subjectName,topicName,chapterName,standardName,sectionName;

    public LibraryFragment(int topicId, int standardId, int chapterId, String topicName) {
        this.topicId= topicId;
        this.standardId=standardId;
        this.chapterId= chapterId;
        this.topicName= topicName;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_library, container, false);
        topic=view.findViewById(R.id.topic_libary_name);
        topic.setText(topicName);
        Log.i("t1", String.valueOf(topicId));

        Log.i("chap1", String.valueOf(chapterId));
        Log.i("standardddd", String.valueOf(standardId));
        Log.i("topicname", topicName);


        apiInit();
        back = view.findViewById(R.id.library_activity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

       getLibrary();


        return view;
    }

    public void apiInit() {
        retrofit = ApiClient.getRetrofit();
        loginService = ApiClient.getApiService();
    }


    public void getLibrary() {

        Call<GetLibraryResponse> call = loginService.getLibraryCall(topicId,chapterId, standardId);
        call.enqueue(new Callback<GetLibraryResponse>() {
            @Override
            public void onResponse(Call<GetLibraryResponse> call, Response<GetLibraryResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                }
                getLibraryResponse = response.body();
                contentModels = new ArrayList<>();
                notes = Integer.parseInt(getLibraryResponse.totalCount.get(0).notesCount);
                video = Integer.parseInt(getLibraryResponse.totalCount.get(0).videoCount);
                question = Integer.parseInt(getLibraryResponse.totalCount.get(0).quesBankCount);
                Log.i("notes", String.valueOf(notes));
                Log.i("videos", String.valueOf(video));
                Log.i("ques", String.valueOf(question));
                contentModels.add(new ContentModel(R.drawable.lecturenotes, String.valueOf(notes), "Lecture notes",String.valueOf(getLibraryResponse.lastWeekLectureNotesCount+" from last week")));
                contentModels.add(new ContentModel(R.drawable.lecturevideos, String.valueOf(video), "Videos",String.valueOf(getLibraryResponse.lastWeekVideoCount+" from last week")));
                contentModels.add(new ContentModel(R.drawable.questionbank, String.valueOf(question), "Question Bank",String.valueOf(getLibraryResponse.lastWeekQuestionBankCount+" from last week")));
                build();

                FragmentManager fm = getParentFragmentManager();
                ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle());
                final ViewPager2 pa =view. findViewById(R.id.pager);
                pa.setAdapter(sa);
                TabLayout tabLayout = view.findViewById(R.id.tabLayout);
                //
                tabLayout.addTab(tabLayout.newTab().setText(String.valueOf("LECTURE NOTES ("+notes+")")));
                tabLayout.addTab(tabLayout.newTab().setText(String.valueOf("VIDEOS ("+video+")")));
                tabLayout.addTab(tabLayout.newTab().setText(String.valueOf("QUESTION BANKS ("+question+")")));
                //
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        pa.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                pa.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        tabLayout.selectTab(tabLayout.getTabAt(position));
                    }
                });


            }
            @Override
            public void onFailure(Call<GetLibraryResponse> call, Throwable t) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();

            }
        });

    }


    private void build() {
        recyclerView = view.findViewById(R.id.recy1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        adapter1 = new ContentAdapter(contentModels, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter1);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private class ViewStateAdapter extends FragmentStateAdapter {

        public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Hardcoded in this order, you'll want to use lists and make sure the titles match
            if (position == 0) {
                LibraryLecture fragment = new LibraryLecture(topicId, chapterId,standardId);
                return fragment;


            }
            if (position == 1) {
                return new VideosFragment(topicId, chapterId,standardId);
            } else {
                return new Question(topicId, chapterId,standardId);
            }
        }

        @Override
        public int getItemCount() {
            // Hardcoded, use lists
            return 3;
        }
    }

}