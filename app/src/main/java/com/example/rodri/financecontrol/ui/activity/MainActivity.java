package com.example.rodri.financecontrol.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rodri.financecontrol.R;
import com.example.rodri.financecontrol.ui.adapter.MonthAdapter;
import com.example.rodri.financecontrol.util.Util;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView monthList;
    private ArrayList<String> monthsArray;

    private MonthAdapter monthAdapter;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.months_title);
        initialize();

        String[] months = getResources().getStringArray(R.array.months);

        monthAdapter = new MonthAdapter(MainActivity.this, 0, months, typeface);
        monthList.setAdapter(monthAdapter);

        monthList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){

                Intent expensesScreen = new Intent(MainActivity.this, ExpensesActivity.class);
                expensesScreen.putExtra("month_id", position+1);
                startActivity(expensesScreen);

            }

        });

    }

    public void initialize() {
        typeface = Typeface.createFromAsset(getAssets(), "fonts/AutourOne-Regular.otf");
        monthList = (ListView) findViewById(R.id.listMonth);
    }

    @Override
    public void onResume(){
        super.onResume();
        monthAdapter.notifyDataSetChanged();
    }

}
