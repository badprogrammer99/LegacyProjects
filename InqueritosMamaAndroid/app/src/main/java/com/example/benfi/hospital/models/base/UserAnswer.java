package com.example.benfi.hospital.models.base;

public class UserAnswer {
    private int questionId;
    private String observations;

    protected UserAnswer() {

    }

    public UserAnswer(int questionId, String observations) {
        this.questionId = questionId;
        this.observations = observations;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
