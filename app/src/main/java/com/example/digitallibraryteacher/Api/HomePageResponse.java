package com.example.digitallibraryteacher.Api;

import java.util.ArrayList;
import java.util.Date;


public class HomePageResponse {
        public ArrayList<Datum> data;
        public Count count;
        public ArrayList<LectureNote> lectureNotes;
        public ArrayList<Video> video;
        public ArrayList<QuestionBank> questionBank;

    public ArrayList<Datum> getData() {
        return data;
    }

    public Count getCount() {
        return count;
    }

    public ArrayList<LectureNote> getLectureNotes() {
        return lectureNotes;
    }

    public ArrayList<Video> getVideo() {
        return video;
    }

    public ArrayList<QuestionBank> getQuestionBank() {
        return questionBank;
    }
}


