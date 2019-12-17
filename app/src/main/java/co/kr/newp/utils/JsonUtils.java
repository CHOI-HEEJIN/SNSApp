//학번들을 불러와서 서버에서 잘라서 가져옴
package co.kr.newp.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils
{/*여기 있어야되는거 멤버변수 근데없음*/
	public static JSONObject/*자료형*/ getSafeJSONObject(JSONObject json, String key/*인자값*/)/*함수이름*/
	{
		try
		{
			return json.getJSONObject(key);//반환값1
		}
		catch (Exception e) { }
		
		return null;//반환값2
	}

	public static JSONArray getSafeJSONArray(JSONObject json, String key)
	{
		try
		{
			return json.getJSONArray(key);
		}
		catch (Exception e) { }
		
		return null;
	}

	public static String getSafeString(JSONObject json, String key)
	{
		try
		{
			String value = json.getString(key);
			if (value.equals("null") == true) 
			{
				value = "";
			}
			return value;
		}
		catch (Exception e) { }
		
		return null;
	}
	
	public static int getSafeInt(JSONObject json, String key)
	{
		try
		{
			return json.getInt(key);
		}
		catch (Exception e) { }
		
		return 0;
	}
	
	public static long getSafeLong(JSONObject json, String key)
	{
		try
		{
			return json.getLong(key);
		}
		catch (Exception e) { }
		
		return 0;
	}
	
	public static boolean getSafeBoolean(JSONObject json, String key)
	{
		try
		{
			return json.getBoolean(key);
		}
		catch (Exception e) { }
		
		return false;
	}
	
	public static void putSafe(JSONObject json, String key, Object value)
	{
		try
		{
			json.put(key, value);
		}
		catch (Exception e) { }
	}
	
	public static void putSafe(JSONObject json, String key, int value)
	{
		try
		{
			json.put(key, value);
		}
		catch (Exception e) { }
	}

	public static void putSafe(JSONObject json, String key, boolean value)
	{
		try
		{
			json.put(key, value);
		}
		catch (Exception e) { }
	}
	
	
}
