package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.shadabsheikh.pain_tracker.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> mData;
    HashMap<String, String> hm;
    public Context context;
    TextView name,value,time;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;
    Context con;

    public ListAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        mData = data;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ls_row, parent, false);
            name = (TextView) convertView.findViewById(R.id.name);
            value = (TextView) convertView.findViewById(R.id.value);
            time = (TextView) convertView.findViewById(R.id.time);
        hm = mData.get(position);
        name.setText(hm.get("name"));
        value.setText(hm.get("value"));
        time.setText(hm.get("time"));
        return convertView;
    }


}

