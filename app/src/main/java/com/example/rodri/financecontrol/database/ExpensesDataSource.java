package com.example.rodri.financecontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;

import com.example.rodri.financecontrol.expense.Expense;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodri on 2/1/2016.
 */
public class ExpensesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                                    MySQLiteHelper.COLUMN_EXPENSE,
                                    MySQLiteHelper.COLUMN_VALUE,
                                    MySQLiteHelper.COLUMN_DATE,
                                    MySQLiteHelper.COLUMN_MONTH_ID};

    public ExpensesDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Expense createExpense(String expense, double value, String date, long month_id){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_EXPENSE, expense);
        values.put(MySQLiteHelper.COLUMN_VALUE, value);
        values.put(MySQLiteHelper.COLUMN_DATE, date);
        values.put(MySQLiteHelper.COLUMN_MONTH_ID, month_id);

        long insertId = database.insert(MySQLiteHelper.TABLE_EXPENSES, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES, allColumns,
                                        MySQLiteHelper.COLUMN_ID + " = " + insertId,
                                        null, null, null, null);
        cursor.moveToFirst();
        Expense newExpense = cursorToExpense(cursor);
        cursor.close();
        return newExpense;
    }

    public void deleteExpense(Expense expense){
        long id = expense.getId();
        System.out.println("Expense deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_EXPENSES, MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Expense> getAllExpenses(int month_id){
        List<Expense> expenses = new ArrayList<>();

        /*Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES,
                allColumns,
                "month_id = " + month_id,
                allColumns,
                MySQLiteHelper.COLUMN_EXPENSE + ", " + MySQLiteHelper.COLUMN_DATE + ", " + MySQLiteHelper.COLUMN_VALUE,
                MySQLiteHelper.COLUMN_DATE + " ASC",
                null);*/
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EXPENSES,
                allColumns,
                "month_id = " + month_id,
                null,
                null,
                null,
                MySQLiteHelper.COLUMN_VALUE + " ASC");
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Expense expense = cursorToExpense(cursor);
            expenses.add(expense);
            cursor.moveToNext();
        }

        cursor.close();
        return expenses;
    }

    private Expense cursorToExpense(Cursor cursor){
        Expense expense = new Expense();
        expense.setId(cursor.getLong(0));
        expense.setExpenseName(cursor.getString(1));
        expense.setValue(cursor.getDouble(2));
        expense.setDate(cursor.getString(3));
        expense.setMonth_id(cursor.getInt(4));
        System.out.println(expense.getExpenseName());
        return expense;
    }

}
