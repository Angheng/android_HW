package com.bungae1112.test;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class PrefManager {
    private SharedPreferences pref;
    private Context context;
    private static final  String prefName = "users";
    protected static JSONArray userData;

    static {
        try {
            userData = new JSONArray("[]");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    PrefManager(Context context, String key) throws JSONException{
        this.context = context;
        userData = getJsonArray(key);
    }

    protected static boolean isCorrect(String id, String pass, boolean checkId, boolean checkPass) throws JSONException {
        Log.w("isCorrect", "inserted id: " + id + ", pass : " + pass);
        if (checkId && checkPass) {
            for (int i = 0; i < userData.length(); i++) {

                JSONObject object = userData.getJSONObject(i);
                Log.w(TAG, "obj's id: " + object.get("id") + "\tobj's pass: " + object.get("pass") + "\ntarget's id: " + id + "\ttatget's pass: " + pass);

                if (object.get("id").toString().equals( id ) && object.get("pass").toString().equals( pass )) {
                    return true;
                }
            }
        } else if (checkId) {

            for (int i = 0; i < userData.length(); i++) {

                JSONObject object = userData.getJSONObject(i);
                if (object.get("id") == id) {
                    return true;
                }
            }

        }

        return false;
    }

    protected JSONArray getJsonArray(String key) throws JSONException {
        JSONArray jArr;
        String data = getPrefString(key);

        if  (data.equals("Null")) {
            return new JSONArray("[]");
        }

        jArr = new JSONArray(data);

        return jArr;
    }

    protected String getPrefString(String key) {
        pref = context.getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        Log.w("getPref", "called : " + pref.getString(key, "Null"));
        return pref.getString(key, "Null");
    }

    protected void setPrefString (String key, String json) {

        Log.w("setPref", "called : " + json);
        pref = context.getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, json);
        editor.apply();
    }


}
