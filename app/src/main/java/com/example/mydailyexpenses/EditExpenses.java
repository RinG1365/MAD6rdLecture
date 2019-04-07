package com.example.mydailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import Model.ExpensesDBModel;
import sqliteexpenses.ExpensesDB;

public class EditExpenses extends AppCompatActivity {

    ExpensesDB expensesDB;
    ExpensesDBModel expensesList;
    EditText edtName,edtPrice,edtDate, edtTime;
    String expPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expenses);


        Intent intent = getIntent();
       expPosition = intent.getStringExtra("expPosition");

        edtName = findViewById(R.id.edtTxtNameAft);
        edtDate = findViewById(R.id.edtTxtDateAft);
        edtPrice = findViewById(R.id.edtTxtPriceAft);
        edtTime = findViewById(R.id.edtTxtTimeAft);

       expensesDB = new ExpensesDB(getApplicationContext());
       expensesList = (ExpensesDBModel) expensesDB.fnGetExpenses(Integer.valueOf(expPosition));



        edtName.setText(expensesList.getStrExpName());
        edtDate.setText(expensesList.getStrExpDate());
        edtTime.setText(expensesList.getStrExpTime());
        edtPrice.setText(String.valueOf(expensesList.getStrExpPrice()));


    }


    public void fnConfirmEdit(View view)
    {
        expensesList = new ExpensesDBModel();
        expensesList.setStrExpId(expPosition);
        expensesList.setStrExpName(edtName.getText().toString());
        expensesList.setStrExpDate(edtDate.getText().toString());
        expensesList.setStrExpTime(edtTime.getText().toString());
        expensesList.setStrExpPrice(Double.valueOf(edtPrice.getText().toString()));

        expensesDB.fnUpdateExpenses(expensesList);

        Toast.makeText(getApplicationContext(),"Expenses Updated!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,ActivityExpList.class);
        startActivity(intent);
    }




    public void fnBack(View view)
    {
        Intent intent = null;
        intent = new Intent(this,ActivityExpList.class);
        startActivity(intent);

    }
}
