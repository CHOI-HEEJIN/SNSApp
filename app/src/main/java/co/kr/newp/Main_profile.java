package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Main_profile extends Activity {
ImageView img1,img2,img3,img4,img5,imgtop;
TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7,txtop;
SharedPreferencesUtil spu;
final int REQ_CODE_SELECT_IMAGE=100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_profile);
		tx1=(TextView) findViewById(R.id.main_profile_tx1);	//레이아웃의 뷰를 변수로 선언
		tx2=(TextView) findViewById(R.id.main_profile_tx2);
		tx3=(TextView) findViewById(R.id.main_profile_tx3);
		tx4=(TextView) findViewById(R.id.main_profile_tx4);
		tx5=(TextView) findViewById(R.id.main_profile_tx5);
		tx6=(TextView) findViewById(R.id.main_profile_tx6);
		tx7=(TextView) findViewById(R.id.main_profile_tx7);
		txtop=(TextView) findViewById(R.id.main_profile_txtop);
		img1=(ImageView) findViewById(R.id.main_profile_img1);
		img2=(ImageView) findViewById(R.id.main_profile_img2);
		img3=(ImageView) findViewById(R.id.main_profile_img3);
		img4=(ImageView) findViewById(R.id.main_profile_img4);
		img5=(ImageView) findViewById(R.id.main_profile_img5);
		imgtop=(ImageView) findViewById(R.id.main_profile_imgtop);
		spu=new SharedPreferencesUtil(getApplicationContext());	//임시로 데이터를 저장하기위한 변수
		
		
		NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_profile.php",getApplicationContext());
		ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));	//현재 사용자의 id를 ns에 저장
		
			
			JSONArray jso;
			try {
				jso = nw.getJSONArrayByPOST(ns); //id를 POST방식으로 서버에 전달하고, JSONArray형태로 사용자 정보를 받는다.
				for(int i=0;i<jso.length();i++){
					JSONObject jo=jso.getJSONObject(i);
					tx2.setText(jo.getString("name"));		//받아온 사용자 정보를 뷰에 세팅한다.
					txtop.setText(jo.getString("name"));
					tx3.setText(jo.getString("id"));
					tx4.setText(jo.getString("birth"));
					tx5.setText("남");
					if(jo.getInt("sex")==2){
					tx5.setText("여");
					}
					tx6.setText(jo.getString("profile"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		setbtnexe();

		try {	//프로필 사진 넣기
			WebGetImage wg=new WebGetImage();
			Bitmap s=wg.execute("http://sogo4070.dothome.co.kr/profile/"+spu.getValue("id", "")+".jpg").get();
			//서버에서 프로필이미지를 가져온다.
			if(s==null)
				return;

			Matrix mat=new Matrix();
			s=Bitmap.createBitmap(s, 0, 0, s.getWidth(), s.getHeight(), mat, true); //원본 사진의 크기 그대로 bitmap 생성
			if(s!=null)		//이미지가 존재할 경우
				img1.setImageBitmap(s);		//반환된 이미지를 뷰에 세팅한다.
				imgtop.setImageBitmap(s);
		} catch (Exception e) {
	e.printStackTrace();
}
}
	
	//버튼 클릭 함수
	private void setbtnexe() {
		tx1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			startActivity(new Intent(getApplicationContext(),Mystory.class));
			finish();
			}
		});
		tx7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			Intent i=new Intent(getApplicationContext(),Modify_profile.class);		//프로필 수정 액티비티로 이동
			i.putExtra("name", tx2.getText().toString().trim());		//name, birth, sex, profile 값을 넘겨줄 것임
			i.putExtra("birth", tx4.getText().toString().trim());
			i.putExtra("sex", tx5.getText().toString().trim());
			i.putExtra("profile", tx6.getText().toString().trim());
			startActivity(i);
			finish();
			}
		});
		img1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {			//사진편집 클릭할 때
				Intent intent = new Intent(Intent.ACTION_PICK); //데이터로부터 아이템을 선택하고 선택한 아이템을 리턴시키라는 액션
				intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
				intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				//외부에 저장되어있는 데이터를 가져올 것임을 선언
				startActivityForResult(intent, REQ_CODE_SELECT_IMAGE); //이미지를 넘긴다.

			}
		});
		
		img3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(),Story.class));
			}
		});
		img5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(),Setting.class));
			}
		});
		img4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			startActivity(new Intent(getApplicationContext(),Write.class));
			}
		});
		
	}
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(requestCode == REQ_CODE_SELECT_IMAGE) 
			{ 
				if(resultCode==Activity.RESULT_OK) {	//이미지가 정상적으로 넘어왔다면
					try {
						Bitmap image_bitmap = Images.Media.getBitmap(getContentResolver(), data.getData());
						Matrix mat = new Matrix();

						image_bitmap=Bitmap.createBitmap(image_bitmap, 0, 0, image_bitmap.getWidth(), image_bitmap.getHeight(), mat, true);
						//원본이미지 그대로 비트맵 생성
						img1.setImageBitmap(image_bitmap);	//이미지를 뷰에 세팅한다.
						imgtop.setImageBitmap(image_bitmap);

						Dojob upload_profile_pic=new Dojob();
						upload_profile_pic.execute(getPath(data.getData())+"");	//이미지의 경로를 불러온다.

					} catch (FileNotFoundException e) {
						e.printStackTrace(); 
					} catch (IOException e) {
						e.printStackTrace(); 
					} catch (Exception e) {
	            		e.printStackTrace();
					} 
				}     
			}   
		}

		
	public String getPath(Uri uri) {		//이미지 경로를 불러오는 함수
	    String[] projection = {MediaStore.Images.Media.DATA};
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
		// 이미지 경로로 해당 이미지에 대한 정보를 가지고 있는 cursor 호출
	    startManagingCursor(cursor);
	    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		// 해당 필드의 인덱스를 반환하고, 존재하지 않을 경우 예외를 발생시킨다.
	    cursor.moveToFirst(); //가장 처음에 위치한 레코드를 가리킨다.
	    return cursor.getString(columnIndex);
	}
	
	
	 class Dojob extends AsyncTask<String,Void, Void>{
			@Override
			protected Void doInBackground(String... params) {
				try {
				     FTPClient mFTP = new FTPClient();

				     mFTP.connect("sogo4070.dothome.co.kr", 21);  // ftp로 접속
				     mFTP.login("sogo4070", "sopwer12"); 		// ftp 로그인 계정/비밀번호
				     mFTP.setFileType(FTP.BINARY_FILE_TYPE); // 바이너리 파일 형태로 설정
				     mFTP.setBufferSize(1024 * 1024); 			// 버퍼 사이즈 설정
				     mFTP.enterLocalPassiveMode();
				     mFTP.setFileType(FTP.BINARY_FILE_TYPE);

				     mFTP.cwd("html");	 	// ftp 상의 업로드 디렉토리
				     mFTP.mkd("profile"); 	// public아래로 files 디렉토리를 만든다
				     mFTP.cwd("profile"); 	// public/files 로 이동 (이 디렉토리로 업로드가 진행)

				     File file = new File(params[0].trim()); // 업로드 할 파일이 있는 경로
				    
				     Bitmap src = BitmapFactory.decodeFile(params[0]);
					// 파일을 통하여 저장된 이미지를 이미지 객체에 넣어줌
				     String z = SaveBitmapToFileCache(src, Environment.getExternalStorageDirectory().getAbsolutePath(),"/img_temp.jpg");
				     //SD카드의 절대 경로에 비트맵 이미지를 저장한다.


				     File filezz = new File(z); 
				               if (filezz.isFile()) {
				                    FileInputStream ifile = new FileInputStream(filezz);
				                    mFTP.storeFile(spu.getValue("id", "")+".jpg", ifile);
				                 // ftp에 해당 파일을 저장한다.

				                    mFTP.appendFile(spu.getValue("id", "")+".jpg", ifile); // ftp 해당 파일이 없다면 새로쓰기
				               }
				 mFTP.disconnect();

				     } catch (Exception e) {
				          e.printStackTrace();
				     }
				return null;
			}
		}
	  class WebGetImage extends AsyncTask<String, Void, Bitmap> {
		  
		    @Override
		    protected Bitmap doInBackground(String... params) {
		      // 네트워크에 접속해서 데이터를 가져옴
		      
		      try {
		        URL Url = new URL(params[0]); 					//웹사이트에 접속 (사진이 있는 주소로 접근)
		        URLConnection urlcon = Url.openConnection();	// 연결
		        urlcon.connect();
		        int imagelength = urlcon.getContentLength();	// 이미지 길이 불러옴
		        BufferedInputStream bis = new BufferedInputStream(urlcon.getInputStream(), imagelength);
				  // 스트림 클래스를 이용하여 이미지를 불러옴

		        Bitmap bit = BitmapFactory.decodeStream(bis);
				  // 스트림을 통하여 저장된 이미지를 이미지 객체에 넣어줌
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

	  public String SaveBitmapToFileCache(Bitmap bitmap, String strFilePath,
	            String filename) {
	 
	        File file = new File(strFilePath);
	 
	        //파일이 존재하지 않는다면
	        if (!file.exists()) {
	            file.mkdirs();		//디렉터리를 만든다.
	        }
	 
	        File fileCacheItem = new File(strFilePath + filename);	//이미지파일의 경로를 fileCacheItem에 담는다.
	        OutputStream out = null;
	 
	        try {
	            fileCacheItem.createNewFile();
	            out = new FileOutputStream(fileCacheItem);
	 
	            bitmap.compress(CompressFormat.JPEG, 10, out);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	         
	               }
	            return strFilePath + filename;
	        }
	    }


	@Override
	public void onBackPressed() {

	}
}
