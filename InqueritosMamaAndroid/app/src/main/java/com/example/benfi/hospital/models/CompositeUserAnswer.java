package com.example.benfi.hospital.models;

import com.example.benfi.hospital.models.base.UserAnswer;

import java.util.ArrayList;
import java.util.HashMap;

public class CompositeUserAnswer extends UserAnswer {
    private HashMap<String, ArrayList<String>> answer;

    public CompositeUserAnswer() {

    }

    public CompositeUserAnswer(int questionId, String observations, HashMap<String, ArrayList<String>> answer) {
        super(questionId, observations);
        this.answer = answer;
    }

    public HashMap<String, ArrayList<String>> getAnswer() {
        return answer;
    }

    public void setAnswer(HashMap<String, ArrayList<String>> answer) {
        this.answer = answer;
    }
}
