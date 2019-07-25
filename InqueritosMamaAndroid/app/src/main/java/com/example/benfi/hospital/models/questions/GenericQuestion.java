package com.example.benfi.hospital.models.questions;

import com.example.benfi.hospital.models.base.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class GenericQuestion extends Question implements Serializable {
    private ArrayList<String> possibleAnswerNames;

    public GenericQuestion() {

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
