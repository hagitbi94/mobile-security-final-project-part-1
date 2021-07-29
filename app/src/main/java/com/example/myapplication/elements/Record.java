package com.example.myapplication.elements;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class Record implements  Comparable<Record> {

    private String name = "";
    private String date = "";
    private int score = 0 ;
    private double lat = 0.0;
    private double lng = 0.0;

    public Record() {
    }

    public Record(String name, String date, int score, double lat, double lng) {
        this.name = name;
        this.date = date;
        this.score = score;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy hh:mm");
        String date = format.format(System.currentTimeMillis());
        return date;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Record o) {
        if (o instanceof Record) {
            Record r = (Record) o;
            return this.getScore() - o.getScore();
        } else {
            throw new RuntimeException( "Not comparable object");
        }
    }

    public static Comparator<Record> RecordComperator =  new Comparator<Record>() {

        public int compare(Record record1, Record record2) {

            int score1 = record1.getScore();
            int score2 = record2.getScore();

            //ascending order
            return record2.compareTo(record1);

        }
    };
}
