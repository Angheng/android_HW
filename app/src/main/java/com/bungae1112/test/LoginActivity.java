package com.bungae1112.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    private JSONArray userData;

    Button loginBtn;
    Button regBtn;

    EditText idText;
    EditText pwText;

    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Load User Data (Format: JSON) ===========
        try {
            prefManager = new PrefManager(getApplicationContext(), "users");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // ===================================

        // initializing objects and button Event =======
        init();
        // ===================================
    }

    private void init() {
        idText = findViewById(R.id.idText);
        pwText = findViewById(R.id.pwText);

        // Login button =========================

        loginBtn = (Button) findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if ( prefManager.isCorrect(idText.getText().toString(), pwText.getText().toString(), true, true) ) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);

                        finish();
                    } else {
                        idText.setText("");
                        pwText.setText("");

                        Toast.makeText(LoginActivity.this, "Id or PassWord Mismatch.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        // ===================================

        // register button =======================
        regBtn = (Button) findViewById(R.id.register);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        // ===================================
    }

}
