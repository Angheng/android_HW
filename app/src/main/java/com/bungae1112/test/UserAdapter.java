package com.bungae1112.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater inflater = null;
    ArrayList<UserData> userData;

    public UserAdapter(Context context, ArrayList<UserData> userData) {
        mContext = context;
        this.userData = userData;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return userData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public UserData getItem(int position) {
        return userData.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.user_layout, null);

        TextView id = (TextView) view.findViewById(R.id.user_id);
        TextView pass = (TextView) view.findViewById(R.id.user_pass);

        id.setText(userData.get(position).getId());
        pass.setText(userData.get(position).getPass());

        return view;
    }
}
