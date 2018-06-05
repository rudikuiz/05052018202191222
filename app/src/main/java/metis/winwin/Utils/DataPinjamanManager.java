package metis.winwin.Utils;

/**
 * Created by Web on 16/04/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataPinjamanManager {

    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "metis.winwin.datapinjaman";


    public static final String KEY_JSON = "json";
    private static final String IS_EXIST = "IsExisi";

    public DataPinjamanManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createData(String json) {

        editor.putBoolean(IS_EXIST, true);
        editor.putString(KEY_JSON, json);

        editor.commit();
    }

    public boolean checkData() {
        // Check login status

        boolean stExist = true;

        if (!this.isExisting()) {

            stExist = false;
        }

        return stExist;

    }

    public void clearData() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

    public boolean isExisting() {
        return pref.getBoolean(IS_EXIST, false);
    }

    public String getJson() {
        return pref.getString(KEY_JSON, null);
    }


    public void setJson(String json) {
        editor.putString(KEY_JSON, json);
        editor.commit();
    }

}