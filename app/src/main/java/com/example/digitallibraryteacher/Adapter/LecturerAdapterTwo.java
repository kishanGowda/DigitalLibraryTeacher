package com.example.digitallibraryteacher.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitallibraryteacher.Fragment.WebView;
import com.example.digitallibraryteacher.Model.LecturerModelTwo;
import com.example.digitallibraryteacher.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class LecturerAdapterTwo extends RecyclerView.Adapter<LecturerAdapterTwo.MyviewHolder> {
    ArrayList<LecturerModelTwo> lecturerModels;
    Context context;
    String pdfFile;
    BottomSheetDialog bt;
    Retrofit retrofit;
    private OnItemClickListener onItemClickListener;
    String standardName,sectionName,subjectName,chapterName,topicName;
    int standardId,subjectId,chapterId,topicId;

    public LecturerAdapterTwo(ArrayList<LecturerModelTwo> lecturerModelTwo, Context context) {
       lecturerModels=lecturerModelTwo;
      this.context=context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecturer_note_item, parent, false);
        return new MyviewHolder(view,onItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, @SuppressLint("RecyclerView") int position) {
        LecturerModelTwo modal = lecturerModels.get(position);
        holder.content.setText(modal.getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String file=  modal.getFile();
                Log.i("file", file);
                String name=modal.getContent();
                String link= "https://docs.google.com/viewer?url="+"https://test-digital-library.s3.ap-south-1.amazonaws.com/"+ modal.getFile();
                String title=modal.getContent();
                Intent intent = new Intent(context, WebView.class);
                intent.putExtra("key",link);
                intent.putExtra("title",title);
                context.startActivity(intent);
//                Fragment fragment = new PdfReader(file,name);
//                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lecturerModels.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {


        TextView content;

        public MyviewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            content = itemView.findViewById(R.id.lecturer_note_text);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (listener != null) {
//                        int position = getAbsoluteAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
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

    }

