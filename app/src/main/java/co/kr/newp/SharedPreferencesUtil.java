//임시로 데이터 저장
package co.kr.newp;

import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils.StringSplitter;

public class SharedPreferencesUtil {

	private final String PREF_NAME = "co.kr.apphow.bohum";

	public final static String PREF_INTRO_USER_AGREEMENT = "PREF_USER_AGREEMENT";
	public final static String PREF_MAIN_VALUE = "PREF_MAIN_VALUE";
	

	static Context mContext;

	public SharedPreferencesUtil(Context c) {
		mContext = c;
	}

	public void put(String key, String value) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putString(key, value);
		editor.commit();
	}
	
	public void put(String key, Set<String> value) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putStringSet(key, value);
		editor.commit();
	}
	
	

	public void put(String key, boolean value) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putBoolean(key, value);
		editor.commit();
	}

	public void put(String key, int value) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putInt(key, value);
		editor.commit();
	}

	public String getValue(String key, String dftValue) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);

		try {
			return pref.getString(key, dftValue);
		} catch (Exception e) {
			return dftValue;
		}

	}
	
	public Set<String> getValue(String key, Set<String> dftValue) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);

		try {
			return pref.getStringSet(key, dftValue);
		} catch (Exception e) {
			return dftValue;
		}

	}


	public int getValue(String key, int dftValue) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);

		try {
			return pref.getInt(key, dftValue);
		} catch (Exception e) {
			return dftValue;
		}

	}

	public boolean getValue(String key, boolean dftValue) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);

		try {
			return pref.getBoolean(key, dftValue);
		} catch (Exception e) {
			return dftValue;
		}
	}
	
	public void remove(String key) {
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.remove(key);
		editor.commit();
	}
}
