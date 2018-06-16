package com.softthenext.myappdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;
import com.softthenext.myappdemo.ApIInterface.AddorRemoveCallbacks;
import com.softthenext.myappdemo.ApIInterface.ApiInterface;
import com.softthenext.myappdemo.AppUrl.Urls;
import com.softthenext.myappdemo.Model.ProductModel;
import com.softthenext.myappdemo.RecyclerAdapter.ProductAdapter;
import com.softthenext.myappdemo.Sqlite.MysqlDbHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    List<ProductModel> prductlist;
    SearchView searchView;
    ProductAdapter adapter;
    private static int cart_count=0;
    NotificationBadge badge;
    MysqlDbHelper db;
    SQLiteDatabase database;
    TextView txtcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtcount = findViewById(R.id.txt_count);
        recyclerView = findViewById(R.id.pro_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        /*final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterfaces = retrofit.create(ApiInterface.class);
        Call<List<ProductModel>> call =apiInterfaces.getCarlists();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                //pd.dismiss();
                prductlist = response.body();
                 adapter = new ProductAdapter(prductlist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {

                //pd.dismiss();

                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.cart) {

            Intent intent = new Intent(this,CartActivity.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

    BadgeDrawable badge;

    // Reuse drawable if possible
    Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
    if (reuse != null && reuse instanceof BadgeDrawable) {
      badge = (BadgeDrawable) reuse;
    } else {
      badge = new BadgeDrawable(context);
    }

    badge.setCount(count);
    icon.mutate();
    icon.setDrawableByLayerId(R.id.ic_badge, badge);

  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);

        View view = menu.findItem(R.id.cart).getActionView();

        //badge = (NotificationBadge)view.findViewById(R.id.badge);
      MenuItem itemCart = menu.findItem(R.id.cart);
      LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
      setBadgeCount(this, icon, "0");


        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        ((EditText) searchView.findViewById(
                android.support.v7.appcompat.R.id.search_src_text)).
                setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final  List<ProductModel> filtermodelist=filter(prductlist,newText);
                adapter.setfilter(filtermodelist);
                return true;
            }
        });
        return true;
    }

    private List<ProductModel> filter(List<ProductModel> pl,String query)
    {
        query=query.toLowerCase();
        final List<ProductModel> filteredModeList=new ArrayList<>();
        for (ProductModel model:pl)
        {
            final String text=model.getProductName().toLowerCase();

            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }


}
