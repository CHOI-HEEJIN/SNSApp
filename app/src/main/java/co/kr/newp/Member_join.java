package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class Member_join extends Activity {
	EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8;
	ImageView img1;
	RelativeLayout rel1,rel2;
	Button btn1;
	int sex=0;		//0=중립 1=남자 2=여자

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_join);
		setbutton();
		setbuttonexe();
	}
	private void setbuttonexe() {
		rel1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	//남자 캐릭터 클릭시 배경을 회색으로 칠함
				rel1.setBackgroundColor(Color.GRAY);
				rel2.setBackgroundColor(Color.parseColor("#50ffffff"));
				sex=1;
			}
		});
		rel2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {	//여자 캐릭터 클릭시 배경을 회색으로 칠함
				rel2.setBackgroundColor(Color.GRAY);
				rel1.setBackgroundColor(Color.parseColor("#50ffffff"));
				sex=2;
			}
		});

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {		//회원가입 버튼 눌렀을 때 동작하는 함수
				if(ed2.getText().toString().trim().equals(ed3.getText().toString().trim())==false){
					//'비밀번호'와 '비밀번호확인'을 다르게 입력하였을 경우
					Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
					return;
				}

				//모든 칸에 내용을 기입한 경우,
				if(ed1.getText().toString().trim()!=null&&ed2.getText().toString().trim()!=null
						&&ed3.getText().toString().trim()!=null &&ed4.getText().toString().trim()!=null
						&&ed5.getText().toString().trim()!=null && ed8.getText().toString().trim()!=null
						&&ed6.getText().toString().trim()!=null&&ed7.getText().toString().trim()!=null
						&& !ed1.getText().toString().trim().equals("") && !ed2.getText().toString().trim().equals("")
						&& !ed3.getText().toString().trim().equals("")&& !ed4.getText().toString().trim().equals("")
						&& !ed5.getText().toString().trim().equals("") &&!ed8.getText().toString().trim().equals("")
						&& !ed6.getText().toString().trim().equals("") && !ed7.getText().toString().trim().equals("") && sex!=0  ){
					NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/member_insert.php",getApplicationContext());
					//연결할 url 선언

					ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();

					//기입한 정보를 'ns'에 저장한다.
					ns.add(new BasicNameValuePair("id", ed1.getText().toString().trim()));
					ns.add(new BasicNameValuePair("password", ed2.getText().toString().trim()));
					ns.add(new BasicNameValuePair("name", ed4.getText().toString().trim()));
					ns.add(new BasicNameValuePair("email", ed5.getText().toString().trim()));
					ns.add(new BasicNameValuePair("phone_num", ed8.getText().toString().trim()));
					ns.add(new BasicNameValuePair("birth", ed6.getText().toString().trim()));
					ns.add(new BasicNameValuePair("sex", sex+""));
					ns.add(new BasicNameValuePair("profile", ed7.getText().toString().trim()));

					try {
						String a=nw.getStringByPOST(ns);	//ns를 POST방식으로 서버에 전달하고, String형태로 결과값을 받는다.
						if("1".equals(a)){		//결과값이 1이면 가입완료
							Toast.makeText(getApplicationContext(), "가입 완료 되었습니다.", Toast.LENGTH_SHORT).show();
							finish();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else{
					Toast.makeText(getApplicationContext(), "잘못 입력 되었습니다.", Toast.LENGTH_SHORT).show();
				}

			}
		});


		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {		//뒤로가기 버튼
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		img1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	//달력버튼 클릭시
				startActivityForResult(new Intent(getApplicationContext(),Timepick.class),1);
				//TimPick 액티비티를 띄워 값을 받는다.

				/*startActivityForResult() : 단순히 액티비티를 시작하는 startActivity()와는 다르게
				"시작할 액티비티를 통해 어떠한 값을 받을 것을 기대하고 액티비티를 시작"하는 메소드
				*/
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==1){
			String x=data.getStringExtra("content");	//Timepick에서 받은 데이터를 변수 x에 저장한다.
			ed6.setText(x);		//뷰 member_join_ed6(생년월일 칸)에 x의 내용을 입력한다.
		}

	}
	// 레이아웃에 설정된 뷰들을 변수로 가져옴
	private void setbutton() {
		ed1=(EditText) findViewById(R.id.member_join_ed1);
		ed2=(EditText) findViewById(R.id.member_join_ed2);
		ed3=(EditText) findViewById(R.id.member_join_ed3);
		ed4=(EditText) findViewById(R.id.member_join_ed4);
		ed5=(EditText) findViewById(R.id.member_join_ed5);
		ed8=(EditText) findViewById(R.id.member_join_ed8);
		ed6=(EditText) findViewById(R.id.member_join_ed6);
		ed7=(EditText) findViewById(R.id.member_join_ed7);
		img1=(ImageView) findViewById(R.id.member_join_date);
		rel1=(RelativeLayout) findViewById(R.id.member_join_rel1);
		rel2=(RelativeLayout) findViewById(R.id.member_join_rel2);
		btn1=(Button) findViewById(R.id.member_join_btn_confirm);

	}
}