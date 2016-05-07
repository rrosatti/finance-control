package com.example.rodri.financecontrol.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rodri.financecontrol.R;
import com.example.rodri.financecontrol.ui.adapter.MonthAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private MonthAdapter monthAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ListView monthList = (ListView) findViewById(R.id.listMonth);
        String[] months = {"January", "February", "March", "April", "May", "June",
                            "July", "August", "September", "October", "November", "December"};
        //final String[] months = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
        //                    "Setembro", "Outubro", "Novembro", "Dezembro" };


        final ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<months.length; i++){
            list.add(months[i]);
        }

        //final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        //monthList.setAdapter(adapter);

        monthAdapter = new MonthAdapter(MainActivity.this, 0, months);
        monthList.setAdapter(monthAdapter);


        monthList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id){

                Intent expensesScreen = new Intent(MainActivity.this, ExpensesActivity.class);
                expensesScreen.putExtra("month_id", position+1);
                /*Bundle extras = new Bundle();
                extras.putInt("month_id", position + 1);
                extras.putString("month_name", months[position]);
                expensesScreen.putExtras(extras);*/
                startActivity(expensesScreen);

            }

        });

    }

    @Override
    public void onResume(){
        super.onResume();
        monthAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
