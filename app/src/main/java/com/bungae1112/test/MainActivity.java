package com.bungae1112.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity {
    Button deletePref;
    PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deletePref = findViewById(R.id.del_pref);
        deletePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pref = new PrefManager(getApplicationContext(), "users");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pref.setPrefString("users", "[]");
            }
        });

    }
}
