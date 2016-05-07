package com.example.rodri.financecontrol.ui.activity;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rodri.financecontrol.expense.Expense;
import com.example.rodri.financecontrol.database.ExpensesDataSource;
import com.example.rodri.financecontrol.R;
import com.example.rodri.financecontrol.ui.adapter.ExpenseAdapter;
import com.example.rodri.financecontrol.util.Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by rodri on 2/1/2016.
 */
public class ExpensesActivity extends AppCompatActivity {

    private Button newExpense;
    private TextView txtTotal;
    private ListView expensesListView;

    private ExpensesDataSource dataSource;
    private ExpenseAdapter expenseAdapter;
    private Typeface typeface;
    private List<Expense> expenses;

    private int month_id;
    private double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses);
        setTitle(R.string.expenses_title);
        initialize();


        try {
            dataSource.open();

            expenses = dataSource.getAllExpenses(month_id);

            dataSource.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Expense value : expenses){
            total += value.getValue();
        }

        NumberFormat formatter = new DecimalFormat("#0.00");

        txtTotal.setText("Total: R$ " + formatter.format(total));
        txtTotal.setVisibility(View.VISIBLE);

        expenseAdapter = new ExpenseAdapter(ExpensesActivity.this, 0, expenses, typeface);
        expensesListView.setAdapter(expenseAdapter);


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

    public void initialize() {
        month_id = getIntent().getIntExtra("month_id", 0);
        dataSource = new ExpensesDataSource(this);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/AutourOne-Regular.otf");

        txtTotal = (TextView)findViewById(R.id.txtTotal);
        expensesListView = (ListView)findViewById(R.id.listExpenses);
        newExpense = (Button)findViewById(R.id.btAddExpense);

        newExpense.setTypeface(typeface);
        txtTotal.setTypeface(typeface);
    }


}
