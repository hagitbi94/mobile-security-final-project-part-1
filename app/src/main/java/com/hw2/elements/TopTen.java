package com.hw2.elements;

import java.io.Serializable;
import java.util.ArrayList;

public class TopTen  implements Serializable {
    private ArrayList<Record> records = new ArrayList<>();

    public TopTen()  {
    }

    public TopTen(ArrayList<Record> records) {
        this.records = records;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void addRecord(Record record){
        this.records.add(record);
    }

    public Record getRecord(int i){
        return records.get(i);
    }


    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }
}
