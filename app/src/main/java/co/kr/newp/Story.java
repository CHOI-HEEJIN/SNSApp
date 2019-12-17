package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Story extends Activity {
	ArrayList<Data_content> arr;
	GridView grid1;
	Gridadapter gridadt;
	ImageView img1,img2,img3,img4,img5;
	TextView tx1,tx2,tx3;
	EditText ed1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);
	
		ed1=(EditText) findViewById(R.id.ed1);		//레이아웃의 뷰듈을 변수로 반환한다.
		img1=(ImageView) findViewById(R.id.img1);
		grid1=(GridView) findViewById(R.id.grid1);
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
		tx3=(TextView) findViewById(R.id.tx3);
		img2=(ImageView) findViewById(R.id.img2);
		img3=(ImageView) findViewById(R.id.img3);
		img4=(ImageView) findViewById(R.id.img4);
		img5=(ImageView) findViewById(R.id.img5);
		
		setbtnexe();	//기타 클릭 함수들 구현
		grid1.setOnItemClickListener(new OnItemClickListener() {
					//그리드뷰 클릭시 나오는 화면
								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
										long arg3) {		//그리드뷰를 클릭할 때 동작하는 함수
									G.arr.add(arr.get(arg2));				//뷰의 위치를 arr에 저장하고, Main_content 액티비티로 이동
									startActivity(new Intent(Story.this,Main_content.class));
								}
			});

		img1.performClick();
	}

	private void setbtnexe() {
		 tx1.setOnClickListener(new OnClickListener(
				 ) {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),Story.class));
				finish();
				
			}
		});
		 tx2.setOnClickListener(new OnClickListener(
		 ) {

			 @Override
			 public void onClick(View v) {
				 startActivity(new Intent(getApplicationContext(),Activity_Calendar.class));
			 }
		 });
		 tx3.setOnClickListener(new OnClickListener(
		 ) {

			 @Override
			 public void onClick(View v) {
				 startActivity(new Intent(getApplicationContext(),Activity_map.class));
			 }
		 });
		 img3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getApplicationContext(),Story.class));
				}
			});
		 
			img4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getApplicationContext(),Write.class));
				}
			});
			
			img5.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(),Setting.class));
					
				}
			});
		img2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(),Mystory.class));
					
				}
			});

		img1.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {		//검색 이미지 클릭할 때 동작하는 함수
			arr=new ArrayList<Data_content>();

			NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/get_content_search.php"	,getApplicationContext());	//연결할 url 설정
			ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("id", ed1.getText().toString().trim())); //검색할 내용을 ns에 저장

			 try{
				 JSONArray jsonarr=null;
				 
				 jsonarr=nw.getJSONArrayByPOST(ns);		//검색할 내용을 서버에 POST방식으로 전달하고, JSONArray형태로 검색한 내용과 관련된 게시물 정보를 받아온다.

				 for(int i=0;i<jsonarr.length();i++){
					 JSONObject jsonob=jsonarr.getJSONObject(i);

						 nw = new NetWork("http://sogo4070.dothome.co.kr/get_choice.php", getApplicationContext());
						 ns = new ArrayList<NameValuePair>();
						 ns.add(new BasicNameValuePair("id", jsonob.getString("_num"))); 	//게시물id를 ns에 담는다.
						 String z = nw.getStringByPOST(ns);	//게시물id를 서버에 전달하고, String형태로  관련된 게시물의 데이터를 받아온다.

						 nw = new NetWork("http://sogo4070.dothome.co.kr/get_reply.php", getApplicationContext());
						 ns = new ArrayList<NameValuePair>();
						 ns.add(new BasicNameValuePair("id", jsonob.getString("_num")));	//게시물id를 ns에 담는다.
						 String q = nw.getStringByPOST(ns); // 게시물id를 서버에 전달하고, 관련된 게시물의 데이터를 받아온다.

					 	//arr배열에 받아온 데이터들을 담는다.
						 arr.add(new Data_content(jsonob.getString("_num").toString(), jsonob.getString("_writer").toString(),
								 jsonob.getString("_date").toString(), jsonob.getString("_title").toString(),
								 jsonob.getString("_place").toString(), jsonob.getString("_pic").toString(),
								 jsonob.getString("_text").toString(), z, q));

				 }
				 gridadt=new Gridadapter(getApplicationContext(), arr);
				 grid1.setAdapter(gridadt);	// 그리드뷰에 붙이기
				 }
			 catch(Exception e){}
	}
});

		}
	@Override
	protected void onResume() {
		super.onResume();
		img1.performClick();
	}
}
