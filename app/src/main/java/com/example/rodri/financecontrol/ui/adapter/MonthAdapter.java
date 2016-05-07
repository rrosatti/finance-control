package com.example.rodri.financecontrol.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.financecontrol.expense.Expense;
import com.example.rodri.financecontrol.database.ExpensesDataSource;
import com.example.rodri.financecontrol.R;
import com.example.rodri.financecontrol.ui.activity.ExpensesActivity;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by rodri on 2/4/2016.
 */
public class MonthAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private String[] lMonth;
    private static LayoutInflater inflater = null;

    private ExpensesDataSource expensesDataSource;
    private List<Expense> lExpense;



    public MonthAdapter(Activity activity, int textViewResourceId, String[] _lMonth){
        super(activity, textViewResourceId, _lMonth);
        try {
            this.activity = activity;
            this.lMonth = _lMonth;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e){

        }

    }

    public int getCount() {
        return lMonth.length;
    }

    public String getItem(String position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_month;
        public TextView display_total;
    }

    public View getView(final int position, View convertView, final ViewGroup parent){
        View v = convertView;
        final ViewHolder holder;
        final String month = lMonth[position];

        double total = 0.0;
        expensesDataSource = new ExpensesDataSource(activity);
        expensesDataSource.open();
        lExpense = expensesDataSource.getAllExpenses(position+1);

        for (Expense exp : lExpense){
            total += exp.getValue();
        }

        try {
            if (convertView == null){
                v = inflater.inflate(R.layout.month_list_item, null);
                holder = new ViewHolder();
                holder.display_month = (TextView) v.findViewById(R.id.txtMonth);
                holder.display_total = (TextView) v.findViewById(R.id.txtTotalByMonth);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            DecimalFormat formatter = new DecimalFormat("#0.00");

            holder.display_month.setText(month);
            holder.display_total.setText("R$ " + String.valueOf(formatter.format(total)));

        } catch (Exception e){
            Log.e("Error", "Error" + e);
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expensesList = new Intent(activity, ExpensesActivity.class);
                expensesList.putExtra("month_id", position + 1);
                activity.startActivity(expensesList);
            }
        });

        return v;
    }

}
