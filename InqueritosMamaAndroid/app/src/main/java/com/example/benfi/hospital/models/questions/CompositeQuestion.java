package com.example.benfi.hospital.models.questions;

import com.example.benfi.hospital.models.base.CompositePossibleAnswer;
import com.example.benfi.hospital.models.base.Question;

import java.io.Serializable;
import java.util.ArrayList;

public class CompositeQuestion extends Question implements Serializable {
    private ArrayList<CompositePossibleAnswer> compositePossibleAnswers;

    public CompositeQuestion() {

    }

    public CompositeQuestion(ArrayList<CompositePossibleAnswer> compositePossibleAnswers) {
        this.compositePossibleAnswers = compositePossibleAnswers;
    }

    public ArrayList<CompositePossibleAnswer> getCompositePossibleAnswers() {
        return compositePossibleAnswers;
    }

    public void setCompositePossibleAnswers(ArrayList<CompositePossibleAnswer> compositePossibleAnswers) {
        this.compositePossibleAnswers = compositePossibleAnswers;
    }

    public CompositePossibleAnswer getCompositePossibleAnswer(int index) {
        return this.compositePossibleAnswers.get(index);
    }

    public void setCompositePossibleAnswer(int index, CompositePossibleAnswer compositePossibleAnswer) {
        this.compositePossibleAnswers.add(index, compositePossibleAnswer);
    }
}
