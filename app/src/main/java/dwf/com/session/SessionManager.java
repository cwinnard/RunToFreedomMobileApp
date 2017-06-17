package dwf.com.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import dwf.com.model.AccountDTO;
import dwf.com.model.SessionDTO;

/**
 * Created by Charlie Winnardd on 5/30/2017.
 */

public class SessionManager {
    SharedPreferences pref;
    Editor editor;
    Context _context;

    private static final String TAG = "RunToFreedom";

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "RunToFreedomPrefs";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_NAME = "Name";
    private static final String KEY_ACCOUNT_VALUE = "AccountValue";
    private static final String KEY_MONTHLY_SAVINGS = "MonthlySavings";
    private static final String KEY_CURRENT_RUN = "CurrentRunID";
    private static final String KEY_SAVED_RUNS = "SavedRunIDs";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String userName) {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public void storeUserData(AccountDTO dto) {
        Log.w(TAG, "storing user data in preferences for " + dto.getUserName());
        editor.putString(KEY_USERNAME, dto.getUserName());
        editor.putString(KEY_NAME, dto.getFirstName());
        editor.putFloat(KEY_ACCOUNT_VALUE, (float)dto.getAccountValue());
        editor.putFloat(KEY_MONTHLY_SAVINGS, (float)dto.getMonthlySavings());
        editor.commit();
    }

    public void storeNewRun(int runID) {
        Log.w(TAG, "storing new run ID");
        editor.putInt(KEY_CURRENT_RUN, runID);
        editor.commit();
    }

    public SessionDTO getUserData() {
        SessionDTO dto = new SessionDTO();
        dto.setUserName(pref.getString(KEY_USERNAME, null));
        dto.setFirstName(pref.getString(KEY_NAME, null));
        dto.setAccountValue((double)pref.getFloat(KEY_ACCOUNT_VALUE, 0));
        dto.setMonthlySavings((double)pref.getFloat(KEY_MONTHLY_SAVINGS, 0));
        dto.setCurrentRunID(pref.getInt(KEY_CURRENT_RUN, 0));
        return dto;
    }

}
