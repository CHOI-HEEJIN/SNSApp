package co.kr.newp.core;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Set;

public class DefaultActivity extends Activity implements ShareablePreferences {

    public SharedPreferences getPreferences() {

        return this.getSharedPreferences($PREF_NAME, Activity.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor(){

        return getPreferences().edit();
    }

    public void put(String key, String value) {
        SharedPreferences.Editor editor = getEditor();

        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, Set<String> value) {

        SharedPreferences.Editor editor = getEditor();

        editor.putStringSet(key, value);
        editor.commit();
    }

    public void put(String key, boolean value) {

        SharedPreferences.Editor editor = getEditor();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public void put(String key, int value) {
        SharedPreferences.Editor editor = getEditor();

        editor.putInt(key, value);
        editor.commit();
    }

    public String getValue(String key) {

        return getPreferences().getString(key,null);
    }

    public String getValue(String key, String dftValue) {

        try {

            return getPreferences().getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    public Set<String> getValue(String key, Set<String> dftValue) {

        try {

            return getPreferences().getStringSet(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    public int getValue(String key, int dftValue) {

        try {

            return getPreferences().getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }

    }

    public boolean getValue(String key, boolean dftValue) {

        try {

            return getPreferences().getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public void remove(String key) {

        SharedPreferences.Editor editor = getEditor();

        editor.remove(key);
        editor.commit();
    }
}
