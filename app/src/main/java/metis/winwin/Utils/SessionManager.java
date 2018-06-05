package metis.winwin.Utils;

/**
 * Created by Web on 16/04/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "metis.winwin.log";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_IDUSER = "iduser";

    public static final String KEY_NAMA = "name";

    public static final String KEY_IDSESSION = "idsession";

    public static final String KEY_INCALL = "incall";

    public static final String KEY_INCHAT = "inchat";

    public static final String KEY_RATE = "rate";

    public static final String SESSION = "_session";

    public static final String KEY_MAX = "max";

    public static final String KEY_RATING = "rating";

    public static final String KEY_MAX_PINJAM = "maxpinjam";

    public static final String KEY_ID_HQ = "id_hq";

    public static final String KEY_TOTAL_SETUJUI = "total_setujui";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String iduser, String nama, String session) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_IDUSER, iduser);

        editor.putString(KEY_NAMA, nama);

        editor.putString(SESSION, session);

        editor.commit();
    }

    public boolean checkLogin() {
        // Check login status

        boolean stLogin = true;

        if (!this.isLoggedIn()) {

            stLogin = false;
        }

        return stLogin;

    }

    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


    }

    public boolean session() {
        return pref.getBoolean(SESSION, false);
    }

    public String getSession() {
        return pref.getString(SESSION, null);
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String getIduser() {
        return pref.getString(KEY_IDUSER, null);
    }

    public String getNama() {
        return pref.getString(KEY_NAMA, null);
    }

    public String getIdsession() {
        return pref.getString(KEY_IDSESSION, null);
    }

    public boolean getInCall() {
        return pref.getBoolean(KEY_INCALL, false);
    }

    public String getRate() {
        return pref.getString(KEY_RATE, null);
    }

    public String getMax() {
        return pref.getString(KEY_MAX, null);
    }

    public String getRating() {
        return pref.getString(KEY_RATING, null);
    }

    public String getMaxpinjam() {
        return pref.getString(KEY_MAX_PINJAM, null);
    }

    public String getTotalSetujui() {
        return pref.getString(KEY_TOTAL_SETUJUI, null);
    }

    public String getIdhq() {
        return pref.getString(KEY_ID_HQ, null);
    }


    public boolean getInChat() {
        return pref.getBoolean(KEY_INCHAT, false);
    }

    public void setNama(String nama) {
        editor.putString(KEY_NAMA, nama);
        editor.commit();
    }

    public void setIduser(String iduser) {
        editor.putString(KEY_IDUSER, iduser);
        editor.commit();
    }

    public void setIdsession(String idsession) {
        editor.putString(KEY_IDSESSION, idsession);
        editor.commit();
    }

    public void setInCall(boolean incall) {
        editor.putBoolean(KEY_INCALL, incall);
        editor.commit();
    }

    public void setInChat(boolean inchat) {
        editor.putBoolean(KEY_INCHAT, inchat);
        editor.commit();
    }

    public void setRate(String rate) {
        editor.putString(KEY_RATE, rate);
        editor.commit();
    }

    public void setMax(String value) {
        editor.putString(KEY_MAX, value);
        editor.commit();
    }

    public void setIdhq(String value) {
        editor.putString(KEY_ID_HQ, value);
        editor.commit();
    }

    public void setRating(String value) {
        editor.putString(KEY_RATING, value);
        editor.commit();
    }

    public void setMaxpinjam(String value) {
        editor.putString(KEY_MAX_PINJAM, value);
        editor.commit();
    }

    public void setTotalSetujui(String value) {
        editor.putString(KEY_TOTAL_SETUJUI, value);
        editor.commit();
    }


}