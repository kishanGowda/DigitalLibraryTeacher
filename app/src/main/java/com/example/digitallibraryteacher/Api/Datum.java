package com.example.digitallibraryteacher.Api;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Datum {
    public int std_id;
    public String std_std;
    public String std_section;
    public String std_stream;
    public String courseName;
    public String notesCount;
    public String videoCount;
    public String quesBankCount;
}
