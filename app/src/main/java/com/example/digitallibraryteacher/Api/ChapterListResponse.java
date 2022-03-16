package com.example.digitallibraryteacher.Api;

import java.util.ArrayList;

public class ChapterListResponse {
    public int chapterCount;
    public ArrayList<Chapter2> chapters;

    public int getChapterCount() {
        return chapterCount;
    }

    public ArrayList<Chapter2> getChapters() {
        return chapters;
    }
}
