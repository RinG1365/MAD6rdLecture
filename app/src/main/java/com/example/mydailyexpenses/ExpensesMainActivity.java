package com.example.mydailyexpenses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Model.ExpensesDBModel;
import sqliteexpenses.ExpensesDB;

public class ExpensesMainActivity extends AppCompatActivity {

    EditText edtExpName, edtExpPrice, edtExpDate, edtExpTime;
    String strURL = "http://192.168.1.105:8888/webServiceJSON/globalWebService.php";
    ExpensesDB expensesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_main);

        edtExpName = findViewById(R.id.edtName);
        edtExpPrice = findViewById(R.id.edtPrice);
        edtExpDate = findViewById(R.id.edtDate);
        edtExpTime = findViewById(R.id.edtTime);

        /*Get Real Time & Date */
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL,
                new Response.Listener<String>() {

                    @Override
                        public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            edtExpDate.setText(jsonObject.optString("currDate"));
                            edtExpTime.setText(jsonObject.optString("currTime"));
                        } catch (Exception ee) {
                            Log.e("onResponse", ee.getMessage());
                        }
                    }



                },new Response.ErrorListener(){

                        @Override
                            public void onErrorResponse(VolleyError error){
                            Log.e("ErrorListener",error.getMessage());
                        }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
               params.put("selectFn","fnGetDateTime");


                return params;
            }
        };
        requestQueue.add(stringRequest);
        /*END Get Real Time & Date */

        expensesDB = new ExpensesDB(getApplicationContext());


    }

    public void fnSave(View view)
    {
        ExpensesDBModel myExpenses = new ExpensesDBModel(edtExpName.getText().toString(),edtExpDate.getText().toString(),
                edtExpTime.getText().toString(),Double.parseDouble(edtExpPrice.getText().toString()));
        expensesDB.fnInsertExpense(myExpenses);

        Toast.makeText(getApplicationContext(),"Expenses Saved!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int idMenu = item.getItemId();
        Intent intent = null;
        if(idMenu == R.id.addExp)
        {
            intent = new Intent(this,ExpensesMainActivity.class);
        }
        else if(idMenu == R.id.vwExp)
        {
            intent = new Intent(this,ActivityExpList.class);
        }

        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }



}
