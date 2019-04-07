package Model;

import java.io.Serializable;

public class ExpensesDBModel
{
    String strExpId;
    String strExpName;
    String strExpDate;
    String strExpTime;
    double strExpPrice;

    public ExpensesDBModel()
    {
    }

    public ExpensesDBModel(String strExpName, String strExpDate, String strExpTime,double strExpPrice)
    {

        this.strExpName = strExpName;
        this.strExpDate = strExpDate;
        this.strExpTime = strExpTime;
        this.strExpPrice = strExpPrice;
    }

    public String getStrExpName() {
        return strExpName;
    }

    public void setStrExpName(String strExpName) {
        this.strExpName = strExpName;
    }

    public String getStrExpDate() {
        return strExpDate;
    }

    public void setStrExpDate(String strExpDate) {
        this.strExpDate = strExpDate;
    }

    public double getStrExpPrice() {
        return strExpPrice;
    }

    public void setStrExpPrice(double strExpPrice) {
        this.strExpPrice = strExpPrice;
    }

    public String getStrExpId() {
        return strExpId;
    }

    public void setStrExpId(String strExpId) {
        this.strExpId = strExpId;
    }

    public String getStrExpTime() {
        return strExpTime;
    }

    public void setStrExpTime(String strExpTime) {
        this.strExpTime = strExpTime;
    }
}
