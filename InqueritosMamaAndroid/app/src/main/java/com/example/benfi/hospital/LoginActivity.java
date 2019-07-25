package com.example.benfi.hospital;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.benfi.hospital.requests.LoginRequest;
import com.example.benfi.hospital.interfaces.OnResponseCallback;
import com.example.benfi.hospital.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements OnResponseCallback, KeyEvent.Callback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (this.checkIfTokenExistsInSharedPreferences()) {
            this.switchToRegisterPasswordActivity();
        }
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_log_in);
    }

    public void attemptLogin(View v) {
        EditText nomeUtilizador = findViewById(R.id.nome_utilizador);
        EditText password = findViewById(R.id.password);

        LoginRequest loginRequest = new LoginRequest(nomeUtilizador.getText().toString(), password.getText().toString(),
                this);
    }

    private boolean checkIfTokenExistsInSharedPreferences() {
        SharedPreferences tokenSharedPrefs = getApplicationContext().
                getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
        return tokenSharedPrefs.contains("token");
    }

    private String parseJsonToken(String response) {
        String token;
        try {
            JSONObject jsonResponse = new JSONObject(response);
            token = jsonResponse.getString("token");
            if (token != null) {
                return token;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addTokenToSharedPreferences(String token) {
        SharedPreferences tokenSharedPrefs = getApplicationContext().
                getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor tokenPrefsEdit = tokenSharedPrefs.edit();
        tokenPrefsEdit.putString("token", token);
        tokenPrefsEdit.apply();
    }

    private void switchToRegisterPasswordActivity() {
        Intent intent = new Intent(this, RegisterPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessResponse(String response) {
        String token = this.parseJsonToken(response);
        if (token != null) {
            this.addTokenToSharedPreferences(token);
            this.switchToRegisterPasswordActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Ocorreu um erro, por favor tente mais tarde.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError response) {
        AppUtils.parseErrorMessage(response);
    }
}
