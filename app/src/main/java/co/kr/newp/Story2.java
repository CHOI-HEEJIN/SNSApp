package co.kr.newp;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import co.kr.newp.Mystory.WebGetImage;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Story2 extends Activity {
ImageView img1;
SharedPreferencesUtil spu;
RelativeLayout rel1,rel2;
TextView tx1,tx2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story2);

		spu=new SharedPreferencesUtil(getApplicationContext());
		img1=(ImageView) findViewById(R.id.img1);
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);

		tx1.setText("이름 "+spu.getValue("name", ""));		//저장해놨던 사용자정보를 뷰에 세팅한다.
		tx2.setText("ID  "+spu.getValue("id", ""));

		try {		//프로필 사진 넣기
			WebGetImage wg=new WebGetImage();
			Bitmap s=wg.execute("http://sogo4070.dothome.co.kr/profile/"+spu.getValue("id", "")+".jpg").get();
			//서버에서 프로필이미지를 가져온다.
			Matrix mat=new Matrix();
			s=Bitmap.createBitmap(s, 0, 0, s.getWidth(), s.getHeight(), mat, true);
			//원본 사진의 크기 그대로 bitmap 생성

			if(s!=null)					//이미지가 존재할 경우
			img1.setImageBitmap(s);			//반환된 이미지를 뷰에 세팅한다.
		} catch (Exception e) {
			e.printStackTrace();
		}

		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {		//뒤로가기
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.rel2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	//로그아웃 버튼 클릭시
			startActivity(new Intent(getApplicationContext(),Login.class));
			spu.put("id", "");			//임시로 저장해놨던 유저정보를 지운다.
			spu.put("name", "");
			spu.put("password", "");
			finish();
			}
		});
		
	findViewById(R.id.rel3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			startActivity(new Intent(getApplicationContext(),Bye.class));
			}
		});
	}
	
	  class WebGetImage extends AsyncTask<String, Void, Bitmap> {
		  
		    @Override
		    protected Bitmap doInBackground(String... params) {
		      // 네트워크에 접속해서 데이터를 가져옴
		      
		      try {
		        URL Url = new URL(params[0]);					//웹사이트에 접속 (사진이 있는 주소로 접근)
		        URLConnection urlcon = Url.openConnection();  	// 웹사이트에 접속 설정
		        urlcon.connect();		 						// 연결한다.
		        int imagelength = urlcon.getContentLength();	// 이미지 길이 불러옴
		        BufferedInputStream bis = new BufferedInputStream(urlcon.getInputStream(), imagelength);
				  // 스트림 클래스를 이용하여 이미지를 불러옴
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
}
