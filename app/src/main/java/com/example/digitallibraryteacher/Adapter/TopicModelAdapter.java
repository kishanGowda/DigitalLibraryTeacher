package com.example.digitallibraryteacher.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.digitallibraryteacher.Api.LoginService;
import com.example.digitallibraryteacher.Fragment.LibraryFragment;
import com.example.digitallibraryteacher.Model.TopicModel;
import com.example.digitallibraryteacher.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class TopicModelAdapter extends RecyclerView.Adapter<TopicModelAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<TopicModel> courseModalArrayList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    String standardId;
    int subjectName;
    View sheetView;
     BottomSheetDialog bt,btnew;
     int subjectId,chapterId;
     LoginService loginService;
     Retrofit retrofit;
    BottomSheetDialog mBottomSheetDialog;
//    String chapterId,standardName,section,chapterName;

    // creating a constructor for our variables.
    public TopicModelAdapter(ArrayList<TopicModel> courseModalArrayList, Context context, int subjectId, int chapterId, String standardId) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
        this.subjectId=subjectId;
        this.chapterId=chapterId;
        this.standardId=standardId;

    }


    public void filterList(ArrayList<TopicModel> filterllist) {

        courseModalArrayList = filterllist;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item, parent, false);
        return new ViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TopicModel modal = courseModalArrayList.get(position);
        holder.aboutTopic.setText(modal.getCourseDescription());
        holder.topicNoteCount.setText(String.valueOf(modal.getNcount()));
        holder.topicVideoCount.setText(String.valueOf(modal.getV()));
        holder.topicQuestionCount.setText(String.valueOf(modal.getQcount()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topicName=modal.getCourseDescription();
                int topicId= Integer.valueOf(modal.getTopicId());
                Log.i("topicId",String.valueOf(topicId));
                Fragment fragment = new LibraryFragment(topicId,Integer.valueOf(standardId),chapterId,topicName);
                FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.your_placeholder, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });




    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView aboutTopic, topicNoteCount, topicVideoCount, topicQuestionCount;
        private ImageView edit;

        public ViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            // initializing our views with their ids.

            aboutTopic = itemView.findViewById(R.id.about_chapter);
            topicNoteCount = itemView.findViewById(R.id.notes_c);
            topicVideoCount = itemView.findViewById(R.id.video_c);
            topicQuestionCount = itemView.findViewById(R.id.question_c);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(listener!=null){
//                        int position=getAbsoluteAdapterPosition();
//                        if(position!=RecyclerView.NO_POSITION){
//                            listener.onItemClickListener(position);
//
//                        }
//
//                    }
//                }
//            });

        }
    }
    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener=  listener;
    }
//    public void apiInit() {
//
//        retrofit = ApiClient.getRetrofit();
//        loginService = ApiClient.getApiService();
//
//    }
}

