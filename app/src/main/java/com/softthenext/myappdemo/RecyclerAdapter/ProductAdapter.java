package com.softthenext.myappdemo.RecyclerAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softthenext.myappdemo.ApIInterface.AddorRemoveCallbacks;
import com.softthenext.myappdemo.MainActivity;
import com.softthenext.myappdemo.Model.ProductModel;
import com.softthenext.myappdemo.R;
import com.softthenext.myappdemo.Sqlite.MysqlDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dilip Ghawade
 * Organization Name SoftTheNext
 * on Date 13/06/2018.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProcuctViewHolder> {

    List<ProductModel> list;
    Context context;
    public ProductAdapter(List<ProductModel> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public ProcuctViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_list,parent,false);
        parent.getContext();
       return new ProcuctViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProcuctViewHolder holder, final int position) {
        Picasso.get().load(list.get(position).getImageUrl()).resize(120, 60).into(holder.img);
        holder.txtpname.setText(list.get(position).getProductName());
        holder.txtpprice.setText(list.get(position).getPrice());
        holder.txtpqty.setText(list.get(position).getQuantity());
        holder.txtpunit.setText(list.get(position).getQuantity());

        /*holder.txtadditem.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (Integer.parseInt(holder.txtcount.getText().toString()) >= 1) {
              int a = Integer.parseInt(holder.txtcount.getText().toString());
              int x = a++;
              //holder.txtadditem.setText(Integer.toString(a));
            }
          }
        });*/



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setfilter(List<ProductModel> listitem)
    {
        list=new ArrayList<>();
        list.addAll(listitem);
        notifyDataSetChanged();
    }
    public class ProcuctViewHolder extends RecyclerView.ViewHolder
    {
        Context context;
        ImageView img;
        TextView txtpname,txtpprice,txtpqty,txtpunit,txtadditem,txtcount;

        MysqlDbHelper mysqlDbHelper;
        SQLiteDatabase sqLiteDatabase;
        int mCount;

        public ProcuctViewHolder(final View itemView) {
            super(itemView);
            itemView.getContext().getApplicationContext();

            txtpname = itemView.findViewById(R.id.p_name);
            txtpprice = itemView.findViewById(R.id.p_price);
            txtpqty = itemView.findViewById(R.id.p_qty);
            txtpunit = itemView.findViewById(R.id.p_unit);
            txtadditem = itemView.findViewById(R.id.txt_addToCart);
            txtcount = itemView.findViewById(R.id.txt_count);
            img = itemView.findViewById(R.id.product_img);
            txtadditem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name=txtpname.getText().toString();
                    String mob=txtpprice.getText().toString();
                    String emaill=txtpqty.getText().toString();
                    String address=txtpunit.getText().toString();
                    mysqlDbHelper = new MysqlDbHelper(itemView.getContext());
                    sqLiteDatabase=mysqlDbHelper.getWritableDatabase();
                    mysqlDbHelper.addInformation(name,mob,emaill,address,sqLiteDatabase);
                    Toast.makeText(itemView.getContext(),"Added Successfully",Toast.LENGTH_LONG).show();
                    mysqlDbHelper.close();
                    doIncrease();
                }
            });


        }
      private void doIncrease() {
        mCount++;

      }
    }

}
