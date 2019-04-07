package com.example.mydailyexpenses;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Model.CustomAdapterExpList;
import Model.ExpensesDBModel;
import sqliteexpenses.ExpensesDB;

public class ActivityExpList extends AppCompatActivity {

    RecyclerView recycleViewExpList;
    ArrayList<ExpensesDBModel> expensesList;

    ExpensesDB expensesDB;
    ExpensesDBModel expensesModel;
    CustomAdapterExpList customAdapterExpList;
    TextView txtVwTp;

    double totalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_list);

        recycleViewExpList = findViewById(R.id.recyListExp);
        txtVwTp = findViewById(R.id.txtVwTp);

        expensesDB = new ExpensesDB(getApplicationContext());
        expensesList = (ArrayList<ExpensesDBModel>) expensesDB.fnGetAllExpenses();


        customAdapterExpList = new CustomAdapterExpList(expensesList);

        recycleViewExpList.setLayoutManager(new LinearLayoutManager(this));
        recycleViewExpList.setAdapter(customAdapterExpList);
        customAdapterExpList.notifyDataSetChanged();

        /**
         * RecyclerView: Implementing single item click and long press (Part-II)
         * */
        recycleViewExpList.addOnItemTouchListener(new RecyclerTouchListener(this,
                recycleViewExpList, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well

                Toast.makeText(ActivityExpList.this, "Short press on position :"+position,
                        Toast.LENGTH_LONG).show();

                    Intent intent = null;
                    intent = new Intent(ActivityExpList.this,EditExpenses.class);
                    intent.putExtra("expPosition",String.valueOf(position+1));
                    startActivity(intent);




            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(ActivityExpList.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));

        totalPrice = expensesDB.fnCalculateTotalPrice();

        txtVwTp.setText(String.valueOf(totalPrice));


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

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     *
     * - creating an Interface for single tap and long press
     * - Parameters are its respective view and its position
     * */

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    /**
     * RecyclerView: Implementing single item click and long press (Part-II)
     *
     * - creating an innerclass implementing RevyvlerView.OnItemTouchListener
     * - Pass clickListener interface as parameter
     * */

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
