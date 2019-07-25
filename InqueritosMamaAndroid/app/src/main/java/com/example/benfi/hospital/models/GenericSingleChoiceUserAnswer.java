package com.example.benfi.hospital.models;

import com.example.benfi.hospital.models.base.UserAnswer;

public class GenericSingleChoiceUserAnswer extends UserAnswer {
    private String answer;

    public GenericSingleChoiceUserAnswer() {

    }

    public GenericSingleChoiceUserAnswer(int questionId, String observations, String answer) {
        super(questionId, observations);
        this.answer = answer;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
