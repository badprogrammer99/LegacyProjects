package com.example.benfi.hospital.models;

import com.example.benfi.hospital.models.base.UserAnswer;

import java.util.ArrayList;

public class GenericMultipleChoiceUserAnswer extends UserAnswer {
    private ArrayList<String> answer;

    public GenericMultipleChoiceUserAnswer() {

    }

    public GenericMultipleChoiceUserAnswer(int questionId, String observations, ArrayList<String> answer) {
        super(questionId, observations);
        this.answer = answer;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public String getAnswer(int index) {
        return answer.get(index);
    }

    public void setAnswer(int index, String answer) {
        this.answer.set(index, answer);
    }
}
