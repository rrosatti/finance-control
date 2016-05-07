package com.example.rodri.financecontrol.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rodri.financecontrol.expense.Expense;
import com.example.rodri.financecontrol.database.ExpensesDataSource;
import com.example.rodri.financecontrol.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by rodri on 2/1/2016.
 */
public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private Activity activity;
    private List<Expense> lExpense;
    private static LayoutInflater inflater = null;

    private ExpensesDataSource dataSource;
    private Typeface typeface;

    public ExpenseAdapter(Activity activity, int textViewResourceId, List<Expense> _lExpense, Typeface typeface){
        super(activity, textViewResourceId, _lExpense);
        try {
            this.activity = activity;
            this.lExpense = _lExpense;
            this.typeface = typeface;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        } catch (Exception e){

        }
    }

    public int getCount(){
        return lExpense.size();
    }

    public Expense getItem(Expense position){
        return position;
    }

    public long getItemId(int position){
        return position;
    }

    public static class ViewHolder {
        public TextView display_expense;
        public TextView display_value;
        public TextView display_date;
    }

    public View getView(int position, View convertView, final ViewGroup parent){
        View vi = convertView;
        final ViewHolder holder;
        final Expense expense = lExpense.get(position);
        try {
            if (convertView == null){
                vi = inflater.inflate(R.layout.expense_list_item, null);
                holder = new ViewHolder();

                holder.display_expense = (TextView) vi.findViewById(R.id.txtShowExpense);
                holder.display_value = (TextView) vi.findViewById(R.id.txtShowValue);
                holder.display_date = (TextView) vi.findViewById(R.id.txtShowDate);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            DecimalFormat formatter = new DecimalFormat("#0.00");

            holder.display_expense.setText(lExpense.get(position).getExpenseName());
            holder.display_value.setText("R$ " + String.valueOf(formatter.format(lExpense.get(position).getValue())));
            holder.display_date.setText(lExpense.get(position).getDate());

            holder.display_date.setTypeface(typeface);
            holder.display_expense.setTypeface(typeface);
            holder.display_value.setTypeface(typeface);

        } catch (Exception e){
            Log.e("Error: ", "Error: " + e);
        }

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //Toast.makeText(parent.getContext(), "view clicked: " + expense.getExpenseName(), Toast.LENGTH_SHORT).show();


                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Remove item?");
                builder.setCancelable(true);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dataSource = new ExpensesDataSource(activity);
                        dataSource.open();

                        dataSource.deleteExpense(expense);

                        Intent reload = new Intent(activity, activity.getClass());
                        activity.finish();
                        reload.putExtra("month_id", expense.getMonth_id());
                        activity.startActivity(reload);

                   }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }
        });

        return vi;
    }

}
