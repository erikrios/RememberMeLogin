package com.erikriosetiawan.remembermelogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    CheckBox checkBoxRememberMe;
    Button buttonLogin;
    String username, password;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        checkBoxRememberMe = findViewById(R.id.checkbox_remember_me);
        buttonLogin = findViewById(R.id.button_login);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            editTextUsername.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            checkBoxRememberMe.setChecked(true);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);

                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();

                if (checkBoxRememberMe.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                if ((username.equalsIgnoreCase("admin")) && (password.equalsIgnoreCase("admin"))) {
                    Intent intentSuccessLogin = new Intent(MainActivity.this, TransactionActivity.class);
                    startActivity(intentSuccessLogin);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Username atau password salah!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
