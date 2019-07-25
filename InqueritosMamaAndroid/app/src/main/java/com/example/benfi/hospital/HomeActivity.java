package com.example.benfi.hospital;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.benfi.hospital.requests.GetUserIdRequest;
import com.example.benfi.hospital.utils.AppUtils;

import com.example.benfi.hospital.requests.GetUserQuestionnairesRequest.OnUserQuestionnairesReady;

public class HomeActivity extends AppCompatActivity implements KeyEvent.Callback,
        OnUserQuestionnairesReady, OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        new GetUserIdRequest(this);
    }

    @Override
    public void onUserQuestionnairesReady() {
        setContentView(R.layout.activity_inicial);

        TextView numeroPaciente = findViewById(R.id.numero_paciente);
        numeroPaciente.setText(getString(R.string.numero_paciente, AppUtils.getUserId()));

        TextView questionario1 = findViewById(R.id.questionario1);
        TextView questionario2 = findViewById(R.id.questionario2);

        try {
            questionario1.setText(AppUtils.getUserQuestionnaires().get(0).getName());
            questionario2.setText(AppUtils.getUserQuestionnaires().get(1).getName());
        } catch (IndexOutOfBoundsException e) {}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mala:
                this.putExtrasInIntentAndStartActivity(R.id.mala, MalaActivity.class);
                break;
            case R.id.btn_recomendacoes:
                this.putExtrasInIntentAndStartActivity(R.id.recomendacoes, RecomendacoesActivity.class);
                break;
            case R.id.btn_questionario_1:
                this.putExtrasInIntentAndStartActivity(R.id.questionario1, QuestionActivity.class);
                break;
            case R.id.btn_questionario_2:
                this.putExtrasInIntentAndStartActivity(R.id.questionario2, QuestionActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            SharedPreferences tokenPrefs = getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
            tokenPrefs.edit().clear().apply();

            SharedPreferences userResetPasswordPrefs = getApplicationContext().
                    getSharedPreferences("userResetPasswordPrefs", Context.MODE_PRIVATE);
            userResetPasswordPrefs.edit().clear().apply();

            TextView questionario1 = findViewById(R.id.questionario1);
            TextView questionario2 = findViewById(R.id.questionario2);

            SharedPreferences questionario1Prefs = getApplicationContext()
                    .getSharedPreferences(questionario1.getText().toString(), Context.MODE_PRIVATE);
            questionario1Prefs.edit().clear().apply();

            SharedPreferences questionario2Prefs = getApplicationContext()
                    .getSharedPreferences(questionario2.getText().toString(), Context.MODE_PRIVATE);
            questionario2Prefs.edit().clear().apply();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void putExtrasInIntentAndStartActivity(int id, Class<?> targetActivity) {
        TextView textView = findViewById(id);
        boolean hasUserAnsweredQuestionnaire = getSharedPreferences(textView.getText().toString(), Context.MODE_PRIVATE)
                .getBoolean("answered", false);
        if (!hasUserAnsweredQuestionnaire) {
            Intent intent = new Intent(this, targetActivity);
            Bundle extras = new Bundle();
            extras.putString("titleText", textView.getText().toString());
            intent.putExtras(extras);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Já respondeu a este questionário!", Toast.LENGTH_SHORT).show();
        }
    }
}
