package pl.lukpra.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class ShopListDbAdapter {

    public ShopListDbAdapter(Context context)
    {
        this.context = context;
    }

    public ShopListDbAdapter open() throws android.database.SQLException{
        slistDBHelper = new SlistDBHelper(context);
        sqldb = slistDBHelper.getWritableDatabase();
        return this;
    }

    public ShopList createElement(String name, String info, ShopList.Category category){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_INFO, info);
        values.put(COL_CAT, category.name());
        values.put(COL_DATE, Calendar.getInstance().getTimeInMillis() + "");

        long insertId = sqldb.insert(SHOP_LIST_TABLE, null, values);

        Cursor cursor = sqldb.query(SHOP_LIST_TABLE, allTable, COL_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        ShopList newSList = cursorToSlist(cursor);
        cursor.close();
        return newSList;
    }

    public long deleteNote(long id){
        return sqldb.delete(SHOP_LIST_TABLE, COL_ID + " = " + id, null);
    }

    public long updateElement(String name, String info, ShopList.Category category, long id){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_INFO, info);
        values.put(COL_CAT, category.name());
        values.put(COL_DATE, Calendar.getInstance().getTimeInMillis() + "");

        return sqldb.update(SHOP_LIST_TABLE, values, COL_ID + " = " + id, null);
    }

    public ArrayList<ShopList> getAllShopListElements(){
        ArrayList<ShopList> elems = new ArrayList<ShopList>();

        Cursor cursor = sqldb.query(SHOP_LIST_TABLE, allTable, null, null, null, null, null);

        for(cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious())
        {
            ShopList slist = cursorToSlist(cursor);
            elems.add(slist);
        }

        cursor.close();

        return elems;

    }

    private ShopList cursorToSlist(Cursor cursor){
        ShopList slist = new ShopList( cursor.getString(1), cursor.getString(2), ShopList.Category.valueOf(cursor.getString(3)), cursor.getLong(0), cursor.getLong(4));
        return slist;
    }

    public void close(){
        slistDBHelper.close();
    }
    private static final String DATABASE_NAME = "shoplist.db";
    private static final int DATABASE_VERSION = 1;

    public static final String SHOP_LIST_TABLE = "element";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_INFO = "info";
    public static final String COL_CAT = "category";
    public static final String COL_DATE = "date";


    private String[] allTable = { COL_ID, COL_NAME, COL_INFO, COL_CAT, COL_DATE};

    public static final String DATABASE_CREATE = "create table " + SHOP_LIST_TABLE + " ( "
            + COL_ID    + " integer primary key autoincrement, "
            + COL_NAME  + " text not null, "
            + COL_INFO  + " text not null, "
            + COL_CAT   + " integer not null, "
            + COL_DATE  + " );";

    private SQLiteDatabase sqldb;
    private Context context;
    private SlistDBHelper slistDBHelper;



    private static class SlistDBHelper extends SQLiteOpenHelper{
        SlistDBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL(" DROP TABLE IF EXISTS " + SHOP_LIST_TABLE);
            onCreate(db);
        }
    }
}
