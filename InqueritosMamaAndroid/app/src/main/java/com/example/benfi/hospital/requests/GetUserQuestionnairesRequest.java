package com.example.benfi.hospital.requests;

import com.android.volley.VolleyError;
import com.example.benfi.hospital.interfaces.OnResponseCallback;
import com.example.benfi.hospital.abstracts.AbstractRequest;
import com.example.benfi.hospital.models.questions.CompositeQuestion;
import com.example.benfi.hospital.models.questions.GenericQuestion;
import com.example.benfi.hospital.models.base.CompositePossibleAnswer;
import com.example.benfi.hospital.models.base.Question;
import com.example.benfi.hospital.models.base.Questionnaire;
import com.example.benfi.hospital.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class GetUserQuestionnairesRequest extends AbstractRequest implements OnResponseCallback {
    private OnUserQuestionnairesReady userQuestionnairesReadyListener;

    public GetUserQuestionnairesRequest(OnUserQuestionnairesReady userQuestionnairesReadyListener) {
        super(AppUtils.getUrl() + "/api/user/questionnaries");
        this.userQuestionnairesReadyListener = userQuestionnairesReadyListener;
        this.getUserQuestionnaires();
    }

    public void getUserQuestionnaires() {
        this.getStringRequestWrapper().setRequestMethod("GET");

        this.getStringRequestWrapper().addRequestHeaders("Authorization", "Bearer " + AppUtils.getAppToken());

        this.getStringRequestWrapper().setOnResponseCallback(this);
        this.getStringRequestWrapper().start();
    }

    @Override
    public void onSuccessResponse(String response) {
        ArrayList<Questionnaire> questionnaires = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonResponseData = jsonResponse.getJSONArray("data");

            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject jsonDataFields = jsonResponseData.getJSONObject(i);

                Questionnaire questionnaire = new Questionnaire();
                questionnaire.setId(jsonDataFields.getInt("id"));
                questionnaire.setName(jsonDataFields.getString("name"));

                ArrayList<Question> questions = new ArrayList<>();
                JSONArray jsonQuestionFields = jsonDataFields.getJSONArray("questions");

                for (int z = 0; z < jsonQuestionFields.length(); z++) {
                    JSONObject jsonQuestionField = jsonQuestionFields.getJSONObject(z);

                    JSONObject jsonPossibleAnswerNamesObject = jsonQuestionField.getJSONObject("possible_answer_names");
                    Object nestedPossibleAnswerNames = jsonPossibleAnswerNamesObject.get("possible_answer_names");

                    Question question = null;

                    if (nestedPossibleAnswerNames instanceof JSONObject) {
                        JSONObject nestedPossibleAnswerNamesObject = (JSONObject) nestedPossibleAnswerNames;
                        Iterator<String> keys = nestedPossibleAnswerNamesObject.keys();

                        ArrayList<CompositePossibleAnswer> compositePossibleAnswers = new ArrayList<>();

                        while (keys.hasNext()) {
                            String key = keys.next();

                            ArrayList<String> possibleAnswerNames = new ArrayList<>();
                            JSONArray jsonPossibleAnswerNames = nestedPossibleAnswerNamesObject.getJSONArray(key);

                            for (int j = 0; j < jsonPossibleAnswerNames.length(); j++) {
                                possibleAnswerNames.add(jsonPossibleAnswerNames.getString(j));
                            }

                            CompositePossibleAnswer compositePossibleAnswer = new CompositePossibleAnswer();

                            compositePossibleAnswer.setId(key);
                            compositePossibleAnswer.setPossibleAnswerNames(possibleAnswerNames);

                            compositePossibleAnswers.add(compositePossibleAnswer);
                        }

                        question = new CompositeQuestion();

                        question.setId(jsonQuestionField.getInt("id"));
                        question.setName(jsonQuestionField.getString("name"));
                        question.setDescription(jsonQuestionField.getString("description"));
                        question.setPossibleAnswers(jsonQuestionField.getInt("possible_answers"));
                        ((CompositeQuestion) question).setCompositePossibleAnswers(compositePossibleAnswers);

                        questions.add(question);
                    } else {
                        JSONArray jsonPossibleAnswerNames = (JSONArray) nestedPossibleAnswerNames;

                        ArrayList<String> possibleAnswerNames = new ArrayList<>();

                        for (int j = 0; j < jsonPossibleAnswerNames.length(); j++) {
                            possibleAnswerNames.add(jsonPossibleAnswerNames.getString(j));
                        }

                        question = new GenericQuestion();

                        question.setId(jsonQuestionField.getInt("id"));
                        question.setName(jsonQuestionField.getString("name"));
                        question.setDescription(jsonQuestionField.getString("description"));
                        question.setPossibleAnswers(jsonQuestionField.getInt("possible_answers"));
                        ((GenericQuestion) question).setPossibleAnswerNames(possibleAnswerNames);

                        questions.add(question);
                    }
                }
                questionnaire.setQuestions(questions);
                questionnaires.add(questionnaire);
            }
        } catch (JSONException e) {
            throw new RuntimeException("JSON parsing of the questionnaires unsuccessful: the program execution cannot continue. Please contact the app developer in order to solve the error.");
        }
        AppUtils.setUserQuestionnaires(questionnaires);
        userQuestionnairesReadyListener.onUserQuestionnairesReady();
    }

    @Override
    public void onErrorResponse(VolleyError response) {
        AppUtils.parseErrorMessage(response);
    }

    public interface OnUserQuestionnairesReady {
        void onUserQuestionnairesReady();
    }
}