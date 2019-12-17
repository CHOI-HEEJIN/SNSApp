package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lost_pass extends Activity {
EditText ed1,ed2,ed3,ed4,ed5;
Button btn1,btn2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_pass);
		setbutton();
		setbuttonexe();
	}

	private void setbuttonexe() {
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {		//"아이디찾기"버튼 클릭하면 동작하는 함수
				//이름, 이메일 모두 입력했을때
				if(ed1.getText()!=null&&ed2.getText()!=null &&!ed1.getText().toString().trim().equals("")&&!ed1.getText().toString().trim().equals("")){
				NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/lost_id.php"	, getApplicationContext());	//연결할 url 설정
				ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("name", ed1.getText().toString().trim()));	//입력한 데이터를 ns에 저장
				ns.add(new BasicNameValuePair("email", ed2.getText().toString().trim()));
				try {
					String a=nw.getStringByPOST(ns);	//ns를 POST방식으로 서버에 전달하고, String형태로 결과값을 받는다.

					new AlertDialog.Builder(Lost_pass.this)		//Dialog창을 띄워서 ID를 알려준다.
					   .setTitle("확인")
					   .setMessage("분실한 ID는   "+a+ "  입니다.")

					   .setNegativeButton("확인", new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int which) {
					    	 dialog.dismiss();
					    }
					   })
					   .show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			}
		});
btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {		//"비밀번호찾기" 버튼 클릭하면 동작하는 함수
				//id, 이름, 이메일 모두 입력했을 경우,
			if(ed3.getText()!=null&&ed4.getText()!=null&&ed5.getText()!=null  &&!ed3.getText().toString().trim().equals("")
					&&!ed4.getText().toString().trim().equals("")&&!ed5.getText().toString().trim().equals("")){
			NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/lost_pass.php"	, getApplicationContext());	//연결할 url 설정
			ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("id", ed3.getText().toString().trim()));		//입력한 데이터를 ns에 저장
			ns.add(new BasicNameValuePair("name", ed4.getText().toString().trim()));
			ns.add(new BasicNameValuePair("email", ed5.getText().toString().trim()));
			try {
				String a=nw.getStringByPOST(ns);		//ns를 POST방식으로 서버에 전달하고, String형태로 결과값을 받는다.
				new AlertDialog.Builder(Lost_pass.this)		//Dialog창을 띄워서 비밀번호를 알려준다.
				   .setTitle("확인")
				   .setMessage("분실한 비밀번호는   "+a+ "  입니다.")
				   .setNegativeButton("확인", new DialogInterface.OnClickListener() {
				    
				    public void onClick(DialogInterface dialog, int which) {
				    	 dialog.dismiss();
				    }
				   })
				   .show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			}
		});
	}
	// 레이아웃에 설정된 뷰들을 변수로 가져옴
	private void setbutton() {
		 ed1=(EditText) findViewById(R.id.lost_pass_ed1);
		 ed2=(EditText) findViewById(R.id.lost_pass_ed2);
		 ed3=(EditText) findViewById(R.id.lost_pass_ed3);
		 ed4=(EditText) findViewById(R.id.lost_pass_ed4);
		 ed5=(EditText) findViewById(R.id.lost_pass_ed5);
		 btn1=(Button) findViewById(R.id.lost_pass_btn1);
		 btn2=(Button) findViewById(R.id.lost_pass_btn2);
		
	}
	

}
