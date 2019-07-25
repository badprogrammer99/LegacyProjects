package com.example.benfi.hospital.models.base;

import java.io.Serializable;
import java.util.ArrayList;

public class CompositePossibleAnswer implements Serializable {
    private String id;
    private ArrayList<String> possibleAnswerNames;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPossibleAnswerName(int index) {
        return this.possibleAnswerNames.get(index);
    }

    public void setPossibleAnswerName(int index, String possibleAnswerName) {
        this.possibleAnswerNames.add(index, possibleAnswerName);
    }

    public ArrayList<String> getPossibleAnswerNames() {
        return this.possibleAnswerNames;
    }

    public void setPossibleAnswerNames(ArrayList<String> possibleAnswerNames) {
        this.possibleAnswerNames = possibleAnswerNames;
    }
}
