package com.example.shadabsheikh.pain_tracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by shadabsheikh on 2017-10-09.
 */



public class TroubleCodes extends Fragment {
    private ArrayList countries;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.trouble_codes,container,false);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Trouble Codes");

        initViews();

        final Switch onOffSwitch = (Switch) getView().findViewById(R.id.on_off_switch);
        //onOffSwitch.setChecked(true);
        //onOffSwitch.setText("Online");
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                Toast.makeText(getContext(),""+isChecked,
                       Toast.LENGTH_SHORT).show();

                if(isChecked)
                {
onOffSwitch.setText("Online");
                }
                else {
                    onOffSwitch.setText("Offline");
                }

            }

        });

    }
    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)getView().findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        countries = new ArrayList<>();
        countries.add("SERVICE PROVIDER");
        countries.add("SYNC");
        countries.add("TBD");

        RecyclerView.Adapter adapter = new DataAdapter(countries);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
//                    Intent i_trouble = new Intent(getActivity().getApplicationContext(),TroubleMaker.class);
//                    i_trouble.putExtra("from", (CharSequence) countries.get(position));
//                    startActivity(i_trouble);
//                    Toast.makeText(getActivity().getApplicationContext(), (CharSequence) countries.get(position), Toast.LENGTH_SHORT).show();

                if(position == 0) {

                    Toast.makeText(getActivity().getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                    Intent i_trouble = new Intent(getActivity().getApplicationContext(),ServiceRegister.class);
                    startActivity(i_trouble);
                }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}

