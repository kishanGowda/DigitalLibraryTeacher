package com.example.digitallibraryteacher.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface LoginService {

    String token="Authorization:Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTE1NiwicGhvbmUiOiIrOTE4ODg0ODMxMjg0IiwidXJsIjoidGVzdC50aGVjbGFzc3Jvb20uYml6Iiwib3JnSWQiOiI0Y2IyNTA5ZC03MGY1LTQzNWUtODc5Mi1kMjQ5Mzc3NDNiNTMiLCJicm93c2VyTG9naW5Db2RlIjoiKzkxODg4NDgzMTI4NDExNTY4NzAwZDQ3Mi03MDY2LTRiNjYtYjk5ZS04ODk0N2RiYjNiMzQiLCJkZXZpY2VMb2dpbkNvZGUiOm51bGwsImlhdCI6MTY0NzI0OTcwMH0.HA5EFFeQKyLVJ7dCE9DlBsdCc1vWxJIuA9z-DXh7pBY";
    String link="orgurl:test.theclassroom.biz";


    @Headers({token,link})
    @GET("admin-library/class")
    Call<HomePageResponse> getHomepageCall();

    //get standard by id get

    @Headers({token,link})
    @GET("admin-library/standardById")
    Call<GetTeacherResponse> standardCall(@Query("id")int id);

    //chapter
    @Headers({token,link})
    @GET("admin-library/chapter-list")
    Call<ChapterListResponse> chapterListCall(@Query("subjectId")int subjectId,@Query("standardId")int standardId);


    //topic
    @Headers({token,link})
    @GET("admin-library/topic-list-by-chapterId")
    Call<List<GetTopicsResponse>> getTopicsCall(@Query("chapterId") int chapterId, @Query("subjectId") int subjectId,@Query("standardId")int standardId);


//library
    @Headers({token,link})
    @GET("admin-library/library-contents")
    Call<GetLibraryResponse> getLibraryCall(@Query("topicId") int topicId, @Query("chapterId")int chapterId,@Query("standardId")int standardId);

    //getLibray_notes
    @Headers({token,link})
    @GET("admin-library/library-contents")
    Call<GetLibraryResponse> getLibraryCall_notes(@Query("topicId")int topicId,@Query("standardId")int standardId,@Query("chapterId")int chapterId,@Query("type")String type);

}
