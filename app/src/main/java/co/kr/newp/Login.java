package co.kr.newp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import  android.content.pm.Signature;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.SocialObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

public class Login extends Activity {

	EditText ed1, ed2;
	Button btn1, mSendBtn;
	TextView tx1, tx2;
	SharedPreferencesUtil spu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//버튼을 클릭하면 카카오링크호출
		mSendBtn = (Button) findViewById(R.id.btnSend);
		mSendBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendLink();
			}
		});

		spu = new SharedPreferencesUtil(getApplicationContext()); //임시로 데이터를 저장하는 spu 선언

		setbutton();
		setbuttonexe();


		/*권한이 이미 획득되었는지  확인 : WRITE_EXTERNAL_STORAGE - SD카드에 파일 읽고 쓰는 권한
		*ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION- gps와 네트워크 사용 권한*/
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			//권한을 아직 부여 받지 못했다면, requestPermission을 통해 해당권한을 사용자에게 요청한다.
			ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
		} else {
		}
	}

	//카카오링크 호출함수
	private void sendLink(){
			FeedTemplate params = FeedTemplate
				.newBuilder(ContentObject.newBuilder("희희낙樂으로 초대합니다",			//제목설정
						"https://i.imgur.com/7VPTCEz.png",						//링크초대 대표사진 설정
						LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")	//링크 연결
								.setMobileWebUrl("https://developers.kakao.com").build())
						.setDescrption("즐거운 여행기! 추억을 남겨보아요:)")					//부가설명 설정
						.build())
				.addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()		//'앱에서보기' 버튼 추가
						.setWebUrl("'https://developers.kakao.com")
						.setMobileWebUrl("'https://developers.kakao.com")
						.setAndroidExecutionParams("key1=value1")
						.setIosExecutionParams("key1=value1")
						.build()))
				.build();

		Map<String, String> serverCallbackArgs = new HashMap<String, String>();
		serverCallbackArgs.put("user_id", "${current_user_id}");
		serverCallbackArgs.put("product_id", "${shared_product_id}");


		//상세페이지 접근 리스너
		KakaoLinkService.getInstance().sendDefault(this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
			@Override
			public void onFailure(ErrorResult errorResult) {
				Logger.e(errorResult.toString());
			}
			@Override
			public void onSuccess(KakaoLinkResponse result) {
			}
		});
	}

	@Override		//권한 획득 확인하는 함수
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		for(int i=0;i<permissions.length;i++){
			if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
				Toast.makeText(this, "해당 앱을 사용하려면 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private void setbuttonexe() {
		//"로그인하기"를 누르면 동작하는 함수
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//id와 password가 null이 아니라면,
				if(ed1.getText().toString().trim()!=null &&ed2.getText().toString().trim()!=null && !ed1.getText().toString().trim().equals("") && !ed2.getText().toString().trim().equals("")) {
					NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/member_confirm.php",getApplicationContext());
				ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", ed1.getText().toString().trim())); // 사용자가 입력한 id와 password 내용을 id, pass라는 이름으로 ns에 저장
				ns.add(new BasicNameValuePair("pass", ed2.getText().toString().trim()));
				String a;
				try {
					a = nw.getStringByPOST(ns);	//ns를 POST방식으로 서버에 전달하고, String형태로 결과값을 받는다.
					if(a.equals("1")){			//id와 password가 서버의 유저정보와 일치하다면
						Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
						G.islogin=true;

						spu.put("id", ed1.getText().toString().trim());		//사용자의 id와 password를 spu(임시 데이터)에 저장한다.
						spu.put("password",ed2.getText().toString().trim());

						Intent j=new Intent(getApplicationContext(), Mystory.class);	//Mystory 액티비티로 이동
						startActivity(j);
						finish();
					}
					else if(a.equals("2")){		//등록된 id이지만 password가 다르다면
						Toast.makeText(getApplicationContext(), "패쓰워드가 틀립니다", Toast.LENGTH_SHORT).show();
						
					}
					else if(a.equals("3")){		//등록된 id가 아니라면
						new AlertDialog.Builder(Login.this)		//Dialog창 띄우기
						   .setTitle("id가 가입되어있지 않습니다.")
						   .setMessage("가입하시겠습니까?")
						   .setPositiveButton("예", new DialogInterface.OnClickListener() {
						    
						    public void onClick(DialogInterface dialog, int which) {
						    	startActivity(new Intent(Login.this,Member_join.class));	//Member_join 액티비티로 이동
						    }
						   })
						   
						   .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
						    
						    public void onClick(DialogInterface dialog, int which) {
						    	 dialog.dismiss();
						    }
						   })
						   .show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
				else{		//id나 password를 입력하지 않았다면,
				Toast.makeText(getApplicationContext(), "입력하지않은항목이있거나 잘못된 입력이있습니다", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		tx1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(),Lost_pass.class));
			}
		});
		tx2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			startActivity(new Intent(getApplicationContext(),Member_join.class));	
			}
		});
		
	}

	// 레이아웃에 설정된 뷰들을 변수로 가져옴
	private void setbutton() {
		ed1=(EditText) findViewById(R.id.login_ed1);
		ed2=(EditText) findViewById(R.id.login_ed2);
		btn1=(Button) findViewById(R.id.login_btn1);
		tx1=(TextView) findViewById(R.id.login_tx1);
		tx2=(TextView) findViewById(R.id.login_tx2);

		ed1.setText("a");
		ed2.setText("1");
		
	}
}
