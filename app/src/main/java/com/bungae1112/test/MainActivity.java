package com.bungae1112.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button deletePref;
    PrefManager pref;
    ArrayList<UserData> userDataList;

    ListView listView;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.user_list);

        try {
            this.initUserList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new UserAdapter(this, userDataList);
        listView.setAdapter(adapter);

        setListViewClickEvent();

        deletePref = findViewById(R.id.del_pref);
        setDeleteBtn();
    }

    public void initUserList() throws JSONException {
        userDataList = new ArrayList<UserData>();

        for (int i = 0; i < PrefManager.userData.length(); i ++) {
            JSONObject jsonObject = PrefManager.userData.getJSONObject(i);

            userDataList.add( new UserData( jsonObject.get("id").toString(), jsonObject.get("pass").toString() ) );
        }

    }

    private void setListViewClickEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete User Data");
                builder.setMessage("Will you Delete This Id? \n\n" + userDataList.get(position).getId());

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JSONArray result = new JSONArray();

                        for (int i = 0; i < PrefManager.userData.length(); i ++) {

                            if (i != position) {
                                try {
                                    result.put(PrefManager.userData.get(i));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        PrefManager.setPrefString("users", result.toString());
                        try {
                            PrefManager.userData = PrefManager.getJsonArray("users");
                            userDataList.clear();

                            for (int i = 0; i < PrefManager.userData.length(); i ++) {
                                JSONObject jsonObject = PrefManager.userData.getJSONObject(i);

                                userDataList.add( new UserData( jsonObject.get("id").toString(), jsonObject.get("pass").toString() ) );
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainActivity.this, "Successfully Deleted.", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("No", null);

                builder.show();
            }

        });

    }

    private void setDeleteBtn() {

        deletePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pref = new PrefManager(getApplicationContext(), "users");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pref.setPrefString("users", "[]");

                try {
                    PrefManager.userData = PrefManager.getJsonArray("users");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                userDataList.clear();

                adapter.notifyDataSetChanged();
            }
        });

    }
}
