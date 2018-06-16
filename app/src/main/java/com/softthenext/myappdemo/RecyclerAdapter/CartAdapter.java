package com.softthenext.myappdemo.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.softthenext.myappdemo.MainActivity;
import com.softthenext.myappdemo.Model.CartModel;
import com.softthenext.myappdemo.R;
import com.softthenext.myappdemo.Sqlite.MysqlDbHelper;

import java.util.List;

/**
 * Created by Dilip Ghawade
 * Organization Name SoftTheNext
 * on Date 14/06/2018.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<CartModel> list;
    Context context;

    public CartAdapter(List<CartModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        parent.getContext();
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        holder.txtpname.setText(list.get(position).getName());
        holder.txtpprice.setText(list.get(position).getEmail());
        holder.txtpqty.setText(list.get(position).getPassword());
        holder.txtpunit.setText(list.get(position).getMobno());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtpname,txtpprice,txtpqty,txtpunit,txtdel;
        MysqlDbHelper myDb;
        int i;


        public CartViewHolder(final View itemView) {
          super(itemView);
          txtpname = itemView.findViewById(R.id.txt_name);
          txtpprice = itemView.findViewById(R.id.txt_email);
          txtpqty = itemView.findViewById(R.id.txt_address);
          txtpunit = itemView.findViewById(R.id.txt_mobno);
          txtdel = itemView.findViewById(R.id.txt_ditem);

        }
    }
}
