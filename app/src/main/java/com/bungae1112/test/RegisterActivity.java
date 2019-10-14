package com.bungae1112.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    PrefManager pref;

    EditText editId;
    Button overlap;
    TextView idConfirm;
    boolean idConfirmed = false;

    EditText passEdit;
    TextView confirmPass;
    boolean passConfirmed = false;

    EditText matchPassEdit;
    TextView confirmMatch;
    boolean passMatchConfirmed = false;

    EditText name;
    EditText phoneNum;
    EditText adress;

    RadioButton agree, ignore;
    RadioGroup agreement;

    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            pref = new PrefManager(getApplicationContext(), "users");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editId = findViewById(R.id.idEdit);
        setOverlapBtn();
        idConfirm = findViewById(R.id.confirm_result);

        passEdit = findViewById(R.id.passEdit);
        setPassEditEvent();
        confirmPass = findViewById(R.id.confilm_pass);

        matchPassEdit = findViewById(R.id.confirm_passEdit);
        setMatchPassEditEvent();
        confirmMatch = findViewById(R.id.is_match);

        name = findViewById(R.id.nameEdit);
        phoneNum = findViewById(R.id.phoneEdit);
        adress = findViewById(R.id.AdressEdit);

        agree = findViewById(R.id.reg_agree);
        ignore = findViewById(R.id.reg_ignore);
        agreement = findViewById(R.id.agreement);

        register = findViewById(R.id.register);
        setRegisterBtn();
    }

    private void setOverlapBtn() {
        overlap = findViewById(R.id.overlap);
        overlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( Pattern.matches( "^\\S{6,15}$", editId.getText().toString() ) && Pattern.matches( "^\\S*[0-9]+[a-zA-z]*$",  editId.getText().toString()) ) {
                    
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    TextView message = new TextView(RegisterActivity.this);
                    message.setGravity(Gravity.CENTER_HORIZONTAL);
                    message.setTextSize(20);

                    builder.setTitle("Checking Overlap.");

                    try {

                        if ( !PrefManager.isCorrect(editId.getText().toString(), "", true, false) ) {
                            message.setText(String.format("\nUse this id?\n%s", editId.getText()));

                            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    editId.setFocusable(false);
                                    editId.setClickable(false);
                                    editId.setTextColor(Color.GRAY);

                                    idConfirm.setText("Id Confilmed");
                                    idConfirmed = true;

                                    overlap.setClickable(false);
                                }
                            });

                            builder.setNegativeButton("no", null);
                        }
                        else {
                            message.setText(String.format("\nId Already Registered\n%s", editId.getText()));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    builder.setView(message);

                    AlertDialog alert =  builder.create();
                    alert.show();
                }
                
                else {
                    Toast.makeText(RegisterActivity.this, "Invalid Id", Toast.LENGTH_SHORT).show();
                }
            }
            
        });
    }

    private void setPassEditEvent() {
        passEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                confirmPass.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ( Pattern.matches( "^\\S{8,15}$", passEdit.getText().toString() ) && Pattern.matches( "^\\S*\\W+\\w*$",  passEdit.getText().toString()) ) {
                    confirmPass.setTextColor(getColor(R.color.colorPrimary));
                    confirmPass.setText("PassWord Confirmed");
                    passConfirmed = true;
                }
                else {
                    confirmPass.setTextColor(Color.RED);
                    confirmPass.setText("Invalid Password");
                    passConfirmed = false;
                }
            }
        });
    }

    private void setMatchPassEditEvent() {
        matchPassEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                confirmMatch.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged( Editable s ) {
                if (passEdit.getText().toString().equals( matchPassEdit.getText().toString()) )  {
                    confirmMatch.setTextColor( getColor(R.color.colorPrimary) );
                    confirmMatch.setText( "Match" );
                    passMatchConfirmed = true;
                }
                else {
                    confirmMatch.setTextColor(Color.RED);
                    confirmMatch.setText( "Mismatch" );
                    passMatchConfirmed = false;
                }
            }
        });
    }

    private void setRegisterBtn() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! idConfirmed) {
                    Toast.makeText(RegisterActivity.this, "Please Check Id Overlap", Toast.LENGTH_SHORT).show();
                }
                else if ( ! passConfirmed) {
                    Toast.makeText(RegisterActivity.this, "UnCorrect PassWord", Toast.LENGTH_SHORT).show();
                }
                else if ( ! passMatchConfirmed) {
                    Toast.makeText(RegisterActivity.this, "PassWord mismatch", Toast.LENGTH_SHORT).show();
                }

                else if (name.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Insert Name.", Toast.LENGTH_SHORT).show();
                }
                else if (phoneNum.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Insert Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if (adress.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Insert Address", Toast.LENGTH_SHORT).show();
                }

                else if (! agree.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "Please Check to Agreement", Toast.LENGTH_SHORT).show();
                }

                else {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", editId.getText().toString());
                        jsonObject.put("pass", passEdit.getText().toString());
                        jsonObject.put("name", name.getText().toString());
                        jsonObject.put("phoneNum",  phoneNum.getText().toString());
                        jsonObject.put("address", adress.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    PrefManager.userData.put(jsonObject);
                    pref.setPrefString("users", PrefManager.userData.toString());

                    finish();
                }
            }
        });
    }
}
