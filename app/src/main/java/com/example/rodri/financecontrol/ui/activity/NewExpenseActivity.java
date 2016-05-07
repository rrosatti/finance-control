package com.example.rodri.financecontrol.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.financecontrol.database.ExpensesDataSource;
import com.example.rodri.financecontrol.R;
import com.example.rodri.financecontrol.util.Util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by rodri on 2/1/2016.
 */
public class NewExpenseActivity extends AppCompatActivity {

    private Button btConfirm, btChooseDate;
    private EditText etDate, etExpenseName, etValue;

    private ExpensesDataSource dataSource;
    private Typeface typeface;

    private int month_id;

    protected void onCreate(Bundle savedInstanceState){
        Util.setFullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_expense);
        setTitle(R.string.new_expense_tittle);
        initialize();

        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = etDate.getText().toString();

                if (etExpenseName.getText().toString().matches("")){
                    Toast.makeText(NewExpenseActivity.this, "You did not enter a name for the expense", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etValue.getText().toString().matches("")){
                    Toast.makeText(NewExpenseActivity.this, "You did not enter a value", Toast.LENGTH_SHORT).show();
                    return;
                } else if (date.matches("")){
                    Toast.makeText(NewExpenseActivity.this, "You did not enter a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                dataSource.createExpense(etExpenseName.getText().toString(),
                        Double.parseDouble(etValue.getText().toString()),
                        date.trim(),
                        month_id);

                Intent back = new Intent(NewExpenseActivity.this, ExpensesActivity.class);
                back.putExtra("month_id", month_id);
                startActivity(back);
                finish();
            }
        });

        btChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener datePickerListener =     new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                };

                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePicker = new DatePickerDialog(NewExpenseActivity.this, R.style.AppTheme, datePickerListener,
                        cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                datePicker.show();

            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        final int month_id = getIntent().getIntExtra("month_id", 0);

        Intent intent = new Intent(NewExpenseActivity.this ,ExpensesActivity.class);
        intent.putExtra("month_id", month_id);
        startActivity(intent);
    }

    public void initialize() {
        month_id = getIntent().getIntExtra("month_id", 0);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/AutourOne-Regular.otf");
        dataSource = new ExpensesDataSource(this);

        etExpenseName = (EditText)findViewById(R.id.etExpenseName);
        etValue = (EditText)findViewById(R.id.etValue);
        btConfirm = (Button)findViewById(R.id.btSaveExpense);
        btChooseDate = (Button)findViewById(R.id.btChoose);
        etDate = (EditText)findViewById(R.id.etDate);

        etExpenseName.setTypeface(typeface);
        etValue.setTypeface(typeface);
        etDate.setTypeface(typeface);
        btChooseDate.setTypeface(typeface);
        btConfirm.setTypeface(typeface);
    }

}
