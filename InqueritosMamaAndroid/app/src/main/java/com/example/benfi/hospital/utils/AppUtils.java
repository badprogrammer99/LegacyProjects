package com.example.benfi.hospital.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.benfi.hospital.models.base.Questionnaire;
import com.example.benfi.hospital.requests.SendErrorReportToPastebinRequest;

import java.util.ArrayList;

public class AppUtils extends Application {
    private static Context appContext;
    private static String userId;
    private static ArrayList<Questionnaire> userQuestionnaires;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return AppUtils.appContext;
    }

    public static void parseErrorMessage(VolleyError response) {
        String responseError = new String(response.networkResponse.data);
        Log.e("ResponseError", responseError);
        new SendErrorReportToPastebinRequest(responseError);
    }

    public static String getAppToken() {
        SharedPreferences tokenSharedPrefs = AppUtils.getAppContext().
                getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
        return tokenSharedPrefs.getString("token", null);
    }

    public static Questionnaire getUserQuestionnaire(int index) {
        return AppUtils.userQuestionnaires.get(index);
    }

    public static void setUserQuestionnaire(int index, Questionnaire questionnaire) {
        AppUtils.userQuestionnaires.add(index, questionnaire);
    }

    public static ArrayList<Questionnaire> getUserQuestionnaires() {
        return AppUtils.userQuestionnaires;
    }

    public static void setUserQuestionnaires(ArrayList<Questionnaire> userQuestionnaires) {
        AppUtils.userQuestionnaires = userQuestionnaires;
    }

    public static String getUserId() {
        return AppUtils.userId;
    }

    public static void setUserId(String userId) {
        AppUtils.userId = userId;
    }

    public static String getUrl() {
        SharedPreferences sharedPreferences = getAppContext().getSharedPreferences("url", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("url", null) == null) {
            sharedPreferences.edit().putString("url", "http://inqueritos-mama.herokuapp.com/").apply();
        }
        return sharedPreferences.getString("url", null);
    }

    public static void setUrl(String url) {
        SharedPreferences sharedPreferences = getAppContext().getSharedPreferences("url", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("url", "http://" + url).apply();
    }
}
