package com.softthenext.myappdemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.softthenext.myappdemo.Model.CartModel;
import com.softthenext.myappdemo.RecyclerAdapter.CartAdapter;
import com.softthenext.myappdemo.Sqlite.MysqlDbHelper;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MysqlDbHelper databaseHelper;
    List<CartModel> userlist;
    CartAdapter adapter;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_recyclerview);
        userlist = new ArrayList<>();
        adapter = new CartAdapter(userlist);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        databaseHelper = new MysqlDbHelper(this);
        getdataFromSQLite();

    }

    public void getdataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                userlist.clear();
                userlist.addAll(databaseHelper.getAllRegisterUser());

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
