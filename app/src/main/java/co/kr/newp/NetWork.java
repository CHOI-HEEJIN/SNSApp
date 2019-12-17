//네트워크에 연결
package co.kr.newp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class NetWork {
	String url;
	Context mContext;

	public NetWork(String url, Context context) {
		this.url = url;
		this.mContext = context;
	}

	//JSONArray형태의 정보를 POST방식으로 받아온다.
	public JSONArray getJSONArrayByPOST(List<NameValuePair> nameValuePairs) throws Exception {
		return new JSONArrayTask(nameValuePairs).execute().get();
	}
	//String형태의 정보를 POST방식으로 받아온다.
	public String getStringByPOST(List<NameValuePair> nameValuePairs) throws Exception {
		return new StringTask(nameValuePairs).execute().get();
	}



	private class JSONArrayTask extends AsyncTask<Void, Void, JSONArray> {
		List<NameValuePair> nameValuePair;
		InputStream is = null;
		StringBuilder sb = null;
		JSONArray jsonArray;

		public JSONArrayTask(List<NameValuePair> nameValuePair) {
			this.nameValuePair = nameValuePair;
		}

		@Override
		protected void onPreExecute() {	//본격적인 작업에 앞서 준비 작업을 진행

			super.onPreExecute();
		}

		@Override
		protected JSONArray doInBackground(Void... arg0) {		//메인 작업이 실행되는 곳
			try {
				Log.e("url", url);

				DefaultHttpClient httpClient = new DefaultHttpClient();				//http접속을 위한 httpClient를 선언
				HttpParams params = httpClient.getParams();
				HttpConnectionParams.setConnectionTimeout(params, 10000);	//서버가 응답하는 시간의 한도를 정함
				HttpConnectionParams.setSoTimeout(params, 10000);			//서버가 응답하지 않는 경우 소켓의 연결을 끊음

				HttpPost httpPost = new HttpPost(url);

				if (nameValuePair != null) {		//전달할 인자가 null이 아니면
					//web을 통해서 나가는 값이기 때문에 반드시 url encoding을 해주어야 한다.
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePair, "UTF-8");
					httpPost.setEntity(entity);	//데이터를 싣는다.
				}

				HttpResponse httpResponse = httpClient.execute(httpPost);	 //서버를 실행하고 결과를 httpResponse에 받아온다.
				HttpEntity httpEntity = httpResponse.getEntity();			//엔티티를 얻어와서
				is = httpEntity.getContent();								//응답된 데이터를 읽을 수 있는 inputstream을 저장한다.

				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
				//입력 버퍼 생성
				//입력 스트림 객체를 매개변수로 주고 입력버퍼 객체를 생성한다.
				//버퍼링을 함으로써 문자, 문자배열, 문자열 라인 등을 효율적으로 처리하게 한다.
				sb = new StringBuilder();
				
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);	//한 라인씩 읽어서 스트링 버퍼에 담는다.
				}

				String jsonData = sb.toString();	//서버에서 받아온 문자열을 String형태로 변환하여 jsonData에 저장한다.

				if(!jsonData.endsWith("}")) {
					jsonData += "}";
				}
				Log.d(" json query result : ",jsonData);
				JSONObject json = new JSONObject(jsonData);			//jsonData를 JSONObject형태로 선언
				jsonArray = JSONUtil.getSafeJSONArray(json, "data");	//JSONArray형태로 저장

			} catch (Exception e) {

			}

			return jsonArray;
			
		}

		@Override
		protected void onPostExecute(JSONArray result) {
		}

	}

	private class StringTask extends AsyncTask<Void, Void, String> {
		List<NameValuePair> nameValuePair;
		InputStream is = null;
		StringBuilder sb = null;

		public StringTask(List<NameValuePair> nameValuePair) {
			this.nameValuePair = nameValuePair;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(Void... arg0) {	//메인 작업이 실행되는 곳
			try {

				Log.e("url", url);

				DefaultHttpClient httpClient = new DefaultHttpClient(); //http접속을 위한 httpClient를 선언
				HttpPost httpPost = new HttpPost(url);					//POST방식의 url 설정

				if (nameValuePair != null) {		//전달할 인자가 null이 아니라면
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePair, "UTF-8");
					//web을 통해서 나가는 값이기 때문에 반드시 url encoding을 해주어야 한다.
					httpPost.setEntity(entity);		//데이터를 싣는다.

				}
				HttpResponse httpResponse = httpClient.execute(httpPost); //서버를 실행하고 결과를 httpResponse에 받아온다.
				HttpEntity httpEntity = httpResponse.getEntity();		//엔티티를 얻어와서
				is = httpEntity.getContent();							//응답된 데이터를 읽을 수 있는 inputstream을 저장한다.

				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
				//입력 버퍼 생성
				//입력 스트림 객체를 매개변수로 주고 입력버퍼 객체를 생성한다.
				//버퍼링을 함으로써 문자, 문자배열, 문자열 라인 등을 효율적으로 처리하게 한다.
				sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {		//한 라인씩 읽어서 스트링 버퍼에 담는다.
					sb.append(line);
				}
			} catch (Exception e) {

			}
			return sb.toString();	//서버에서 받아온 문자열을 String형태로 반환한다.
		}

		@Override
		protected void onPostExecute(String result) {
		}

	}





}
