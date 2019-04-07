package sqliteexpenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.ExpensesDBModel;


public class ExpensesDB extends SQLiteOpenHelper
{
    public static final String dbName = "dbMyExpenses";
    public static final String tblNameExpense = "expenses";
    public static final String colExpName = "expenses_name";
    public static final String colExpPrice = "expenses_price";
    public static final String colExpDate = "expenses_date";
    public static final String colExpTime = "expenses_time";
    public static final String colExpId = "expenses_id";
    public static final String colTotalPrice = "total_price";

    public static final String strCrtTblExpenses ="CREATE TABLE " + tblNameExpense + " (" +
            colExpId + " INTEGER PRIMARY KEY, " + colExpName + " TEXT, "+ colExpPrice +
            " REAL, "+ colExpDate + " DATE, "+ colExpTime + " TEXT)";
    public static final String strDrpTblExpenses = "DROP TABLE IF EXISTS "+ tblNameExpense;

    public ExpensesDB(Context context)
    {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(strCrtTblExpenses);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(strDrpTblExpenses);
        onCreate(db);
    }

    public float fnInsertExpense(ExpensesDBModel meExpenses)
    {
        float retResult = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colExpName, meExpenses.getStrExpName());
        values.put(colExpPrice, meExpenses.getStrExpPrice());
        values.put(colExpDate, meExpenses.getStrExpDate());
        values.put(colExpTime, meExpenses.getStrExpTime());

        retResult = db.insert(tblNameExpense,null,values);
        return retResult;

    }

    public int fnUpdateExpenses(ExpensesDBModel meExpense)
    {
        int retResult = 0;
        ContentValues values = new ContentValues();
        values.put(colExpName, meExpense.getStrExpName());
        values.put(colExpPrice, meExpense.getStrExpPrice());
        values.put(colExpDate, meExpense.getStrExpDate());
        String [] argg = {String.valueOf(meExpense.getStrExpId()) };
        this.getWritableDatabase().update(tblNameExpense,values, colExpId + " = ?", argg);
        return retResult;
    }


    public ExpensesDBModel fnGetExpenses (int intExpId)
    {
        ExpensesDBModel modelExpenses = new ExpensesDBModel();
        String strSelQry = "Select * from " + tblNameExpense + " where "+ colExpId + " = " + intExpId;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelQry,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }

        modelExpenses.setStrExpPrice(cursor.getDouble(cursor.getColumnIndex(colExpPrice)));
        modelExpenses.setStrExpDate(cursor.getString(cursor.getColumnIndex(colExpDate)));
        modelExpenses.setStrExpName(cursor.getString(cursor.getColumnIndex(colExpName)));
        modelExpenses.setStrExpTime(cursor.getString(cursor.getColumnIndex(colExpTime)));

        return modelExpenses;
    }

    public List<ExpensesDBModel> fnGetAllExpenses()
    {
        List<ExpensesDBModel> listExp = new ArrayList<>();
        String strSelAll = "Select * from " + tblNameExpense;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelAll,null);
        if(cursor.moveToFirst())
        {
            do{
                ExpensesDBModel modelExpenses = new ExpensesDBModel();
                modelExpenses.setStrExpPrice(cursor.getDouble(cursor.getColumnIndex(colExpPrice)));
                modelExpenses.setStrExpDate(cursor.getString(cursor.getColumnIndex(colExpDate)));
                modelExpenses.setStrExpName(cursor.getString(cursor.getColumnIndex(colExpName)));
                modelExpenses.setStrExpTime(cursor.getString(cursor.getColumnIndex(colExpTime)));
                listExp.add(modelExpenses);
            }while(cursor.moveToNext());
        }
        return listExp;
    }

    public double fnCalculateTotalPrice()
    {
        double totalPrice =0.00;
        String strSelAll = "Select SUM("+colExpPrice+") AS "+ colTotalPrice +" from " + tblNameExpense;
        Cursor cursor = this.getReadableDatabase().rawQuery(strSelAll,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
        }

        totalPrice=cursor.getDouble(cursor.getColumnIndex(colTotalPrice));
        return totalPrice;
    }
}
