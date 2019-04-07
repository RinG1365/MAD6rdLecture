package Model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydailyexpenses.R;

import java.util.List;

public class CustomAdapterExpList extends RecyclerView.Adapter<CustomAdapterExpList.ViewHolder>
{
    List<ExpensesDBModel> listExpenses;


    public CustomAdapterExpList(List<ExpensesDBModel> expensesDBModels)
    {
            this.listExpenses = expensesDBModels;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expenses_recyler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ExpensesDBModel expensesDBModel = listExpenses.get(position);
        holder.txtVwExpName.setText(expensesDBModel.getStrExpName());
        holder.txtVwExpPrice.setText(String.valueOf(expensesDBModel.getStrExpPrice()));
        holder.txtVwExpDate.setText(expensesDBModel.getStrExpDate());
        holder.txtVwExpTime.setText(expensesDBModel.getStrExpTime());
    }

    @Override
    public int getItemCount()
    {
        return listExpenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtVwExpName, txtVwExpPrice, txtVwExpDate,txtVwExpTime;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtVwExpName = itemView.findViewById(R.id.txtVwExpName);
            txtVwExpPrice = itemView.findViewById(R.id.txtVwExpPrice);
            txtVwExpDate = itemView.findViewById(R.id.txtVwExpDate);
            txtVwExpTime = itemView.findViewById(R.id.txtVwExpTime);
        }

    }
}
