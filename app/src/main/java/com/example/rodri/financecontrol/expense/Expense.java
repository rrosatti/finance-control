package com.example.rodri.financecontrol.expense;


/**
 * Created by rodri on 2/1/2016.
 */
public class Expense {

    private long id;
    private String expenseName;
    private double value;
    private String date;
    private int month_id;


    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getExpenseName(){
        return expenseName;
    }

    public void setExpenseName(String expenseName){
        this.expenseName = expenseName;
    }

    public double getValue(){
        return value;
    }

    public void setValue(double value){
        this.value = value;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public int getMonth_id(){
        return month_id;
    }

    public void setMonth_id(int month_id){
        this.month_id = month_id;
    }

    @Override
    public String toString(){
        return date + " - " + expenseName + " - " + value;
    }

}
