package pl.arkadius.testapp;

import android.content.SharedPreferences;

/**
 * Created by arkadius on 04.12.17.
 */

public class SharedPreferencesManagerImpl implements SharedPreferencesManager {

    private SharedPreferences shrdPref;
    private SharedPreferences.Editor editor;

    public static final String PREF_NAME="contacts_preferences";

    public SharedPreferencesManagerImpl(SharedPreferences shrdPref){
        this.shrdPref=shrdPref;
        editor=shrdPref.edit();
    }

    @Override
    public boolean checkIsSeen(String id) {
        return shrdPref.getBoolean(id,false);
    }

    @Override
    public void setSeen(String id,boolean seen) {
        editor.putBoolean(id,seen).apply();
    }
}
