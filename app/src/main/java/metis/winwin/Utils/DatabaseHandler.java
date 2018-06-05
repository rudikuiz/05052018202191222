package metis.winwin.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import metis.winwin.Model.Notif;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by innan on 11/11/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "winwin";
    private static final String TABLE_NOTIF = "notif";

    private static final String KEY_ID = "id";
    private static final String KEY_ID_PENG = "id_pengajuan";
    private static final String KEY_MESSAGE = "message";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INTERNAL_TABLE = "CREATE TABLE " + TABLE_NOTIF + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_PENG + " TEXT,"
                + KEY_MESSAGE + " TEXT" + ")";
        db.execSQL(CREATE_INTERNAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIF);

        // Create tables again
        onCreate(db);
    }

    public void addNotif(Notif internal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_PENG, internal.getId_pengajuan());
        values.put(KEY_MESSAGE, internal.getMessage());

        // Inserting Row
        db.insert(TABLE_NOTIF, null, values);
        db.close(); // Closing database connection
    }

    public List<Notif> getAllNotifs() {
        List<Notif> contactList = new ArrayList<Notif>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIF + " order by id desc";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notif contact = new Notif();
                contact.setId(cursor.getString(0));
                contact.setId_pengajuan(cursor.getString(1));
                contact.setMessage(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

}
