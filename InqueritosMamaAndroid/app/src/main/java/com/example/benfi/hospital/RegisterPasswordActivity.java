package com.example.benfi.hospital;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.benfi.hospital.interfaces.OnResponseCallback;
import com.example.benfi.hospital.requests.ChangePasswordRequest;
import com.example.benfi.hospital.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterPasswordActivity extends AppCompatActivity implements OnResponseCallback, KeyEvent.Callback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (this.checkIfUserAlreadyResetPassword()) {
            this.switchToHomeActivity();
        }
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_log_in_nova_pass);
    }

    public void changePassword(View v) {
        EditText passwordView = findViewById(R.id.password);
        EditText confirmarPasswordView = findViewById(R.id.confirmar_password);

        String password = passwordView.getText().toString();
        String confirmarPassword = confirmarPasswordView.getText().toString();

        this.checkIfPasswordIsValid(password, confirmarPassword);

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(password, this);
    }

    private boolean checkIfUserAlreadyResetPassword() {
        SharedPreferences userSetPasswordPrefs = getApplicationContext().
                getSharedPreferences("userResetPasswordPrefs", Context.MODE_PRIVATE);
        return userSetPasswordPrefs.getBoolean("userResetPassword", false);
    }

    private boolean doPasswordsMatch(String password, String confirmarPassword) {
        return password.equals(confirmarPassword);
    }

    private boolean isPasswordLengthGreaterThan3(String password) {
        return password.length() > 3;
    }

    private void checkIfPasswordIsValid(String password, String confirmarPassword) {
        if (!this.isPasswordLengthGreaterThan3(password)) {
            Toast.makeText(getApplicationContext(), "A password tem que ter mais que 3 caracteres!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!this.doPasswordsMatch(password, confirmarPassword)) {
            Toast.makeText(getApplicationContext(), "As passwords não correspondem!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private boolean didUserChangePassword(String response) {
        boolean didUserChangePassword = false;
        try {
            JSONObject jsonResponse = new JSONObject(response);
            didUserChangePassword = jsonResponse.getBoolean("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return didUserChangePassword;
    }

    private void setUserResetPassword() {
        SharedPreferences userSetPasswordPrefs = getApplicationContext().
                getSharedPreferences("userResetPasswordPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor userResetPrefs = userSetPasswordPrefs.edit();
        userResetPrefs.putBoolean("userResetPassword", true);
        userResetPrefs.apply();
    }

    private void switchToHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessResponse(String response) {
        if (this.didUserChangePassword(response)) {
            this.setUserResetPassword();
            this.switchToHomeActivity();
        } else {
            Toast.makeText(getApplicationContext(), "Change password not successful, please try again later.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError response) {
        AppUtils.parseErrorMessage(response);
    }
}
