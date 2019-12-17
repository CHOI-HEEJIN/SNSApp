package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Modify_profile extends Activity {
	EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
	ImageView img1;
	RelativeLayout rel1,rel2;
	Button btn1;
	int sex=0;		//0=중립 1=남자 2=여자
	SharedPreferencesUtil spu;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_modify_profile);
			spu=new SharedPreferencesUtil(getApplicationContext());
			setbutton();
			setbuttonexe();
			
			Intent i=getIntent();	//자신을 호출한 액티비티로부터 데이터를 받아온다.
			ed4.setText(i.getStringExtra("name").toString().trim());		//받은 데이터들을 뷰에 세팅
			ed6.setText(i.getStringExtra("birth").toString().trim());
			ed7.setText(i.getStringExtra("profile").toString().trim());
			rel1.setBackgroundColor(Color.GRAY);
			rel2.setBackgroundColor(Color.parseColor("#50ffffff"));
			sex=1;
			if(i.getStringExtra("sex").equals("여")){
				rel2.setBackgroundColor(Color.GRAY);
				rel1.setBackgroundColor(Color.parseColor("#50ffffff"));
				sex=2;
			}
		}

		private void setbuttonexe() {
			rel1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {		//남자 캐릭터를 누름
				rel1.setBackgroundColor(Color.GRAY);
				rel2.setBackgroundColor(Color.parseColor("#50ffffff"));
				sex=1;
				}
			});
			rel2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {		//여자캐릭터를 누름
				rel2.setBackgroundColor(Color.GRAY);
				rel1.setBackgroundColor(Color.parseColor("#50ffffff"));
				sex=2;
				}
			});
			btn1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//모든 칸이 다 작성이 되어있으면
					if(ed4.getText().toString().trim()!=null&&ed6.getText().toString().trim()!=null&&ed7.getText().toString().trim()!=null
							&& !ed4.getText().toString().trim().equals("")&& !ed6.getText().toString().trim().equals("")
							&& !ed7.getText().toString().trim().equals("") && sex!=0 ){
					NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/member_modify.php",getApplicationContext());	//연결할 urㅣ 설정
				ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));	//작성한 데이터를 ns에 저장
				ns.add(new BasicNameValuePair("name", ed4.getText().toString().trim()));
				ns.add(new BasicNameValuePair("birth", ed6.getText().toString().trim()));
				ns.add(new BasicNameValuePair("sex", sex+""));
				ns.add(new BasicNameValuePair("profile", ed7.getText().toString().trim()));

						try {
							String a=nw.getStringByPOST(ns);  //ns를 POST방식으로 서버에 전달하고, String형태로 결과값을 받는다.
							if(a.equals("1")){		//결과값 1을 받으면 수정완료
							Toast.makeText(getApplicationContext(), "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(),Main_profile.class));
							finish();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					else{
						Toast.makeText(getApplicationContext(), "잘못 입력된 부분이있습니다", Toast.LENGTH_SHORT).show();
					}
		}
		});
			
			findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {		//뒤로가기 버튼
				 startActivity(new Intent(getApplicationContext(),Main_profile.class));
					finish();
				}
			});
			
			img1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {		//달력이미지 클릭시
					startActivityForResult(new Intent(getApplicationContext(),Timepick.class),1);
					//TimPick 액티비티를 띄워 값을 받는다.
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
		ed6=(EditText) findViewById(R.id.member_join_ed6);
		ed7=(EditText) findViewById(R.id.member_join_ed7);
		img1=(ImageView) findViewById(R.id.member_join_date);
		rel1=(RelativeLayout) findViewById(R.id.member_join_rel1);
		rel2=(RelativeLayout) findViewById(R.id.member_join_rel2);
		btn1=(Button) findViewById(R.id.member_join_btn_confirm);
			
	}
}

