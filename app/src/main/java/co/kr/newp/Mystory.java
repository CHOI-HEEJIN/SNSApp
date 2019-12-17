package co.kr.newp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import co.kr.newp.Data_content;
import co.kr.newp.core.DefaultActivity;

//나의 이야기
public class Mystory extends DefaultActivity {
	ImageView img2,img3,img4,img5,imgtop;
	TextView tx1,txtop;

	ArrayList<Data_content> arr;
	GridView grid1;
	Gridadapter gridadt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mystory);
		tx1=(TextView) findViewById(R.id.main_profile_tx1);
		txtop=(TextView) findViewById(R.id.main_profile_txtop);
		img2=(ImageView) findViewById(R.id.main_profile_img2);
		img3=(ImageView) findViewById(R.id.main_profile_img3);
		img4=(ImageView) findViewById(R.id.main_profile_img4);
		img5=(ImageView) findViewById(R.id.main_profile_img5);
		imgtop=(ImageView) findViewById(R.id.main_profile_imgtop);
		grid1=(GridView) findViewById(R.id.mystory_grid1);
		//레이아웃에 설정된 뷰들을 변수로 가져옴
		}

		//처음 화면에서 이미지가져오기(이미지가 존재할 경우)
	  class WebGetImage extends AsyncTask<String, Void, Bitmap> {
		  
		    @Override
		    protected Bitmap doInBackground(String... params) {
		    	// 네트워크에 접속해서 데이터를 가져옴
		      
		      try {
		        URL Url = new URL(params[0]);		//url에 접속 설정(사진이 있는 주소로 접근)
		        URLConnection urlcon = Url.openConnection();	//연결
		        HttpURLConnection.setFollowRedirects(false);

		        HttpURLConnection con = (HttpURLConnection) Url.openConnection();
			  	con.setRequestMethod("HEAD");		//헤더 정보를 읽어온다.
			  	if(con.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND){
					  return null;
			  	}

			  	urlcon.connect();
		        int imagelength = urlcon.getContentLength();	// 이미지 길이 불러옴
		        BufferedInputStream bis = new BufferedInputStream(urlcon.getInputStream(), imagelength);	// 스트림 클래스를 이용하여 이미지를 불러옴
		        Bitmap bit = BitmapFactory.decodeStream(bis);	// 스트림을 통하여 저장된 이미지를 이미지 객체에 넣어줌
		        return bit;

		      } catch (Exception e) {
		        e.printStackTrace();
		        return null;
		      }
		    }
		    @Override
		    protected void onPostExecute(Bitmap result) {
		    	super.onPostExecute(result);
		    }
	  }


	@Override
	protected void onResume() {
		super.onResume();
		NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_profile.php",getApplicationContext());		//연결할 url 설정
		ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
		ns.add(new BasicNameValuePair("id", getValue("id", "")));	//임시로 저장되어있던 유저의 id값을 받아와 변수 ns에 담는다.

		JSONArray jso;
		try {		//프로필 이름 넣기
			if ((jso = nw.getJSONArrayByPOST(ns)) != null) {	//ns를 POST방식으로 서버에 전달하고, 결과값이 null이 아니라면,
				for (int i = 0; i < jso.length(); i++) {
					JSONObject jo = jso.getJSONObject(i);		//JSONObject형태로 데이터를 jo에 저장하고
					txtop.setText(jo.getString("name"));	//jo의 "name"값을 txtop 뷰에 세팅한다.
					put("name", jo.getString("name"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		setbtnexe();	//버튼 클릭 함수 설정

		arr=new ArrayList<Data_content>();
			try {		//프로필 사진 넣기
				WebGetImage wg=new WebGetImage();
				Bitmap s=wg.execute("http://sogo4070.dothome.co.kr/profile/"+getValue("id", "")+".jpg").get();
				//서버에서 프로필이미지를 가져온다.
				if(s==null) {
				}
				else {		//이미지가 존재할 때
					Matrix mat = new Matrix();		//Matrix : ImageView의 틀을 기준으로 해서 왼쪽 상단을 꼭지점으로 정렬된다.
					s = Bitmap.createBitmap(s, 0, 0, s.getWidth(), s.getHeight(), mat, true);	//원본 사진의 크기 그대로 bitmap 생성
					imgtop.setImageBitmap(s);		//뷰 imgtop에 반환된 사진을 세팅한다.
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		  	nw=new NetWork("http://sogo4070.dothome.co.kr/get_content.php"	,getApplicationContext()); //연결할 url 선언
			 ns=new ArrayList<NameValuePair>();
			 ns.add(new BasicNameValuePair("id", getValue("id", ""))); //현재 사용자의 id를 ns에 저장한다.


			 try{		// 나의글 검색후 가져오기
				 JSONArray jsonarr=null;

				 jsonarr=nw.getJSONArrayByPOST(ns);		//ns를 POST방식으로 서버에 전달하고, JSONArray형태로 결과값을 받는다.
				 										// (사용자가 작성한 게시물의 정보가 리턴됨)
				 for(int i=0;i<jsonarr.length();i++){
					 JSONObject jsonob=jsonarr.getJSONObject(i);
					  nw=new NetWork("http://sogo4070.dothome.co.kr/get_choice.php"	,getApplicationContext());
						 ns=new ArrayList<NameValuePair>();
						 ns.add(new BasicNameValuePair("id", jsonob.getString("_num")));	//사용자가 작성한 게시물번호를 ns에 저장
						 String z=nw.getStringByPOST(ns);		//게시물번호를 서버에 전달하고, 결과값을 받는다. ('choice'를 누른 유저들의 정보가 리턴됨)

						 nw=new NetWork("http://sogo4070.dothome.co.kr/get_reply.php"	,getApplicationContext());
						 ns=new ArrayList<NameValuePair>();
						 ns.add(new BasicNameValuePair("id", jsonob.getString("_num")));	//사용자가 작성한 게시물번호를 ns에 저장
						 String q=nw.getStringByPOST(ns);		//유저id, 게시물번호, 댓글의 정보를 받는다.

					 	//arr에 String 형태로 지금까지 가져온 데이터를 저장한다.
						arr.add(new Data_content(jsonob.getString("_num").toString(),jsonob.getString("_writer").toString(),
								jsonob.getString("_date").toString(), jsonob.getString("_title").toString(),
								jsonob.getString("_place").toString(), jsonob.getString("_pic").toString(),
								jsonob.getString("_text").toString(),z,q));

				 }
				 gridadt=new Gridadapter(getApplicationContext(), arr);
				 grid1.setAdapter(gridadt);	// 그리드뷰에 붙이기
				 }
			 catch(Exception e){

			 }
				grid1.setOnItemClickListener(new OnItemClickListener() {	//그리드뷰 클릭시 나오는 화면
								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
										long arg3) {
							G.arr.add(arr.get(arg2));		//뷰의 위치를 arr에 저장하고, Main_content 액티비티로 이동
							startActivity(new Intent(Mystory.this,Main_content.class));
									}
			});
	  }
		//버튼 클릭 함수
	  private void setbtnexe() {
			tx1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),Main_profile.class));
				finish();
				}
			});
			img3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getApplicationContext(),Story.class));
					finish();
				}
			});
			img4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(getApplicationContext(),Write.class));
				}
			});
			img5.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(getApplicationContext(),Setting.class));
					
				}
			});
			
		}
}
