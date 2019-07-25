package com.example.benfi.hospital.models.base;

import java.util.ArrayList;

import java.io.Serializable;

public class Questionnaire implements Serializable {
    private int id;
    private String name;
    private ArrayList<Question> questions;

    public Questionnaire() {

    }

    public Questionnaire(int id, String name, ArrayList<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Question getQuestion(int index) {
        return this.questions.get(index);
    }

    public void setQuestion(int index, Question question) {
        this.questions.add(index, question);
    }

    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
