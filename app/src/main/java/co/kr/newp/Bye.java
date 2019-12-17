package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Bye extends Activity {
Button btn1,btn2;
EditText ed1,ed2;
SharedPreferencesUtil spu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bye);
		ed1=(EditText) findViewById(R.id.ed1);
		ed2=(EditText) findViewById(R.id.ed2);
		btn2=(Button) findViewById(R.id.btn1);
		
		spu=new SharedPreferencesUtil(getApplicationContext());
		
		
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {		//뒤로가기
			@Override
			public void onClick(View v) {
			finish();	
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

			if(ed1.getText().toString().equals(ed2.getText().toString()) && !ed1.equals("") &&ed1.getText().toString().equals(spu.getValue("password", ""))){
				//	비밀번호를 제대로 작성했다면

				NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/user_del.php", getApplicationContext());
				ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));	//유저의 id를 ns에 담는다.
				String s;
				try {
					s = nw.getStringByPOST(ns);		//유저id를 서버에 전달해서 유저와 관련된 데이터를 모두 지우고, 결과값을 받는다.


					Toast.makeText(getApplicationContext(), "정상적으로 탈퇴되었습니다", Toast.LENGTH_SHORT).show();
					spu.put("name", "");		//임시로 담았던 유저 데이터를 지운다.
					spu.put("id", "");
					spu.put("password", "");
					startActivity(new Intent(getApplicationContext(),Login.class));
					finish();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				Toast.makeText(getApplicationContext(), "비밀번호가 같지않습니다", Toast.LENGTH_SHORT).show();
			}
			}
		});
	}
}
