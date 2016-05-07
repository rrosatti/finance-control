package com.example.rodri.financecontrol.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rodri.financecontrol.expense.Expense;
import com.example.rodri.financecontrol.database.ExpensesDataSource;
import com.example.rodri.financecontrol.R;
import com.example.rodri.financecontrol.ui.adapter.ExpenseAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by rodri on 2/1/2016.
 */
public class ExpensesActivity extends Activity {

    private ExpensesDataSource datasource;
    private ExpenseAdapter adpExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses);

        String[] months = {"January", "February", "March", "April", "May", "June",
                            "July", "August", "September", "October", "November", "December"};
        //final String[] months = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
        //        "Setembro", "Outubro", "Novembro", "Dezembro" };


        final int month_id = getIntent().getIntExtra("month_id", 0);

        //final int month_id = getIntent().getExtras().getInt("month_id");
        //final String month_name = getIntent().getExtras().getString("month_name");

        TextView tvMonthName = (TextView)findViewById(R.id.tvExpenses);
        tvMonthName.setText(months[month_id - 1]);

        TextView txtTotal = (TextView)findViewById(R.id.txtTotal);
        double total = 0.0;

        datasource = new ExpensesDataSource(this);
        datasource.open();

        ListView allExpenses = (ListView)findViewById(R.id.listExpenses);
        List<Expense> values = datasource.getAllExpenses(month_id);

        for (Expense value : values){
            total += value.getValue();
        }

        NumberFormat formatter = new DecimalFormat("#0.00");

        txtTotal.setText("Total: R$ " + formatter.format(total));
        txtTotal.setVisibility(View.VISIBLE);

        //final ExpenseAdapter adpExpense;
        adpExpense = new ExpenseAdapter(ExpensesActivity.this, 0, values);
        allExpenses.setAdapter(adpExpense);

        Button newExpense = (Button)findViewById(R.id.btAddExpense);

        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addExpense = new Intent(ExpensesActivity.this, NewExpenseActivity.class);
                addExpense.putExtra("month_id", month_id);
                startActivity(addExpense);
                finish();

            }
        });


    }


}
