package com.example.benfi.hospital.models.base;

import java.io.Serializable;

public class Question implements Serializable {
    private int id;
    private String name;
    private String description;
    private int possibleAnswers;

    public Question() {

    }

    public Question(int id, String name, String description, int possibleAnswers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.possibleAnswers = possibleAnswers;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPossibleAnswers() {
        return this.possibleAnswers;
    }

    public void setPossibleAnswers(int possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
}
