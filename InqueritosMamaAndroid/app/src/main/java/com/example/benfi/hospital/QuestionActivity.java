package com.example.benfi.hospital;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.benfi.hospital.fragments.MultipleChoiceCompositeQuestionFragment;
import com.example.benfi.hospital.fragments.MultipleChoiceGenericQuestionFragment;
import com.example.benfi.hospital.fragments.SingleChoiceCompositeQuestionFragment;
import com.example.benfi.hospital.fragments.SingleChoiceGenericQuestionFragment;
import com.example.benfi.hospital.interfaces.OnJsonResponseCallback;
import com.example.benfi.hospital.models.CompositeUserAnswer;
import com.example.benfi.hospital.models.GenericMultipleChoiceUserAnswer;
import com.example.benfi.hospital.models.GenericSingleChoiceUserAnswer;
import com.example.benfi.hospital.models.base.UserAnswer;
import com.example.benfi.hospital.models.questions.CompositeQuestion;
import com.example.benfi.hospital.models.questions.GenericQuestion;
import com.example.benfi.hospital.models.base.Question;
import com.example.benfi.hospital.models.base.Questionnaire;
import com.example.benfi.hospital.requests.SendUserQuestionsRequest;
import com.example.benfi.hospital.utils.AppUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity implements
        SingleChoiceGenericQuestionFragment.SingleChoiceGenericQuestionFragmentInteractionListener,
        SingleChoiceCompositeQuestionFragment.SingleChoiceCompositeQuestionFragmentInteractionListener,
        MultipleChoiceGenericQuestionFragment.MultipleChoiceGenericQuestionFragmentListener,
        MultipleChoiceCompositeQuestionFragment.MultipleChoiceCompositeQuestionFragmentListener {

    private Questionnaire mCurrentQuestionnaire;
    private Question mCurrentQuestion;
    private int mCurrentQuestionNumber = 0;
    private boolean mSentRequest = false;
    private ArrayList<UserAnswer> mUserUserAnswers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle((Html.fromHtml("<font color=" +
                getResources().getColor(R.color.colorTitle) + ">" +
                getIntent().getExtras().getString("titleText") + "</font>")));

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorTitle), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        for (Questionnaire questionnaire : AppUtils.getUserQuestionnaires()) {
            if (questionnaire.getName().equals(getIntent().getExtras().getString("titleText"))) {
                this.mCurrentQuestionnaire = questionnaire;
                break;
            }
        }

        setContentView(R.layout.activity_question);
        this.pushNextQuestion(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.exitAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentQuestionNumber == 0) {
            this.exitAlertDialog();
        } else {
            mCurrentQuestionNumber--;
            this.mCurrentQuestion = this.mCurrentQuestionnaire.getQuestion(mCurrentQuestionNumber);
            this.mUserUserAnswers.remove(this.mUserUserAnswers.size() - 1);
            super.onBackPressed();
        }
    }

    @Override
    public void onSingleChoiceGenericQuestionFragmentConfirmButton(View v, String choice, String observations, String exitMode) {
        if (!exitMode.equals("Sair sem gravar")) {
            GenericSingleChoiceUserAnswer genericSingleChoiceAnswer = new GenericSingleChoiceUserAnswer();
            genericSingleChoiceAnswer.setQuestionId(mCurrentQuestion.getId());
            genericSingleChoiceAnswer.setAnswer(choice);
            genericSingleChoiceAnswer.setObservations(observations);
            mUserUserAnswers.add(genericSingleChoiceAnswer);
            this.updateQuestionNumber();
        } else {
            this.exitAlertDialog();
        }
    }

    @Override
    public void onSingleChoiceCompositeQuestionFragmentConfirmButton(View v, HashMap<String, ArrayList<String>> choice, String observations, String exitMode) {
        this.pushCompositeAnswer(choice, observations, exitMode);
    }

    @Override
    public void onMultipleChoiceGenericQuestionFragmentConfirmButton(View v, ArrayList<String> choices, String observations, String exitMode) {
        if (!exitMode.equals("Sair sem gravar")) {
            GenericMultipleChoiceUserAnswer genericMultipleChoiceAnswer = new GenericMultipleChoiceUserAnswer();
            genericMultipleChoiceAnswer.setQuestionId(mCurrentQuestion.getId());
            genericMultipleChoiceAnswer.setAnswer(choices);
            genericMultipleChoiceAnswer.setObservations(observations);
            mUserUserAnswers.add(genericMultipleChoiceAnswer);
            this.updateQuestionNumber();
        } else {
            this.exitAlertDialog();
        }
    }

    @Override
    public void onMultipleChoiceCompositeQuestionFragmentConfirmButton(View v, HashMap<String, ArrayList<String>> choices, String observations, String exitMode) {
        this.pushCompositeAnswer(choices, observations, exitMode);
    }

    private void pushNextQuestion(boolean transactionAnimations) {
        this.mCurrentQuestion = this.mCurrentQuestionnaire.getQuestion(this.mCurrentQuestionNumber);

        if (this.mCurrentQuestion.getPossibleAnswers() == 1) {
            if (mCurrentQuestion instanceof GenericQuestion) {
                this.fragmentTransaction(SingleChoiceGenericQuestionFragment.newInstance(this.mCurrentQuestionNumber + 1, (GenericQuestion) this.mCurrentQuestion), transactionAnimations);
            } else {
                this.fragmentTransaction(SingleChoiceCompositeQuestionFragment.newInstance(this.mCurrentQuestionNumber + 1, (CompositeQuestion) this.mCurrentQuestion), transactionAnimations);
            }
        } else {
            if (mCurrentQuestion instanceof GenericQuestion) {
                this.fragmentTransaction(MultipleChoiceGenericQuestionFragment.newInstance(this.mCurrentQuestionNumber + 1, (GenericQuestion) this.mCurrentQuestion), transactionAnimations);
            } else {
                this.fragmentTransaction(MultipleChoiceCompositeQuestionFragment.newInstance(this.mCurrentQuestionNumber + 1, (CompositeQuestion) this.mCurrentQuestion), transactionAnimations);
            }
        }
    }

    private void fragmentTransaction(Fragment fragment, boolean transactionAnimations) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (transactionAnimations) fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.question_container, fragment, "question" + this.mCurrentQuestionNumber);
        fragmentTransaction.addToBackStack("question" + this.mCurrentQuestionNumber);
        fragmentTransaction.commit();
    }

    private void pushCompositeAnswer(HashMap<String, ArrayList<String>> choices, String observations, String exitMode) {
        if (!exitMode.equals("Sair sem gravar")) {
            CompositeUserAnswer compositeAnswer = new CompositeUserAnswer();
            compositeAnswer.setQuestionId(mCurrentQuestion.getId());
            compositeAnswer.setAnswer(choices);
            compositeAnswer.setObservations(observations);
            mUserUserAnswers.add(compositeAnswer);
            this.updateQuestionNumber();
        } else {
            this.exitAlertDialog();
        }
    }

    private void updateQuestionNumber() {
        if (mCurrentQuestionNumber + 1 == mCurrentQuestionnaire.getQuestions().size()) {
            try {
                System.out.println(this.convertUserAnswersToJson());
            } catch (JSONException e) {}
            /* if (!mSentRequest) {
                mSentRequest = true;
                try {
                    SendUserQuestionsRequest sendUserQuestionsRequest = new SendUserQuestionsRequest(this.convertUserAnswersToJson(), new OnJsonResponseCallback() {
                        @Override
                        public void onSuccessResponse(JSONObject response) {
                            Toast.makeText(AppUtils.getAppContext(), "Acabou o questionário!", Toast.LENGTH_LONG).show();
                            lockQuestionnaire();
                            switchToHomeActivity();
                        }

                        @Override
                        public void onErrorResponse(VolleyError response) {
                            AppUtils.parseErrorMessage(response);
                        }
                    });
                } catch(JSONException e){
                    e.printStackTrace();
                }
            } */
        } else {
            mCurrentQuestionNumber++;
            this.pushNextQuestion(true);
        }
    }

    private JSONObject convertUserAnswersToJson() throws JSONException {
        Gson gson = new Gson();
        Type userAnswersJson = new TypeToken<ArrayList<UserAnswer>>(){}.getType();

        JSONObject jsonAnswers = new JSONObject();
        String toJsonAnswers = gson.toJson(mUserUserAnswers, userAnswersJson);
        jsonAnswers.put("data", new JSONArray(toJsonAnswers));

        return jsonAnswers;
    }

    private void lockQuestionnaire() {
        SharedPreferences sharedPreferences = getSharedPreferences(mCurrentQuestionnaire.getName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("answered", true).apply();
    }

    private void switchToHomeActivity() {
        Intent openMainActivity = new Intent(this, HomeActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 0);
    }

    private void exitAlertDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Sair do questionário")
            .setMessage("Tem a certeza que deseja sair deste questionário? Todas as respostas irão ser perdidas.")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    switchToHomeActivity();
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
}