package com.example.digitallibraryteacher.Api;

import java.util.ArrayList;

public class GetTeacherResponse {
    public ArrayList<Datum2> data;
    public int lastWeekLectureNotesCount;
    public int lastWeekVideoCount;
    public int lastWeekQuestionBankCount;
    public ArrayList<Subject2> subjects;
    public ArrayList<AnalyticsData> analyticsData;
    public ArrayList<ActiveTime> activeTime;

    public ArrayList<Datum2> getData() {
        return data;
    }

    public int getLastWeekLectureNotesCount() {
        return lastWeekLectureNotesCount;
    }

    public int getLastWeekVideoCount() {
        return lastWeekVideoCount;
    }

    public int getLastWeekQuestionBankCount() {
        return lastWeekQuestionBankCount;
    }

    public ArrayList<Subject2> getSubjects() {
        return subjects;
    }

    public ArrayList<AnalyticsData> getAnalyticsData() {
        return analyticsData;
    }

    public ArrayList<ActiveTime> getActiveTime() {
        return activeTime;
    }
}
