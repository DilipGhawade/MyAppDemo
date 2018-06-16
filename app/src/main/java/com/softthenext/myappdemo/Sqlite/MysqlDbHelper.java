package com.softthenext.myappdemo.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.softthenext.myappdemo.Model.CartModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dilip Ghawade
 * Organization Name SoftTheNext
 * on Date 14/06/2018.
 */
public class MysqlDbHelper extends SQLiteOpenHelper {
  SQLiteDatabase db;

    private static final String DATABASE_NAME="register.DB";
    private static final int DATABASE_VERSION=1;
    protected static final String CREATE_QUERY=
            "CREATE TABLE "+ RegistrationContract.UserContract.TABLE_NAME+"" +
                    "("+ RegistrationContract.UserContract.USER_NAME+" TEXT,"+
                    RegistrationContract.UserContract.USER_MOB+" TEXT,"+
                    RegistrationContract.UserContract.USER_EMAIL+" TEXT,"+
                    RegistrationContract.UserContract.USER_ADDRESS+" TEXT);";

    public MysqlDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.e("Database Operation","Database is created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("Database Operation","Table is Created");

    }
    public void addInformation(String name,String mobileno,String email,String address,SQLiteDatabase db)
    {
        ContentValues values = new ContentValues();
        values.put(RegistrationContract.UserContract.USER_NAME,name);
        values.put(RegistrationContract.UserContract.USER_MOB,mobileno);
        values.put(RegistrationContract.UserContract.USER_EMAIL,email);
        values.put(RegistrationContract.UserContract.USER_ADDRESS,address);
        db.insert(RegistrationContract.UserContract.TABLE_NAME,null,values);

    }

    public List<CartModel> getAllRegisterUser()
    {
        String colm[] = {RegistrationContract.UserContract.USER_NAME,
                RegistrationContract.UserContract.USER_MOB,
                RegistrationContract.UserContract.USER_EMAIL,
                RegistrationContract.UserContract.USER_ADDRESS,
                };

        String order = RegistrationContract.UserContract.USER_NAME + " ASC";

        List<CartModel> userlist = new ArrayList<CartModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.query(RegistrationContract.UserContract.TABLE_NAME ,
                colm,
                null,
                null,
                null,
                null,
                order
        );
        if (cursor.moveToFirst())
        {
            do {
                CartModel dataModel = new CartModel();
                dataModel.setName(cursor.getString(cursor.getColumnIndex(RegistrationContract.UserContract.USER_NAME)));
                dataModel.setEmail(cursor.getString(cursor.getColumnIndex(RegistrationContract.UserContract.USER_MOB)));
                dataModel.setPassword(cursor.getString(cursor.getColumnIndex(RegistrationContract.UserContract.USER_EMAIL)));
                dataModel.setMobno(cursor.getString(cursor.getColumnIndex(RegistrationContract.UserContract.USER_ADDRESS)));

                userlist.add(dataModel);

            }
            while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return userlist;
    }
    /*public Cursor getinformation(SQLiteDatabase db)
    {
        Cursor cursor;
        String[] projection={RegistrationContract.UserContract.USER_NAME,
                RegistrationContract.UserContract.USER_MOB,
                RegistrationContract.UserContract.USER_EMAIL,
                RegistrationContract.UserContract.USER_ADDRESS};
        cursor = db.query(RegistrationContract.UserContract.TABLE_NAME,projection,null,null,null,null,null);
        return cursor;
    }
*/
    public Cursor SearchInformation(String username,SQLiteDatabase sqLiteDatabase)
    {
        String[] projection={RegistrationContract.UserContract.USER_MOB,
                RegistrationContract.UserContract.USER_EMAIL,
                RegistrationContract.UserContract.USER_ADDRESS};
        String selection= RegistrationContract.UserContract.USER_NAME+" LIKE ?";
        String[] selection_args={username};
        Cursor cursor = sqLiteDatabase.query(RegistrationContract.UserContract.TABLE_NAME,
                projection,selection,selection_args,null,null,null);
        return cursor;
    }
    public void deleteinfromation(String username,SQLiteDatabase sqLiteDatabase)
    {
        String selection= RegistrationContract.UserContract.USER_NAME+" LIKE ?";
        String[] selection_args={username};
        sqLiteDatabase.delete(RegistrationContract.UserContract.TABLE_NAME,selection,selection_args);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
     public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(RegistrationContract.UserContract.TABLE_NAME, RegistrationContract.UserContract.USER_NAME,new String[] {id});
    }
  //DELETE/REMOVE
  public boolean delete(String id)
  {
    try {
      int result=db.delete(RegistrationContract.UserContract.TABLE_NAME,RegistrationContract.UserContract.USER_NAME+" =?",new String[]{(id)});
      if(result>0) {
        return true;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public int countData()
  {
    int row = 0;
    String sql = "SELECT COUNT(*) FROM "+RegistrationContract.UserContract.TABLE_NAME;
    Cursor cursor = getReadableDatabase().rawQuery(sql,null);
    if (cursor.getCount()>0)
    {
      cursor.moveToFirst();
      row = cursor.getInt(0);
      cursor.close();

    }
    return row;
  }
  public int getProfilesCount() {
    String countQuery = "SELECT  * FROM " + RegistrationContract.UserContract.TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    int count = cursor.getCount();
    cursor.close();
    return count;
  }
}
